package net.tsuniko.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.tsuniko.EstusClient;
import net.tsuniko.attachment.ModAttachmentTypes;
import net.tsuniko.attachment.ModCustomAttachedData;

import java.util.List;

public class EstusFlaskItem extends Item {

    public EstusFlaskItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ModCustomAttachedData charges = user.getAttachedOrCreate(ModAttachmentTypes.ESTUS_CHARGES);

        PlayerEntity playerEntity = user instanceof PlayerEntity ? (PlayerEntity)user : null;
        if (playerEntity instanceof ServerPlayerEntity) {
            Criteria.CONSUME_ITEM.trigger((ServerPlayerEntity)playerEntity, stack);
        }

        if (!world.isClient) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 1, 0));
        }


        if (playerEntity != null) {
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            if (charges.charges() > 0) user.setAttached(ModAttachmentTypes.ESTUS_CHARGES, new ModCustomAttachedData(charges.charges() - 1));
            else user.setAttached(ModAttachmentTypes.ESTUS_CHARGES, new ModCustomAttachedData(0));

            if (user.getAttached(ModAttachmentTypes.ESTUS_CHARGES).charges() <= 0) {
                PlayerInventory inventory = ((PlayerEntity) user).getInventory();
                do {
                    int slot = inventory.getSlotWithStack(new ItemStack(ModItems.ESTUS_FLASK));

                    inventory.setStack(slot, new ItemStack(ModItems.EMPTY_ESTUS_FLASK));
                } while (inventory.getSlotWithStack(new ItemStack(ModItems.ESTUS_FLASK)) != -1);
            }
        }

        user.emitGameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        PlayerEntity user = EstusClient.getPlayer();
        tooltip.add(Text.of(user.getAttachedOrCreate(ModAttachmentTypes.ESTUS_CHARGES).charges() + "/" + user.getAttachedOrCreate(ModAttachmentTypes.MAX_ESTUS_CHARGES).charges()));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
