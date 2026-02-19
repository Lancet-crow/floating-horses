package lancet_.floating_horses.mixin;

import lancet_.floating_horses.FloatingHorsesConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkeletonHorseEntity.class)
public abstract class SkeletonHorseEntityMixin extends LivingEntity {

    protected SkeletonHorseEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initCustomGoals", at = @At("TAIL"))
    private void addSwimGoal(CallbackInfo ci) {
        ((MobEntityAccessor) this).goalSelector().add(0, new SwimGoal((MobEntity) (Object) this));
    }

    @Override
    public float getBaseMovementSpeedMultiplier() {
        if (this.getControllingPassenger() instanceof PlayerEntity &&
                Math.abs(this.getControlledMovementInput((PlayerEntity) this.getControllingPassenger(), Vec3d.ZERO)
                        .subtract(Vec3d.ZERO).lengthSquared()) >= 0.01){
            return FloatingHorsesConfig.config.scaleSpeed(FloatingHorsesConfig.config.skeletonHorseSpeed);
        }
        return 0.96F;
    }
}
