package com.example.handmod.mixin;

import com.example.handmod.config.ModConfig;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HandMixin {
    @Inject(method = "renderFirstPersonItem", at = @At("HEAD"))
    private void adjustHand(MatrixStack matrices, float tickDelta, float pitch, net.minecraft.util.Hand hand, float swingProgress, net.minecraft.item.ItemStack item, float equipProgress, CallbackInfo ci) {
        matrices.translate(ModConfig.handX, ModConfig.handY, ModConfig.handZ);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(ModConfig.handRotation));
    }
}
