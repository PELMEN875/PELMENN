package com.example.tobacconerysmoking.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

/** Первое лицо: переносит предмет в руке ко рту (курение) или поднимает пачку (радиальное меню). */
public class SmokeClientExtensions implements IClientItemExtensions {
    public static final SmokeClientExtensions SMOKE = new SmokeClientExtensions(false);
    public static final SmokeClientExtensions PACK = new SmokeClientExtensions(true);

    private final boolean pack;

    private SmokeClientExtensions(boolean pack) { this.pack = pack; }

    @Override
    public boolean applyForgeHandTransform(PoseStack pose, LocalPlayer player, HumanoidArm arm, ItemStack stack,
                                           float partialTick, float equipProcess, float swingProcess) {
        InteractionHand hand = arm == player.getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        int side = arm == HumanoidArm.RIGHT ? 1 : -1;

        if (pack) {
            float p = PackAnimationState.get(partialTick);
            if (p <= 0.001F || hand != InteractionHand.MAIN_HAND) return false;
            baseTransform(pose, side, equipProcess);
            pose.translate(-side * SmokeTuning.PACK_DX * p, SmokeTuning.PACK_DY * p, SmokeTuning.PACK_DZ * p);
            pose.mulPose(Axis.XP.rotationDegrees(SmokeTuning.PACK_ROT_X * p));
            pose.mulPose(Axis.YP.rotationDegrees(side * SmokeTuning.PACK_ROT_Y * p));
            return true;
        }

        if (!player.isUsingItem() || player.getUsedItemHand() != hand || player.getUseItemRemainingTicks() <= 0) {
            return false;
        }
        float t = (player.getTicksUsingItem() + partialTick) / SmokeTuning.RAISE_TICKS;
        float p = Mth.clamp(t, 0.0F, 1.0F);
        p = p * p * (3.0F - 2.0F * p);

        baseTransform(pose, side, equipProcess);
        pose.translate(-side * SmokeTuning.FP_DX * p, SmokeTuning.FP_DY * p, SmokeTuning.FP_DZ * p);
        pose.mulPose(Axis.XP.rotationDegrees(SmokeTuning.FP_ROT_X * p));
        pose.mulPose(Axis.YP.rotationDegrees(side * SmokeTuning.FP_ROT_Y * p));
        pose.mulPose(Axis.ZP.rotationDegrees(side * SmokeTuning.FP_ROT_Z * p));
        return true;
    }

    /** То же, что делает ваниль в applyItemArmTransform: исходная позиция предмета в руке. */
    private static void baseTransform(PoseStack pose, int side, float equipProcess) {
        pose.translate(side * 0.56F, -0.52F + equipProcess * -0.6F, -0.72F);
    }
}
