package com.fishingbell.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BellBlock;

@Mixin(FishingBobberEntity.class)
public class FishingBobberEntityMixin {
	@Inject(at = @At("RETURN"), method = "tick()V")
	private void onCatch(CallbackInfo info) {
		FishingBobberEntity _this = ((FishingBobberEntity)(Object)this);
		if (Block.getBlockFromItem(_this.getPlayerOwner().getOffHandStack().getItem()) instanceof BellBlock)
		{
            _this.getWorld().playSound(null, _this.getPlayerOwner().getX(), _this.getPlayerOwner().getY(), _this.getPlayerOwner().getZ(), SoundEvents.BLOCK_BELL_USE, SoundCategory.NEUTRAL, 0.5f, 0.4f / (_this.getWorld().getRandom().nextFloat() * 0.4f + 0.8f));
		}
	}
}