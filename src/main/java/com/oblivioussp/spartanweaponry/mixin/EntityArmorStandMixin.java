package com.oblivioussp.spartanweaponry.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.oblivioussp.spartanweaponry.entity.projectile.EntityThrownWeapon;
import com.oblivioussp.spartanweaponry.util.Log;

import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.DamageSource;

@Mixin(EntityArmorStand.class)
public class EntityArmorStandMixin extends EntityMixin
{
	@Inject(method = "attackEntityFrom(Lnet/minecraft/util/DamageSource;F)Z", at = @At(value = "HEAD"), cancellable = true)
	public void attackEntityFrom(DamageSource sourceIn, float amountIn, CallbackInfoReturnable<Boolean> callback)
	{
		if(!world.isRemote && !isDead && sourceIn.getImmediateSource() instanceof EntityThrownWeapon && sourceIn.getDamageType().equals("player"))
		{
			dropBlock();
			playParticles();
			setDead();
			callback.setReturnValue(false);
		}
	}
	
	@Shadow
	private void dropBlock() 
	{
		throw new IllegalStateException("Mixin failed to shadow the \"EntityArmorStand.dropBlock()\" method!");
	}
	
	@Shadow
	private void playParticles() 
	{
		throw new IllegalStateException("Mixin failed to shadow the \"EntityArmorStand.playParticles()\" method!");
	}
}
