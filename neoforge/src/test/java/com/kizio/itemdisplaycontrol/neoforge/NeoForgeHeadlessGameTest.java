package com.kizio.itemdisplaycontrol.neoforge;

import com.kizio.itemdisplaycontrol.common.Constants;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;

@GameTestHolder(Constants.MOD_ID)
public final class NeoForgeHeadlessGameTest {

    @GameTest(templateNamespace = Constants.MOD_ID, template = "empty")
    public static void shouldBootHeadlessNeoForgeGameTestServer(GameTestHelper helper) {
        helper.assertTrue(
                Constants.MOD_ID.equals("itemdisplaycontrol"),
                "Expected common constants to be available during NeoForge GameTest bootstrap"
        );
        helper.succeed();
    }
}
