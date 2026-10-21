package net.jukoz.me.gui.structuremanager.structurenest;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.utils.CycledSelectionButtonType;
import net.jukoz.me.gui.utils.widgets.CycledSelectionWidget;
import net.jukoz.me.gui.utils.widgets.ModWidget;
import net.jukoz.me.gui.utils.widgets.SearchBarWidget;
import net.jukoz.me.gui.utils.widgets.searchbar.SearchBarResult;
import net.jukoz.me.gui.utils.widgets.searchbar.SearchBarResultType;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.jukoz.me.resources.datas.structure_manager_datas.StructureManagerData;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class StructureNestScreen extends AbstractContainerScreen<StructureNestScreenHandler> {
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath("textures", "gui", "structure_manager.png");
    private static final int TEXT_COLOR = Color.WHITE.getRGB();

    public SearchBarWidget managerSearchBarWidget;
    public CycledSelectionWidget nestCycledSelection;
    public Button isEnabledToggleButton;

    public StructureManagerData manager;
    public SpawnNestNodeData nest;

    public ArrayList<StructureManagerData> managers;

    public StructureNestScreen(StructureNestScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);

        Level world = inventory.player.level();

        this.managers = new ArrayList<>();
        var registryManager = world.registryAccess().getOptional(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).get();
        for(ResourceKey<StructureManagerData> data : registryManager.getKeys()){
            this.managers.add(registryManager.get(data.getValue()));
        }
        this.manager = registryManager.get(handler.getManagerKey());
        if(manager != null)
            this.nest = manager.getNpcSpawnNest(handler.getNestKey());
    }

    @Override
    protected void init() {
        super.init();

        List<SearchBarResult> results = new ArrayList<>();
        for(StructureManagerData data : this.managers){
            results.add(new SearchBarResult(Component.translatable(data.getId().toLanguageKey("structure_manager_data")), data.getId(), SearchBarResultType.NORMAL, button -> selectManager(data)));
        }

        this.managerSearchBarWidget = new SearchBarWidget(9, results, x -> updateScreenInformation(), 170);
        addDrawableChild(this.managerSearchBarWidget.getSearchBarToggleButton());
        this.managerSearchBarWidget.getAllButtons().forEach(this::addDrawableChild);
        addDrawableChild(this.managerSearchBarWidget.getScreenClickButton());

        nestCycledSelection = new CycledSelectionWidget(
                x -> this.updateNestList(-1),
                x -> this.updateNestList(1),
                null,
                CycledSelectionButtonType.GOLD);
        nestCycledSelection.getButtons().forEach(this::addDrawableChild);
        updateNestList(0);

        isEnabledToggleButton = Button.builder(Component.of("isEnabledToggleButton"),x -> toggleEnable()).build();
        net.jukoz.me.compat.client.gui.MEGui.setSize(isEnabledToggleButton, 15, 15);
        addDrawableChild(isEnabledToggleButton);
    }

    private void toggleEnable() {
        this.handler.toggleToActivate();
    }

    private void updateNestList(int difference) {
        if(this.manager == null || this.manager.getNpcSpawnNest().isEmpty()){
            nestCycledSelection.enableArrows(false);
            nestCycledSelection.setText(null);
            return;
        }

        var nests = manager.getNpcSpawnNest();
        if(nests == null){
            nestCycledSelection.enableArrows(false);
            nestCycledSelection.setText(null);
            return;
        }

        if(this.nest == null){
            // Pick first by default
            this.nest = nests.get(0);
        }

        if(difference != 0){
            int index =  nests.indexOf(this.nest);
            index += difference;
            if(index < 0)
                index = nests.size() - 1;
            if(index >= nests.size())
                index = 0;

            this.nest = nests.get(index);
        }

        this.handler.selectNestId(client.player, this.nest.getId());

        this.nestCycledSelection.enableArrows(nests.size() > 1);
        this.nestCycledSelection.setText(Component.of(this.nest.getId().toLanguageKey()).copy());
    }

    @Override
    protected void drawBackground(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
    }

    @Override
    protected void drawForeground(GuiGraphics context, int mouseX, int mouseY) {
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float deltaTicks) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, deltaTicks);
        ModWidget.updateMouse(mouseX, mouseY);

        int centerX = (int) (client.currentScreen.width / 2f);
        int startY = 70;

        int managerSearchBarWidgetStartY = startY;
        managerSearchBarWidgetStartY += this.managerSearchBarWidget.drawSearchBar(context, centerX - 5 - this.managerSearchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY, font);
        this.managerSearchBarWidget.setEndY(startY + 500);

        if(this.managerSearchBarWidget.searchIsToggled()) {
            this.managerSearchBarWidget.drawSearchResults(context, centerX - 5 - this.managerSearchBarWidget.searchBarToggleButton.getWidth(), managerSearchBarWidgetStartY - 20);
        }

        Component managerIdText = (manager == null)
                ? Component.translatable("N/A")
                : Component.translatable(manager.getId().toLanguageKey("structure_manager_data"));

        context.drawText(this.font, Component.translatable("ui.middle-earth.structure_manager.label_selected_id", managerIdText).formatted(ChatFormatting.BOLD).formatted(ChatFormatting.WHITE), centerX + 5, startY + 5, TEXT_COLOR, false);

        if(manager != null && this.managerSearchBarWidget != null){
            startY += 25;

            Component nestIdText = (nest == null)
                    ? Component.translatable("N/A")
                    : Component.translatable(nest.getId().toLanguageKey("structure_nest"));

            this.nestCycledSelection.drawAnchored(context, centerX, startY, true, nestIdText.copy(), font);

            isEnabledToggleButton.active = true;
            isEnabledToggleButton.setPosition(centerX + CycledSelectionWidget.TOTAL_WIDTH + 5, startY);
            //isEnabledToggleButton.render(context, mouseX, mouseY, deltaTicks);
            boolean isEnabledToggleButtonFocused = isEnabledToggleButton.isMouseOver(mouseX, mouseY) || isEnabledToggleButton.isFocused();
            int isEnabledToggleButtonUvY = 1;
            if(handler.getIsEnabled())
                isEnabledToggleButtonUvY = isEnabledToggleButtonFocused ? 52 : 35;
            else if(isEnabledToggleButtonFocused)
                isEnabledToggleButtonUvY = 18;
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, isEnabledToggleButton.getX(), isEnabledToggleButton.getY(), 18, isEnabledToggleButtonUvY, isEnabledToggleButton.getWidth(), isEnabledToggleButton.getHeight(), 256, 256);
            if(isEnabledToggleButton.isMouseOver(mouseX, mouseY))
                context.drawTooltip(this.font, Component.of("[SET TO TRUE] To ready up the structure manager subscription."), isEnabledToggleButton.getX(), isEnabledToggleButton.getY());
        }
        else
            this.isEnabledToggleButton.active = false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        this.managerSearchBarWidget.keyPressed(keyCode, scanCode, modifiers);
     if(keyCode <= 90 && keyCode >= 65)
            return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        this.managerSearchBarWidget.mouseReleased(mouseX, mouseY, button);
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        this.managerSearchBarWidget.keyReleased(keyCode, scanCode, modifiers);
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double verticalAmount) {
        return super.mouseScrolled(mouseX, mouseY, verticalAmount);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        this.managerSearchBarWidget.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        this.managerSearchBarWidget.charTyped(chr, modifiers);
        return super.charTyped(chr, modifiers);
    }

    private void selectManager(StructureManagerData data) {
        this.handler.selectManagerId(client.player, data.getId());
        this.manager = data;
        this.nest = null;
        updateNestList(0);
    }

    private void updateScreenInformation() {

    }
}
