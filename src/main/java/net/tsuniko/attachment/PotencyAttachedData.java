package net.tsuniko.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record PotencyAttachedData(int potency) {
    public static Codec<PotencyAttachedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("potency").forGetter(PotencyAttachedData::potency)
    ).apply(instance, PotencyAttachedData::new));

    public static PacketCodec<ByteBuf, PotencyAttachedData> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public static PotencyAttachedData DEFAULT = new PotencyAttachedData(0);
}
