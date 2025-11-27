package net.tsuniko.item;

import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.tsuniko.attachment.ModAttachmentTypes;
import net.tsuniko.attachment.PotencyAttachedData;

public class UndeadBoneShardItem extends Item {
    public UndeadBoneShardItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        Block target = world.getBlockState(context.getBlockPos()).getBlock();

        if (target instanceof CampfireBlock) {
            boolean result = increasePotency(context.getPlayer().getStackInHand(context.getHand()), context.getPlayer());

            if (result) {
                if (world.isClient()) context.getPlayer().sendMessage(Text.translatable("info.estus.potency_upgrade"));

                if (!world.isClient()) {
                    ServerWorld serverWorld = (ServerWorld) context.getWorld();

                    serverWorld.spawnParticles(ParticleTypes.FLAME,
                            context.getBlockPos().getX() + 0.5,
                            context.getBlockPos().getY() + 1,
                            context.getBlockPos().getZ() + 0.5,
                            15,0,0,0,1);

                    serverWorld.playSound(null, context.getBlockPos(),
                            SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1f, 1f);
                }
                return ActionResult.SUCCESS;
            } else {
                context.getPlayer().sendMessage(Text.translatable("info.estus.potency_max_reached"));
                return ActionResult.PASS;
            }




        }
        return ActionResult.PASS;
    }

    private boolean increasePotency(ItemStack itemStack, PlayerEntity player) {
        if (player.getAttachedOrCreate(ModAttachmentTypes.FLASK_POTENCY).potency() >= 5) return false;

        itemStack.decrement(1);

        int potency = player.getAttachedOrCreate(ModAttachmentTypes.FLASK_POTENCY).potency();

        player.setAttached(ModAttachmentTypes.FLASK_POTENCY, new PotencyAttachedData(potency + 1));

        return true;
    }
}
