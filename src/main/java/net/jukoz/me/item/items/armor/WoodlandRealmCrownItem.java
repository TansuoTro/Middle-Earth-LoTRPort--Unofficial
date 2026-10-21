package net.jukoz.me.item.items.armor;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.item.dataComponents.CooldownDataComponent;
import net.jukoz.me.item.dataComponents.SeasonDataComponent;
import net.jukoz.me.item.utils.armor.ExtendedArmorMaterial;
import net.jukoz.me.world.biomes.BiomeTagsME;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.particles.ParticleTypes;

public class WoodlandRealmCrownItem extends CustomHelmetItem {
    private static final float OFFSET_XZ = 0.5f;
    private static final float OFFSET_Y = -0.3f;
    private SeasonDataComponent.Season season = SeasonDataComponent.Season.DEAD;
    private boolean initialized = false;

    public WoodlandRealmCrownItem(ExtendedArmorMaterial material, Settings settings) {
        super(material, settings.maxCount(1));
    }

    @Override
    public void inventoryTick(ItemStack stack, net.minecraft.world.level.Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        Holder<Biome> biomeEntry = world.getBiome(entity.blockPosition());

        int cooldown = 5;
        if(MEComponents.get(stack, MEComponents.COOLDOWN) != null) {
            cooldown = MEComponents.get(stack, MEComponents.COOLDOWN).cooldown();
        } else {
            MEComponents.set(stack, MEComponents.COOLDOWN, new CooldownDataComponent(cooldown));
        }

        if(cooldown <= 0 && biomeEntry.getKey().isPresent()) {
            SeasonDataComponent.Season newSeason = SeasonDataComponent.Season.SUMMER;
            if(biomeEntry.is(BiomeTagsME.SPRING)) {
                newSeason = SeasonDataComponent.Season.SPRING;
            } else if(biomeEntry.is(BiomeTagsME.AUTUMN)) {
                newSeason = SeasonDataComponent.Season.AUTUMN;
            } else if(biomeEntry.is(BiomeTagsME.WINTER)) {
                newSeason = SeasonDataComponent.Season.WINTER;
            } else if(biomeEntry.is(BiomeTagsME.DEAD)) {
                newSeason = SeasonDataComponent.Season.DEAD;
            }

            if(season != newSeason || !initialized) {
                initialized = true;
                LivingEntity livingEntity = (LivingEntity) entity;
                MEComponents.set(stack, MEComponents.COOLDOWN, new CooldownDataComponent(35));
                double scale = 1.8f * livingEntity.getAttributeValue(EntityAttributesME.SCALE);
                Vec3 pos = entity.position().add(0, 1.8f * scale, 0).add(0, -1.1f, 0);

                if(season.equals(SeasonDataComponent.Season.DEAD) && !newSeason.equals(SeasonDataComponent.Season.WINTER)) {
                    ((net.minecraft.server.level.ServerLevel) world).spawnParticles(ParticleTypes.COMPOSTER, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.SPRING)) {
                    ((net.minecraft.server.level.ServerLevel) world).spawnParticles(ParticleTypes.CHERRY_LEAVES, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.SUMMER)) {
                    int leavesColor = world.getBiome(entity.blockPosition()).value().getFoliageColor();
                    ((net.minecraft.server.level.ServerLevel) world).spawnParticles(ParticleTypes.CHERRY_LEAVES, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.AUTUMN)) {
                    ((net.minecraft.server.level.ServerLevel) world).spawnParticles(ParticleTypes.CHERRY_LEAVES, pos.getX(), pos.getY(), pos.getZ(), 9, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 1);
                } else if(season.equals(SeasonDataComponent.Season.WINTER)) {
                    ((net.minecraft.server.level.ServerLevel) world).spawnParticles(ParticleTypes.SNOWFLAKE, pos.getX(), pos.getY(), pos.getZ(), 12, OFFSET_XZ, OFFSET_Y, OFFSET_XZ, 0.05f);
                }

                String itemModelName = "woodland_realm_crown";
                if(newSeason != SeasonDataComponent.Season.DEAD) {
                    itemModelName += "_" + newSeason;
                }
                ResourceLocation newItemModel = new ResourceLocation(MiddleEarth.MOD_ID, itemModelName.lowercaseVariant());
                MEComponents.set(stack, MEComponents.ITEM_MODEL, newItemModel);
                MEComponents.set(stack, MEComponents.SEASON_DATA, new SeasonDataComponent(newSeason));
            }
            season = newSeason;
        }

        MEComponents.set(stack, MEComponents.COOLDOWN, new CooldownDataComponent(Math.max(0, cooldown - 1)));
    }
}
