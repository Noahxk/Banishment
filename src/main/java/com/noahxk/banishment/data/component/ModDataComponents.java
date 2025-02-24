package com.noahxk.banishment.data.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.noahxk.banishment.Banishment;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModDataComponents {
    public record BoundTo(String uuid) {}

    public static final Codec<BoundTo> BASIC_CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("uuid").forGetter(BoundTo::uuid)
            ).apply(instance, BoundTo::new)
    );

    public static final StreamCodec<ByteBuf, BoundTo> BASIC_STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BoundTo::uuid,
            BoundTo::new
    );

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Banishment.MOD_ID);

    public static final Supplier<DataComponentType<BoundTo>> BOUND_TO = DATA_COMPONENTS.registerComponentType(
            "basic",
            builder -> builder
                    .persistent(BASIC_CODEC)
                    .networkSynchronized(BASIC_STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
