package com.kizio.itemdisplaycontrol.client.interaction;

import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import com.kizio.itemdisplaycontrol.common.interaction.ProtectionTargetResolver;
import com.kizio.itemdisplaycontrol.common.math.ProjectionMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Shared client calculation and resolution helper for placement bypass and target identification.
 * <p>
 * Eliminates duplicate logic between Fabric and NeoForge client interaction listeners.
 */
public final class ClientPlacementHelper {

    private ClientPlacementHelper() {
    }

    /**
     * Projects and creates a placement {@link BlockHitResult} adjacent to the clicked block face.
     *
     * @param originalHitResult the original block raycast hit result.
     * @return a new {@link BlockHitResult} targeting the adjacent block offset.
     */
    public static BlockHitResult createPlacementHitResult(BlockHitResult originalHitResult) {
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

    /**
     * Projects and creates a placement {@link BlockHitResult} against the wall behind an item frame.
     *
     * @param entity the clicked entity.
     * @param hitResult the original entity hit result.
     * @return a new {@link BlockHitResult} targeting the supporting wall block, or {@code null} if not an item frame.
     */
    public static BlockHitResult createPlacementHitResult(Entity entity, EntityHitResult hitResult) {
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

    /**
     * Projects a 3D raycast coordinate onto the surface plane of the target adjacent block.
     *
     * @param originalPos the clicked vector.
     * @param blockPos the target block position.
     * @param side the face of the target block being struck.
     * @return the clamped 3D vector aligned to the face plane.
     */
    public static Vec3 projectHitPosition(Vec3 originalPos, BlockPos blockPos, Direction side) {
        double x = ProjectionMath.clampToBlock(originalPos.x, blockPos.getX());
        double y = ProjectionMath.clampToBlock(originalPos.y, blockPos.getY());
        double z = ProjectionMath.clampToBlock(originalPos.z, blockPos.getZ());

        return switch (side.getAxis()) {
            case X -> new Vec3(side == Direction.WEST ? blockPos.getX() : blockPos.getX() + 1.0D, y, z);
            case Y -> new Vec3(x, side == Direction.DOWN ? blockPos.getY() : blockPos.getY() + 1.0D, z);
            case Z -> new Vec3(x, y, side == Direction.NORTH ? blockPos.getZ() : blockPos.getZ() + 1.0D);
        };
    }

    /**
     * Resolves the {@link ProtectionTarget} matching the given block instance.
     *
     * @param block the block to inspect.
     * @return the corresponding protection target, or {@code null} if unprotected.
     */
    public static ProtectionTarget resolveBlockTarget(Block block) {
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(block);
        return resolveBlockTarget(blockId);
    }

    /**
     * Resolves the {@link ProtectionTarget} matching the given block identifier.
     *
     * @param blockId the block identifier.
     * @return the corresponding protection target, or {@code null} if unprotected.
     */
    public static ProtectionTarget resolveBlockTarget(Identifier blockId) {
        return ProtectionTargetResolver.resolveBlockTarget(blockId.getNamespace(), blockId.getPath());
    }

    /**
     * Resolves the {@link ProtectionTarget} matching the given entity instance.
     *
     * @param entity the entity to inspect.
     * @return the corresponding protection target, or {@code null} if unprotected.
     */
    public static ProtectionTarget resolveEntityTarget(Entity entity) {
        Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        return ProtectionTargetResolver.resolveEntityTarget(entityId.getNamespace(), entityId.getPath());
    }

    /**
     * Formats the string identifier for the given item.
     *
     * @param item the item to format.
     * @return normalized string identifier (e.g. {@code "minecraft:diamond"}).
     */
    public static String resolveItemId(Item item) {
        return BuiltInRegistries.ITEM.getKey(item).toString();
    }
}
