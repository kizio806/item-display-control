package com.kizio.itemdisplaycontrol.fabric;

import com.kizio.itemdisplaycontrol.client.interaction.ClientPlacementHelper;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class ClientPlacementHelperTest {

    @Test
    void shouldProjectHitPositionAgainstWestAndEastFaces() {
        BlockPos pos = new BlockPos(10, 64, -20);
        Vec3 original = new Vec3(10.3, 64.7, -19.4);

        Vec3 westProjection = ClientPlacementHelper.projectHitPosition(original, pos, Direction.WEST);
        assertEquals(10.0, westProjection.x);
        assertEquals(64.7, westProjection.y);
        assertEquals(-19.4, westProjection.z);

        Vec3 eastProjection = ClientPlacementHelper.projectHitPosition(original, pos, Direction.EAST);
        assertEquals(11.0, eastProjection.x);
        assertEquals(64.7, eastProjection.y);
        assertEquals(-19.4, eastProjection.z);
    }

    @Test
    void shouldProjectHitPositionAgainstDownAndUpFaces() {
        BlockPos pos = new BlockPos(5, 70, 15);
        Vec3 original = new Vec3(5.2, 70.1, 15.8);

        Vec3 downProjection = ClientPlacementHelper.projectHitPosition(original, pos, Direction.DOWN);
        assertEquals(5.2, downProjection.x);
        assertEquals(70.0, downProjection.y);
        assertEquals(15.8, downProjection.z);

        Vec3 upProjection = ClientPlacementHelper.projectHitPosition(original, pos, Direction.UP);
        assertEquals(5.2, upProjection.x);
        assertEquals(71.0, upProjection.y);
        assertEquals(15.8, upProjection.z);
    }

    @Test
    void shouldProjectHitPositionAgainstNorthAndSouthFaces() {
        BlockPos pos = new BlockPos(-3, 60, 42);
        Vec3 original = new Vec3(-2.8, 60.5, 42.1);

        Vec3 northProjection = ClientPlacementHelper.projectHitPosition(original, pos, Direction.NORTH);
        assertEquals(-2.8, northProjection.x);
        assertEquals(60.5, northProjection.y);
        assertEquals(42.0, northProjection.z);

        Vec3 southProjection = ClientPlacementHelper.projectHitPosition(original, pos, Direction.SOUTH);
        assertEquals(-2.8, southProjection.x);
        assertEquals(60.5, southProjection.y);
        assertEquals(43.0, southProjection.z);
    }

    @Test
    void shouldCreatePlacementHitResultAdjacentToClickedFace() {
        BlockPos blockPos = new BlockPos(100, 65, 200);
        BlockHitResult original = new BlockHitResult(
                new Vec3(100.5, 65.5, 200.0),
                Direction.NORTH,
                blockPos,
                false
        );

        BlockHitResult placement = ClientPlacementHelper.createPlacementHitResult(original);

        assertEquals(blockPos.north(), placement.getBlockPos());
        assertEquals(Direction.SOUTH, placement.getDirection());
        assertEquals(100.5, placement.getLocation().x);
        assertEquals(65.5, placement.getLocation().y);
        assertEquals(200.0, placement.getLocation().z);
        assertFalse(placement.isInside());
    }

    @Test
    void shouldResolveKnownBlockIdentifiers() {
        assertEquals(
                ProtectionTarget.FLOWER_POTS,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "flower_pot"))
        );
        assertEquals(
                ProtectionTarget.DECORATED_POTS,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "decorated_pot"))
        );
        assertEquals(
                ProtectionTarget.CHISELED_BOOKSHELVES,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "chiseled_bookshelf"))
        );
        assertEquals(
                ProtectionTarget.LECTERNS,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "lectern"))
        );
        assertEquals(
                ProtectionTarget.JUKEBOXES,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "jukebox"))
        );
        assertEquals(
                ProtectionTarget.CAMPFIRES,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "soul_campfire"))
        );
        assertEquals(
                ProtectionTarget.COMPOSTERS,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "composter"))
        );
        assertEquals(
                ProtectionTarget.RESPAWN_ANCHORS,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "respawn_anchor"))
        );
        assertNull(ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "stone")));
    }
}
