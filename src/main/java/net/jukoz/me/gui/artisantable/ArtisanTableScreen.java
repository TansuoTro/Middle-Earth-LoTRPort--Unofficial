package net.jukoz.me.gui.artisantable;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.EquipmentItemsME;
import net.jukoz.me.item.ToolItemsME;
import net.jukoz.me.item.WeaponItemsME;
import net.jukoz.me.network.packets.C2S.ArtisanTableTabPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArtisanTableScreen extends HandledScreen<ArtisanTableScreenHandler> implements ScreenHandlerListener {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/gui/artisan_table.png");
    public static final int SLOT_SCALE = 18;
    public static final int SLOT_OFFSET_X = 12;
    public static final int SLOT_OFFSET_Y = 15;
    public static final int SPRITE_OFFSET_X = SLOT_OFFSET_X + 1;
    public static final int SPRITE_OFFSET_Y = SLOT_OFFSET_Y + 1;

    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;

    private static final Quaternionf ARMOR_STAND_ROTATION = new Quaternionf().rotationXYZ(0.43633232f, 0.0f, (float) Math.PI);

    private ArmorStandEntity armorStand;
    private final List<ArtisanTableTab> categories = new ArrayList<>();
    private final HashMap<Integer, List<ArtisanTableTab>> tabs = new HashMap<>();
    @Nullable
    private ArtisanTableTab selectedCategory;
    private ArtisanTableTab selectedTab;

    public ArtisanTableScreen(ArtisanTableScreenHandler handler, PlayerInventory inventory, Component title) {
        super(handler, inventory, title);
        this.backgroundWidth = 232;
        this.backgroundHeight = 166;
        handler.setContentsChangedListener(this::onInventoryChange);

        int index = 0;
        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("weapons"), WeaponItemsME.GONDORIAN_NOBLE_LONGSWORD.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("sword"), WeaponItemsME.STEEL_SWORD.getDefaultStack(), ArtisanTableInputsShape.SWORD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("axe"), ToolItemsME.STEEL_AXE.getDefaultStack(), ArtisanTableInputsShape.AXE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("spear"), WeaponItemsME.STEEL_SPEAR.getDefaultStack(), ArtisanTableInputsShape.SPEAR));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("bow"), WeaponItemsME.GONDORIAN_BOW.getDefaultStack(), ArtisanTableInputsShape.BOW));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 4, getTabTranslation("crossbow"), Items.CROSSBOW.getDefaultStack(), ArtisanTableInputsShape.CROSSBOW));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("tools"), ToolItemsME.MITHRIL_PICKAXE.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("pickaxe"), ToolItemsME.STEEL_PICKAXE.getDefaultStack(), ArtisanTableInputsShape.PICKAXE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("shovel"), ToolItemsME.STEEL_SHOVEL.getDefaultStack(), ArtisanTableInputsShape.SHOVEL));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("hoe"), ToolItemsME.STEEL_HOE.getDefaultStack(), ArtisanTableInputsShape.HOE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("chisel"), ToolItemsME.STEEL_CHISEL.getDefaultStack(), ArtisanTableInputsShape.CHISEL));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("armors"), EquipmentItemsME.GONDORIAN_FOUNTAIN_GUARD_CHESTPLATE.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("helmet"), EquipmentItemsME.RAVENHILL_WATCHWARDEN_HELMET.getDefaultStack(), ArtisanTableInputsShape.HELMET));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("chestplate"), EquipmentItemsME.RAVENHILL_WATCHWARDEN_CHESTPLATE.getDefaultStack(), ArtisanTableInputsShape.CHESTPLATE));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("leggings"), EquipmentItemsME.RAVENHILL_WATCHWARDEN_LEGGINGS.getDefaultStack(), ArtisanTableInputsShape.LEGGINGS));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("boots"), EquipmentItemsME.RAVENHILL_WATCHWARDEN_BOOTS.getDefaultStack(), ArtisanTableInputsShape.BOOTS));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 4, getTabTranslation("mount_armor"), EquipmentItemsME.ROHIRRIC_HORSE_ARMOR.getDefaultStack(), ArtisanTableInputsShape.MOUNT_ARMOR));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("shields"), Items.SHIELD.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("light_shield"), WeaponItemsME.GUNDABAD_WOODEN_SHIELD.getDefaultStack(), ArtisanTableInputsShape.LIGHT_SHIELD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("medium_shield"), WeaponItemsME.ROUND_SHIELD.getDefaultStack(), ArtisanTableInputsShape.MEDIUM_SHIELD));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("heavy_shield"), WeaponItemsME.URUK_HAI_WHITE_HAND_SHIELD.getDefaultStack(), ArtisanTableInputsShape.HEAVY_SHIELD));
        index++;

        categories.add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.LEFT, index, getTabTranslation("misc"), EquipmentItemsME.STRAW_HAT.getDefaultStack()));
        tabs.put(index, new ArrayList<>());
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 0, getTabTranslation("hat"), EquipmentItemsME.STRAW_HAT.getDefaultStack(), ArtisanTableInputsShape.HAT));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 1, getTabTranslation("helmet_attachment"), EquipmentItemsME.HOOD.getDefaultStack(), ArtisanTableInputsShape.HELMET_ATTACHMENT));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 2, getTabTranslation("back_attachment"), EquipmentItemsME.CAPE.getDefaultStack(), ArtisanTableInputsShape.BACK_ATTACHMENT));
        tabs.get(index).add(new ArtisanTableTab(this.client, this, ArtisanTableTabType.ABOVE, 3, getTabTranslation("pipe"), ToolItemsME.CLAY_PIPE.getDefaultStack(), ArtisanTableInputsShape.PIPE));
        index++;

        selectedCategory = categories.get(0);
        selectedTab = tabs.get(selectedCategory.getIndex()).get(0);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        titleY -= 1;

        (this.handler).addListener(this);
        changeTab();

        this.armorStand = new ArmorStandEntity(this.client.world, 0.0, 0.0, 0.0);
        this.armorStand.setHideBasePlate(true);
        this.armorStand.setShowArms(true);
        this.armorStand.bodyYaw = 210.0f;
        this.armorStand.setPitch(25.0f);
        this.armorStand.headYaw = this.armorStand.getYaw();
        this.equipArmorStand((this.handler).getSlot(9).getStack());
    }

    @Override
    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
        if (slotId == 9) {
            this.equipArmorStand(stack);
        }
    }

    @Override
    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
    }

    private boolean canCraft() {
        return !handler.getAvailableOutputs().isEmpty();
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        CraftingResultInventory resultInventory = this.handler.output;
        if (resultInventory != null && !resultInventory.getStack(0).isEmpty()) {
            equipArmorStand(resultInventory.getStack(0));
        }
    }

    public void equipArmorStand(ItemStack stack) {
        if (this.armorStand == null) {
            return;
        }
        for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
            this.armorStand.equipStack(equipmentSlot, ItemStack.EMPTY);
        }
        if (!stack.isEmpty()) {
            EquipmentSlot equipmentSlot = LivingEntity.getPreferredEquipmentSlot(stack.copy());
            this.armorStand.equipStack(equipmentSlot, stack.copy());
        }
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        int k = (int) (41.0F * this.scrollAmount);
        context.drawTexture(TEXTURE, i + 143, j + 15 + k, 232 + (this.shouldScroll() ? 0 : 12), 0, 12, 15);

        int l = this.x + 76;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;

        ArtisanTableInputsShape shape = selectedTab.getInputShape();
        for (int w = 0; w < 3; w++) {
            for (int z = 0; z < 3; z++) {
                if (handler.getSlot(w * 3 + z).isEnabled()) {
                    context.drawTexture(TEXTURE, x + SLOT_OFFSET_X + SLOT_SCALE * z, y + SLOT_OFFSET_Y + SLOT_SCALE * w, 232, 15, SLOT_SCALE, SLOT_SCALE);
                    InputType inputType = shape.getInputType(z, w);
                    if (!handler.getSlot(w * 3 + z).hasStack()) {
                        switch (inputType) {
                            case HANDLE -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 87, 16, 16);
                            case HILT -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 103, 16, 16);
                            case BLADE -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 119, 16, 16);
                            case AXE -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 135, 16, 16);
                            case PICKAXE -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 151, 16, 16);
                            case SHOVEL -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 167, 16, 16);
                            case HOE -> context.drawTexture(TEXTURE, x + SPRITE_OFFSET_X + SLOT_SCALE * z, y + SPRITE_OFFSET_Y + SLOT_SCALE * w, 232, 183, 16, 16);
                        }
                    }
                }
            }
        }

        this.renderRecipeBackground(context, mouseX, mouseY, l, m, n);
        this.renderRecipeIcons(context, l, m, n);
        InventoryScreen.drawEntity(context, this.x + 206, this.y + 128, 28, ARMOR_STAND_ROTATION, (Quaternionf) null, this.armorStand);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);

        super.render(context, mouseX, mouseY, delta);

        for (ArtisanTableTab category : this.categories) {
            category.drawBackground(context, x, y + 3, category == this.selectedCategory);
            category.drawIcon(context, x, y + 3);
        }
        for (ArtisanTableTab tab : this.tabs.get(selectedCategory.getIndex())) {
            tab.drawBackground(context, x + 3, y, tab == this.selectedTab);
            tab.drawIcon(context, x + 3, y);
        }

        for (ArtisanTableTab category : this.categories) {
            if (category.isClickOnTab(x, y + 3, mouseX, mouseY)) continue;
            context.drawTooltip(this.textRenderer, category.getTitle(), mouseX, mouseY);
        }
        for (ArtisanTableTab tab : this.tabs.get(selectedCategory.getIndex())) {
            if (tab.isClickOnTab(x + 3, y, mouseX, mouseY)) continue;
            context.drawTooltip(this.textRenderer, tab.getTitle(), mouseX, mouseY);
        }
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        if (canCraft()) {
            int i = this.x + 76;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;
            List<ItemStack> list = (this.handler).getAvailableOutputs();

            for (int l = this.scrollOffset; l < k && l < (this.handler).getAvailableOutputs().size(); ++l) {
                int m = l - this.scrollOffset;
                int n = i + m % 4 * 16;
                int o = j + m / 4 * 18 + 2;
                if (x >= n && x < n + 16 && y >= o && y < o + 18) {
                    context.drawItemTooltip(this.textRenderer, list.get(l), x, y);
                }
            }
        }
    }

    private void renderRecipeBackground(DrawContext context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < ((ArtisanTableScreenHandler) this.handler).getAvailableOutputs().size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = this.backgroundHeight;
            if (i == (this.handler).getSelectedRecipe()) {
                n += 18;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 36;
            }

            context.drawTexture(TEXTURE, k, m - 1, 0, n, 16, 18);
        }
    }

    private void renderRecipeIcons(DrawContext context, int x, int y, int scrollOffset) {
        List<ItemStack> list = this.handler.getAvailableOutputs();
        for (int i = this.scrollOffset; i < scrollOffset && i < (this.handler).getAvailableOutputs().size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(list.get(i), k, m);
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        this.mouseClicked = false;
        if (button == 0) {
            for (ArtisanTableTab category : this.categories) {
                if (category.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.selectedCategory = category;
                selectedTab = tabs.get(selectedCategory.getIndex()).get(0);
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                changeTab();
                break;
            }
            for (ArtisanTableTab tab : tabs.get(selectedCategory.getIndex())) {
                if (tab.isClickOnTab(x, y, mouseX, mouseY)) continue;
                this.selectedTab = tab;
                MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                changeTab();
                break;
            }
        }
        if (canCraft()) {
            int i = this.x + 76;
            int j = this.y + 14;
            int k = this.scrollOffset + 12;

            for (int l = this.scrollOffset; l < k; ++l) {
                int m = l - this.scrollOffset;
                double d = mouseX - (double) (i + m % 4 * 16);
                double e = mouseY - (double) (j + m / 4 * 18);
                if (d >= 0.0 && e >= 0.0 && d < 16.0 && e < 18.0 && (this.handler).onButtonClick(this.client.player, l)) {
                    MinecraftClient.getInstance().getSoundManager().play(PositionedSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                    this.handler.setSelectedRecipe(l);
                    return true;
                }
            }

            i = this.x + 119;
            j = this.y + 9;
            if (mouseX >= (double) i && mouseX < (double) (i + 12) && mouseY >= (double) j && mouseY < (double) (j + 54)) {
                this.mouseClicked = true;
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.mouseClicked && this.shouldScroll()) {
            int i = this.y + 14;
            int j = i + 54;
            this.scrollAmount = ((float) mouseY - (float) i - 7.5F) / ((float) (j - i) - 15.0F);
            this.scrollAmount = Mth.clamp(this.scrollAmount, 0.0F, 1.0F);
            this.scrollOffset = (int) ((double) (this.scrollAmount * (float) this.getMaxScroll()) + 0.5) * 4;
            return true;
        } else {
            return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        }
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.shouldScroll()) {
            int i = this.getMaxScroll();
            float f = (float) amount / (float) i;
            this.scrollAmount = Mth.clamp(this.scrollAmount - f, 0.0F, 1.0F);
            this.scrollOffset = (int) ((double) (this.scrollAmount * (float) i) + 0.5) * 4;
        }

        return true;
    }

    private boolean shouldScroll() {
        return canCraft() && this.handler.getAvailableOutputs().size() > 12;
    }

    protected int getMaxScroll() {
        return (this.handler.getAvailableOutputs().size() + 4 - 1) / 4 - 3;
    }

    private void onInventoryChange() {
        if (!canCraft()) {
            this.scrollAmount = 0.0F;
            this.scrollOffset = 0;
        }
    }

    private void changeTab() {
        handler.changeTab(selectedTab.getInputShape().getId());
        ClientPlayNetworking.send(new ArtisanTableTabPacket(selectedTab.getInputShape().getId(), handler.syncId));
    }

    private static Component getTabTranslation(String tab) {
        return Component.translatable("screen." + MiddleEarth.MOD_ID + ".artisan_table." + tab);
    }
}
