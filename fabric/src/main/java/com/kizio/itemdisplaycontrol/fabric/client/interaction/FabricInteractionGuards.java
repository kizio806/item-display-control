package com.kizio.itemdisplaycontrol.fabric.client.interaction;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.interaction.ProtectionTargetResolver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public final class FabricInteractionGuards {

    private static boolean placementBypassActive;

    private FabricInteractionGuards() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (placementBypassActive) {
                return ActionResult.PASS;
            }

            ProtectionTarget target = resolveBlockTarget(world.getBlockState(hitResult.getBlockPos()).getBlock());
            if (!shouldBlock(player, hand, world, target)) {
                return ActionResult.PASS;
            }

            attemptPlacementBypass(player, hand, createPlacementHitResult(hitResult));
            return ActionResult.FAIL;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (placementBypassActive) {
                return ActionResult.PASS;
            }

            ProtectionTarget target = resolveEntityTarget(entity);
            if (!shouldBlock(player, hand, world, target)) {
                return ActionResult.PASS;
            }

            attemptPlacementBypass(player, hand, createPlacementHitResult(entity, hitResult));
            return ActionResult.FAIL;
        });
    }

    private static boolean shouldBlock(PlayerEntity player, Hand hand, World world, ProtectionTarget target) {
        if (target == null || player == null || world == null || !world.isClient()) {
            return false;
        }
        if (player.isSpectator() || player.isSneaking()) {
            return false;
        }

        ItemStack stack = player.getStackInHand(hand);
        return !stack.isEmpty() && ItemDisplayControl.shouldBlock(target, resolveItemId(stack.getItem()));
    }

    private static void attemptPlacementBypass(PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (!(player instanceof ClientPlayerEntity clientPlayer) || hitResult == null) {
            return;
        }

        ItemStack stack = player.getStackInHand(hand);
        if (!(stack.getItem() instanceof BlockItem)) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.interactionManager == null || client.player != clientPlayer) {
            return;
        }

        ItemPlacementContext placementContext = new ItemPlacementContext(player, hand, stack, hitResult);
        if (!placementContext.canPlace()) {
            return;
        }

        placementBypassActive = true;
        try {
            ActionResult result = client.interactionManager.interactBlock(clientPlayer, hand, hitResult);
            if (result instanceof ActionResult.Success success
                    && success.swingSource() == ActionResult.SwingSource.CLIENT) {
                player.swingHand(hand);
            }
        } finally {
            placementBypassActive = false;
        }
    }

    private static BlockHitResult createPlacementHitResult(BlockHitResult originalHitResult) {
        Direction clickedSide = originalHitResult.getSide();
        BlockPos placementPos = originalHitResult.getBlockPos().offset(clickedSide);
        Direction placementSide = clickedSide.getOpposite();
        return new BlockHitResult(
                projectHitPosition(originalHitResult.getPos(), placementPos, placementSide),
                placementSide,
                placementPos,
                false
        );
    }

    private static BlockHitResult createPlacementHitResult(Entity entity, EntityHitResult hitResult) {
        if (!(entity instanceof ItemFrameEntity itemFrame) || hitResult == null) {
            return null;
        }

        Direction frameFacing = itemFrame.getHorizontalFacing();
        return new BlockHitResult(
                projectHitPosition(hitResult.getPos(), itemFrame.getAttachedBlockPos(), frameFacing.getOpposite()),
                frameFacing.getOpposite(),
                itemFrame.getAttachedBlockPos(),
                false
        );
    }

    private static Vec3d projectHitPosition(Vec3d originalPos, BlockPos blockPos, Direction side) {
        double x = clampToBlock(originalPos.x, blockPos.getX());
        double y = clampToBlock(originalPos.y, blockPos.getY());
        double z = clampToBlock(originalPos.z, blockPos.getZ());

        return switch (side.getAxis()) {
            case X -> new Vec3d(side == Direction.WEST ? blockPos.getX() : blockPos.getX() + 1.0D, y, z);
            case Y -> new Vec3d(x, side == Direction.DOWN ? blockPos.getY() : blockPos.getY() + 1.0D, z);
            case Z -> new Vec3d(x, y, side == Direction.NORTH ? blockPos.getZ() : blockPos.getZ() + 1.0D);
        };
    }

    private static double clampToBlock(double coordinate, int blockCoordinate) {
        return Math.max(blockCoordinate, Math.min(blockCoordinate + 1.0D, coordinate));
    }

    private static ProtectionTarget resolveBlockTarget(Block block) {
        Identifier blockId = Registries.BLOCK.getId(block);
        if (blockId == null) {
            return null;
        }

        return ProtectionTargetResolver.resolveBlockTarget(blockId.getNamespace(), blockId.getPath());
    }

    private static ProtectionTarget resolveEntityTarget(Entity entity) {
        Identifier entityId = Registries.ENTITY_TYPE.getId(entity.getType());
        if (entityId == null) {
            return null;
        }

        return ProtectionTargetResolver.resolveEntityTarget(entityId.getNamespace(), entityId.getPath());
    }

    private static String resolveItemId(Item item) {
        Identifier itemId = Registries.ITEM.getId(item);
        return itemId != null ? itemId.toString() : null;
    }
}
