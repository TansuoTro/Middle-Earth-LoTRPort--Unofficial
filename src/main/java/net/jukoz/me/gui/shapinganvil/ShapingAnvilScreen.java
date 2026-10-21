package net.jukoz.me.gui.shapinganvil;

import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.packets.C2S.AnvilIndexPacket;
import java.util.ArrayList;
import java.util.List;

public class ShapingAnvilScreen extends AbstractContainerScreen<ShapingAnvilScreenHandler> {
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath( "textures", "gui", "shaping_anvil.png");

    private float scrollAmount;
    private boolean mouseClicked;
    private int scrollOffset;

    private List<ItemStack> outputs;

    public ShapingAnvilScreen(ShapingAnvilScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        outputs = new ArrayList<>();
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - font.getWidth(title)) / 2;

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        BlockPos pos = this.handler.getPos();
        AnvilIndexPacket newPacket = new AnvilIndexPacket(-1, pos.getX(), pos.getY(), pos.getZ());
        ClientPlayNetworking.send(newPacket);
    }

    public void addRecipe(int index, ItemStack output) {
        boolean exists = outputs.stream().contains(item -> output.getItem().equals(item.getItem()));
        if(exists) return;

        while (outputs.size() < index) {
            outputs.add(ItemStack.EMPTY);
        }
        outputs.add(output);
    }

    public void clearOutputs() {
        outputs.clear();
    }

    private boolean shouldScroll() {
        return this.outputs.size() > 12;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int i = this.x + 20;
        int j = this.y + 14;
        int k = this.scrollOffset + 12;

        for(int l = this.scrollOffset; l < k; ++l) {
            int m = l - this.scrollOffset;
            double d = mouseX - (double)(i + m % 4 * 16);
            double e = mouseY - (double)(j + m / 4 * 18);
            if (d >= 0.0 && e >= 0.0 && d < 16.0 && e < 18.0 && (this.handler).onButtonClick(this.client.player, l)) {
                Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.master(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                this.handler.setSelectedRecipe(l);
                return true;
            }
        }

        i = this.x + 119;
        j = this.y + 9;
        if (mouseX >= (double)i && mouseX < (double)(i + 12) && mouseY >= (double)j && mouseY < (double)(j + 54)) {
            this.mouseClicked = true;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void drawBackground(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
    }

    @Override
    protected void drawForeground(GuiGraphics context, int mouseX, int mouseY) {
        context.drawText(this.font, this.title, this.titleX, this.titleY - 1, -12566464, false);
        context.drawText(this.font, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY, -12566464, false);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        int k = (int)(41.0F * this.scrollAmount);
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 87, y + 15 + k, 176 + (this.shouldScroll() ? 0 : 12), 20, 12, 15, 256, 256);

        int l = this.x + 19;
        int m = this.y + 14;
        int n = this.scrollOffset + 12;
        this.renderRecipeBackground(context, mouseX, mouseY, l + 1, m, n);
        this.renderRecipeIcons(context, l, m, n);
    }

    private void renderOutputTooltip(GuiGraphics context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 16 && mouseY <= y + 33){
            context.drawTooltip(this.client.font, handler.getOutputStack().getItem().getName(), mouseX, mouseY);
        }
    }

    private void renderHammerTooltip(GuiGraphics context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 79 && mouseX <= x + 96 && mouseY >= y + 34 && mouseY <= y + 51){
            context.drawOrderedTooltip(this.client.font,
                    Lists.transform(List.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer"),
                            Component.translatable("tooltip." + MiddleEarth.MOD_ID +".anvil_hammer_2")),
                            Component::asOrderedText), mouseX, mouseY);
        }
    }

    private void renderRecipeBackground(GuiGraphics context, int mouseX, int mouseY, int x, int y, int scrollOffset) {
        for(int i = this.scrollOffset; i < scrollOffset && i < this.outputs.size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            int n = 0;
            if (i == (this.handler).getSelectedRecipe()) {
                n += 16;
            } else if (mouseX >= k && mouseY >= m && mouseX < k + 16 && mouseY < m + 18) {
                n += 32;
            }

            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, k, m - 1, 176 + n, 1, 16, 18, 256, 256);
        }
    }

    private void renderRecipeIcons(GuiGraphics context, int x, int y, int scrollOffset) {
        for (int i = this.scrollOffset; i < scrollOffset && i < this.outputs.size(); ++i) {
            int j = i - this.scrollOffset;
            int k = x + 1 + j % 4 * 16;
            int l = j / 4;
            int m = y + l * 18 + 2;
            context.drawItem(outputs.get(i), k, m);
        }

    }
}
