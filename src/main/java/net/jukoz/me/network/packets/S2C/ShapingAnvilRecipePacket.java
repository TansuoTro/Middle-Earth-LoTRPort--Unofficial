package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreen;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreen;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class ShapingAnvilRecipePacket extends ServerToClientPacket<ShapingAnvilRecipePacket> {
    public static final Identifier ID = MiddleEarth.of("shaping_anvil_recipe_packet");

    public static final PacketType<ShapingAnvilRecipePacket> TYPE = PacketType.create(ID, ShapingAnvilRecipePacket::new);

    private final int index;
    private final ItemStack output;

    public ShapingAnvilRecipePacket(int index, ItemStack output) {
        this.index = index;
        this.output = output;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        MinecraftClient client = MinecraftClient.getInstance();
        ShapingAnvilScreen screen = (ShapingAnvilScreen)client.currentScreen;
        if (screen != null) screen.addRecipe(this.index, this.output);
    }

    public ShapingAnvilRecipePacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readItemStack());
    }

    @Override
    public PacketType<ShapingAnvilRecipePacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.index);
        buf.writeItemStack(this.output);
    }


}
