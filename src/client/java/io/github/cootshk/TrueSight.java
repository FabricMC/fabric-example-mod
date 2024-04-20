package io.github.cootshk;

import java.util.logging.Logger;

import net.fabricmc.api.ClientModInitializer;

public class TrueSight implements ClientModInitializer {
    public static boolean enabled = false;
    @Override
    public void onInitializeClient() {
		Logger logger = Logger.getLogger("TrueSight");
		logger.info("TrueSight is enabled.");
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    }
}