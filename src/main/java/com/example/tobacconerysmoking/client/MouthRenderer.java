package com.example.tobacconerysmoking.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/**
 * Рисует сигарету так же, как ваниль рисует предмет на голове (CustomHeadLayer), с контекстом HEAD.
 * В моделях Tobacconery (lit_*_cigarette.json) блок "head" уже подобран под положение у рта.
 */
public class MouthRenderer implements ICurioRenderer {
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack pose, RenderLayerParent<T, M> parent,
            MultiBufferSource buffer, int light, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {

        if (!(parent.getModel() instanceof HeadedModel headed)) return;

        pose.pushPose();
        headed.getHead().translateAndRotate(pose);
        pose.translate(0.0F, -0.25F, 0.0F);
        pose.mulPose(Axis.YP.rotationDegrees(180.0F));
        pose.scale(0.625F, -0.625F, -0.625F);
        Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer()
            .renderItem(slotContext.entity(), stack, ItemDisplayContext.HEAD, false, pose, buffer, light);
        pose.popPose();
    }
}
