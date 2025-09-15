package lancet_.swimming_horses.mixin;

import lancet_.swimming_horses.SwimmingHorsesConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonHorseEntity;
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
        return SwimmingHorsesConfig.config.scaleSpeed(SwimmingHorsesConfig.config.skeletonHorseSpeed);
    }
}
