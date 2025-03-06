package com.noahxk.banishment.misc;

import com.noahxk.banishment.data.component.ModDataComponents;
import com.noahxk.banishment.item.ModItems;
import com.noahxk.banishment.worldgen.dimension.ModDimensions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

import static com.noahxk.banishment.data.attachment.ModAttachmentTypes.BANISHED;

public class BanishmentStatus {
    public static void banish(Level level, LivingEntity target, Player exiler) {
        if(!level.isClientSide()) {
            // Banishing effects & sound
            level.playSound(null, exiler.blockPosition(), SoundEvents.WITHER_SPAWN, SoundSource.PLAYERS, 100, 1);
            ServerLevel serverLevel = (ServerLevel) level;
            serverLevel.sendParticles(ParticleTypes.FLASH, target.getX(), target.getY(), target.getZ(), 1, 0, 0, 0, 0.001);

            // Teleporting the target to null zone and giving slow falling
            Set<Relative> deltaMovement = new HashSet<>();
            target.teleport(new TeleportTransition(level.getServer().getLevel(ModDimensions.NULL_ZONE_KEY), new Vec3(0, 60, 0), new Vec3(0, 0, 0), 0, 0, true, true, deltaMovement, TeleportTransition.PLAY_PORTAL_SOUND));
            target.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 1, false, false, false));

            // Giving the target the banished data attachment & dropping soul
            target.setData(BANISHED, true);
            exiler.spawnAtLocation(serverLevel, ModItems.SOUL).getItem().set(ModDataComponents.BOUND_TO.get(), new ModDataComponents.BoundTo(target.getStringUUID()));
        }
    }

    public static void unbanish(Player player, UseOnContext context) {
        if(player.hasData(BANISHED)) {
            if(player.getData(BANISHED) == true) {
                // Unbanishing effect
                ServerLevel serverLevel = (ServerLevel) context.getLevel();
                serverLevel.sendParticles(ParticleTypes.FLASH, context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5, 1, 0, 0, 0, 0.001);

                // Setting the target's banished data attachment to false
                player.setData(BANISHED, false);

                // Teleporting them back to where their soul was used
                Set<Relative> deltaMovement = new HashSet<>();
                player.teleport(new TeleportTransition(serverLevel, new Vec3(context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1, context.getClickedPos().getZ() + 0.5), new Vec3(0, 0, 0), 0, 0, true, true, deltaMovement, TeleportTransition.PLAY_PORTAL_SOUND));
            }
        }
    }

    public static boolean isEntityBanished(LivingEntity entity) {
        return entity.getData(BANISHED);
    }
}
