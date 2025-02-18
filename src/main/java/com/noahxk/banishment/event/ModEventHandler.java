package com.noahxk.banishment.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityTravelToDimensionEvent;

public class ModEventHandler {
    @SubscribeEvent
    public void EntityTravelToDimensionEvent(EntityTravelToDimensionEvent event) {
        System.out.println(event.getEntity().getName() + "to" + event.getDimension());
    }
}
