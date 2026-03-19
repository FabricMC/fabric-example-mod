package com.example.handmod;

import com.example.handmod.client.Keybinds;
import net.fabricmc.api.ClientModInitializer;

public class HandMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Keybinds.register();
    }
}
