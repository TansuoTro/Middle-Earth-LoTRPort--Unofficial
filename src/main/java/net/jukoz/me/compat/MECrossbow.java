package net.jukoz.me.compat;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import java.util.ArrayList;
import java.util.List;

/**
 * NBT-backed replacement for 1.21's {@code CHARGED_PROJECTILES} component.
 *
 * <p>Writes the same keys vanilla 1.20.1's {@code CrossbowItem} uses: a {@code Charged}
 * boolean plus a {@code ChargedProjectiles} list of encoded stacks.
 */
public final class MECrossbow {

    private static final String CHARGED_KEY = "Charged";
    private static final String CHARGED_PROJECTILES_KEY = "ChargedProjectiles";

    private MECrossbow() {
    }

    /** Marks {@code stack} charged and stores the loaded projectiles. */
    public static void setCharged(ItemStack stack, List<ItemStack> projectiles) {
        CompoundTag nbt = stack.getOrCreateNbt();
        nbt.putBoolean(CHARGED_KEY, true);
        ListTag list = new ListTag();
        for (ItemStack projectile : projectiles) {
            list.add(projectile.writeNbt(new CompoundTag()));
        }
        nbt.put(CHARGED_PROJECTILES_KEY, list);
    }

    /** Removes both the charged flag and the stored projectiles. */
    public static void clear(ItemStack stack) {
        CompoundTag nbt = stack.getNbt();
        if (nbt != null) {
            nbt.remove(CHARGED_KEY);
            nbt.remove(CHARGED_PROJECTILES_KEY);
        }
    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag nbt = stack.getNbt();
        return nbt != null && nbt.getBoolean(CHARGED_KEY);
    }

    /** The loaded projectiles; empty when the crossbow is not charged. */
    public static List<ItemStack> getProjectiles(ItemStack stack) {
        List<ItemStack> projectiles = new ArrayList<>();
        CompoundTag nbt = stack.getNbt();
        if (nbt != null && nbt.contains(CHARGED_PROJECTILES_KEY, Tag.LIST_TYPE)) {
            ListTag list = nbt.getList(CHARGED_PROJECTILES_KEY, Tag.COMPOUND_TYPE);
            for (int i = 0; i < list.size(); i++) {
                projectiles.add(ItemStack.fromNbt(list.getCompound(i)));
            }
        }
        return projectiles;
    }
}
