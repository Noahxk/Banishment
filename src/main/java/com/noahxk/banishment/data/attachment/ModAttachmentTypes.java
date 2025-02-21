package com.noahxk.banishment.data.attachment;

import com.mojang.serialization.Codec;
import com.noahxk.banishment.Banishment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachmentTypes {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Banishment.MOD_ID);

    public static final Supplier<AttachmentType<Boolean>> BANISHED = ATTACHMENT_TYPES.register(
            "banished", () -> AttachmentType.builder(() -> false).serialize(Codec.BOOL).build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
