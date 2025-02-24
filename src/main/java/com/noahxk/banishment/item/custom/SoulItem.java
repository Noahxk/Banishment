package com.noahxk.banishment.item.custom;

import com.noahxk.banishment.data.attachment.ModAttachmentTypes;
import com.noahxk.banishment.data.component.ModDataComponents;
import com.noahxk.banishment.worldgen.dimension.ModDimensions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Relative;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.TeleportTransition;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static com.noahxk.banishment.data.attachment.ModAttachmentTypes.BANISHED;

public class SoulItem extends Item {
    public SoulItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide()) {

            if(!context.getItemInHand().has(ModDataComponents.BOUND_TO)) return InteractionResult.FAIL;
            UUID uuid = UUID.fromString(context.getItemInHand().get(ModDataComponents.BOUND_TO).uuid());

            Player player = context.getLevel().getServer().getPlayerList().getPlayer(uuid);
            if(player == null) return InteractionResult.FAIL;

            unbanish(player, context);
        }

        return InteractionResult.SUCCESS;
    }

    public static void unbanish(Player player, UseOnContext context) {
        if(player.hasData(BANISHED)) {
            if(player.getData(BANISHED) == true) {
                player.setData(BANISHED, false);

                ServerLevel serverLevel = (ServerLevel) context.getLevel();
                Set<Relative> deltaMovement = new HashSet<>();
                player.teleport(new TeleportTransition(serverLevel, new Vec3(context.getClickedPos().getX(), context.getClickedPos().getY() + 1, context.getClickedPos().getZ()), new Vec3(0, 0, 0), 0, 0, true, true, deltaMovement, TeleportTransition.PLAY_PORTAL_SOUND));
            }
        }
    }
}
