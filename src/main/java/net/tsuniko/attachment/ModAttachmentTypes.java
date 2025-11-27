package net.tsuniko.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import net.tsuniko.Estus;

public class ModAttachmentTypes {
    public static final AttachmentType<ChargesAttachedData> ESTUS_CHARGES = AttachmentRegistry.create(
            Identifier.of(Estus.MOD_ID, "estus_charges"),
            builder->builder
                    .initializer(()-> ChargesAttachedData.DEFAULT)
                    .persistent(ChargesAttachedData.CODEC)
                    .syncWith(
                            ChargesAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.targetOnly()
                    )
    );

    public static final AttachmentType<ChargesAttachedData> MAX_ESTUS_CHARGES = AttachmentRegistry.create(
            Identifier.of(Estus.MOD_ID, "max_estus_charges"),
            builder->builder
                    .initializer(()-> ChargesAttachedData.DEFAULT)
                    .persistent(ChargesAttachedData.CODEC)
                    .syncWith(
                            ChargesAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.targetOnly()
                    )
    );

    public static final AttachmentType<PotencyAttachedData> FLASK_POTENCY = AttachmentRegistry.create(
            Identifier.of(Estus.MOD_ID, "flask_potency"),
            builder->builder
                    .initializer(()-> PotencyAttachedData.DEFAULT)
                    .persistent(PotencyAttachedData.CODEC)
                    .syncWith(
                            PotencyAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.targetOnly()
                    )
    );

    public static void inititalize() {}
}
