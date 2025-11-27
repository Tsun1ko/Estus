package net.tsuniko.item;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tsuniko.attachment.ModAttachmentTypes;
import net.tsuniko.attachment.ChargesAttachedData;

import java.util.List;

public class EstusShardItem extends Item {
    public EstusShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block target = world.getBlockState(context.getBlockPos()).getBlock();

        if (target instanceof AnvilBlock) {
            if (addCharges(context.getPlayer().getStackInHand(context.getHand()), context.getPlayer(), world)) {
                if (world.isClient()) context.getPlayer().sendMessage(Text.translatable("info.estus.upgrade"));

                if (!world.isClient()) {
                    ServerWorld serverWorld = (ServerWorld) context.getWorld();

                    serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, target.getDefaultState()),
                            context.getBlockPos().getX() + 0.5,
                            context.getBlockPos().getY() + 1,
                            context.getBlockPos().getZ() + 0.5,
                            15,0,0,0,1);

                    serverWorld.playSound(null, context.getBlockPos(),
                            SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, SoundCategory.BLOCKS, 1f, 1f);
                }

                return ActionResult.SUCCESS;
            } else {

                return ActionResult.PASS;
            }
        }
        return ActionResult.PASS;
    }

    private boolean addCharges(ItemStack itemStack, PlayerEntity player, World world) {
        if (itemStack == null || player == null) {
            if (world.isClient() && player != null) player.sendMessage(Text.translatable("info.estus.upgrade_failed"));
            return false;
        }

        int maxCharges = player.getAttachedOrCreate(ModAttachmentTypes.MAX_ESTUS_CHARGES).charges();
        int requiredShards = Math.toIntExact(Math.round(Math.pow(maxCharges - 2, 1.5)));

        if (itemStack.getCount() >= requiredShards) {
            itemStack.decrement(requiredShards);

            player.setAttached(ModAttachmentTypes.MAX_ESTUS_CHARGES, new ChargesAttachedData(maxCharges + 1));
            player.setAttached(ModAttachmentTypes.ESTUS_CHARGES, new ChargesAttachedData(maxCharges + 1));
            return true;
        } else {
            player.sendMessage(Text.translatable("info.estus.insufficient_shards", requiredShards));
            return false;
        }

    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("info.estus.estus_shard"));
        super.appendTooltip(stack, context, tooltip, type);
    }
}
