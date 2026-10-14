package com.example.tobacconerysmoking.client;

/**
 * ВСЕ числа анимаций в одном месте. Я не мог запустить игру, поэтому значения подобраны расчётом,
 * а не на глаз: подкрутите их после первого запуска (/reload не нужен, нужен перезапуск клиента).
 */
public final class SmokeTuning {
    private SmokeTuning() {}

    /** За сколько тиков рука доходит до рта. */
    public static final float RAISE_TICKS = 6.0F;

    // --- Третье лицо (повороты руки модели, радианы) ---
    public static final float ARM_X = -2.25F;     // поднятие руки вперёд-вверх (чем меньше, тем выше)
    public static final float ARM_Y_IN = 0.50F;   // поворот руки к центру тела
    public static final float ARM_Z = 0.0F;
    public static final float HEAD_PITCH_FOLLOW = 0.5F;  // рука следует за наклоном головы

    // --- Первое лицо, курение (блоки) ---
    public static final float FP_DX = 0.40F;      // смещение к центру экрана
    public static final float FP_DY = 0.42F;      // вверх
    public static final float FP_DZ = 0.10F;      // к камере
    public static final float FP_ROT_X = -30.0F;
    public static final float FP_ROT_Y = 25.0F;
    public static final float FP_ROT_Z = -15.0F;

    // --- Первое лицо, пачка при открытом радиальном меню ---
    public static final float PACK_DX = 0.30F;
    public static final float PACK_DY = 0.30F;
    public static final float PACK_DZ = 0.20F;
    public static final float PACK_ROT_X = -20.0F;
    public static final float PACK_ROT_Y = 15.0F;
}
