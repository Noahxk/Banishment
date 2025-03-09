package com.noahxk.banishment.item.custom;

import com.noahxk.banishment.data.component.ModDataComponents;
import com.noahxk.banishment.misc.Banish;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class SoulItem extends Item {
    public SoulItem(Properties properties) {
        super(properties);
    }

    // Executes when used on a block
    @Override
    public InteractionResult useOn(UseOnContext context) {
        if(!context.getLevel().isClientSide()) {

            if(!context.getItemInHand().has(ModDataComponents.BOUND_TO)) return InteractionResult.FAIL;
            UUID uuid = UUID.fromString(context.getItemInHand().get(ModDataComponents.BOUND_TO).uuid());

            Player player = context.getLevel().getServer().getPlayerList().getPlayer(uuid);
            if(player == null) {
                ServerLevel serverLevel = (ServerLevel) context.getLevel();
                serverLevel.sendParticles(ParticleTypes.ASH, context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.1, context.getClickedPos().getZ() + 0.5, 20, 0.1, 0.1, 0.1, 0.00001);
                return InteractionResult.FAIL;
            }

            Banish.unbanish(player, context);

            context.getItemInHand().hurtAndBreak(1, (ServerLevel) context.getLevel(), context.getPlayer(),
                    item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));
        }

        return InteractionResult.SUCCESS;
    }
}
