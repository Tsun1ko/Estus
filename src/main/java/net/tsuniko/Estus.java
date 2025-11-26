package net.tsuniko;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.tsuniko.attachment.ModAttachmentTypes;
import net.tsuniko.block.ModBlocks;
import net.tsuniko.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Estus implements ModInitializer {
	public static final String MOD_ID = "estus";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModAttachmentTypes.inititalize();

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            refill(newPlayer);
        });

        EntitySleepEvents.START_SLEEPING.register((entity, sleepingPos) -> {
            if (entity instanceof PlayerEntity) refill((PlayerEntity) entity);
        });
	}

    private void refill(PlayerEntity user) {
        if (user == null) user = EstusClient.getPlayer();
        user.setAttached(ModAttachmentTypes.ESTUS_CHARGES, user.getAttachedOrCreate(ModAttachmentTypes.MAX_ESTUS_CHARGES));

        user.sendMessage(Text.translatable("info.estus.flasks_replenished"));
    }
}