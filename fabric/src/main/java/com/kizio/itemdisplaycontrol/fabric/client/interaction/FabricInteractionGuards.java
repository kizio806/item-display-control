package com.kizio.itemdisplaycontrol.fabric.client.interaction;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.interaction.ProtectionTargetResolver;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class FabricInteractionGuards {

    private static boolean placementBypassActive;

    private FabricInteractionGuards() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (placementBypassActive) {
                return InteractionResult.PASS;
            }

            ProtectionTarget target = resolveBlockTarget(world.getBlockState(hitResult.getBlockPos()).getBlock());
            if (!shouldBlock(player, hand, world, target)) {
                return InteractionResult.PASS;
            }

            attemptPlacementBypass(player, hand, createPlacementHitResult(hitResult));
            return InteractionResult.FAIL;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (placementBypassActive) {
                return InteractionResult.PASS;
            }

            ProtectionTarget target = resolveEntityTarget(entity);
            if (!shouldBlock(player, hand, world, target)) {
                return InteractionResult.PASS;
            }

            attemptPlacementBypass(player, hand, createPlacementHitResult(entity, hitResult));
            return InteractionResult.FAIL;
        });
    }

    private static boolean shouldBlock(Player player, InteractionHand hand, Level world, ProtectionTarget target) {
        if (target == null || player == null || world == null || !world.isClientSide()) {
            return false;
        }
        if (player.isSpectator() || player.isShiftKeyDown()) {
            return false;
        }

        ItemStack stack = player.getItemInHand(hand);
        return !stack.isEmpty() && ItemDisplayControl.shouldBlock(target, resolveItemId(stack.getItem()));
    }

    private static void attemptPlacementBypass(Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(player instanceof LocalPlayer clientPlayer) || hitResult == null) {
            return;
        }

        ItemStack stack = player.getItemInHand(hand);
        if (!(stack.getItem() instanceof BlockItem)) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.gameMode == null || client.player != clientPlayer) {
            return;
        }

        BlockPlaceContext placementContext = new BlockPlaceContext(player, hand, stack, hitResult);
        if (!placementContext.canPlace()) {
            return;
        }

        placementBypassActive = true;
        try {
            InteractionResult result = client.gameMode.useItemOn(clientPlayer, hand, hitResult);
            if (result instanceof InteractionResult.Success success) {
                player.swing(hand);
            }
        } finally {
            placementBypassActive = false;
        }
    }

    private static BlockHitResult createPlacementHitResult(BlockHitResult originalHitResult) {
        Direction clickedSide = originalHitResult.getDirection();
        BlockPos placementPos = originalHitResult.getBlockPos().relative(clickedSide);
        Direction placementSide = clickedSide.getOpposite();
        return new BlockHitResult(
                projectHitPosition(originalHitResult.getLocation(), placementPos, placementSide),
                placementSide,
                placementPos,
                false
        );
    }

    private static BlockHitResult createPlacementHitResult(Entity entity, EntityHitResult hitResult) {
        if (!(entity instanceof ItemFrame itemFrame) || hitResult == null) {
            return null;
        }

        Direction frameFacing = itemFrame.getDirection();
        return new BlockHitResult(
                projectHitPosition(hitResult.getLocation(), itemFrame.getPos(), frameFacing.getOpposite()),
                frameFacing.getOpposite(),
                itemFrame.getPos(),
                false
        );
    }

    private static Vec3 projectHitPosition(Vec3 originalPos, BlockPos blockPos, Direction side) {
        double x = clampToBlock(originalPos.x, blockPos.getX());
        double y = clampToBlock(originalPos.y, blockPos.getY());
        double z = clampToBlock(originalPos.z, blockPos.getZ());

        return switch (side.getAxis()) {
            case X -> new Vec3(side == Direction.WEST ? blockPos.getX() : blockPos.getX() + 1.0D, y, z);
            case Y -> new Vec3(x, side == Direction.DOWN ? blockPos.getY() : blockPos.getY() + 1.0D, z);
            case Z -> new Vec3(x, y, side == Direction.NORTH ? blockPos.getZ() : blockPos.getZ() + 1.0D);
        };
    }

    private static double clampToBlock(double coordinate, int blockCoordinate) {
        return Math.max(blockCoordinate, Math.min(blockCoordinate + 1.0D, coordinate));
    }

    private static ProtectionTarget resolveBlockTarget(Block block) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);
        if (blockId == null) {
            return null;
        }

        return ProtectionTargetResolver.resolveBlockTarget(blockId.getNamespace(), blockId.getPath());
    }

    private static ProtectionTarget resolveEntityTarget(Entity entity) {
        Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (entityId == null) {
            return null;
        }

        return ProtectionTargetResolver.resolveEntityTarget(entityId.getNamespace(), entityId.getPath());
    }

    private static String resolveItemId(Item item) {
        Identifier itemId = BuiltInRegistries.ITEM.getKey(item);
        return itemId != null ? itemId.toString() : null;
    }
}
