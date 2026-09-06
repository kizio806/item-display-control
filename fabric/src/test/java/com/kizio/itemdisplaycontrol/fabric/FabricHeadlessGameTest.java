package com.kizio.itemdisplaycontrol.fabric;

import com.kizio.itemdisplaycontrol.common.Constants;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.test.TestContext;

public final class FabricHeadlessGameTest {

    @GameTest
    public void shouldBootHeadlessFabricGameTestServer(TestContext context) {
        context.assertTrue(
                Constants.MOD_ID.equals("itemdisplaycontrol"),
                "Expected common constants to be available during Fabric GameTest bootstrap"
        );
        context.complete();
    }
}
