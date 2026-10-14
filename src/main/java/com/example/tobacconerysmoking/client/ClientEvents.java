package com.example.tobacconerysmoking.client;

import com.example.tobacconerysmoking.TobacconerySmoking;
import com.example.tobacconerysmoking.network.ToggleMouthPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = TobacconerySmoking.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        PackAnimationState.tick(mc);
        while (ClientModBus.MOUTH_KEY.consumeClick()) {
            if (mc.player != null && mc.screen == null) {
                PacketDistributor.sendToServer(new ToggleMouthPayload());
            }
        }
    }
}
