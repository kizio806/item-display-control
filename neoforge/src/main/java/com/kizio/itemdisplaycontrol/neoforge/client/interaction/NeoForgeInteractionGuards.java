package com.kizio.itemdisplaycontrol.neoforge.client.interaction;

import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.interaction.ProtectionTargetResolver;
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
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;

@OnlyIn(Dist.CLIENT)
public final class NeoForgeInteractionGuards {

    private static boolean placementBypassActive;

    private NeoForgeInteractionGuards() {
    }

    public static void onInteractionKeyMappingTriggered(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isUseItem()) {
            return;
        }
        if (placementBypassActive) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null || client.level == null || client.hitResult == null) {
            return;
        }
        if (client.player.isSpectator() || client.player.isShiftKeyDown()) {
            return;
        }

        InteractionHand hand = event.getHand();
        ItemStack stack = client.player.getItemInHand(hand);
        if (stack.isEmpty()) {
            return;
        }

        ProtectionTarget target = resolveTarget(client.hitResult, client);
        if (target == null || !ItemDisplayControl.shouldBlock(target, resolveItemId(stack.getItem()))) {
            return;
        }

        attemptPlacementBypass(client, hand, client.hitResult);
        event.setSwingHand(false);
        event.setCanceled(true);
    }

    private static void attemptPlacementBypass(Minecraft client, InteractionHand hand, HitResult hitResult) {
        if (!(client.player instanceof LocalPlayer localPlayer) || client.gameMode == null) {
            return;
        }

        ItemStack stack = localPlayer.getItemInHand(hand);
        if (!(stack.getItem() instanceof BlockItem)) {
            return;
        }

        BlockHitResult placementHitResult = createPlacementHitResult(hitResult);
        if (placementHitResult == null) {
            return;
        }

        BlockPlaceContext placementContext = new BlockPlaceContext(localPlayer, hand, stack, placementHitResult);
        if (!placementContext.canPlace()) {
            return;
        }

        placementBypassActive = true;
        try {
            InteractionResult result = client.gameMode.useItemOn(localPlayer, hand, placementHitResult);
            if (result == InteractionResult.SUCCESS) {
                localPlayer.swing(hand);
            }
        } finally {
            placementBypassActive = false;
        }
    }

    private static ProtectionTarget resolveTarget(HitResult hitResult, Minecraft client) {
        return switch (hitResult.getType()) {
            case BLOCK -> resolveBlockTarget(
                    BuiltInRegistries.BLOCK.getKey(
                            client.level.getBlockState(((BlockHitResult) hitResult).getBlockPos()).getBlock()
                    )
            );
            case ENTITY -> resolveEntityTarget(((EntityHitResult) hitResult).getEntity());
            default -> null;
        };
    }

    private static BlockHitResult createPlacementHitResult(HitResult hitResult) {
        return switch (hitResult.getType()) {
            case BLOCK -> createPlacementHitResultFromBlock((BlockHitResult) hitResult);
            case ENTITY -> createPlacementHitResultFromEntity((EntityHitResult) hitResult);
            default -> null;
        };
    }

    private static BlockHitResult createPlacementHitResultFromBlock(BlockHitResult originalHitResult) {
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

    private static BlockHitResult createPlacementHitResultFromEntity(EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof ItemFrame itemFrame)) {
            return null;
        }

        Direction frameFacing = itemFrame.getDirection();
        return new BlockHitResult(
                projectHitPosition(hitResult.getLocation(), itemFrame.blockPosition(), frameFacing.getOpposite()),
                frameFacing.getOpposite(),
                itemFrame.blockPosition(),
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

    private static ProtectionTarget resolveBlockTarget(Identifier blockId) {
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
