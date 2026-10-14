package com.example.tobacconerysmoking;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

/** Серверная логика слота "рот": положить/достать сигарету и догорание. */
@EventBusSubscriber(modid = TobacconerySmoking.MODID)
public class MouthSlotHandler {
    public static final String SLOT = "mouth";

    /** Сколько единиц прочности съедается за шаг. Подберите так, чтобы время горения совпало с Tobacconery. */
    public static final int DAMAGE_PER_STEP = 1;
    /** Шаг в тиках (1 = каждый тик). */
    public static final int STEP_TICKS = 1;

    public static void toggle(ServerPlayer player) {
        CuriosApi.getCuriosInventory(player).flatMap(inv -> inv.getStacksHandler(SLOT)).ifPresent(handler -> {
            IDynamicStackHandler stacks = handler.getStacks();
            if (stacks.getSlots() == 0) return;

            ItemStack inMouth = stacks.getStackInSlot(0);
            if (!inMouth.isEmpty()) {                       // достать
                stacks.setStackInSlot(0, ItemStack.EMPTY);
                if (!player.getInventory().add(inMouth)) player.drop(inMouth, false);
                sound(player, false);
                return;
            }
            for (InteractionHand hand : InteractionHand.values()) {   // вставить из руки
                ItemStack held = player.getItemInHand(hand);
                if (SmokeItems.isLitCigarette(held)) {
                    stacks.setStackInSlot(0, held.split(1));
                    sound(player, true);
                    return;
                }
            }
            player.displayClientMessage(Component.translatable("message.tobacconery_smoking.need_lit"), true);
        });
    }

    private static void sound(ServerPlayer p, boolean in) {
        p.level().playSound(null, p.blockPosition(), in ? SoundEvents.ITEM_PICKUP : SoundEvents.ITEM_FRAME_REMOVE_ITEM,
            SoundSource.PLAYERS, 0.4F, in ? 1.4F : 1.0F);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        if (!(player.level() instanceof ServerLevel level)) return;
        if (player.tickCount % STEP_TICKS != 0) return;

        CuriosApi.getCuriosInventory(player).flatMap(inv -> inv.getStacksHandler(SLOT)).ifPresent(handler -> {
            IDynamicStackHandler stacks = handler.getStacks();
            for (int i = 0; i < stacks.getSlots(); i++) {
                ItemStack stack = stacks.getStackInSlot(i);
                if (stack.isEmpty() || !SmokeItems.isLitCigarette(stack)) continue;
                if (player.isInWaterOrRain()) continue;        // под водой/дождём не тлеет

                int dmg = stack.getDamageValue() + DAMAGE_PER_STEP;
                if (stack.isDamageableItem() && dmg >= stack.getMaxDamage()) {   // сгорела
                    stacks.setStackInSlot(i, ItemStack.EMPTY);
                    continue;
                }
                if (stack.isDamageableItem()) stack.setDamageValue(dmg);

                if (player.tickCount % 12 == 0) {
                    Vec3 pos = player.getEyePosition().add(player.getLookAngle().scale(0.32)).add(0, -0.2, 0);
                    level.sendParticles(ParticleTypes.SMOKE, pos.x, pos.y, pos.z, 1, 0.02, 0.02, 0.02, 0.0);
                }
            }
        });
    }
}
