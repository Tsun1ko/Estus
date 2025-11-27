package net.tsuniko.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record ChargesAttachedData(int charges) {
    public static Codec<ChargesAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("charges").forGetter(ChargesAttachedData::charges)
    ).apply(instance, ChargesAttachedData::new));

    public static PacketCodec<ByteBuf, ChargesAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static ChargesAttachedData DEFAULT = new ChargesAttachedData(3);
}
