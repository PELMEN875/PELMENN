package com.example.tobacconerysmoking.network;

import com.example.tobacconerysmoking.MouthSlotHandler;
import com.example.tobacconerysmoking.TobacconerySmoking;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ToggleMouthPayload() implements CustomPacketPayload {
    public static final Type<ToggleMouthPayload> TYPE = new Type<>(TobacconerySmoking.id("toggle_mouth"));
    public static final StreamCodec<ByteBuf, ToggleMouthPayload> CODEC = StreamCodec.unit(new ToggleMouthPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() { return TYPE; }

    public static void handle(ToggleMouthPayload payload, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            if (ctx.player() instanceof ServerPlayer sp) MouthSlotHandler.toggle(sp);
        });
    }
}
