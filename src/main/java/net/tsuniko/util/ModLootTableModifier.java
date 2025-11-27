package net.tsuniko.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.registry.RegistryKey;
import net.tsuniko.item.ModItems;

public class ModLootTableModifier {
    private static final RegistryKey<LootTable> BURIED_TREASURE_ID = LootTables.BURIED_TREASURE_CHEST;
    private static final RegistryKey<LootTable> DESERT_PYRAMID_ID = LootTables.DESERT_PYRAMID_CHEST;
    private static final RegistryKey<LootTable> JUNGLE_TEMPLE_ID = LootTables.JUNGLE_TEMPLE_CHEST;
    private static final RegistryKey<LootTable> SIMPLE_DUNGEON_ID = LootTables.SIMPLE_DUNGEON_CHEST;
    private static final RegistryKey<LootTable> MINESHAFT_ID = LootTables.ABANDONED_MINESHAFT_CHEST;
    private static final RegistryKey<LootTable> TRAIL_RUINS_COMMON_ID = LootTables.TRAIL_RUINS_COMMON_ARCHAEOLOGY;

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((registryKey, builder, lootTableSource, wrapperLookup) -> {
            if (lootTableSource.isBuiltin() && (
                    BURIED_TREASURE_ID.equals(registryKey) ||
                    DESERT_PYRAMID_ID.equals(registryKey) ||
                    JUNGLE_TEMPLE_ID.equals(registryKey) ||
                            SIMPLE_DUNGEON_ID.equals(registryKey) ||
                            MINESHAFT_ID.equals(registryKey)
            )) {
                builder.modifyPools(pool -> {
                    pool.with(
                            ItemEntry.builder(ModItems.ESTUS_SHARD)
                                    .weight(5)
                    );
                });
            }
            else if (TRAIL_RUINS_COMMON_ID.equals(registryKey)) {
                builder.modifyPools(pool -> {
                    pool.with(
                            ItemEntry.builder(ModItems.ESTUS_SHARD)
                    );
                });
            }
        }));
    }
}
