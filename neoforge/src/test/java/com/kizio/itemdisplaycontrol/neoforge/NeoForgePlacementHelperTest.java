package com.kizio.itemdisplaycontrol.neoforge;

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

class NeoForgePlacementHelperTest {

    @Test
    void shouldProjectHitPositionDeterministically() {
        BlockPos pos = new BlockPos(0, 10, 0);
        Vec3 loc = new Vec3(0.5, 10.5, 0.5);

        Vec3 west = ClientPlacementHelper.projectHitPosition(loc, pos, Direction.WEST);
        assertEquals(0.0, west.x);

        Vec3 east = ClientPlacementHelper.projectHitPosition(loc, pos, Direction.EAST);
        assertEquals(1.0, east.x);
    }

    @Test
    void shouldCreatePlacementHitResultWithOppositeFace() {
        BlockPos pos = new BlockPos(5, 5, 5);
        BlockHitResult hit = new BlockHitResult(new Vec3(5.5, 5.0, 5.5), Direction.DOWN, pos, false);

        BlockHitResult result = ClientPlacementHelper.createPlacementHitResult(hit);

        assertEquals(pos.below(), result.getBlockPos());
        assertEquals(Direction.UP, result.getDirection());
        assertFalse(result.isInside());
    }

    @Test
    void shouldResolveDecoratedPotsAndChiseledBookshelves() {
        assertEquals(
                ProtectionTarget.DECORATED_POTS,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "decorated_pot"))
        );
        assertEquals(
                ProtectionTarget.CHISELED_BOOKSHELVES,
                ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "chiseled_bookshelf"))
        );
        assertNull(ClientPlacementHelper.resolveBlockTarget(Identifier.fromNamespaceAndPath("minecraft", "dirt")));
    }
}
