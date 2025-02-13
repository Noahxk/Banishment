package com.noahxk.banishment.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CrucifixItem extends Item {
    public CrucifixItem(Properties properties) {
        super(properties);
    }

    public static LivingEntity banishmentTarget;
    public static Player banisher;

    // Happens when the player starts using the item on another player
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {

        if(target.isAlive()) {
            banishmentTarget = target;
        } else return InteractionResult.FAIL;
        banisher = player;
        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 100, 1, false, false, false));
        player.startUsingItem(usedHand);

        return InteractionResult.SUCCESS;
    }

    // Sets the 10-second use duration
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 100;
    }

    // Happens every tick while using the crucifix
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(!level.isClientSide()) {
            System.out.println(remainingUseDuration);
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    // Player used the crucifix for the 10 seconds
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            System.out.println("Used item");
            banisher.stopUsingItem();
            banishmentTarget.kill((ServerLevel) level);

            stack.hurtAndBreak(1, (ServerLevel) level, banisher,
                    item -> banisher.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    // Player released the crucifix early
    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if(!level.isClientSide()) {
            System.out.println("Released early");
            banisher.stopUsingItem();
            banishmentTarget.removeAllEffects();
        }

        return super.releaseUsing(stack, level, entity, timeLeft);
    }
}
