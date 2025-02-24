package com.noahxk.banishment.item.custom;

import com.noahxk.banishment.data.attachment.ModAttachmentTypes;
import com.noahxk.banishment.data.component.ModDataComponents;
import com.noahxk.banishment.item.ModItems;
import com.noahxk.banishment.worldgen.dimension.ModDimensions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.HashSet;
import java.util.Set;

import static com.noahxk.banishment.data.attachment.ModAttachmentTypes.BANISHED;

public class CrucifixItem extends Item {
    public CrucifixItem(Properties properties) {
        super(properties);
    }

    private LivingEntity banishmentTarget;
    private Player banisher;

    // The crucifix's use duration in ticks
    private static final int USE_DURATION = 100;

    // Happens when the player starts using the item on another player
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand usedHand) {
        if(target.isAlive() && target.getData(BANISHED) == false && target instanceof Player) {
            banishmentTarget = target;
        } else return InteractionResult.FAIL;
        banisher = player;

        target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, USE_DURATION, 1, false, false, false));
        player.startUsingItem(usedHand);

        return InteractionResult.SUCCESS;
    }

    // Sets the 10-second use duration
    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return USE_DURATION;
    }

    // Happens every tick while using the crucifix
    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        if(!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(ParticleTypes.TRIAL_SPAWNER_DETECTED_PLAYER, banishmentTarget.getX(), banishmentTarget.getY(), banishmentTarget.getZ(), 1, 1 ,1 ,1, 0.001);
        }

        super.onUseTick(level, livingEntity, stack, remainingUseDuration);
    }

    // Player used the crucifix for the 10 seconds
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if(!level.isClientSide()) {
            banisher.stopUsingItem();
            banish(level, banishmentTarget, banisher);

            stack.hurtAndBreak(1, (ServerLevel) level, banisher,
                    item -> banisher.onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
        }

        return super.finishUsingItem(stack, level, livingEntity);
    }

    // Player released the crucifix early
    @Override
    public boolean releaseUsing(ItemStack stack, Level level, LivingEntity entity, int timeLeft) {
        if(!level.isClientSide()) {
            banisher.stopUsingItem();
            banishmentTarget.removeEffect(MobEffects.LEVITATION);
        }

        return super.releaseUsing(stack, level, entity, timeLeft);
    }

    public static void banish(Level level, LivingEntity target, Player exiler) {
        if(!level.isClientSide()) {
            level.playSound(null, exiler.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 100, 1);

            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(ParticleTypes.FLASH, target.getX(), target.getY(), target.getZ(), 1, 0, 0, 0, 0.001);

            Set<Relative> deltaMovement = new HashSet<>();
            target.teleport(new TeleportTransition(level.getServer().getLevel(ModDimensions.NULL_ZONE_KEY), new Vec3(0, 60, 0), new Vec3(0, 0, 0), 0, 0, true, true, deltaMovement, TeleportTransition.PLAY_PORTAL_SOUND));
            target.setData(BANISHED, true);
            target.spawnAtLocation(serverLevel, ModItems.SOUL).getItem().set(ModDataComponents.BOUND_TO.get(), new ModDataComponents.BoundTo(target.getStringUUID()));
        }
    }
}
