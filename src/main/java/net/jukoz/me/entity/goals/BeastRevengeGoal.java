package net.jukoz.me.entity.goals;

import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.GameRules;
import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.ai.control.Control;

public class BeastRevengeGoal extends TargetGoal {
    private static final TargetingConditions VALID_AVOIDABLES_PREDICATE = TargetingConditions.createAttackable().ignoreVisibility().ignoreDistanceScalingFactor();
    private static final int BOX_VERTICAL_EXPANSION = 10;
    private boolean groupRevenge;
    private int lastAttackedTime;
    private final Class<?>[] noRevengeTypes;
    @Nullable
    private Class<?>[] noHelpTypes;

    public BeastRevengeGoal(AbstractBeastEntity mob, Class<?> ... noRevengeTypes) {
        super(mob, true);
        this.noRevengeTypes = noRevengeTypes;
        this.setControls(EnumSet.of(Control.TARGET));
    }

    @Override
    public boolean canStart() {
        int i = this.mob.getLastAttackedTime();
        LivingEntity livingEntity = this.mob.getAttacker();
        if (i == this.lastAttackedTime || livingEntity == null) {
            return false;
        }
        if (this.mob.getWorld() instanceof ServerLevel serverWorld && livingEntity.getType() == EntityType.PLAYER && serverWorld.getGameRules().getBoolean(GameRules.UNIVERSAL_ANGER)) {
            return false;
        }
        if (((AbstractBeastEntity)this.mob).isTame()) {
            return false;
        }
        for (Class<?> class_ : this.noRevengeTypes) {
            if (!class_.isAssignableFrom(livingEntity.getClass())) continue;
            return false;
        }
        return this.canTrack(livingEntity, VALID_AVOIDABLES_PREDICATE);
    }

    public BeastRevengeGoal setGroupRevenge(Class<?> ... noHelpTypes) {
        this.groupRevenge = true;
        this.noHelpTypes = noHelpTypes;
        return this;
    }

    @Override
    public void start() {
        this.mob.setTarget(this.mob.getAttacker());
        this.target = this.mob.getTarget();
        this.lastAttackedTime = this.mob.getLastAttackedTime();
        this.maxTimeWithoutVisibility = 300;
        if (this.groupRevenge) {
            this.callSameTypeForRevenge();
        }
        super.start();
    }

    protected void callSameTypeForRevenge() {
        double d = this.getFollowRange();
        AABB box = AABB.from(this.mob.getPos()).expand(d, 10.0, d);
        List<Mob> list = (List<Mob>)this.mob.getWorld().getEntitiesByClass(this.mob.getClass(), box, EntitySelector.EXCEPT_SPECTATOR);
        for (Mob mobEntity : list) {
            if (this.mob == mobEntity || mobEntity.getTarget() != null || this.mob instanceof TamableAnimal && ((TamableAnimal)this.mob).getOwner() != ((TamableAnimal)mobEntity).getOwner() || mobEntity.isTeammate(this.mob.getAttacker())) continue;
            if (this.noHelpTypes != null) {
                boolean bl = false;
                for (Class<?> class_ : this.noHelpTypes) {
                    if (mobEntity.getClass() != class_) continue;
                    bl = true;
                    break;
                }
                if (bl) continue;
            }
            this.setMobEntityTarget(mobEntity, this.mob.getAttacker());
        }
    }

    protected void setMobEntityTarget(Mob mob, LivingEntity target) {
        mob.setTarget(target);
    }
}
