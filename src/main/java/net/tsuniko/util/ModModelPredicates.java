package net.tsuniko.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.tsuniko.Estus;
import net.tsuniko.EstusClient;
import net.tsuniko.attachment.ModAttachmentTypes;
import net.tsuniko.item.ModItems;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        Estus.LOGGER.info("Registering Model Predicates");

        ModelPredicateProviderRegistry.register(ModItems.ESTUS_FLASK, Identifier.of(Estus.MOD_ID, "charges"),
                (stack, world, entity, seed) -> {
                    if (entity == null) entity = EstusClient.getPlayer();

                    int charges = entity.getAttachedOrCreate(ModAttachmentTypes.ESTUS_CHARGES).charges();
                    int maxCharges = entity.getAttachedOrCreate(ModAttachmentTypes.MAX_ESTUS_CHARGES).charges();

                    if (charges <= 0) {
                        return 0.0f;
                    } else if (charges <= maxCharges / 2) {
                        return 0.5f;
                    } else {
                        return 1.0f;
                    }
                });
    }
}
