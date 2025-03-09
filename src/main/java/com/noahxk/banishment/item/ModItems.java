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

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
