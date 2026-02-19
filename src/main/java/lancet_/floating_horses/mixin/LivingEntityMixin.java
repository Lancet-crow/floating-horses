package lancet_.floating_horses.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Unique
    private int tick = 0;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "travel", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/LivingEntity;canWalkOnFluid(Lnet/minecraft/fluid/FluidState;)Z"))
    private void fixForHorsesSwimWithNonPlayerEntities(CallbackInfo ci){
        if ((LivingEntity) (Object) this instanceof AbstractHorseEntity && this.hasControllingPassenger() && !(this.getControllingPassenger().isPlayer())){
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
    }
}
