package net.tsuniko.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ModCustomAttachedData(int charges) {
    public static Codec<ModCustomAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("charges").forGetter(ModCustomAttachedData::charges)
    ).apply(instance, ModCustomAttachedData::new));

    public static PacketCodec<ByteBuf, ModCustomAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static ModCustomAttachedData DEFAULT = new ModCustomAttachedData(3);
}
