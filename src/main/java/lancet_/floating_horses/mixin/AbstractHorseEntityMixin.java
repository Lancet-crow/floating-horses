package lancet_.floating_horses.mixin;

import lancet_.floating_horses.FloatingHorsesConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends LivingEntity {

    @Shadow public abstract @Nullable LivingEntity getControllingPassenger();

    @Unique
    private int tick = 0;

    protected AbstractHorseEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tickControlled", at = @At(value = "TAIL"))
    private void swim(PlayerEntity controllingPlayer, Vec3d movementInput, CallbackInfo ci) {
        double k;
        if (this.isInLava()) {
            k = this.getFluidHeight(FluidTags.LAVA);
        } else {
            k = this.getFluidHeight(FluidTags.WATER);
        }

        if (k > 0.4 || this.tick > 0) {
            this.tick++;

            if (this.isTouchingWater()) {
                this.addVelocity(0.0, (k > 0.8 ? 0.04 : 0.01), 0.0);
            } else if (this.isInLava()) {
                this.addVelocity(0.0, (k > 0.8 ? 0.11 : 0.0275), 0.0);
            } else {
                this.tick = 0;
            }

            if (this.tick == 30) {
                this.tick = 0;
            }
        }
    }

    @Override
    public boolean shouldDismountUnderwater() {
        return false;
    }

    @Override
    public float getBaseMovementSpeedMultiplier() {
        if (this.getControllingPassenger() instanceof PlayerEntity &&
                Math.abs(this.getControlledMovementInput((PlayerEntity) this.getControllingPassenger(), Vec3d.ZERO)
                        .subtract(Vec3d.ZERO).lengthSquared()) >= 0.01){
            return FloatingHorsesConfig.config.scaleSpeed(FloatingHorsesConfig.config.normalHorseSpeed);
        }
        return super.getBaseMovementSpeedMultiplier();
    }
}
