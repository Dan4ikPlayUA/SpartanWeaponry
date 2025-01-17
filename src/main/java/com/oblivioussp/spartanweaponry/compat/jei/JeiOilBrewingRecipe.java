package com.oblivioussp.spartanweaponry.compat.jei;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import com.google.common.collect.ImmutableList;
import com.oblivioussp.spartanweaponry.api.OilEffects;
import com.oblivioussp.spartanweaponry.api.oil.OilEffect;
import com.oblivioussp.spartanweaponry.item.crafting.OilBrewingRecipe;
import com.oblivioussp.spartanweaponry.util.OilHelper;

import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

public class JeiOilBrewingRecipe implements IJeiBrewingRecipe 
{
	private final List<ItemStack> baseOils;
	private final List<ItemStack> ingredients;
	private final ItemStack output;
	private final ResourceLocation uid;
	private int brewingSteps = Integer.MAX_VALUE;
	
	public JeiOilBrewingRecipe(List<ItemStack> baseOilsIn, List<ItemStack> ingredientsIn, ItemStack outputIn)
	{
		baseOils = ImmutableList.copyOf(baseOilsIn);
		ingredients = ImmutableList.copyOf(ingredientsIn);
		output = outputIn;
		ResourceLocation outputLocation = ForgeRegistries.ITEMS.getKey(outputIn.getItem());
		ForgeRegistry<OilEffect> registry = RegistryManager.ACTIVE.getRegistry(OilEffects.REGISTRY_KEY);
		uid = new ResourceLocation(outputLocation.getNamespace(), outputLocation.getPath() + "." + registry.getKey(OilHelper.getOilFromStack(outputIn)).getPath() + "_from_brewing");
	}
	
	@Override
	public @Unmodifiable List<ItemStack> getPotionInputs() 
	{
		return baseOils;
	}

	@Override
	public @Unmodifiable List<ItemStack> getIngredients()
	{
		return ingredients;
	}

	@Override
	public ItemStack getPotionOutput() 
	{
		return output;
	}

	@Override
	public int getBrewingSteps() 
	{
		// Check and see if the value isn't cached first
		if(brewingSteps == Integer.MAX_VALUE)
		{
			// Calculate the brewing steps value then cache it
			OilEffect oilEffect = OilHelper.getOilFromStack(output);
			brewingSteps = OilBrewingRecipe.getBrewingSteps(oilEffect);
		}
		return brewingSteps;
	}

	@Override
	public @Nullable ResourceLocation getUid() 
	{
		return uid;
	}

}
