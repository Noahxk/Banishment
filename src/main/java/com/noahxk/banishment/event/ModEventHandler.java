package com.noahxk.banishment.event;

import com.noahxk.banishment.data.attachment.ModAttachmentTypes;
import com.noahxk.banishment.item.ModItems;
import com.noahxk.banishment.item.custom.SoulItem;
import com.noahxk.banishment.worldgen.dimension.ModDimensions;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;
import net.neoforged.neoforge.event.entity.item.ItemExpireEvent;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.HashSet;
import java.util.Set;

public class ModEventHandler {
    @SubscribeEvent
    public void EntityTravelToDimensionEvent(EntityTravelToDimensionEvent event) {
        if(event.getEntity().hasData(ModAttachmentTypes.BANISHED)) {
            if(event.getEntity().getData(ModAttachmentTypes.BANISHED) == true) {
                if(event.getDimension() != ModDimensions.NULL_ZONE_KEY) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void PlayerRespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        System.out.println("Player Respawned");
        Player player = event.getEntity();

        if(player.hasData(ModAttachmentTypes.BANISHED)) {
            if(player.getData(ModAttachmentTypes.BANISHED) == true) {
                Set<Relative> deltaMovement = new HashSet<>();
                player.teleport(new TeleportTransition(player.level().getServer().getLevel(ModDimensions.NULL_ZONE_KEY), new Vec3(0, 60, 0), new Vec3(0, 0, 0), 0, 0, true, true, deltaMovement, TeleportTransition.PLAY_PORTAL_SOUND));
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 200, 1, false, false, false));
                System.out.println("Player teleported to null zone");
            }
        }
    }

    @SubscribeEvent
    public void ItemTossEvent(ItemTossEvent event) {
        if(event.getEntity().getItem().getItem() instanceof SoulItem) {
            event.setCanceled(true);
            event.getPlayer().addItem(event.getEntity().getItem());
        }
    }

    @SubscribeEvent
    public void ItemExpireEvent(ItemExpireEvent event) {
        if(event.getEntity().getItem().getItem() instanceof SoulItem) {
            event.getEntity().setUnlimitedLifetime();
        }
    }
}
