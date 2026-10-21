package net.jukoz.me.gui.structuremanager.structurenest;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.special.structureManager.nest.StructureNestBlockEntity;
import net.jukoz.me.gui.ModScreenHandlers;
import net.jukoz.me.network.packets.C2S.PacketStructureNestUpdateBlockEntityRequest;
import java.util.Optional;

public class StructureNestScreenHandler  extends AbstractContainerMenu {
    private final Level world;
    private StructureNestScreenData data;
    StructureNestBlockEntity blockEntity;

    public StructureNestScreenHandler(int syncId, Inventory playerInventory, StructureNestScreenData structureNestScreenData) {
        super(ModScreenHandlers.STRUCTURE_NEST_SCREEN_HANDLER, syncId);
        this.world = playerInventory.player.level();
        this.data = structureNestScreenData;
        this.blockEntity = (StructureNestBlockEntity) this.world.getBlockEntity(data.getPos());
    }

    @Override
    public ItemStack quickMove(Player player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }

    public ResourceLocation getManagerKey() {
        return data.getStructureManagerId();
    }

    public ResourceLocation getNestKey() {
        return data.getStructureNestId();
    }

    public void selectManagerId(LocalPlayer player, ResourceLocation identifier) {
        this.data.setStructureManagerId(identifier);
        this.data.setStructureNestId(null);
        updateBlockEntity();
    }

    public void selectNestId(LocalPlayer player, ResourceLocation identifier) {
        this.data.setStructureNestId(identifier);
        updateBlockEntity();
    }

    public void toggleToActivate() {
        this.data.toggleActiveState();
        updateBlockEntity();
    }

    private void updateBlockEntity() {
        ClientPlayNetworking.send(new PacketStructureNestUpdateBlockEntityRequest(data.getPos(), Optional.ofNullable(getManagerKey()), Optional.ofNullable(getNestKey()), data.getSpawnRadius(), data.getIsEnabled()));
    }

    public boolean getIsEnabled() {
        return data.getIsEnabled();
    }
}
