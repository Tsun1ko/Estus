package net.tsuniko;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.tsuniko.util.ModModelPredicates;

public class EstusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModelPredicates.registerModelPredicates();
    }

    public static PlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }
}
