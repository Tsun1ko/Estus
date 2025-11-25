package net.tsuniko.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tsuniko.Estus;

public class ModItems {
    // Item Group
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(Estus.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.ESTUS_FLASK))
            .displayName(Text.translatable("itemGroup.estus"))
            .build();

    public static final Item ESTUS_SHARD = register(new Item(new Item.Settings()), "estus_shard");
    public static final Item ESTUS_FLASK = register(new EstusFlaskItem(new Item.Settings().maxCount(1)), "estus_flask");
    public static final Item EMPTY_ESTUS_FLASK = register(new Item(new Item.Settings().maxCount(1)), "empty_estus_flask");

    public static Item register(Item item, String id) {
        Identifier itemID = Identifier.of(Estus.MOD_ID, id);

        return Registry.register(Registries.ITEM, itemID, item);
    }

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModItems.ESTUS_SHARD);
            itemGroup.add(ModItems.ESTUS_FLASK);
            itemGroup.add(ModItems.EMPTY_ESTUS_FLASK);
        });
    }
}
