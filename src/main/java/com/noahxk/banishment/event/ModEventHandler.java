package com.noahxk.banishment.event;

import com.noahxk.banishment.data.attachment.ModAttachmentTypes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;

public class ModEventHandler {
    @SubscribeEvent
    public void EntityTravelToDimensionEvent(EntityTravelToDimensionEvent event) {
        if(event.getEntity().hasData(ModAttachmentTypes.BANISHED)) {
            if(event.getEntity().getData(ModAttachmentTypes.BANISHED) == true) event.setCanceled(true);
        }
    }
}
