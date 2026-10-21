package net.jukoz.me.block.special.inscriptiontable;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.inscriptiontable.InscriptionTableScreenHandler;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.RenderShape;

public class InscriptionTableblock extends HorizontalDirectionalBlock {

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

    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClient) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    public MenuProvider createScreenHandlerFactory(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> {
            return new InscriptionTableScreenHandler(syncId, inventory, ContainerLevelAccess.create(world, pos));
        }, TITLE);
    }
}
