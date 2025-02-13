package com.noahxk.banishment.worldgen.dimension;

import com.noahxk.banishment.Banishment;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ModDimensions {
    public static final ResourceKey<Level> WORLD_GAP_KEY = ResourceKey.create(Registries.DIMENSION,
            ResourceLocation.parse("banishment:world_gap"));
    public static final ResourceKey<DimensionType> WORLD_GAP_TYPE =
            ResourceKey.create(Registries.DIMENSION_TYPE, WORLD_GAP_KEY.location());

    public static void register() {
        System.out.println("Registering ModDimensions for " + Banishment.MOD_ID);
    }
}