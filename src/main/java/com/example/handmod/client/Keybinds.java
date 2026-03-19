package com.example.handmod.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Keybinds {
    public static KeyBinding openConfigKey;

    public static void register() {
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.handmod.open_config",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.handmod.general"
        ));
    }
}
