package net.jukoz.me.mixin.client;

import net.jukoz.me.compat.client.render.state.EntityRenderState;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.minecraft.item.ItemStack;
import net.jukoz.me.client.renderer.ArmedEntityRenderStateAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LivingEntityRenderState.class)
public class LivingEntityRenderStateMixin extends EntityRenderState implements ArmedEntityRenderStateAccess {
    @Unique
    private ItemStack mainHandStack;
    @Unique
    private ItemStack offHandStack;
    @Unique
    private boolean restrained;

    @Override
    public ItemStack getMainHandStack() {
        return mainHandStack.copy();
    }
    @Override
    public ItemStack getOffHandStack() {
        return offHandStack.copy();
    }
    @Override
    public boolean isRestrained() {
        return restrained;
    }

    @Override
    public void setMainHandStack(ItemStack mainHandStack) {
        this.mainHandStack = mainHandStack;
    }
    @Override
    public void setOffHandStack(ItemStack offHandStack) {
        this.offHandStack = offHandStack;
    }
    @Override
    public void setRestrained(boolean restrained) {
        this.restrained = restrained;
    }
}
