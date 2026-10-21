package net.fabricmc.fabric.api.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FireBlock;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.level.block.Blocks;

/**
 * Forge compat shim for Fabric's {@code FlammableBlockRegistry}. Vanilla stores the values on
 * {@link FireBlock}'s flame chance / spread chance maps; registering is done once the fire block
 * exists, which it does by the time mod blocks register.
 */
public final class FlammableBlockRegistry {
    private static final FlammableBlockRegistry INSTANCE = new FlammableBlockRegistry();
    private final Map<Block, Integer> flameChance = new HashMap<>();
    private final Map<Block, Integer> spreadChance = new HashMap<>();

    private FlammableBlockRegistry() {}

    public static FlammableBlockRegistry getDefaultInstance() {
        return INSTANCE;
    }

    public void add(Block block, int flame, int spread) {
        flameChance.put(block, flame);
        spreadChance.put(block, spread);
        FireBlock fire = (FireBlock) net.minecraft.world.level.block.Blocks.FIRE;
        fire.setFlammable(block, flame, spread);
    }

    public int getFlameChance(Block block) {
        return flameChance.getOrDefault(block, 0);
    }

    public int getSpreadChance(Block block) {
        return spreadChance.getOrDefault(block, 0);
    }
}
