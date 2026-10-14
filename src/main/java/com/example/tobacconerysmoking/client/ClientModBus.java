package com.example.tobacconerysmoking.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.example.tobacconerysmoking.SmokeItems;
import com.example.tobacconerysmoking.TobacconerySmoking;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = TobacconerySmoking.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModBus {
    public static final KeyMapping MOUTH_KEY = new KeyMapping(
        "key.tobacconery_smoking.mouth", InputConstants.Type.KEYSYM, InputConstants.KEY_G,
        "key.categories.tobacconery_smoking");

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(MOUTH_KEY);
    }

    @SubscribeEvent
    public static void registerExtensions(RegisterClientExtensionsEvent event) {
        for (Item item : SmokeItems.resolve(SmokeItems.SMOKABLE)) event.registerItem(SmokeClientExtensions.SMOKE, item);
        for (Item item : SmokeItems.resolve(SmokeItems.PACKS)) event.registerItem(SmokeClientExtensions.PACK, item);
    }

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            for (Item item : SmokeItems.resolve(SmokeItems.LIT_CIGARETTES)) {
                CuriosRendererRegistry.register(item, MouthRenderer::new);
            }
        });
    }
}
