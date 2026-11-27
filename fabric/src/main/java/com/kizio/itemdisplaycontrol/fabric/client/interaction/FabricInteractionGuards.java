package com.kizio.itemdisplaycontrol.fabric.client.interaction;

import com.kizio.itemdisplaycontrol.client.interaction.ClientPlacementHelper;
import com.kizio.itemdisplaycontrol.common.ItemDisplayControl;
import com.kizio.itemdisplaycontrol.common.config.ProtectionTarget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Fabric client interaction event interceptor.
 * <p>
 * Blocks unintended interactions with protected blocks and entities (e.g. flower pots,
 * item frames, armor stands) while allowing sneak bypass and projecting adjacent block placements.
 */
@Environment(EnvType.CLIENT)
public final class FabricInteractionGuards {

    private static boolean placementBypassActive;

    private FabricInteractionGuards() {
    }

    /**
     * Registers client-side block and entity right-click interception callbacks.
     */
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (placementBypassActive || !ItemDisplayControl.isEnabled()) {
                return InteractionResult.PASS;
            }
            if (player == null || world == null || !world.isClientSide()) {
                return InteractionResult.PASS;
            }
            if (player.isSpectator() || player.isShiftKeyDown()) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);
            if (stack.isEmpty()) {
                return InteractionResult.PASS;
            }

            ProtectionTarget target = ClientPlacementHelper.resolveBlockTarget(world.getBlockState(hitResult.getBlockPos()).getBlock());
            if (target == null || !ItemDisplayControl.shouldBlock(target, ClientPlacementHelper.resolveItemId(stack.getItem()))) {
                return InteractionResult.PASS;
            }

            attemptPlacementBypass(player, hand, ClientPlacementHelper.createPlacementHitResult(hitResult));
            return InteractionResult.FAIL;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (placementBypassActive || !ItemDisplayControl.isEnabled()) {
                return InteractionResult.PASS;
            }
            if (player == null || world == null || !world.isClientSide()) {
                return InteractionResult.PASS;
            }
            if (player.isSpectator() || player.isShiftKeyDown()) {
                return InteractionResult.PASS;
            }

            ItemStack stack = player.getItemInHand(hand);
            if (stack.isEmpty()) {
                return InteractionResult.PASS;
            }

            ProtectionTarget target = ClientPlacementHelper.resolveEntityTarget(entity);
            if (target == null || !ItemDisplayControl.shouldBlock(target, ClientPlacementHelper.resolveItemId(stack.getItem()))) {
                return InteractionResult.PASS;
            }

            attemptPlacementBypass(player, hand, ClientPlacementHelper.createPlacementHitResult(entity, hitResult));
            return InteractionResult.FAIL;
        });
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
            if (result instanceof InteractionResult.Success) {
                player.swing(hand);
            }
        } finally {
            placementBypassActive = false;
        }
    }
}
