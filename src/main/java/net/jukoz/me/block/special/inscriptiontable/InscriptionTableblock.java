package net.jukoz.me.block.special.inscriptiontable;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.screen.*;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import org.jetbrains.annotations.Nullable;

public class InscriptionTableblock extends HorizontalFacingBlock {

    private static final Component TITLE = Component.translatable(MiddleEarth.of("inscription_table").toTranslationKey("container"));

    public InscriptionTableblock(Settings settings) {
        super(settings);
    }



    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite()));
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, Level world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> {
            return new InscriptionTableScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos));
        }, TITLE);
    }
}
