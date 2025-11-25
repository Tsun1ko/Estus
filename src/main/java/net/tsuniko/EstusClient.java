package net.tsuniko;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

public class EstusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

    }

    public static PlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }
}
