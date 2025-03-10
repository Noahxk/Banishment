package com.noahxk.banishment.item;

import com.noahxk.banishment.Banishment;
import com.noahxk.banishment.data.component.ModDataComponents;
import com.noahxk.banishment.item.custom.CrucifixItem;
import com.noahxk.banishment.item.custom.SoulItem;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.DamageResistant;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Banishment.MOD_ID);

    public static final DeferredItem<Item> CRUCIFIX = ITEMS.register("crucifix",
            () -> new CrucifixItem(new Item.Properties()
                    .useItemDescriptionPrefix()
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .stacksTo(1)
                    .durability(1)
                    .rarity(Rarity.EPIC)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:crucifix")))
            ));

    public static final DeferredItem<Item> SOUL = ITEMS.register("soul",
            () -> new SoulItem(new Item.Properties()
                    .useItemDescriptionPrefix()
                    .component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true)
                    .stacksTo(1)
                    .durability(1)
                    .rarity(Rarity.EPIC)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:soul")))
                    .fireResistant()
                    .component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(DamageTypeTags.IS_EXPLOSION))
                    .component(DataComponents.DAMAGE_RESISTANT, new DamageResistant(DamageTypeTags.IS_FIRE))
            ));

    public static final DeferredItem<Item> CRUCIFIX_CASING = ITEMS.register("crucifix_casing",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:crucifix_casing")))));

    public static final DeferredItem<Item> AETHER_CRYSTAL = ITEMS.register("aether_crystal",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:aether_crystal")))));

    public static final DeferredItem<Item> END_CRYSTAL = ITEMS.register("end_crystal",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:end_crystal")))));

    public static final DeferredItem<Item> NETHER_CRYSTAL = ITEMS.register("nether_crystal",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:nether_crystal")))));

    public static final DeferredItem<Item> OVERWORLD_CRYSTAL = ITEMS.register("overworld_crystal",
            () -> new Item(new Item.Properties().useItemDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:overworld_crystal")))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
