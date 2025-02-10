package com.noahxk.banishment.item.custom;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CrucifixItem extends Item {
    public CrucifixItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {

        System.out.println(entity.getName());

        return super.onLeftClickEntity(stack, player, entity);
    }
}
