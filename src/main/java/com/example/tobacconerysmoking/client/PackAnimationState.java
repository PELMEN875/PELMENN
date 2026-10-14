package com.example.tobacconerysmoking.client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

/** Плавный прогресс "пачка поднята", пока открыто радиальное меню выбора сигареты Tobacconery. */
public final class PackAnimationState {
    private static float prev, cur;

    private PackAnimationState() {}

    public static void tick(Minecraft mc) {
        boolean open = mc.screen != null && mc.screen.getClass().getSimpleName().equals("CigaretteRadialMenu");
        prev = cur;
        cur = Mth.approach(cur, open ? 1.0F : 0.0F, 0.25F);
    }

    public static float get(float partialTick) {
        float p = Mth.lerp(partialTick, prev, cur);
        return p * p * (3.0F - 2.0F * p);
    }
}
