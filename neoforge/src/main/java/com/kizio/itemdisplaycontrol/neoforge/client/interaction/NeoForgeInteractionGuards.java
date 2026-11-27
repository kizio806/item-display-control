package com.kizio.itemdisplaycontrol.neoforge.client.interaction;

import com.kizio.itemdisplaycontrol.client.interaction.ClientPlacementHelper;
import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.InputEvent;

/**
 * NeoForge client interaction event interceptor.
 * <p>
 * Subscribes to {@link InputEvent.InteractionKeyMappingTriggered} to intercept accidental item insertions
 * and seamlessly project adjacent block placement when appropriate.
 */
@OnlyIn(Dist.CLIENT)
public final class NeoForgeInteractionGuards {

    private static boolean placementBypassActive;

    private NeoForgeInteractionGuards() {
    }

    /**
     * Intercepts item use key actions triggered by the client player.
     *
     * @param event the interaction key mapping event.
     */
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
        if (target == null || !ItemDisplayControl.shouldBlock(target, ClientPlacementHelper.resolveItemId(stack.getItem()))) {
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
            case BLOCK -> ClientPlacementHelper.resolveBlockTarget(
                    BuiltInRegistries.BLOCK.getKey(
                            client.level.getBlockState(((BlockHitResult) hitResult).getBlockPos()).getBlock()
                    )
            );
            case ENTITY -> ClientPlacementHelper.resolveEntityTarget(((EntityHitResult) hitResult).getEntity());
            default -> null;
        };
    }

    private static BlockHitResult createPlacementHitResult(HitResult hitResult) {
        return switch (hitResult.getType()) {
            case BLOCK -> ClientPlacementHelper.createPlacementHitResult((BlockHitResult) hitResult);
            case ENTITY -> {
                EntityHitResult entityHit = (EntityHitResult) hitResult;
                yield ClientPlacementHelper.createPlacementHitResult(entityHit.getEntity(), entityHit);
            }
            default -> null;
        };
    }
}
