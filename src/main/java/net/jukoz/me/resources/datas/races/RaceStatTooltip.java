package net.jukoz.me.resources.datas.races;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.attributes.AttributeModifierElement;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceKey;

public class RaceStatTooltip {
    private static final String betterSign = "▲";
    private static final String equalSign = "=";
    private static final String worstSign = "▼";
    private static final String removedSign = "Х";
    private static final String additionSign = "+";
    private static final String continuationSign = "▶";
    private static final String listStart = "●";
    private static final String modifierSpacing = "   ";

    private static List<Component> tooltipText;

    public static void draw(Race race, LivingEntity entity, GuiGraphics context, Font renderer, int x, int y, List<AttributePoolElement> playerAttributes, boolean detailed){
        tooltipText = new ArrayList<>();
        addRaceName(race);
        addAttributeListHeader();

        boolean hasAttribute = false;
        var registry = entity.getWorld().getRegistryManager().getOptional(Registries.ATTRIBUTE).get();
        List<AttributePoolElement> nextBoundAttributes = race.getBaseAttributePool().getPool();
        for (AttributePoolElement currentEntityAttribute : playerAttributes){
            hasAttribute = addAttributeLine(entity, registry, currentEntityAttribute, nextBoundAttributes, detailed) || hasAttribute;
        }
        if(!hasAttribute){
            addNoAttributeChanges();
        }

        context.drawTooltip(renderer, tooltipText, x, y);
    }

    private static void addRaceName(Race race) {
        tooltipText.add(race.getFullName().formatted(ChatFormatting.BOLD).formatted(ChatFormatting.WHITE));
    }

    private static void addAttributeListHeader() {
        tooltipText.add(Component.translatable(MiddleEarth.of("attribute_header").toTranslationKey("race_tooltip")).formatted(ChatFormatting.GRAY));
    }

    private static void addNoAttributeChanges() {
        tooltipText.add(Component.translatable(MiddleEarth.of("no_attribute_change").toTranslationKey("race_tooltip")).formatted(ChatFormatting.DARK_GRAY));
    }

    private static boolean addAttributeLine(LivingEntity entity, Registry<Attribute> registry, AttributePoolElement currentEntityAttribute, List<AttributePoolElement> nextBoundAttributes, boolean detailed) {
        ResourceLocation attributeId = currentEntityAttribute.getIdentifier();
        boolean buffIsReversed = registry.getEntry(net.minecraft.resources.ResourceKey.of(net.minecraft.core.registries.Registries.ATTRIBUTE, attributeId)).get().isIn(TagKey.of(Registries.ATTRIBUTE, MiddleEarth.of("is_buff_reversed")));

        AttributePoolElement nextAttribute = nextBoundAttributes.stream().filter(attribute -> attributeId.equals(attribute.getIdentifier())).findFirst().orElse(null);

        double currentValue = round(currentEntityAttribute.getValue());
        double defaultValue = round(AttributePool.getDefaultAttributeValue(attributeId, entity));

        double expectedNextValue = nextAttribute == null ? defaultValue : nextAttribute.getValue();

        // Compare modifiers
        List<Component> modifierTexts = new ArrayList<>();

        List<AttributeModifierElement> currentModifiers = filterAttributeModifiers(currentEntityAttribute);
        List<AttributeModifierElement> nextBoundModifiers = filterAttributeModifiers(nextAttribute);

        if(!currentModifiers.isEmpty() || !nextBoundModifiers.isEmpty()){
            List<AttributeModifierElement> removedModifiersList = new ArrayList<>();
            List<AttributeModifierElement> willFollowInNextModifiersList = new ArrayList<>();

            List<AttributeModifierElement> unchangedModifierList = new ArrayList<>();
            List<AttributeModifierElement> modifiedModifierList = new ArrayList<>();
            List<AttributeModifierElement> addedModifiersList = new ArrayList<>();

            /// Compute the current value following the modifiers
            double currentTotal = currentValue;
            for(AttributeModifierElement currentModifier : currentModifiers){
                boolean willFollow = verifyIfModifierExistInList(currentModifier, nextBoundModifiers);
                double modifierValue = currentModifier.getValue();
                currentTotal += switch (currentModifier.getOperation()){
                    case ADDITION -> modifierValue;
                    case MULTIPLY_BASE -> round(currentValue * modifierValue);
                    case MULTIPLY_TOTAL -> round(currentTotal * modifierValue);
                };

                if(!willFollow){
                    removedModifiersList.add(currentModifier);
                } else {
                    willFollowInNextModifiersList.add(currentModifier);
                }
            }
            currentValue = currentTotal;

            /// Compute the future value based on the bound modifiers
            if(nextAttribute != null){
                double nextTotal = nextAttribute.getValue();
                for(AttributeModifierElement nextModifier : nextBoundModifiers){
                    AttributeModifierElement linkedCurrentModifier = willFollowInNextModifiersList.stream().filter(x -> x.getIdentifier().equals(nextModifier.getIdentifier())).findAny().orElse(null);
                    if(linkedCurrentModifier != null){
                        if(linkedCurrentModifier.getValue() == nextModifier.getValue()){
                            unchangedModifierList.add(linkedCurrentModifier);
                        } else if(linkedCurrentModifier.getValue() != nextModifier.getValue()){
                            modifiedModifierList.add(linkedCurrentModifier);
                        }
                    } else {
                        addedModifiersList.add(nextModifier);
                    }
                    double modifierValue = nextModifier.getValue();
                    nextTotal += switch (nextModifier.getOperation()){
                        case ADDITION -> modifierValue;
                        case MULTIPLY_BASE -> round( nextAttribute.getValue() * modifierValue);
                        case MULTIPLY_TOTAL -> round(nextTotal * modifierValue);
                    };
                }
                expectedNextValue = nextTotal;
            }

            for (var modifiedModifier : modifiedModifierList){
                String sign = betterSign;
                ChatFormatting formatting = ChatFormatting.GREEN;

                AttributeModifierElement nextModifier = nextBoundModifiers.stream().filter(x -> x.getIdentifier().equals(modifiedModifier.getIdentifier())).findAny().orElse(null);
                if(modifiedModifier.getValue() > nextModifier.getValue()){
                    sign = worstSign;
                    formatting = ChatFormatting.RED;
                }

                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).formatted(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + sign + " ").formatted(formatting));
                modifierLine.append(Component.translatable("attribute.modifiers." + modifiedModifier.getIdentifier().getPath()).formatted(ChatFormatting.GRAY));
                modifierLine.append(Component.literal(" ["+ modifiedModifier.getValue() + " " + continuationSign + " " + nextModifier.getValue() + "] ").formatted(ChatFormatting.WHITE));
                double difference = nextModifier.getValue() - modifiedModifier.getValue();
                String differencePrefix = (difference > 0) ? "+" : "";
                modifierLine.append(Component.literal("(" + differencePrefix + round(difference) + ")").formatted(ChatFormatting.GRAY));
                modifierTexts.add(modifierLine);
            }

            for (var addedModifier : addedModifiersList){
                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).formatted(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + additionSign + " ").formatted(ChatFormatting.GREEN));
                modifierLine.append(Component.translatable("attribute.modifiers." + addedModifier.getIdentifier().getPath()).formatted(ChatFormatting.GRAY));
                modifierLine.append(Component.literal(" ["+ addedModifier.getValue() + "] ").formatted(ChatFormatting.WHITE));
                modifierTexts.add(modifierLine);
            }

            for (var unchangedModifier : unchangedModifierList){
                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).formatted(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + equalSign + " ").formatted(ChatFormatting.GRAY));
                modifierLine.append(Component.translatable("attribute.modifiers." + unchangedModifier.getIdentifier().getPath()).formatted(ChatFormatting.GRAY));
                modifierLine.append(Component.literal(" ["+ unchangedModifier.getValue() + " " + continuationSign + " " + unchangedModifier.getValue() + "] ").formatted(ChatFormatting.WHITE));
                modifierTexts.add(modifierLine);
            }

            for (var removedModifier : removedModifiersList){
                MutableComponent modifierLine = Component.literal(modifierSpacing + listStart).formatted(ChatFormatting.GOLD);
                modifierLine.append(Component.literal(" " + removedSign + " ").formatted(ChatFormatting.DARK_GRAY));
                modifierLine.append(Component.translatable("attribute.modifiers." + removedModifier.getIdentifier().getPath()).formatted(ChatFormatting.DARK_GRAY));
                modifierLine.append(Component.literal(" ["+ removedModifier.getValue() + "] ").formatted(ChatFormatting.WHITE));
                modifierTexts.add(modifierLine);
            }
        }

        // The new race does not include that attribute
        ChatFormatting signFormatting = ChatFormatting.GRAY;
        ChatFormatting textFormatting = ChatFormatting.GRAY;
        String sign = equalSign;
        if(nextAttribute == null) {
            if(defaultValue == currentValue){
                return false;
            }
            else if(defaultValue == 0){
                if(buffIsReversed){
                    sign = additionSign;
                    signFormatting = ChatFormatting.YELLOW;
                    textFormatting = ChatFormatting.YELLOW;
                } else {
                    sign = removedSign;
                    signFormatting = ChatFormatting.DARK_GRAY;
                    textFormatting = ChatFormatting.DARK_GRAY;
                }
            } else if(defaultValue > currentValue){
                if(buffIsReversed){
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                } else {
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                }
            } else if(defaultValue < currentValue){
                if(buffIsReversed){
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                } else {
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                }
            }
        } else {
            if(expectedNextValue < currentValue){
                if(buffIsReversed){
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                } else {
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                }
            } else if(expectedNextValue > currentValue){
                if(buffIsReversed){
                    sign = worstSign;
                    signFormatting = ChatFormatting.RED;
                    textFormatting = ChatFormatting.RED;
                } else {
                    sign = betterSign;
                    signFormatting = ChatFormatting.GREEN;
                    textFormatting = ChatFormatting.GREEN;
                }
            }
        }

        MutableComponent newCustomLine = Component.literal(sign).formatted(signFormatting);
        newCustomLine.append(Component.literal(" "));
        newCustomLine.append(Component.translatable("attribute.name." + attributeId.getPath()).formatted(textFormatting));
        if(!modifierTexts.isEmpty()){
            newCustomLine.append(Component.literal("*").formatted(ChatFormatting.GOLD));
        }
        if(!detailed){
            tooltipText.add(newCustomLine);
        } else {
            double difference;
            if(nextAttribute == null){
                newCustomLine.append(Component.literal(" "));
                newCustomLine.append(Component.literal("["+ currentValue +" " + continuationSign + " "+ defaultValue +"]").formatted(ChatFormatting.WHITE));
                difference = defaultValue - currentValue;
            } else {
                newCustomLine.append(Component.literal(" "));
                double newValue = round(expectedNextValue);
                newCustomLine.append(Component.literal("[" + currentValue +" " + continuationSign + " "+ newValue +"]").formatted(ChatFormatting.WHITE));
                difference = newValue - currentValue;
            }
            newCustomLine.append(Component.literal(" "));
            String differencePrefix = (difference > 0) ? "+" : "";

            newCustomLine.append(Component.literal("(" + differencePrefix + round(difference) + ")").formatted(ChatFormatting.GRAY));

            tooltipText.add(newCustomLine);
            tooltipText.addAll(modifierTexts);
        }
        return true;
    }

    private static boolean verifyIfModifierExistInList(AttributeModifierElement currentModifier, List<AttributeModifierElement> nextBoundModifiers) {
        if(nextBoundModifiers == null || nextBoundModifiers.isEmpty()){
            return false;
        }
        return nextBoundModifiers.stream().anyMatch(x -> x.getIdentifier().equals(currentModifier.getIdentifier()));
    }

    private static List<AttributeModifierElement> filterAttributeModifiers(AttributePoolElement attributePoolElement) {
        if(attributePoolElement == null)
            return new ArrayList<>();
        return attributePoolElement.getModifiers().stream().filter(modifier -> !modifier.getIdentifier().getPath().contains("creative")).toList();
    }

    private static double round(double value){
        return Math.round(value * 1000) / 1000.0;
    }
}
