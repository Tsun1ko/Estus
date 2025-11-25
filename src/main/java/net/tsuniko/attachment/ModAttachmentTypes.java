package net.tsuniko.attachment;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import net.tsuniko.Estus;

public class ModAttachmentTypes {
    public static final AttachmentType<ModCustomAttachedData> ESTUS_CHARGES = AttachmentRegistry.create(
            Identifier.of(Estus.MOD_ID, "estus_charges"),
            builder->builder
                    .initializer(()->ModCustomAttachedData.DEFAULT)
                    .persistent(ModCustomAttachedData.CODEC)
                    .syncWith(
                            ModCustomAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.targetOnly()
                    )
    );
    public static final AttachmentType<ModCustomAttachedData> MAX_ESTUS_CHARGES = AttachmentRegistry.create(
            Identifier.of(Estus.MOD_ID, "max_estus_charges"),
            builder->builder
                    .initializer(()->ModCustomAttachedData.DEFAULT)
                    .persistent(ModCustomAttachedData.CODEC)
                    .syncWith(
                            ModCustomAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.targetOnly()
                    )
    );

    public static void init() {}
}
