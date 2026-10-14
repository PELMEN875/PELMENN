package com.example.tobacconerysmoking.mixin;

import com.example.tobacconerysmoking.client.SmokeArmPose;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    private void tobacconerySmoking$pose(LivingEntity entity, float limbSwing, float limbSwingAmount,
                                         float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        SmokeArmPose.apply((HumanoidModel<?>) (Object) this, entity, ageInTicks - entity.tickCount);
    }
}
