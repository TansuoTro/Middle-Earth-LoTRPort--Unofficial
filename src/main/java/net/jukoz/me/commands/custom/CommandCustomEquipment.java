package net.jukoz.me.commands.custom;
import net.jukoz.me.compat.NbtCompat;
import net.jukoz.me.compat.METext;

import net.jukoz.me.item.MEComponents;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllBackAttachmentsSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllHelmetAttachmentsSuggestionProvider;
import net.jukoz.me.item.dataComponents.BackAttachmentDataComponent;
import net.jukoz.me.item.dataComponents.HelmetAttachmentDataComponent;
import net.jukoz.me.item.items.armor.BackAttachmentItem;
import net.jukoz.me.item.items.armor.CustomChestplateItem;
import net.jukoz.me.item.items.armor.CustomHelmetItem;
import net.jukoz.me.item.items.armor.HelmetAttachmentItem;
import net.jukoz.me.item.utils.armor.backAttachments.BackAttachmentsME;
import net.jukoz.me.item.utils.armor.helmetAttachments.HelmetAttachmentsStatesME;
import net.jukoz.me.item.utils.armor.helmetAttachments.HelmetAttachmentsME;
import net.jukoz.me.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.Objects;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandCustomEquipment {
    private static final String EQUIPMENT = "equipment";
    private static final String BACK_ATTACHMENT = "back_attachment";
    private static final String HELMET_ATTACHMENT = "helmet_attachment";
    private static final String BACK_ATTACHMENT_VALUE = "back_attachment_value";
    private static final String HELMENT_ATTACHMENT_VALUE = "helmet_attachment_value";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal(EQUIPMENT)
                    .then(literal(BACK_ATTACHMENT)
                        .then(argument(BACK_ATTACHMENT_VALUE, StringArgumentType.string())
                                .suggests(new AllBackAttachmentsSuggestionProvider())
                                .executes(CommandCustomEquipment::setBackAttachment)))));

        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))
                .then(literal(EQUIPMENT)
                    .then(literal(HELMET_ATTACHMENT)
                        .then(argument(HELMENT_ATTACHMENT_VALUE, StringArgumentType.string())
                                .suggests(new AllHelmetAttachmentsSuggestionProvider())
                                .executes(CommandCustomEquipment::setHelmetAttachment)))));
    }

    private static int setBackAttachment(CommandContext<ServerCommandSource> context) {
        BackAttachmentsME backAttachment = BackAttachmentsME.valueOf(StringArgumentType.getString(context, BACK_ATTACHMENT_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getMainHandStack();

        if (handStack.isEmpty()){
            MutableText sourceText = Text.translatable("command.%s.back_attachment.hand_empty".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomChestplateItem || handStack.getItem() instanceof BackAttachmentItem)){
            MEComponents.set(handStack, MEComponents.BACK_ATTACHMENT_DATA, BackAttachmentDataComponent.newBackAttachment(backAttachment));
            MutableText sourceText = Text.translatable("command.%s.back_attachment.success".formatted(MiddleEarth.MOD_ID)).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + backAttachment.getName()));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableText sourceText = Text.translatable("command.%s.back_attachment.wrong_item".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }
    }

    private static int setHelmetAttachment(CommandContext<ServerCommandSource> context) {
        HelmetAttachmentsME helmetAttachment = HelmetAttachmentsME.valueOf(StringArgumentType.getString(context, HELMENT_ATTACHMENT_VALUE).toUpperCase());

        ItemStack handStack = Objects.requireNonNull(context.getSource().getPlayer()).getInventory().getMainHandStack();

        if (handStack.isEmpty()){
            MutableText sourceText = Text.translatable("command.%s.helmet_attachment.hand_empty".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }

        if ((handStack.getItem() instanceof CustomHelmetItem || handStack.getItem() instanceof HelmetAttachmentItem)){
            if (helmetAttachment.getConstantState() == HelmetAttachmentsStatesME.DOWN){
                MEComponents.set(handStack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(true, helmetAttachment, DyedColorComponent.DEFAULT_COLOR));
            } else if (helmetAttachment.getConstantState() == HelmetAttachmentsStatesME.UP || helmetAttachment.getConstantState() == null){
                MEComponents.set(handStack, MEComponents.HELMET_ATTACHMENT_DATA, new HelmetAttachmentDataComponent(false, helmetAttachment, DyedColorComponent.DEFAULT_COLOR));
            }
            MutableText sourceText = Text.translatable("command.%s.helmet_attachment.success".formatted(MiddleEarth.MOD_ID)).append(Text.translatable("tooltip." + MiddleEarth.MOD_ID + "." + helmetAttachment.getName()));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            return 0;
        } else {
            MutableText sourceText = Text.translatable("command.%s.helmet_attachment.wrong_item".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }
    }
}
