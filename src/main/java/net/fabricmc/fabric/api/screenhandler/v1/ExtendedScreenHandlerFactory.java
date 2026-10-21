package net.fabricmc.fabric.api.screenhandler.v1;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.IContainerFactory;

/**
 * Forge compat shim for Fabric's {@code ExtendedScreenHandlerFactory}: a menu provider that can
 * also write extra opening data to the client. The data is sent through Forge's
 * {@code IContainerFactory} companion mechanism.
 */
public interface ExtendedScreenHandlerFactory extends MenuProvider {
    void writeScreenOpeningData(ServerPlayer player, net.minecraft.network.FriendlyByteBuf buf);

    @Override
    AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player);

    /** Forge's equivalent factory hook: re-sends the opening data alongside the menu id. */
    default IContainerFactory asForgeFactory() {
        return (id, inv, buf) -> {
            writeScreenOpeningData((ServerPlayer) inv.player, buf);
            return createMenu(id, inv, inv.player);
        };
    }

    /** Helper so callers can keep using {@code Component#getDisplayName()}. */
    default Component getTitle() {
        return getDisplayName();
    }
}
