package com.noahxk.banishment.item;

import com.noahxk.banishment.Banishment;
import com.noahxk.banishment.item.custom.CrucifixItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Banishment.MOD_ID);

    public static final DeferredItem<Item> CRUCIFIX = ITEMS.register("crucifix",
            () -> new CrucifixItem(new Item.Properties().useItemDescriptionPrefix().stacksTo(1).setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("banishment:crucifix")))));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
