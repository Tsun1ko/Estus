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

                    if (charges <= 0) return 0.0f;
                    else if (charges == maxCharges) return 1.0f;
                    else if ((double) charges / maxCharges <= 0.1) return 0.1f;
                    else if ((double) charges / maxCharges <= 0.2) return 0.2f;
                    else if ((double) charges / maxCharges <= 0.3) return 0.3f;
                    else if ((double) charges / maxCharges <= 0.4) return 0.4f;
                    else if ((double) charges / maxCharges <= 0.5) return 0.5f;
                    else if ((double) charges / maxCharges <= 0.6) return 0.6f;
                    else if ((double) charges / maxCharges <= 0.7) return 0.7f;
                    else if ((double) charges / maxCharges <= 0.8) return 0.8f;
                    else return 0.9f;
                });
    }
}
