package com.oblivioussp.spartanweaponry.api.data.model;

import javax.annotation.Nullable;

import com.oblivioussp.spartanweaponry.api.ModelOverrides;
import com.oblivioussp.spartanweaponry.api.data.OilCoatingTextures;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Contains helper methods to generate customised model files based off items from Spartan Weaponry.<br>
 * Add to the addon mod's {@linkplain ItemModelProvider#registerModels()} method to use them
 * @author ObliviousSpartan
 *
 */
public class ModelGenerator
{
	protected final ItemModelProvider itemModelProvider;
	
	public ModelGenerator(ItemModelProvider itemModelProviderIn)
	{
		itemModelProvider = itemModelProviderIn;
	}
	
	/**
	 * Generates a model using the same base model as most mundane items
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createSimpleModel(Item item)
	{
		return createSimpleModel(item, null, "");
	}
	
	/**
	 * Generates a model using the same base model as most mundane items
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createSimpleModel(Item item, String textureFolderPath)
	{
		return createSimpleModel(item, null, textureFolderPath);
	}
	
	/**
	 * Generates a model using a defined parent model
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param parent The location of the parent model
	 * @return The generated models location
	 */
	public ResourceLocation createSimpleModel(Item item, ResourceLocation parent)
	{
		return createSimpleModel(item, parent, "");
	}
	
	/**
	 * Generates a model using a defined parent model
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param parent The location of the parent model. If null, the parent model will default to "minecraft:item/generated"
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createSimpleModel(Item item, @Nullable ResourceLocation parent, String textureFolderPath)
	{
		ResourceLocation parentPath = parent != null ? parent : itemModelProvider.mcLoc("item/generated");
		String texturePath = textureFolderPath.isEmpty() ? "item/" : "item/" + textureFolderPath + "/";
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		return itemModelProvider.withExistingParent(itemPath, parentPath).texture("layer0", texturePath + itemPath).getLocation();
	}
	
	/**
	 * Generates a model designed for any melee weapon. Will generate overrides for any event that the weapon has the Melee Block or Throwable Weapon Traits
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param baseModel The base model location to use. These values are conveniently stored here {@linkplain BaseModels}
	 * @param coatingTexture The coating texture location, including the path. The path is independent from the base texture path
	 * @return The generated models location
	 */
	public ResourceLocation createMeleeWeaponModels(Item item, ResourceLocation baseModel, ResourceLocation coatingTexture)
	{
		return createMeleeWeaponModels(item, baseModel, coatingTexture, "");
	}
	
	/**
	 * Generates a model designed for any melee weapon. Will generate overrides for any event that the weapon has the Melee Block or Throwable Weapon Traits
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param baseModel The base model location to use. These values are conveniently stored here {@linkplain BaseModels}
	 * @param coatingTexture The coating texture location, including the path. The path is independent from the base texture path
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createMeleeWeaponModels(Item item, ResourceLocation baseModel, ResourceLocation coatingTexture, String textureFolderPath)
	{
		String texturePath = textureFolderPath.isEmpty() ? "item/" : "item/" + textureFolderPath + "/";
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		ResourceLocation blockingModel = itemModelProvider.withExistingParent(itemPath + "_blocking", new ResourceLocation(baseModel.getNamespace(), baseModel.getPath() + "_blocking")).
				customLoader(OilCoatingItemModelBuilder::new).end().
				texture("layer0", texturePath + itemPath).
				texture("coating", coatingTexture).
				getLocation();
		ResourceLocation throwingModel = itemModelProvider.withExistingParent(itemPath + "_throwing", new ResourceLocation(baseModel.getNamespace(), baseModel.getPath() + "_throwing")).
				customLoader(OilCoatingItemModelBuilder::new).end().
				texture("layer0", texturePath + itemPath).
				texture("coating", coatingTexture).
				getLocation();
		return itemModelProvider.withExistingParent(itemPath, baseModel).
				customLoader(OilCoatingItemModelBuilder::new).end().
				texture("layer0", texturePath + itemPath).
				texture("coating", coatingTexture).
				override().predicate(ModelOverrides.BLOCKING, 1.0f).model(new ExistingModelFile(blockingModel, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.THROWING, 1.0f).model(new ExistingModelFile(throwingModel, itemModelProvider.existingFileHelper)).end().
				getLocation();
	}
	
	/**
	 * Generates a model designed for the Cestus. Will generate overrides for any event that the weapon has the Melee Block or Throwable Weapon Traits
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param baseModel The base model location to use. These values are conveniently stored here {@linkplain BaseModels}
	 * @param coatingTexture Isn't used due to technical constraints of 3D models not having layers
	 * @return The generated models location
	 */
	public ResourceLocation createCestusModels(Item item, ResourceLocation baseModel, ResourceLocation coatingTexture)
	{
		return createCestusModels(item, baseModel, coatingTexture, "");
	}
	
	/**
	 * Generates a model designed for the Cestus. Will generate overrides for any event that the weapon has the Melee Block or Throwable Weapon Traits
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param baseModel The base model location to use. These values are conveniently stored here {@linkplain BaseModels}
	 * @param coatingTexture Isn't used due to technical constraints of 3D models not having layers
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createCestusModels(Item item, ResourceLocation baseModel, ResourceLocation coatingTexture, String textureFolderPath)
	{
		String texturePath = textureFolderPath.isEmpty() ? "item/" : "item/" + textureFolderPath + "/";
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		ResourceLocation blockingModel = itemModelProvider.withExistingParent(itemPath + "_blocking", new ResourceLocation(baseModel.getNamespace(), baseModel.getPath() + "_blocking")).
				texture("layer0", texturePath + itemPath).
				getLocation();
		ResourceLocation throwingModel = itemModelProvider.withExistingParent(itemPath + "_throwing", new ResourceLocation(baseModel.getNamespace(), baseModel.getPath() + "_throwing")).
				texture("layer0", texturePath + itemPath).
				getLocation();
		return itemModelProvider.withExistingParent(itemPath, baseModel).
				texture("layer0", texturePath + itemPath).
				override().predicate(ModelOverrides.BLOCKING, 1.0f).model(new ExistingModelFile(blockingModel, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.THROWING, 1.0f).model(new ExistingModelFile(throwingModel, itemModelProvider.existingFileHelper)).end().
				getLocation();
	}

	/**
	 * Generates a model designed for any throwing weapon
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param baseModel The base model location to use. These values are conveniently stored here {@linkplain BaseModels}
	 * @param baseThrowingModel The base throwing model location to use. See above parameter for the location for this
	 * @param emptyModel The base empty model location to use. See 'baseModel' parameter for the location for this
	 * @return The generated models location
	 */
	public ResourceLocation createThrowingWeaponModels(Item item, ResourceLocation baseModel, ResourceLocation baseThrowingModel, ResourceLocation emptyModel)
	{
		return createThrowingWeaponModels(item, baseModel, baseThrowingModel, emptyModel, "");
	}

	/**
	 * Generates a model designed for any throwing weapon
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param baseModel The base model location to use. These values are conveniently stored here {@linkplain BaseModels}
	 * @param baseThrowingModel The base throwing model location to use. See above parameter for the location for this
	 * @param emptyModel The base empty model location to use. See 'baseModel' parameter for the location for this
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createThrowingWeaponModels(Item item, ResourceLocation baseModel, ResourceLocation baseThrowingModel, ResourceLocation emptyModel, String textureFolderPath)
	{
		String texturePath = textureFolderPath.isEmpty() ? "item/" : "item/" + textureFolderPath + "/";
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		ResourceLocation throwingModel = itemModelProvider.withExistingParent(itemPath + "_throwing", baseThrowingModel).texture("layer0", texturePath + itemPath).getLocation();
		return itemModelProvider.withExistingParent(itemPath, baseModel).texture("layer0", texturePath + itemPath).
				override().predicate(ModelOverrides.THROWING, 1.0f).predicate(ModelOverrides.EMPTY, 0.0f).model(new ExistingModelFile(throwingModel, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.EMPTY, 1.0f).model(new ExistingModelFile(emptyModel, itemModelProvider.existingFileHelper)).end().
				getLocation();
	}
	
	/**
	 * Generates standard and throwing models using the same base model as a Vanilla Sword
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createVanillaSwordModels(Item item)
	{
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		return itemModelProvider.withExistingParent(itemPath, "minecraft:item/handheld").
				customLoader(OilCoatingItemModelBuilder::new).end().
				texture("layer0", "minecraft:item/" + itemPath).
				texture("coating", OilCoatingTextures.VANILLA_SWORD).
				getLocation();
	}
	
	/**
	 * Generates standard and throwing models using the same base model as a Dagger
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createDaggerModels(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.DAGGER, OilCoatingTextures.DAGGER);
	}
	
	/**
	 * Generates standard and throwing models using the same base model as a Dagger
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createDaggerModels(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.DAGGER, OilCoatingTextures.DAGGER, textureFolderPath);
	}
	
	/**
	 * Generates standard and blocking models using the same base model as a Parrying Dagger
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createParryingDaggerModels(Item item) 
	{
		return createMeleeWeaponModels(item, BaseModels.PARRYING_DAGGER, OilCoatingTextures.PARRYING_DAGGER);
	}
	
	/**
	 * Generates standard and blocking models using the same base model as a Parrying Dagger
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createParryingDaggerModels(Item item, String textureFolderPath) 
	{
		return createMeleeWeaponModels(item, BaseModels.PARRYING_DAGGER, OilCoatingTextures.PARRYING_DAGGER, textureFolderPath);
	}

	/**
	 * Generates a model using the same base model as a Longsword
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createLongswordModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.LONGSWORD, OilCoatingTextures.LONGSWORD);
	}

	/**
	 * Generates a model using the same base model as a Longsword
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createLongswordModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.LONGSWORD, OilCoatingTextures.LONGSWORD, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Katana
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createKatanaModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.KATANA, OilCoatingTextures.KATANA);
	}
	
	/**
	 * Generates a model using the same base model as a Katana
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createKatanaModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.KATANA, OilCoatingTextures.KATANA, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Saber
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createSaberModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.SABER, OilCoatingTextures.SABER);
	}
	
	/**
	 * Generates a model using the same base model as a Saber
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createSaberModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.SABER, OilCoatingTextures.SABER, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Rapier
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createRapierModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.RAPIER, OilCoatingTextures.RAPIER);
	}
	
	/**
	 * Generates a model using the same base model as a Rapier
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createRapierModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.RAPIER, OilCoatingTextures.RAPIER, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Greatsword
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createGreatswordModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.GREATSWORD, OilCoatingTextures.GREATSWORD);
	}
	
	/**
	 * Generates a model using the same base model as a Greatsword
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createGreatswordModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.GREATSWORD, OilCoatingTextures.GREATSWORD, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Club
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createClubModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.CLUB, OilCoatingTextures.CLUB);
	}
	
	/**
	 * Generates a model using the same base model as a Club
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createClubModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.CLUB, OilCoatingTextures.CLUB, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Cestus
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createCestusModel(Item item)
	{
		return createCestusModels(item, BaseModels.CESTUS, OilCoatingTextures.CESTUS);
	}
	
	/**
	 * Generates a model using the same base model as a Cestus
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createCestusModel(Item item, String textureFolderPath)
	{
		return createCestusModels(item, BaseModels.CESTUS, OilCoatingTextures.CESTUS, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Battle Hammer
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createBattleHammerModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.BATTLE_HAMMER, OilCoatingTextures.BATTLE_HAMMER);
	}
	
	/**
	 * Generates a model using the same base model as a Battle Hammer
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createBattleHammerModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.BATTLE_HAMMER, OilCoatingTextures.BATTLE_HAMMER, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Warhammer
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createWarhammerModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.WARHAMMER, OilCoatingTextures.WARHAMMER);
	}
	
	/**
	 * Generates a model using the same base model as a Warhammer
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createWarhammerModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.WARHAMMER, OilCoatingTextures.WARHAMMER, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Spear
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createSpearModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.SPEAR, OilCoatingTextures.SPEAR);
	}
	
	/**
	 * Generates a model using the same base model as a Spear
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createSpearModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.SPEAR, OilCoatingTextures.SPEAR, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Halberd
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createHalberdModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.HALBERD, OilCoatingTextures.HALBERD);
	}
	
	/**
	 * Generates a model using the same base model as a Halberd
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createHalberdModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.HALBERD, OilCoatingTextures.HALBERD, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Pike
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createPikeModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.PIKE, OilCoatingTextures.PIKE);
	}
	
	/**
	 * Generates a model using the same base model as a Pike
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createPikeModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.PIKE, OilCoatingTextures.PIKE, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Lance
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createLanceModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.LANCE, OilCoatingTextures.LANCE);
	}
	
	/**
	 * Generates a model using the same base model as a Lance
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createLanceModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.LANCE, OilCoatingTextures.LANCE, textureFolderPath);
	}
	
	/**
	 * Generates standard and 3 drawing models using the same base model as a Longbow
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createLongbowModels(Item item)
	{
		return createLongbowModels(item, "");
	}
	
	/**
	 * Generates standard and 3 drawing models using the same base model as a Longbow
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createLongbowModels(Item item, String textureFolderPath)
	{
		String texturePath = textureFolderPath.isEmpty() ? "item/" : "item/" + textureFolderPath + "/";
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		ResourceLocation pulling0 = itemModelProvider.withExistingParent(itemPath + "_pulling_0", BaseModels.LONGBOW_PULLING).texture("layer0", texturePath + itemPath + "_pulling_0").getLocation();
		ResourceLocation pulling1 = itemModelProvider.withExistingParent(itemPath + "_pulling_1", BaseModels.LONGBOW_PULLING).texture("layer0", texturePath + itemPath + "_pulling_1").getLocation();
		ResourceLocation pulling2 = itemModelProvider.withExistingParent(itemPath + "_pulling_2", BaseModels.LONGBOW_PULLING).texture("layer0", texturePath + itemPath + "_pulling_2").getLocation();
		return itemModelProvider.withExistingParent(itemPath, BaseModels.LONGBOW).texture("layer0", texturePath + itemPath + "_standby").
				override().predicate(ModelOverrides.PULLING, 1.0f).model(new ExistingModelFile(pulling0, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.PULLING, 1.0f).predicate(ModelOverrides.PULL, 0.65f).model(new ExistingModelFile(pulling1, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.PULLING, 1.0f).predicate(ModelOverrides.PULL, 0.9f).model(new ExistingModelFile(pulling2, itemModelProvider.existingFileHelper)).end().
				getLocation();
	}
	
	/**
	 * Generates 1 standard, 3 drawing, 1 loaded and 1 firing models using the same base model as a Heavy Crossbow
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createHeavyCrossbowModels(Item item)
	{
		return createHeavyCrossbowModels(item, "");
	}
	
	/**
	 * Generates 1 standard, 3 drawing, 1 loaded and 1 firing models using the same base model as a Heavy Crossbow
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createHeavyCrossbowModels(Item item, String textureFolderPath)
	{
		String texturePath = textureFolderPath.isEmpty() ? "item/" : "item/" + textureFolderPath + "/";
		String itemPath = ForgeRegistries.ITEMS.getKey(item).getPath();
		ResourceLocation pulling0 = itemModelProvider.withExistingParent(itemPath + "_pulling_0", BaseModels.HEAVY_CROSSBOW_PULLING).texture("layer0", texturePath + itemPath + "_pulling_0").getLocation();
		ResourceLocation pulling1 = itemModelProvider.withExistingParent(itemPath + "_pulling_1", BaseModels.HEAVY_CROSSBOW_PULLING).texture("layer0", texturePath + itemPath + "_pulling_1").getLocation();
		ResourceLocation pulling2 = itemModelProvider.withExistingParent(itemPath + "_pulling_2", BaseModels.HEAVY_CROSSBOW_PULLING).texture("layer0", texturePath + itemPath + "_pulling_2").getLocation();
		ResourceLocation loaded = itemModelProvider.withExistingParent(itemPath + "_loaded", BaseModels.HEAVY_CROSSBOW_LOADED).texture("layer0", texturePath + itemPath + "_loaded").getLocation();
		ResourceLocation firing = itemModelProvider.withExistingParent(itemPath + "_firing", BaseModels.HEAVY_CROSSBOW_FIRING).texture("layer0", texturePath + itemPath + "_loaded").getLocation();
		return itemModelProvider.withExistingParent(itemPath, BaseModels.HEAVY_CROSSBOW).texture("layer0", texturePath + itemPath + "_standby").
				override().predicate(ModelOverrides.PULLING, 1.0f).model(new ExistingModelFile(pulling0, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.PULLING, 1.0f).predicate(ModelOverrides.PULL, 0.65f).model(new ExistingModelFile(pulling1, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.PULLING, 1.0f).predicate(ModelOverrides.PULL, 1.0f).model(new ExistingModelFile(pulling2, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.CHARGED, 1.0f).model(new ExistingModelFile(loaded, itemModelProvider.existingFileHelper)).end().
				override().predicate(ModelOverrides.PULLING, 1.0f).predicate(ModelOverrides.CHARGED, 1.0f).model(new ExistingModelFile(firing, itemModelProvider.existingFileHelper)).end().
				getLocation();
	}

	/**
	 * Generates standard and throwing models using the same base model as a Throwing Knife
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createThrowingKnifeModels(Item item)
	{
		return createThrowingWeaponModels(item, BaseModels.THROWING_KNIFE, BaseModels.THROWING_KNIFE_THROWING, BaseModels.THROWING_KNIFE_EMPTY);
	}
	
	/**
	 * Generates standard and throwing models using the same base model as a Throwing Knife
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createThrowingKnifeModels(Item item, String textureFolderPath)
	{
		return createThrowingWeaponModels(item, BaseModels.THROWING_KNIFE, BaseModels.THROWING_KNIFE_THROWING, BaseModels.THROWING_KNIFE_EMPTY, textureFolderPath);
	}

	/**
	 * Generates standard and throwing models using the same base model as a Tomahawk
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createTomahawkModels(Item item)
	{
		return createThrowingWeaponModels(item, BaseModels.TOMAHAWK, BaseModels.TOMAHAWK_THROWING, BaseModels.TOMAHAWK_EMPTY);
	}

	/**
	 * Generates standard and throwing models using the same base model as a Tomahawk
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createTomahawkModels(Item item, String textureFolderPath)
	{
		return createThrowingWeaponModels(item, BaseModels.TOMAHAWK, BaseModels.TOMAHAWK_THROWING, BaseModels.TOMAHAWK_EMPTY, textureFolderPath);
	}

	/**
	 * Generates standard and throwing models using the same base model as a Javelin
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createJavelinModels(Item item)
	{
		return createThrowingWeaponModels(item, BaseModels.JAVELIN, BaseModels.JAVELIN_THROWING, BaseModels.JAVELIN_EMPTY);
	}

	/**
	 * Generates standard and throwing models using the same base model as a Javelin
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createJavelinModels(Item item, String textureFolderPath)
	{
		return createThrowingWeaponModels(item, BaseModels.JAVELIN, BaseModels.JAVELIN_THROWING, BaseModels.JAVELIN_EMPTY, textureFolderPath);
	}

	/**
	 * Generates standard and throwing models using the same base model as a Boomerang
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createBoomerangModels(Item item)
	{
		return createThrowingWeaponModels(item, BaseModels.BOOMERANG, BaseModels.BOOMERANG_THROWING, BaseModels.BOOMERANG_EMPTY);
	}

	/**
	 * Generates standard and throwing models using the same base model as a Boomerang
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createBoomerangModels(Item item, String textureFolderPath)
	{
		return createThrowingWeaponModels(item, BaseModels.BOOMERANG, BaseModels.BOOMERANG_THROWING, BaseModels.BOOMERANG_EMPTY, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Battleaxe
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createBattleaxeModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.BATTLEAXE, OilCoatingTextures.BATTLEAXE);
	}
	
	/**
	 * Generates a model using the same base model as a Battleaxe
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createBattleaxeModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.BATTLEAXE, OilCoatingTextures.BATTLEAXE, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Flanged Mace
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createFlangedMaceModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.FLANGED_MACE, OilCoatingTextures.FLANGED_MACE);
	}
	
	/**
	 * Generates a model using the same base model as a Flanged Mace
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createFlangedMaceModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.FLANGED_MACE, OilCoatingTextures.FLANGED_MACE, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Glaive
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createGlaiveModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.GLAIVE, OilCoatingTextures.GLAIVE);
	}
	
	/**
	 * Generates a model using the same base model as a Glaive
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createGlaiveModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.GLAIVE, OilCoatingTextures.GLAIVE, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Quarterstaff
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createQuarterstaffModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.QUARTERSTAFF, OilCoatingTextures.QUARTERSTAFF);
	}
	
	/**
	 * Generates a model using the same base model as a Quarterstaff
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createQuarterstaffModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.QUARTERSTAFF, OilCoatingTextures.QUARTERSTAFF, textureFolderPath);
	}
	
	/**
	 * Generates a model using the same base model as a Scythe
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @return The generated models location
	 */
	public ResourceLocation createScytheModel(Item item)
	{
		return createMeleeWeaponModels(item, BaseModels.SCYTHE, OilCoatingTextures.SCYTHE);
	}
	
	/**
	 * Generates a model using the same base model as a Scythe
	 * @param item The item to generate the model for. The registry name is used for the texture name
	 * @param textureFolderPath The texture folder path to look for the texture in
	 * @return The generated models location
	 */
	public ResourceLocation createScytheModel(Item item, String textureFolderPath)
	{
		return createMeleeWeaponModels(item, BaseModels.SCYTHE, OilCoatingTextures.SCYTHE, textureFolderPath);
	}
}
