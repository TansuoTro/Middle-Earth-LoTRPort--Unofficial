package net.jukoz.me.network.packets.S2C;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.artisantable.ArtisanTableScreen;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class ArtisanRecipePacket extends ServerToClientPacket<ArtisanRecipePacket> {
    public static final ResourceLocation ID = MiddleEarth.of("artisan_recipe_packet");

    public static final PacketType<ArtisanRecipePacket> TYPE = PacketType.create(ID, ArtisanRecipePacket::new);

    private final int index;
    private final ItemStack output;

    public ArtisanRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(ClientPacketContext context) {
        Minecraft client = Minecraft.getInstance();
        ArtisanTableScreen screen = (ArtisanTableScreen)client.currentScreen;
        if (screen != null) {
            ArtisanTableScreenHandler screenHandler = screen.getMenu();
            if(screenHandler != null) {
                screenHandler.addRecipeOutput(this.index, this.output);
            }
        }
    }

    public ArtisanRecipePacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readItem());
    }

    @Override
    public PacketType<ArtisanRecipePacket> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
        buf.writeItemStack(this.output);
    }

}
