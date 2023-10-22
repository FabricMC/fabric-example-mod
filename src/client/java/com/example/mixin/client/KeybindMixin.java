package com.example.mixin.client;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
public class KeybindMixin {
    @Inject(at=@At("HEAD"), method="onKey")
    public void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (key == 79 && action == 1) {
            if (MinecraftClient.getInstance().player != null && !(MinecraftClient.getInstance().currentScreen instanceof ChatScreen)) {
                MinecraftClient.getInstance().player.sendMessage(Text.of("You pressed the O button!"));
            }
        }
    }
}
