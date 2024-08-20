package com.oblivioussp.spartanweaponry.api.trait;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/**
 * Callback class for Melee Weapon Traits; Implement this in your weapon trait class to implement custom behavior for your weapon
 * @author ObliviousSpartan
 */
public interface IMeleeTraitCallback extends IGenericTraitCallback
{
	/**
	 * Change this to customise damage from a weapon with this Weapon Trait when it deals damage
	 * @param material The weapon's material
	 * @param baseDamage The base damage that the weapon would deal if unmodified
	 * @param source The source of the damage
	 * @param attacker The attacking Entity
	 * @param victim The Entity being attacked
	 * @return The damage that will be dealt after any necessary modifications.
	 */
	public default float modifyDamageDealt(WeaponMaterial material, float baseDamage, DamageSource source, LivingEntity attacker, LivingEntity victim)
	{
		return baseDamage;
	}
	
	/**
	 * Change this to customise damage taken with this weapon equipped with this Weapon Trait when damage is taken
	 * @param material The weapon's material
	 * @param baseDamage The base damage that should be taken if unmodified
	 * @param source The source of the damage
	 * @param attacker The attacking Entity
	 * @param victim The Entity being attacked
	 * @return The damage that will be taken after any necessary modifications.
	 */
	public default float modifyDamageTaken(WeaponMaterial material, float baseDamage, DamageSource source, LivingEntity attacker, LivingEntity victim)
	{
		return baseDamage;
	}
	
	/**
	 * Use {@linkplain IGenericTraitCallback#onItemUpdate(WeaponMaterial, ItemStack, Level, LivingEntity, int, boolean)} instead!
	 * Called once every item tick. Use if item behavior needs to be changed on the fly
	 * @param material The item's material
	 * @param stack The item
	 * @param level The level (formerly world)
	 * @param entity The Entity equipped with this item
	 * @param itemSlot The slot the item is in
	 * @param isSelected
	 */
	@Override
	public default void onItemUpdate(WeaponMaterial material, ItemStack stack, Level level, LivingEntity entity, int itemSlot, boolean isSelected) {}
	
	/**
	 * Called when an entity is hit with a weapon containing this Weapon Trait
	 * @param material The item's material
	 * @param stack The item
	 * @param target The Entity being attacked
	 * @param attacker The attacking Entity
	 * @param projectile The Entity that is directly hitting the target Entity. Will be null if hit by a melee hit, so please null check!
	 */
	public default void onHitEntity(WeaponMaterial material, ItemStack stack, LivingEntity target, LivingEntity attacker, Entity projectile) {}
	
	/**
	 * Allows Traits to add Attribute Modifiers for any Weapon. Calls {@linkplain #onModifyAttributesMelee(Builder)} by default
	 * @param builder The modifier map builder for the item
	 */
	@Override
	default void onModifyAttributes(Builder<Attribute, AttributeModifier> builder) 
	{
		onModifyAttributesMelee(builder);
	}
	
	/**
	 * Use to edit Melee-only attributes! Otherwise use {@linkplain IGenericTraitCallback#onModifyAttributes(ImmutableMultimap.Builder)} instead!<br>
	 * Allows Traits to add Attribute Modifiers for Melee Weapons
	 * @param builder The modifier map builder for the item
	 */
	public default void onModifyAttributesMelee(ImmutableMultimap.Builder<Attribute, AttributeModifier> builder) {}
	
	/**
	 * Use {@linkplain IGenericTraitCallback#onCreateItem(WeaponMaterial, ItemStack)} instead!
	 * Allows the item to have Enchantments or other NBT data added to the item. This should be reflected in Creative mode too
	 * @param stack The item to edit
	 */
	@Override
	public default void onCreateItem(WeaponMaterial material, ItemStack stack) {}
}
