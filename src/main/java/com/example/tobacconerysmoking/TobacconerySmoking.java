package com.example.tobacconerysmoking;

import com.example.tobacconerysmoking.network.ToggleMouthPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod(TobacconerySmoking.MODID)
public class TobacconerySmoking {
    public static final String MODID = "tobacconery_smoking";

    public TobacconerySmoking(IEventBus modBus) {
        modBus.addListener(TobacconerySmoking::registerPayloads);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(ToggleMouthPayload.TYPE, ToggleMouthPayload.CODEC, ToggleMouthPayload::handle);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
