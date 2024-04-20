package io.github.cootshk.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.cootshk.TrueSight;
import net.minecraft.entity.Entity;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void onIsInvisible(CallbackInfoReturnable<Boolean> info) {
        if (TrueSight.enabled) {
            info.setReturnValue(false);
        }
    }
}
