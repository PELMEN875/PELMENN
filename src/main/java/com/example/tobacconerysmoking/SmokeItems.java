package com.example.tobacconerysmoking;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** Все ID предметов Create: Tobacconery 1.0.2, с которыми работает аддон. Код не зависит от классов Tobacconery. */
public final class SmokeItems {
    public static final String NS = "tobacconery";

    public static final Set<String> LIT_CIGARETTES = Set.of(
        "lit_cigarette", "lit_menthol_cigarette", "lit_strong_cigarette", "lit_blueberry_cigarette",
        "lit_fire_cigarette", "lit_fruit_cigarette", "lit_jumper_cigarette", "lit_sabbath_cigarette",
        "lit_turtle_cigarette", "lit_death_cigarette", "lit_creative_cigarette");

    /** Предметы, при использовании (удержании ПКМ) которых игрок подносит руку ко рту. */
    public static final Set<String> SMOKABLE = Set.of(
        "lit_cigarette", "lit_menthol_cigarette", "lit_strong_cigarette", "lit_blueberry_cigarette",
        "lit_fire_cigarette", "lit_fruit_cigarette", "lit_jumper_cigarette", "lit_sabbath_cigarette",
        "lit_turtle_cigarette", "lit_death_cigarette", "lit_creative_cigarette",
        "filled_wooden_pipe_regular", "vape", "hookah_hose");

    public static final Set<String> PACKS = Set.of(
        "cigarette_pack", "menthol_cigarette_pack", "strong_cigarette_pack", "blueberry_cigarette_pack",
        "fire_cigarette_pack", "fruit_cigarette_pack", "jumper_cigarette_pack", "sabbath_cigarette_pack",
        "turtle_cigarette_pack", "death_cigarette_pack", "creative_cigarette_pack");

    private SmokeItems() {}

    private static boolean in(ItemStack stack, Set<String> set) {
        if (stack.isEmpty()) return false;
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(stack.getItem());
        return key.getNamespace().equals(NS) && set.contains(key.getPath());
    }

    public static boolean isLitCigarette(ItemStack s) { return in(s, LIT_CIGARETTES); }
    public static boolean isSmokable(ItemStack s) { return in(s, SMOKABLE); }
    public static boolean isPack(ItemStack s) { return in(s, PACKS); }

    public static List<Item> resolve(Set<String> ids) {
        List<Item> out = new ArrayList<>();
        for (String id : ids) {
            BuiltInRegistries.ITEM.getOptional(ResourceLocation.fromNamespaceAndPath(NS, id)).ifPresent(out::add);
        }
        return out;
    }
}
