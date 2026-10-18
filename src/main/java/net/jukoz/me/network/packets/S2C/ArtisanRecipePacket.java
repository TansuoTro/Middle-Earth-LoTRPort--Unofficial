package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.artisantable.ArtisanTableScreen;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class ArtisanRecipePacket extends ServerToClientPacket<ArtisanRecipePacket> {
    public static final Identifier ID = MiddleEarth.of("artisan_recipe_packet");

    public static final PacketType<ArtisanRecipePacket> TYPE = PacketType.create(ID, ArtisanRecipePacket::new);

    private final int index;
    private final ItemStack output;

    public ArtisanRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ArtisanTableScreen screen = (ArtisanTableScreen)client.currentScreen;
        if (screen != null) {
            ArtisanTableScreenHandler screenHandler = screen.getScreenHandler();
            if(screenHandler != null) {
                screenHandler.addRecipeOutput(this.index, this.output);
            }
        }
    }

    public ArtisanRecipePacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readItemStack());
    }

    @Override
    public PacketType<ArtisanRecipePacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.index);
        buf.writeItemStack(this.output);
    }


}
