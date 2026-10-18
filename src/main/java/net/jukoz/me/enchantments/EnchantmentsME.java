package net.jukoz.me.enchantments;

import net.jukoz.me.MiddleEarth;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * Middle-earth mod enchantment registry.
 *
 * <p>The 1.21.8 donor registers enchantments through the {@code Enchantment} data-component
 * registry ({@code Enchantment.builder(...).addEffect(...)}). None of that exists in 1.20.1,
 * where an enchantment is a plain {@code Registries.ENCHANTMENT} entry with a rarity, an
 * {@link EnchantmentTarget} and a set of equipment slots.
 *
 * <p>What maps directly: rarity (from the donor weight), max level, the min/max power curves,
 * and the slot set. What does not: the donor's {@code ATTRIBUTES} effects (celerity's attack
 * speed, grounded's knockback resistance, stalwart's armour, vantage's interaction range) —
 * 1.20.1 has no per-enchantment attribute modifier list, so those four are registered as
 * plain enchantments and the bonus has to be applied by hand in code.
 */
public class EnchantmentsME {
    public static final RegistryKey<Enchantment> AILMENT_PROTECTION = of("ailment_protection");
    public static final RegistryKey<Enchantment> AULE_BLESSING = of("aule_blessing");
    public static final RegistryKey<Enchantment> BANE_OF_GIANTS = of("bane_of_giants");
    public static final RegistryKey<Enchantment> BEHEADING = of("beheading");
    public static final RegistryKey<Enchantment> CELERITY = of("celerity");
    public static final RegistryKey<Enchantment> FIRST_STRIKE = of("first_strike");
    public static final RegistryKey<Enchantment> GROUNDED = of("grounded");
    public static final RegistryKey<Enchantment> HEWING = of("hewing");
    public static final RegistryKey<Enchantment> HIGH_STEP = of("high_step");
    public static final RegistryKey<Enchantment> MINER_REACH = of("miner_reach");
    public static final RegistryKey<Enchantment> STEALTHY_TRAIL = of("stealthy_trail");
    public static final RegistryKey<Enchantment> STALWART = of("stalwart");
    public static final RegistryKey<Enchantment> STRIDE = of("stride");
    public static final RegistryKey<Enchantment> TREE_FELLER = of("tree_feller");
    public static final RegistryKey<Enchantment> VANTAGE = of("vantage");

    private static final EquipmentSlot[] ARMOR_SLOTS = {
            EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
    private static final EquipmentSlot[] HAND_SLOT = {EquipmentSlot.MAINHAND};
    private static final EquipmentSlot[] CHEST_SLOT = {EquipmentSlot.CHEST};
    private static final EquipmentSlot[] LEGS_SLOT = {EquipmentSlot.LEGS};
    private static final EquipmentSlot[] FEET_SLOT = {EquipmentSlot.FEET};

    private static RegistryKey<Enchantment> of(String path) {
        Identifier id = MiddleEarth.of(path);
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, id);
    }

    public static void register() {
        register(AILMENT_PROTECTION, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR,
                ARMOR_SLOTS, 4, 18, 8, 10, 8);
        register(AULE_BLESSING, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR,
                ARMOR_SLOTS, 3, 12, 7, 5, 7);
        register(BANE_OF_GIANTS, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.WEAPON,
                HAND_SLOT, 5, 5, 8, 25, 8);
        register(BEHEADING, Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON,
                HAND_SLOT, 3, 20, 10, 50, 10);
        register(CELERITY, Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON,
                HAND_SLOT, 1, 10, 20, 40, 20);
        register(FIRST_STRIKE, Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON,
                HAND_SLOT, 1, 15, 7, 30, 15);
        register(GROUNDED, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR,
                ARMOR_SLOTS, 4, 5, 8, 25, 8);
        register(HEWING, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.DIGGER,
                HAND_SLOT, 4, 18, 8, 10, 8);
        register(TREE_FELLER, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.DIGGER,
                HAND_SLOT, 4, 18, 8, 10, 8);
        register(HIGH_STEP, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_FEET,
                FEET_SLOT, 3, 30, 30, 85, 30);
        register(MINER_REACH, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.DIGGER,
                HAND_SLOT, 1, 18, 15, 25, 15);
        register(STEALTHY_TRAIL, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_CHEST,
                CHEST_SLOT, 3, 25, 25, 75, 25);
        register(STRIDE, Enchantment.Rarity.VERY_RARE, EnchantmentTarget.ARMOR_LEGS,
                LEGS_SLOT, 3, 15, 15, 45, 15);
        register(STALWART, Enchantment.Rarity.UNCOMMON, EnchantmentTarget.ARMOR,
                ARMOR_SLOTS, 1, 10, 8, 25, 8);
        register(VANTAGE, Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON,
                HAND_SLOT, 1, 10, 20, 40, 20);
    }

    private static void register(RegistryKey<Enchantment> key, Enchantment.Rarity rarity,
                                 EnchantmentTarget target, EquipmentSlot[] slots, int maxLevel,
                                 int minBase, int minPerLevel, int maxBase, int maxPerLevel) {
        Registry.register(Registries.ENCHANTMENT, key, new MESimpleEnchantment(
                rarity, target, slots, maxLevel, minBase, minPerLevel, maxBase, maxPerLevel));
    }

    public static void registerModEnchantmentEffects() {
        MiddleEarth.LOGGER.logDebugMsg("Registering EnchantmentEffects for " + MiddleEarth.MOD_ID);
    }

    /** The 1.20.1 counterpart of one {@code Enchantment.definition(...)} call. */
    private static class MESimpleEnchantment extends Enchantment {
        private final int maxLevel;
        private final int minBase;
        private final int minPerLevel;
        private final int maxBase;
        private final int maxPerLevel;

        MESimpleEnchantment(Rarity rarity, EnchantmentTarget target, EquipmentSlot[] slots, int maxLevel,
                            int minBase, int minPerLevel, int maxBase, int maxPerLevel) {
            super(rarity, target, slots);
            this.maxLevel = maxLevel;
            this.minBase = minBase;
            this.minPerLevel = minPerLevel;
            this.maxBase = maxBase;
            this.maxPerLevel = maxPerLevel;
        }

        @Override
        public int getMaxLevel() {
            return this.maxLevel;
        }

        @Override
        public int getMinPower(int level) {
            return this.minBase + (level - 1) * this.minPerLevel;
        }

        @Override
        public int getMaxPower(int level) {
            return this.maxBase + (level - 1) * this.maxPerLevel;
        }
    }
}
