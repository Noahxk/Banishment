package com.noahxk.banishment.item.custom;

import com.noahxk.banishment.misc.Banish;
import net.minecraft.core.particles.ParticleTypes;
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

    private LivingEntity banishmentTarget;
    private Player banisher;

    // The crucifix's use duration in ticks
    private static final int USE_DURATION = 100;

    // Sets the 10-second use duration
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    // Executes when the player starts using the item on another player
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        if(target.isAlive() && !Banish.isEntityBanished(target) && target instanceof Player && target.getHealth() <= target.getMaxHealth() / 2) {
            banishmentTarget = target;
        } else return InteractionResult.FAIL;
        banisher = player;

        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, USE_DURATION, 1, false, false, false));
        player.startUsingItem(usedHand);

        return InteractionResult.SUCCESS;
    }

    // Executes every tick while using the crucifix
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, banishmentTarget.getX(), banishmentTarget.getY(), banishmentTarget.getZ(), 1, 1 ,1 ,1, 0.001);
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    // Executes if the player used the crucifix for the full use duration
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            banisher.stopUsingItem();
            Banish.banish(level, banishmentTarget, banisher);

            stack.hurtAndBreak(1, (ServerLevel) level, banisher,
                    item -> banisher.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    // Executes when if the player releases the crucifix early
    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if(!level.isClientSide()) {
            banisher.stopUsingItem();
            banishmentTarget.removeEffect(MobEffects.LEVITATION);
        }

        return super.releaseUsing(stack, level, entity, timeLeft);
    }
}
