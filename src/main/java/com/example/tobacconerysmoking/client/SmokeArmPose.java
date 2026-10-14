package com.example.tobacconerysmoking.client;

import com.example.tobacconerysmoking.SmokeItems;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;

/** Третье лицо: поднимает руку, держащую курительный предмет, ко рту. Вызывается из миксина. */
public final class SmokeArmPose {
    private SmokeArmPose() {}

    public static void apply(HumanoidModel<?> model, LivingEntity entity, float partialTick) {
        if (!entity.isUsingItem() || !SmokeItems.isSmokable(entity.getUseItem())) return;

        float t = (entity.getTicksUsingItem() + partialTick) / SmokeTuning.RAISE_TICKS;
        float p = Mth.clamp(t, 0.0F, 1.0F);
        p = p * p * (3.0F - 2.0F * p);                       // smoothstep

        InteractionHand hand = entity.getUsedItemHand();
        HumanoidArm arm = hand == InteractionHand.MAIN_HAND ? entity.getMainArm() : entity.getMainArm().getOpposite();
        boolean right = arm == HumanoidArm.RIGHT;
        ModelPart part = right ? model.rightArm : model.leftArm;
        float side = right ? 1.0F : -1.0F;

        float tx = SmokeTuning.ARM_X + model.head.xRot * SmokeTuning.HEAD_PITCH_FOLLOW;
        float ty = -side * SmokeTuning.ARM_Y_IN + model.head.yRot;
        float tz = -side * SmokeTuning.ARM_Z;

        part.xRot = Mth.lerp(p, part.xRot, tx);
        part.yRot = Mth.lerp(p, part.yRot, ty);
        part.zRot = Mth.lerp(p, part.zRot, tz);
    }
}
