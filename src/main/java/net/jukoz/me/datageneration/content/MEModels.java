package net.jukoz.me.datageneration.content;

import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureSlot;
import net.jukoz.me.MiddleEarth;
import net.minecraft.resources.ResourceLocation;
import java.util.Optional;
import net.minecraft.client.model.Model;

public class MEModels {
    public static final Model VERTICAL_SLAB;
    public static final Model VERTICAL_SLAB_INNER;
    public static final Model VERTICAL_SLAB_OUTER;
    public static final Model VERTICAL_COLUMN_SLAB;
    public static final Model VERTICAL_COLUMN_SLAB_INNER;
    public static final Model VERTICAL_COLUMN_SLAB_OUTER;

    public static final Model COLUMN_WALL_POST;
    public static final Model COLUMN_WALL_SIDE;
    public static final Model COLUMN_WALL_SIDE_TALL;
    public static final Model COLUMN_WALL_INVENTORY;

    public static final Model WOOD_STOOL;
    public static final Model WOOD_BENCH;
    public static final Model WOOD_TABLE;
    public static final Model WOOD_CHAIR;

    public static final Model STONE_STOOL;
    public static final Model STONE_TABLE;
    public static final Model STONE_CHAIR;

    public static final Model ROCKS_STAGE_0;
    public static final Model ROCKS_STAGE_1;
    public static final Model ROCKS_STAGE_2;
    public static final Model ROCKS_STAGE_3;

    public static final Model LARGE_DOOR_LEFT;
    public static final Model LARGE_DOOR_LEFT_OPEN;
    public static final Model LARGE_DOOR_RIGHT;
    public static final Model LARGE_DOOR_RIGHT_OPEN;

    public static final Model LARGE_THICK_DOOR_LEFT;
    public static final Model LARGE_THICK_DOOR_LEFT_OPEN;
    public static final Model LARGE_THICK_DOOR_RIGHT;
    public static final Model LARGE_THICK_DOOR_RIGHT_OPEN;

    public static final Model THICK_LADDER;

    public static final Model LARGE_PLANT;
    public static final Model TINTED_LARGE_PLANT;

    public static final Model CROP_VINE;

    public static final Model PATH_BLOCK;

    private static Model block(String parent, TextureSlot... requiredTextureKeys) {
        return new Model(Optional.of(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model block(String parent, String variant, TextureSlot... requiredTextureKeys) {
        return new Model(Optional.of(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    static {
        VERTICAL_SLAB = block("vertical_slab", TextureSlot.ALL, TextureSlot.PARTICLE);
        VERTICAL_SLAB_INNER = block("vertical_slab_inner", "_inner", TextureSlot.ALL, TextureSlot.PARTICLE);
        VERTICAL_SLAB_OUTER = block("vertical_slab_outer", "_outer", TextureSlot.ALL, TextureSlot.PARTICLE);
        VERTICAL_COLUMN_SLAB = block("vertical_column_slab", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.PARTICLE);
        VERTICAL_COLUMN_SLAB_INNER = block("vertical_column_slab_inner", "_inner", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.PARTICLE);
        VERTICAL_COLUMN_SLAB_OUTER = block("vertical_column_slab_outer", "_outer", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.SIDE, TextureSlot.PARTICLE);

        COLUMN_WALL_POST = block("template_column_wall_post", "_post", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.WALL, TextureSlot.PARTICLE);
        COLUMN_WALL_SIDE = block("template_column_wall_side", "_side", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.WALL, TextureSlot.PARTICLE);
        COLUMN_WALL_SIDE_TALL = block("template_column_wall_side_tall", "_side_tall", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.WALL, TextureSlot.PARTICLE);
        COLUMN_WALL_INVENTORY = block("template_column_wall_inventory", "_inventory", TextureSlot.TOP, TextureSlot.BOTTOM, TextureSlot.WALL, TextureSlot.PARTICLE);

        WOOD_STOOL = block("wood_stool", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE);
        WOOD_BENCH = block("wood_bench_template", TextureSlot.ALL, TextureSlot.PARTICLE);
        WOOD_TABLE = block("wood_table", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE);
        WOOD_CHAIR = block("wood_chair", TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.PARTICLE);

        STONE_STOOL = block("stone_stool_template", TextureSlot.ALL, TextureSlot.PARTICLE);
        STONE_TABLE = block("stone_table_template", TextureSlot.ALL, TextureSlot.PARTICLE);
        STONE_CHAIR = block("stone_chair_template", TextureSlot.ALL, TextureSlot.PARTICLE);

        ROCKS_STAGE_0 = block("rocks_stage_0_template", TextureSlot.ALL, TextureSlot.PARTICLE);
        ROCKS_STAGE_1 = block("rocks_stage_1_template", "_1", TextureSlot.ALL, TextureSlot.PARTICLE);
        ROCKS_STAGE_2 = block("rocks_stage_2_template", "_2", TextureSlot.ALL, TextureSlot.PARTICLE);
        ROCKS_STAGE_3 = block("rocks_stage_3_template", "_3", TextureSlot.ALL, TextureSlot.PARTICLE);

        LARGE_DOOR_LEFT = block("large_door_left", TextureSlot.ALL, TextureSlot.PARTICLE);
        LARGE_DOOR_LEFT_OPEN = block("large_door_left_open", TextureSlot.ALL, TextureSlot.PARTICLE);
        LARGE_DOOR_RIGHT = block("large_door_right", TextureSlot.ALL, TextureSlot.PARTICLE);
        LARGE_DOOR_RIGHT_OPEN = block("large_door_right_open", TextureSlot.ALL, TextureSlot.PARTICLE);

        LARGE_THICK_DOOR_LEFT = block("thick_large_door_left", TextureSlot.ALL, TextureSlot.PARTICLE);
        LARGE_THICK_DOOR_LEFT_OPEN = block("thick_large_door_left_open", TextureSlot.ALL, TextureSlot.PARTICLE);
        LARGE_THICK_DOOR_RIGHT = block("thick_large_door_right", TextureSlot.ALL, TextureSlot.PARTICLE);
        LARGE_THICK_DOOR_RIGHT_OPEN = block("thick_large_door_right_open", TextureSlot.ALL, TextureSlot.PARTICLE);

        THICK_LADDER = block("thick_ladder", TextureSlot.TEXTURE, TextureSlot.PARTICLE);

        LARGE_PLANT = block("large_plant_template", TextureSlot.ALL, TextureSlot.PARTICLE);
        TINTED_LARGE_PLANT = block("large_tinted_plant_template", TextureSlot.ALL, TextureSlot.PARTICLE);

        CROP_VINE = block("crop_no_shade_template", TextureSlot.CROP);

        PATH_BLOCK = block("path_template", TextureSlot.PARTICLE, TextureSlot.TOP, TextureSlot.SIDE, TextureSlot.BOTTOM);
    }
}
