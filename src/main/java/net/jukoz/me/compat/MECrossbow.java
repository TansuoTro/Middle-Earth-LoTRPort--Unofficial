package net.jukoz.me.compat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;

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
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putBoolean(CHARGED_KEY, true);
        NbtList list = new NbtList();
        for (ItemStack projectile : projectiles) {
            list.add(projectile.writeNbt(new NbtCompound()));
        }
        nbt.put(CHARGED_PROJECTILES_KEY, list);
    }

    /** Removes both the charged flag and the stored projectiles. */
    public static void clear(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        if (nbt != null) {
            nbt.remove(CHARGED_KEY);
            nbt.remove(CHARGED_PROJECTILES_KEY);
        }
    }

    public static boolean isCharged(ItemStack stack) {
        NbtCompound nbt = stack.getNbt();
        return nbt != null && nbt.getBoolean(CHARGED_KEY);
    }

    /** The loaded projectiles; empty when the crossbow is not charged. */
    public static List<ItemStack> getProjectiles(ItemStack stack) {
        List<ItemStack> projectiles = new ArrayList<>();
        NbtCompound nbt = stack.getNbt();
        if (nbt != null && nbt.contains(CHARGED_PROJECTILES_KEY, NbtElement.LIST_TYPE)) {
            NbtList list = nbt.getList(CHARGED_PROJECTILES_KEY, NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < list.size(); i++) {
                projectiles.add(ItemStack.fromNbt(list.getCompound(i)));
            }
        }
        return projectiles;
    }
}
