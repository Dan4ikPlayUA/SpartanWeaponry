package com.oblivioussp.spartanweaponry.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.APIConfigValues;
import com.oblivioussp.spartanweaponry.api.APIConstants;
import com.oblivioussp.spartanweaponry.api.OilEffects;
import com.oblivioussp.spartanweaponry.api.WeaponMaterial;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.crafting.condition.TypeDisabledCondition;
import com.oblivioussp.spartanweaponry.api.oil.OilEffect;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.merchant.villager.WeaponsmithTrades;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.RegistryManager;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
	public static final Config INSTANCE;
	public static final ForgeConfigSpec CONFIG_SPEC;
	
	protected final Predicate<Object> IS_VALID_RESOURCE_LOCATION = (entry) -> ResourceLocation.isValidResourceLocation(entry.toString());
	
	static
	{
		 final Pair<Config, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Config::new);
		 INSTANCE = specPair.getLeft();
		 CONFIG_SPEC = specPair.getRight();
	}
	
	// Weapon categories
	public WeaponCategory daggers, parryingDaggers, longswords, katanas, sabers, rapiers, greatswords, clubs, cestus;
	public WeaponCategory battleHammers, warhammers, spears, halberds, pikes, lances;
	public RangedWeaponCategory longbows, heavyCrossbows;
	public ThrowingWeaponCategory throwingKnives, tomahawks, javelins, boomerangs;
	public WeaponCategory battleaxes, flangedMaces, glaives, quarterstaves;
	public WeaponCategory scythes;
	
	// Material categories
	public MaterialCategory copper, tin, bronze, steel, silver, electrum, lead, nickel, invar, constantan, platinum, aluminum;
	
	// Explosive settings
	public BooleanValue disableRecipesExplosives, disableTerrainDamage;
	public IntValue fuseTicksDynamite;
	public DoubleValue explosionStrengthDynamite;
	
	// Projectile settings
	public BooleanValue disableNewArrowRecipes, disableCopperAmmoRecipes, disableDiamondAmmoRecipes, disableNetheriteAmmoRecipes, disableQuiverRecipes;
	public ProjectileCategory arrowWood, arrowCopper, arrowIron, arrowDiamond, arrowNetherite;
	public DoubleValue arrowExplosiveExplosionStrength, arrowExplosiveRangeMultiplier;
	public BoltCategory bolt, boltCopper, boltDiamond, boltNetherite;
	public ConfigValue<List<? extends String>> quiverBowBlacklist;
	
	// Loot settings
	public BooleanValue addIronWeaponsToVillageWeaponsmith, addBowAndCrossbowLootToVillageFletcher, addDiamondWeaponsToEndCity,
					disableSpawningZombieWithWeapon, disableSpawningSkeletonWithLongbow;
	public DoubleValue zombieWithMeleeSpawnChanceNormal, zombieWithMeleeSpawnChanceHard,
					skeletonWithLongbowSpawnChanceNormal, skeletonWithLongbowSpawnChanceHard;
	public BooleanValue disableNewHeadDrops;
	
	// Trading settings
	public BooleanValue disableVillagerTrading;
	
	// Trait settings
	public DoubleValue damageBonusChestMultiplier, damageBonusHeadMultiplier, damageBonusRidingMultiplier, //damageBonusRidingVelocityForMaxBonus,
						damageBonusThrowMultiplier, damageBonusThrowJavelinMultiplier,
						damageBonusUnarmoredMultiplier;
	public BooleanValue damageBonusCheckArmorValue;
	public DoubleValue damageBonusMaxArmorValue, 
						damageBonusUndeadMultiplier, damageBonusBackstabMultiplier,
						damageAbsorptionFactor, reach1Value, reach2Value,
						sweep2Percentage, sweep3Percentage, armorPiercePercentage,
						decapitateSkullDropPercentage;
	public IntValue quickStrikeHurtResistTicks;
	
	// Oil settings
	public BooleanValue disableOilRecipes;
	public IntValue oilUsesNormal;
	public IntValue oilUsesLong;
	public DoubleValue oilDamageModifierNormal;
	public DoubleValue oilDamageModifierStrong;
	public DoubleValue potionOilDurationModifier;
	public ConfigValue<List<? extends String>> potionOilBlacklist;
	public ConfigValue<List<? extends String>> potionOilWhitelist;
	
	// JEI settings
	public BooleanValue forceShowDisabledItems;
	
	private Config(ForgeConfigSpec.Builder builder)
	{
		daggers = new WeaponCategory(builder, "dagger", "Daggers", Defaults.SpeedDagger, Defaults.DamageBaseDagger, Defaults.DamageMultiplierDagger, TypeDisabledCondition.DAGGER);
		parryingDaggers = new WeaponCategory(builder, "parrying_dagger", "Parrying Daggers", Defaults.SpeedParryingDagger, Defaults.DamageBaseParryingDagger, Defaults.DamageMultiplierParryingDagger, TypeDisabledCondition.PARRYING_DAGGER);
		longswords = new WeaponCategory(builder, "longsword", "Longswords", Defaults.SpeedLongsword, Defaults.DamageBaseLongsword, Defaults.DamageMultiplierLongsword, TypeDisabledCondition.LONGSWORD);
		katanas = new WeaponCategory(builder, "katana", "Katanas", Defaults.SpeedKatana, Defaults.DamageBaseKatana, Defaults.DamageMultiplierKatana, TypeDisabledCondition.KATANA);
		sabers = new WeaponCategory(builder, "saber", "Sabers", Defaults.SpeedSaber, Defaults.DamageBaseSaber, Defaults.DamageMultiplierSaber, TypeDisabledCondition.SABER);
		rapiers = new WeaponCategory(builder, "rapier", "Rapiers", Defaults.SpeedRapier, Defaults.DamageBaseRapier, Defaults.DamageMultiplierRapier, TypeDisabledCondition.RAPIER);
		greatswords = new WeaponCategory(builder, "greatsword", "Greatswords", Defaults.SpeedGreatsword, Defaults.DamageBaseGreatsword, Defaults.DamageMultiplierGreatsword, TypeDisabledCondition.GREATSWORD);
		clubs = new WeaponCategory(builder, "club", "Clubs", Defaults.SpeedClub, Defaults.DamageBaseClub, Defaults.DamageMultiplierClub, TypeDisabledCondition.CLUB);
		cestus = new WeaponCategory(builder, "cestus", "Cestusae", Defaults.SpeedCestus, Defaults.DamageBaseCestus, Defaults.DamageMultiplierCestus, TypeDisabledCondition.CESTUS);
		battleHammers = new WeaponCategory(builder, "battle_hammer", "Battle Hammers", Defaults.SpeedBattleHammer, Defaults.DamageBaseBattleHammer, Defaults.DamageMultiplierBattleHammer, TypeDisabledCondition.BATTLE_HAMMER);
		warhammers = new WeaponCategory(builder, "warhammer", "Warhammers", Defaults.SpeedWarhammer, Defaults.DamageBaseWarhammer, Defaults.DamageMultiplierWarhammer, TypeDisabledCondition.WARHAMMER);
		spears = new WeaponCategory(builder, "spear", "Spears", Defaults.SpeedSpear, Defaults.DamageBaseSpear, Defaults.DamageMultiplierSpear, TypeDisabledCondition.SPEAR);
		halberds = new WeaponCategory(builder, "halberd", "Halberds", Defaults.SpeedHalberd, Defaults.DamageBaseHalberd, Defaults.DamageMultiplierHalberd, TypeDisabledCondition.HALBERD);
		pikes = new WeaponCategory(builder, "pike", "Pikes", Defaults.SpeedPike, Defaults.DamageBasePike, Defaults.DamageMultiplierPike, TypeDisabledCondition.PIKE);
		lances = new WeaponCategory(builder, "lance", "Lances", Defaults.SpeedLance, Defaults.DamageBaseLance, Defaults.DamageMultiplierLance, TypeDisabledCondition.LANCE);
		longbows = new RangedWeaponCategory(builder, "longbow", "Longbows", TypeDisabledCondition.LONGBOW);
		heavyCrossbows = new RangedWeaponCategory(builder, "heavy_crossbow", "Heavy Crossbows", TypeDisabledCondition.HEAVY_CROSSBOW);
		throwingKnives = new ThrowingWeaponCategory(builder, "throwing_knife", "Throwing Knives", Defaults.MeleeSpeedThrowingKnife, Defaults.DamageBaseThrowingKnife, Defaults.DamageMultiplierThrowingKnife, Defaults.ChargeTicksThrowingKnife, TypeDisabledCondition.THROWING_KNIFE);
		tomahawks = new ThrowingWeaponCategory(builder, "tomahawk", "Tomahawks", Defaults.MeleeSpeedTomahawk, Defaults.DamageBaseTomahawk, Defaults.DamageMultiplierTomahawk, Defaults.ChargeTicksTomahawk, TypeDisabledCondition.TOMAHAWK);
		javelins = new ThrowingWeaponCategory(builder, "javelin", "Javelins", Defaults.MeleeSpeedJavelin, Defaults.DamageBaseJavelin, Defaults.DamageMultiplierJavelin, Defaults.ChargeTicksJavelin, TypeDisabledCondition.JAVELIN);
		boomerangs = new ThrowingWeaponCategory(builder, "boomerang", "Boomerangs", Defaults.MeleeSpeedBoomerang, Defaults.DamageBaseBoomerang, Defaults.DamageMultiplierBoomerang, Defaults.ChargeTicksBoomerang, TypeDisabledCondition.BOOMERANG);
		battleaxes = new WeaponCategory(builder, "battleaxe", "Battleaxes", Defaults.SpeedBattleaxe, Defaults.DamageBaseBattleaxe, Defaults.DamageMultiplierBattleaxe, TypeDisabledCondition.BATTLEAXE);
		flangedMaces = new WeaponCategory(builder, "flanged_mace", "Flanged Maces", Defaults.SpeedFlangedMace, Defaults.DamageBaseFlangedMace, Defaults.DamageMultiplierFlangedMace, TypeDisabledCondition.FLANGED_MACE);
		glaives = new WeaponCategory(builder, "glaive", "Glaives", Defaults.SpeedGlaive, Defaults.DamageBaseGlaive, Defaults.DamageMultiplierGlaive, TypeDisabledCondition.GLAIVE);
		quarterstaves = new WeaponCategory(builder, "quarterstaff", "Quarterstaves", Defaults.SpeedQuarterstaff, Defaults.DamageBaseQuarterstaff, Defaults.DamageMultiplierQuarterstaff, TypeDisabledCondition.QUARTERSTAFF);
		scythes = new WeaponCategory(builder, "scythes", "Scythes", Defaults.SpeedScythe, Defaults.DamageBaseScythe, Defaults.DamageMultiplierScythe, TypeDisabledCondition.SCYTHE);
		
		copper = new MaterialCategory(builder, "copper", APIConstants.DefaultMaterialDamageCopper, APIConstants.DefaultMaterialDurabilityCopper, TypeDisabledCondition.COPPER);
		tin = new MaterialCategory(builder, "tin", APIConstants.DefaultMaterialDamageTin, APIConstants.DefaultMaterialDurabilityTin, TypeDisabledCondition.TIN);
		bronze = new MaterialCategory(builder, "bronze", APIConstants.DefaultMaterialDamageBronze, APIConstants.DefaultMaterialDurabilityBronze, TypeDisabledCondition.BRONZE);
		steel = new MaterialCategory(builder, "steel", APIConstants.DefaultMaterialDamageSteel, APIConstants.DefaultMaterialDurabilitySteel, TypeDisabledCondition.STEEL);
		silver = new MaterialCategory(builder, "silver", APIConstants.DefaultMaterialDamageSilver, APIConstants.DefaultMaterialDurabilitySilver, TypeDisabledCondition.SILVER);
		electrum = new MaterialCategory(builder, "electrum", APIConstants.DefaultMaterialDamageElectrum, APIConstants.DefaultMaterialDurabilityElectrum, TypeDisabledCondition.ELECTRUM);
		lead = new MaterialCategory(builder, "lead", APIConstants.DefaultMaterialDamageLead, APIConstants.DefaultMaterialDurabilityLead, TypeDisabledCondition.LEAD);
		nickel = new MaterialCategory(builder, "nickel", APIConstants.DefaultMaterialDamageNickel, APIConstants.DefaultMaterialDurabilityNickel, TypeDisabledCondition.NICKEL);
		invar = new MaterialCategory(builder, "invar", APIConstants.DefaultMaterialDamageInvar, APIConstants.DefaultMaterialDurabilityInvar, TypeDisabledCondition.INVAR);
		constantan = new MaterialCategory(builder, "constantan", APIConstants.DefaultMaterialDamageConstantan, APIConstants.DefaultMaterialDurabilityConstantan, TypeDisabledCondition.CONSTANTAN);
		platinum = new MaterialCategory(builder, "platinum", APIConstants.DefaultMaterialDamagePlatinum, APIConstants.DefaultMaterialDurabilityPlatinum, TypeDisabledCondition.PLATINUM);
		aluminum = new MaterialCategory(builder, "aluminum", APIConstants.DefaultMaterialDamageAluminum, APIConstants.DefaultMaterialDurabilityAluminum, TypeDisabledCondition.ALUMINUM);
		
		builder.push("explosives");
			disableRecipesExplosives = builder.comment("Disables all recipes for explosive related items")
								.translation("config." + ModSpartanWeaponry.ID + ".explosive.disable_recipe")
								.worldRestart()
								.define("disable_recipe", false);
			disableTerrainDamage = builder.comment("Disables terrain damage for explosives in this mod such as Dynamite and Explosive Arrows. Is overridden by the 'mobGriefing' gamerule.")
								.translation("config." + ModSpartanWeaponry.ID + ".explosive.disable_terrain_damage")
								.define("disable_terrain_damage", false);
			fuseTicksDynamite = builder.comment("Time (in ticks) it takes for Dynamite to explode")
					.translation("config." + ModSpartanWeaponry.ID + ".explosive.fuse_ticks_dynamite")
					.defineInRange("fuse_ticks_dynamite", Defaults.FuseTicksDynamite, 20, 600);
			explosionStrengthDynamite = builder.comment("Explosion strength for Dynamite")
					.translation("config." + ModSpartanWeaponry.ID + ".explosive.explosion_strength_dynamite")
					.defineInRange("explosion_strength_dynamite", Defaults.ExplosionStrengthDynamite, 0.1f, 10.0f);
		builder.pop();
		
		builder.push("projectiles");
			disableNewArrowRecipes = builder.comment("Disables Recipes for all new Arrows.")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_new_arrow_recipes")
								.worldRestart()
								.define("disable_new_arrow_recipes", false);
			disableCopperAmmoRecipes = builder.comment("Disables Recipes for both Copper Arrows and Copper Bolts.")
					.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_copper_ammo_recipes")
					.worldRestart()
					.define("disable_copper_ammo_recipes", false);
			disableDiamondAmmoRecipes = builder.comment("Disables Recipes for both Diamond Arrows and Diamond Bolts.")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_diamond_ammo_recipes")
								.worldRestart()
								.define("disable_diamond_ammo_recipes", false);
			disableNetheriteAmmoRecipes = builder.comment("Disables Recipes for both Netherite Arrows and Netherite Bolts.")
					.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_netherite_ammo_recipes")
					.worldRestart()
					.define("disable_netherite_ammo_recipes", false);
			disableQuiverRecipes = builder.comment("Disables all variants of the Arrow Quiver and the Bolt Quiver in this mod")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.disable_quiver_recipes")
								.worldRestart()
								.define("disable_quiver_recipes", false);
			
			arrowWood = new ProjectileCategory(builder, "wood", "arrow", Defaults.BaseDamageArrowWood, Defaults.RangeMultiplierArrowWood);
			arrowCopper = new ProjectileCategory(builder, "copper", "arrow", Defaults.BaseDamageArrowCopper, Defaults.RangeMultiplierArrowCopper);
			arrowIron = new ProjectileCategory(builder, "iron", "arrow", Defaults.BaseDamageArrowIron, Defaults.RangeMultiplierArrowIron);
			arrowDiamond = new ProjectileCategory(builder, "diamond", "arrow", Defaults.BaseDamageArrowDiamond, Defaults.RangeMultiplierArrowDiamond);
			arrowNetherite = new ProjectileCategory(builder, "netherite", "arrow", Defaults.BaseDamageArrowNetherite, Defaults.RangeMultiplierArrowNetherite);
			bolt = new BoltCategory(builder, "", "bolt", Defaults.BaseDamageBolt, Defaults.RangeMultiplierBolt, Defaults.ArmorPiercingFactorBolt);
			boltCopper = new BoltCategory(builder, "copper", "bolt", Defaults.BaseDamageBoltCopper, Defaults.RangeMultiplierBoltCopper, Defaults.ArmorPiercingFactorBoltCopper);
			boltDiamond = new BoltCategory(builder, "diamond", "bolt", Defaults.BaseDamageBoltDiamond, Defaults.RangeMultiplierBoltDiamond, Defaults.ArmorPiercingFactorBoltDiamond);
			boltNetherite = new BoltCategory(builder, "netherite", "bolt", Defaults.BaseDamageBoltNetherite, Defaults.RangeMultiplierBoltNetherite, Defaults.ArmorPiercingFactorBoltNetherite);
			
			builder.push("explosive");
				arrowExplosiveExplosionStrength = builder.comment("Base damage for explosive arrows")
						.translation("config." + ModSpartanWeaponry.ID + ".arrow.explosion_strength")
						.defineInRange("base_damage", Defaults.ExplosionStrengthArrowExplosive, 0.1d, 10.0d);
				arrowExplosiveRangeMultiplier = builder.comment("Range muliplier for explosive arrows")
						.translation("config." + ModSpartanWeaponry.ID + ".arrow.range_multiplier")
						.defineInRange("range_multiplier", Defaults.RangeMultiplierArrowExplosive, 0.1d, 100.0d);
			builder.pop();
			quiverBowBlacklist = builder.comment("Bows in this blacklist will not get Arrows pulled out of the Arrow Quiver. Use the registry ID of the bow to add to this. e.g. \"minecraft:bow\"")
								.translation("config." + ModSpartanWeaponry.ID + ".projectile.quiver_bow_blacklist")
//								.<String>defineList("quiver_bow_blacklist", Defaults.QuiverArrowBlacklist, /*(value) -> ForgeRegistries.ITEMS.containsKey(new ResourceLocation((String)value))*/ (value) -> value.getClass() == String.class);
								.<String>defineListAllowEmpty(List.of("quiver_bow_blacklist"), () -> Defaults.QuiverArrowBlacklist, IS_VALID_RESOURCE_LOCATION);
		builder.pop();
		
		builder.push("loot");
			addIronWeaponsToVillageWeaponsmith = builder.comment("Set to false to disable spawning Iron Weapons in Village Weaponsmith chests via loot table injection")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.add_iron_weapons_to_village_blacksmith")
								.worldRestart()
								.define("add_iron_weapons_to_village_blacksmith", true);
			addBowAndCrossbowLootToVillageFletcher = builder.comment("Set to false to disable spawning Longbow and Heavy Crossbow-related loot in Village Fletcher chests via loot table injection")
					.translation("config." + ModSpartanWeaponry.ID + ".loot.add_bow_and_crossbow_loot_to_village_fletcher")
					.worldRestart()
					.define("add_bow_and_crossbow_loot_to_village_fletcher", true);
			addDiamondWeaponsToEndCity = builder.comment("Set to false to disable spawning Diamond Weapons in End City chests via loot table injection")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.add_diamond_weapons_to_end_city")
								.worldRestart()
								.define("add_diamond_weapons_to_end_city", true);
			zombieWithMeleeSpawnChanceNormal = builder.comment("Chance for Zombies to spawn with Iron Melee Weapons on all difficulties apart from Hard and Hardcore")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.zombie_with_melee_spawn_chance_normal")
								.defineInRange("zombie_with_melee_spawn_chance_normal", Defaults.zombieWithMeleeSpawnChanceNormal, 0.0, 1.0);
			zombieWithMeleeSpawnChanceHard = builder.comment("Chance for Zombies to spawn with Iron Melee Weapons on Hard or Hardcore difficulty")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.zombie_with_melee_spawn_chance_hard")
								.defineInRange("zombie_with_melee_spawn_chance_hard", Defaults.zombieWithMeleeSpawnChanceHard, 0.0, 1.0);
			disableSpawningZombieWithWeapon = builder.comment("Set to true to disable spawning a Zombie with any weapons from this mod")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.disable_spawning_zombie_with_weapon")
								.define("disable_spawning_zombie_with_weapon", false);
			skeletonWithLongbowSpawnChanceNormal = builder.comment("Chance for Skeletons to spawn with various Longbows on all difficulties apart from Hard and Hardcore")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.skeleton_with_longbow_spawn_chance_normal")
								.defineInRange("skeleton_with_longbow_spawn_chance_normal", Defaults.skeletonWithLongbowSpawnChanceNormal, 0.0, 1.0);
			skeletonWithLongbowSpawnChanceHard = builder.comment("Chance for Skeletons to spawn with various Longbows on Hard or Hardcore difficulty")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.skeleton_with_longbow_spawn_chance_hard")
								.defineInRange("skeleton_with_longbow_spawn_chance_hard", Defaults.skeletonWithLongbowSpawnChanceHard, 0.0, 1.0);
			disableSpawningSkeletonWithLongbow = builder.comment("Set to true to disable spawning a Skeleton with any Longbow from this mod")
					.translation("config." + ModSpartanWeaponry.ID + ".loot.disable_spawning_skeleton_with_longbow")
					.define("disable_spawning_skeleton_with_longbow", false);
			disableNewHeadDrops = builder.comment("Set to true to disable the new mob heads from being dropped from mobs using the Decapitate Weapon Trait from this mod.")
								.translation("config." + ModSpartanWeaponry.ID + ".loot.disable_new_head_drops")
								.define("disable_new_head_drops", false);
		builder.pop();
		
		builder.push("trading");
			disableVillagerTrading = builder.comment("Set to true to disable Villagers (Weaponsmiths and Fletchers) from trading weapons from this mod")
								.translation("config." + ModSpartanWeaponry.ID + ".trading.disabled")
								.define("disable", false);
		builder.pop();
		
		builder.push("traits");
			builder.push("damage_bonus");
				damageBonusChestMultiplier = builder.comment("Changes the \"Chest Damage Bonus\" Weapon Trait multiplier value")
									.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.chest_multiplier")
									.defineInRange("chest_multiplier", Defaults.DamageBonusChestMultiplier, 1.0, 50.0);
				damageBonusHeadMultiplier = builder.comment("Changes the \"Head Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.head_multiplier")
						.defineInRange("head_multiplier", Defaults.DamageBonusHeadMultiplier, 1.0, 50.0);
				damageBonusRidingMultiplier = builder.comment("Changes the \"Riding Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.riding_multiplier")
						.defineInRange("riding_multiplier", Defaults.DamageBonusRidingMultiplier, 1.0, 50.0);
/*				damageBonusRidingVelocityForMaxBonus = builder.comment("Velocity required for the \"Riding Damage Bonus\" Weapon Trait to award the max bonus")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.riding_velocity_for_max_bonus")
						.defineInRange("riding_velocity_for_max_bonus", Defaults.DamageBonusRidingVelocityMax, 0.0, 10.0);*/
				damageBonusThrowMultiplier = builder.comment("Changes the \"Throwing Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.throw_multiplier")
						.defineInRange("throw_multiplier", Defaults.DamageBonusThrowMultiplier, 1.0, 50.0);
				damageBonusThrowJavelinMultiplier = builder.comment("Changes the \"Chest Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.throw_javelin_multiplier")
						.defineInRange("throw_javelin_multiplier", Defaults.DamageBonusThrowJavelinMultiplier, 1.0, 50.0);
				damageBonusUnarmoredMultiplier = builder.comment("Changes the \"Unarmored Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.unarmored_multiplier")
						.defineInRange("unarmored_multiplier", Defaults.DamageBonusUnarmoredMultiplier, 1.0, 50.0);
				damageBonusCheckArmorValue = builder.comment("If set to true, any damage bonus that checks for armor will only apply if the hit mob has less than the total armor value threshold, while still checking for armor")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.check_armor_value")
						.define("check_armor_value", false);
				damageBonusMaxArmorValue = builder.comment("Max armor value allowed for any damage bonus that checks for armor to apply, without any armor equipped")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.max_armor_value")
						.defineInRange("max_armor_value", Defaults.DamageBonusMaxArmorValue, 1.0, 50.0);
				damageBonusUndeadMultiplier = builder.comment("Changes the \"Undead Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.undead_multiplier")
						.defineInRange("undead_multiplier", Defaults.DamageBonusUndeadMultiplier, 1.0, 50.0);
				damageBonusBackstabMultiplier = builder.comment("Changes the \"Backstab Damage Bonus\" Weapon Trait multiplier value")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_bonus.backstab_multiplier")
						.defineInRange("backstab_multiplier", Defaults.DamageBonusBackstabMultiplier, 1.0, 50.0);
			builder.pop();
			builder.push("damage_absorption");
				damageAbsorptionFactor = builder.comment("Changes the percentage of damage absorbed by the \"Damage Absorption\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.damage_absorption_factor")
						.defineInRange("damage_absorption_factor", Defaults.DamageAbsorptionFactor, 0.0, 1.0);
			builder.pop();
			builder.push("reach");
				reach1Value = builder.comment("Changes the reach of any weapons with the \"Reach I\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.reach1.value")
						.defineInRange("reach1_value", Defaults.Reach1Value, 5.0, 15.0);
				reach2Value = builder.comment("Changes the reach of any weapons with the \"Reach II\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.reach2.value")
						.defineInRange("reach2_value", Defaults.Reach2Value, 5.0, 15.0);
			builder.pop();
			builder.push("sweep");
				sweep2Percentage = builder.comment("Changes the factor of damage inflicted to enemies when sweep attacked on weapons with the \"Sweep II\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.sweep2.percentage")
						.defineInRange("sweep2_percentage", Defaults.Sweep2Percentage, 0.0, 1.0);
				sweep3Percentage = builder.comment("Changes the factor of damage inflicted to enemies when sweep attacked on weapons with the \"Sweep III\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.sweep3.percentage")
						.defineInRange("sweep3_percentage", Defaults.Sweep3Percentage, 0.0, 1.0);
			builder.pop();
			builder.push("armor_pierce");
				armorPiercePercentage = builder.comment("Changes the percentage of damage that ignores armor on weapons with the \"Armor Piercing\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.armor_pierce.percentage")
						.defineInRange("percentage", Defaults.ArmorPiercePercentage, 0.0, 100.0);
			builder.pop();
			builder.push("quick_strike");
				quickStrikeHurtResistTicks = builder.comment("Tweaks the hurt resistance ticks for weapons that use the \"Quick Strike\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.quick_strike.hurt_resistance_ticks")
						.defineInRange("hurt_resistance_ticks", Defaults.QuickStrikeHurtResistTicks, 10, 20);
			builder.pop();
			builder.push("decapitate");
				decapitateSkullDropPercentage = builder.comment("Tweaks the percentage of Skull drops from weapons with the \"Decapitate\" Weapon Trait")
						.translation("config." + ModSpartanWeaponry.ID + ".traits.decapitate.skull_drop_percentage")
						.defineInRange("skull_drop_percentage", Defaults.DecapitateSkullDropPercentage, 0.0, 100.0);
			builder.pop();
		builder.pop();
		
		builder.push("oil");
			disableOilRecipes = builder.comment("Set to true to disable crafting recipes for oils")
					.translation("config." + ModSpartanWeaponry.ID + ".disable_oil_recipes")
					.worldRestart()
					.define("disable_oil_recipes", false);
			oilUsesNormal = builder.comment("Max uses for standard oils before the effect wears off")
					.translation("config." + ModSpartanWeaponry.ID + ".oil_uses_normal")
					.worldRestart()
					.defineInRange("oil_uses_normal", Defaults.OIL_USES_NORMAL, 1, 1000);
			oilUsesLong = builder.comment("Max uses for sustained oils before the effect wears off")
					.translation("config." + ModSpartanWeaponry.ID + ".oil_uses_long")
					.worldRestart()
					.defineInRange("oil_uses_long", Defaults.OIL_USES_LONG, 1, 1000);
			oilDamageModifierNormal = builder.comment("Damage modifier that standard oils inflict")
					.translation("config." + ModSpartanWeaponry.ID + ".oil_damage_modifier_normal")
					.worldRestart()
					.defineInRange("oil_damage_modifier_normal", Defaults.OIL_DAMAGE_MODIFIER_NORMAL, 0.0001d, 1.0d);
			oilDamageModifierStrong = builder.comment("Damage modifier that potent oils inflict")
					.translation("config." + ModSpartanWeaponry.ID + ".oil_damage_modifier_strong")
					.worldRestart()
					.defineInRange("oil_damage_modifier_strong", Defaults.OIL_DAMAGE_MODIFIER_STRONG, 0.0001d, 1.0d);
			potionOilDurationModifier = builder.comment("Duration modifier for potion oils, based on the original potion effects")
					.translation("config." + ModSpartanWeaponry.ID + ".potion_oil_duration_modifier")
					.worldRestart()
					.defineInRange("potion_oil_duration_modifier", Defaults.OIL_POTION_DURATION_MODIFIER, 0.0001d, 1.0d);
			potionOilBlacklist = builder.comment("Blacklist for potions to prevent them to be made into oils. By default, only potions with negative effects can be made into oils. Adding already disabled potions to this blacklist will do nothing")
					.translation("config." + ModSpartanWeaponry.ID + ".potion_oil_blacklist")
					.worldRestart()
					.<String>defineListAllowEmpty(List.of("potion_oil_blacklist"), () -> new ArrayList<>(), IS_VALID_RESOURCE_LOCATION);
			potionOilWhitelist = builder.comment("Whitelist for potions to allow them to be made into oils. By default, only potions with negative effects can be made into oils. Adding already enabled potions to this whitelist will do nothing")
					.translation("config." + ModSpartanWeaponry.ID + ".potion_oil_whitelist")
					.worldRestart()
					.<String>defineListAllowEmpty(List.of("potion_oil_whitelist"), () -> new ArrayList<>(), IS_VALID_RESOURCE_LOCATION);
		builder.pop();
		
		builder.push("jei");
			forceShowDisabledItems = builder.comment("Set to true to forcibly show disabled items in JEI, even if they cannot be crafted. Should be useful for modpack makers defining their own recipes.")
					.translation("config." + ModSpartanWeaponry.ID + ".jei.force_show_disabled_items")
					.worldRestart()
					.define("force_show_disabled_items", false);
		builder.pop();
	}
	
	@SubscribeEvent
	public static void onConfigLoad(ModConfigEvent ev)
	{
		if(ev.getConfig().getSpec() != CONFIG_SPEC)
			return;
		
		TypeDisabledCondition.disabledRecipeTypes.clear();
		
		updateMaterialValues(WeaponMaterial.COPPER, INSTANCE.copper.damage.get().floatValue(), INSTANCE.copper.durability.get());
		INSTANCE.copper.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.TIN, INSTANCE.tin.damage.get().floatValue(), INSTANCE.tin.durability.get());
		INSTANCE.tin.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.BRONZE, INSTANCE.bronze.damage.get().floatValue(), INSTANCE.bronze.durability.get());
		INSTANCE.bronze.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.STEEL, INSTANCE.steel.damage.get().floatValue(), INSTANCE.steel.durability.get());
		INSTANCE.steel.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.SILVER, INSTANCE.silver.damage.get().floatValue(), INSTANCE.silver.durability.get());
		INSTANCE.silver.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.ELECTRUM, INSTANCE.electrum.damage.get().floatValue(), INSTANCE.electrum.durability.get());
		INSTANCE.electrum.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.LEAD, INSTANCE.lead.damage.get().floatValue(), INSTANCE.lead.durability.get());
		INSTANCE.lead.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.NICKEL, INSTANCE.nickel.damage.get().floatValue(), INSTANCE.nickel.durability.get());
		INSTANCE.nickel.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.INVAR, INSTANCE.invar.damage.get().floatValue(), INSTANCE.invar.durability.get());
		INSTANCE.invar.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.CONSTANTAN, INSTANCE.constantan.damage.get().floatValue(), INSTANCE.constantan.durability.get());
		INSTANCE.constantan.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.PLATINUM, INSTANCE.platinum.damage.get().floatValue(), INSTANCE.platinum.durability.get());
		INSTANCE.platinum.updateDisabledRecipeList();
		updateMaterialValues(WeaponMaterial.ALUMINUM, INSTANCE.aluminum.damage.get().floatValue(), INSTANCE.aluminum.durability.get());
		INSTANCE.aluminum.updateDisabledRecipeList();
		
		ModItems.DAGGERS.updateSettingsFromConfig(INSTANCE.daggers.baseDamage.get().floatValue(), INSTANCE.daggers.damageMultipler.get().floatValue(), INSTANCE.daggers.speed.get().doubleValue());
		INSTANCE.daggers.updateDisabledRecipeList();
		ModItems.PARRYING_DAGGERS.updateSettingsFromConfig(INSTANCE.parryingDaggers.baseDamage.get().floatValue(), INSTANCE.parryingDaggers.damageMultipler.get().floatValue(), INSTANCE.parryingDaggers.speed.get().doubleValue());
		INSTANCE.parryingDaggers.updateDisabledRecipeList();
		ModItems.LONGSWORDS.updateSettingsFromConfig(INSTANCE.longswords.baseDamage.get().floatValue(), INSTANCE.longswords.damageMultipler.get().floatValue(), INSTANCE.longswords.speed.get().doubleValue());
		INSTANCE.longswords.updateDisabledRecipeList();
		ModItems.KATANAS.updateSettingsFromConfig(INSTANCE.katanas.baseDamage.get().floatValue(), INSTANCE.katanas.damageMultipler.get().floatValue(), INSTANCE.katanas.speed.get().doubleValue());
		INSTANCE.katanas.updateDisabledRecipeList();
		ModItems.SABERS.updateSettingsFromConfig(INSTANCE.sabers.baseDamage.get().floatValue(), INSTANCE.sabers.damageMultipler.get().floatValue(), INSTANCE.sabers.speed.get().doubleValue());
		INSTANCE.sabers.updateDisabledRecipeList();
		ModItems.RAPIERS.updateSettingsFromConfig(INSTANCE.rapiers.baseDamage.get().floatValue(), INSTANCE.rapiers.damageMultipler.get().floatValue(), INSTANCE.rapiers.speed.get().doubleValue());
		INSTANCE.rapiers.updateDisabledRecipeList();
		ModItems.GREATSWORDS.updateSettingsFromConfig(INSTANCE.greatswords.baseDamage.get().floatValue(), INSTANCE.greatswords.damageMultipler.get().floatValue(), INSTANCE.greatswords.speed.get().doubleValue());
		INSTANCE.greatswords.updateDisabledRecipeList();
		
		ImmutableList.of(ModItems.WOODEN_CLUB, ModItems.STUDDED_CLUB).forEach((club) -> club.get().setAttackDamageAndSpeed(INSTANCE.clubs.baseDamage.get().floatValue(), INSTANCE.clubs.damageMultipler.get().floatValue(), INSTANCE.clubs.speed.get().doubleValue()));
		INSTANCE.clubs.updateDisabledRecipeList();
		ImmutableList.of(ModItems.CESTUS, ModItems.STUDDED_CESTUS).forEach((club) -> club.get().setAttackDamageAndSpeed(INSTANCE.cestus.baseDamage.get().floatValue(), INSTANCE.cestus.damageMultipler.get().floatValue(), INSTANCE.cestus.speed.get().doubleValue()));
		INSTANCE.cestus.updateDisabledRecipeList();
		
		ModItems.BATTLE_HAMMERS.updateSettingsFromConfig(INSTANCE.battleHammers.baseDamage.get().floatValue(), INSTANCE.battleHammers.damageMultipler.get().floatValue(), INSTANCE.battleHammers.speed.get().doubleValue());
		INSTANCE.battleHammers.updateDisabledRecipeList();
		ModItems.WARHAMMERS.updateSettingsFromConfig(INSTANCE.warhammers.baseDamage.get().floatValue(), INSTANCE.warhammers.damageMultipler.get().floatValue(), INSTANCE.warhammers.speed.get().doubleValue());
		INSTANCE.warhammers.updateDisabledRecipeList();
		ModItems.SPEARS.updateSettingsFromConfig(INSTANCE.spears.baseDamage.get().floatValue(), INSTANCE.spears.damageMultipler.get().floatValue(), INSTANCE.spears.speed.get().doubleValue());
		INSTANCE.spears.updateDisabledRecipeList();
		ModItems.HALBERDS.updateSettingsFromConfig(INSTANCE.halberds.baseDamage.get().floatValue(), INSTANCE.halberds.damageMultipler.get().floatValue(), INSTANCE.halberds.speed.get().doubleValue());
		INSTANCE.halberds.updateDisabledRecipeList();
		ModItems.PIKES.updateSettingsFromConfig(INSTANCE.pikes.baseDamage.get().floatValue(), INSTANCE.pikes.damageMultipler.get().floatValue(), INSTANCE.pikes.speed.get().doubleValue());
		INSTANCE.pikes.updateDisabledRecipeList();
		ModItems.LANCES.updateSettingsFromConfig(INSTANCE.lances.baseDamage.get().floatValue(), INSTANCE.lances.damageMultipler.get().floatValue(), INSTANCE.lances.speed.get().doubleValue());
		INSTANCE.lances.updateDisabledRecipeList();
		
		// Updating configurable values for Longbows and Heavy Crossbows are not required
		INSTANCE.longbows.updateDisabledRecipeList();
		INSTANCE.heavyCrossbows.updateDisabledRecipeList();
		
		ModItems.THROWING_KNIVES.updateSettingsFromConfig(INSTANCE.throwingKnives.baseDamage.get().floatValue(), INSTANCE.throwingKnives.damageMultipler.get().floatValue(), INSTANCE.throwingKnives.speed.get().doubleValue(), INSTANCE.throwingKnives.chargeTicks.get());
		INSTANCE.throwingKnives.updateDisabledRecipeList();
		ModItems.TOMAHAWKS.updateSettingsFromConfig(INSTANCE.tomahawks.baseDamage.get().floatValue(), INSTANCE.tomahawks.damageMultipler.get().floatValue(), INSTANCE.tomahawks.speed.get().doubleValue(), INSTANCE.tomahawks.chargeTicks.get());
		INSTANCE.tomahawks.updateDisabledRecipeList();
		ModItems.JAVELINS.updateSettingsFromConfig(INSTANCE.javelins.baseDamage.get().floatValue(), INSTANCE.javelins.damageMultipler.get().floatValue(), INSTANCE.javelins.speed.get().doubleValue(), INSTANCE.javelins.chargeTicks.get());
		INSTANCE.javelins.updateDisabledRecipeList();
		
		ModItems.BOOMERANGS.updateSettingsFromConfig(INSTANCE.boomerangs.baseDamage.get().floatValue(), INSTANCE.boomerangs.damageMultipler.get().floatValue(), INSTANCE.boomerangs.speed.get().doubleValue(), INSTANCE.boomerangs.chargeTicks.get());
		INSTANCE.boomerangs.updateDisabledRecipeList();
		ModItems.BATTLEAXES.updateSettingsFromConfig(INSTANCE.battleaxes.baseDamage.get().floatValue(), INSTANCE.battleaxes.damageMultipler.get().floatValue(), INSTANCE.battleaxes.speed.get().doubleValue());
		INSTANCE.battleaxes.updateDisabledRecipeList();
		ModItems.FLANGED_MACES.updateSettingsFromConfig(INSTANCE.flangedMaces.baseDamage.get().floatValue(), INSTANCE.flangedMaces.damageMultipler.get().floatValue(), INSTANCE.flangedMaces.speed.get().doubleValue());
		INSTANCE.flangedMaces.updateDisabledRecipeList();
		
		ModItems.GLAIVES.updateSettingsFromConfig(INSTANCE.glaives.baseDamage.get().floatValue(), INSTANCE.glaives.damageMultipler.get().floatValue(), INSTANCE.glaives.speed.get().doubleValue());
		INSTANCE.glaives.updateDisabledRecipeList();
		ModItems.QUARTERSTAVES.updateSettingsFromConfig(INSTANCE.quarterstaves.baseDamage.get().floatValue(), INSTANCE.quarterstaves.damageMultipler.get().floatValue(), INSTANCE.quarterstaves.speed.get().doubleValue());
		INSTANCE.quarterstaves.updateDisabledRecipeList();
		
		ModItems.SCYTHES.updateSettingsFromConfig(INSTANCE.scythes.baseDamage.get().floatValue(), INSTANCE.scythes.damageMultipler.get().floatValue(), INSTANCE.scythes.speed.get().doubleValue());
		INSTANCE.scythes.updateDisabledRecipeList();
		
		updateDisabledRecipe(TypeDisabledCondition.ARROWS, INSTANCE.disableNewArrowRecipes.get());
		updateDisabledRecipe(TypeDisabledCondition.COPPER_AMMO, INSTANCE.disableCopperAmmoRecipes.get());
		updateDisabledRecipe(TypeDisabledCondition.DIAMOND_AMMO, INSTANCE.disableDiamondAmmoRecipes.get());
		updateDisabledRecipe(TypeDisabledCondition.NETHERITE_AMMO, INSTANCE.disableNetheriteAmmoRecipes.get());
		updateDisabledRecipe(TypeDisabledCondition.QUIVER, INSTANCE.disableQuiverRecipes.get());
		updateDisabledRecipe(TypeDisabledCondition.BOLTS, INSTANCE.heavyCrossbows.disableRecipes.get());
		
		ModItems.WOODEN_ARROW.get().updateFromConfig(INSTANCE.arrowWood.baseDamage.get().floatValue(), INSTANCE.arrowWood.rangeMultiplier.get().floatValue());
		ModItems.TIPPED_WOODEN_ARROW.get().updateFromConfig(INSTANCE.arrowWood.baseDamage.get().floatValue(), INSTANCE.arrowWood.rangeMultiplier.get().floatValue());
		ModItems.COPPER_ARROW.get().updateFromConfig(INSTANCE.arrowCopper.baseDamage.get().floatValue(), INSTANCE.arrowCopper.rangeMultiplier.get().floatValue());
		ModItems.TIPPED_COPPER_ARROW.get().updateFromConfig(INSTANCE.arrowCopper.baseDamage.get().floatValue(), INSTANCE.arrowCopper.rangeMultiplier.get().floatValue());
		ModItems.IRON_ARROW.get().updateFromConfig(INSTANCE.arrowIron.baseDamage.get().floatValue(), INSTANCE.arrowIron.rangeMultiplier.get().floatValue());
		ModItems.TIPPED_IRON_ARROW.get().updateFromConfig(INSTANCE.arrowIron.baseDamage.get().floatValue(), INSTANCE.arrowIron.rangeMultiplier.get().floatValue());
		ModItems.DIAMOND_ARROW.get().updateFromConfig(INSTANCE.arrowDiamond.baseDamage.get().floatValue(), INSTANCE.arrowDiamond.rangeMultiplier.get().floatValue());
		ModItems.TIPPED_DIAMOND_ARROW.get().updateFromConfig(INSTANCE.arrowDiamond.baseDamage.get().floatValue(), INSTANCE.arrowDiamond.rangeMultiplier.get().floatValue());
		ModItems.NETHERITE_ARROW.get().updateFromConfig(INSTANCE.arrowNetherite.baseDamage.get().floatValue(), INSTANCE.arrowNetherite.rangeMultiplier.get().floatValue());
		ModItems.TIPPED_NETHERITE_ARROW.get().updateFromConfig(INSTANCE.arrowNetherite.baseDamage.get().floatValue(), INSTANCE.arrowNetherite.rangeMultiplier.get().floatValue());
		ModItems.BOLT.get().updateFromConfig(INSTANCE.bolt.baseDamage.get().floatValue(), INSTANCE.bolt.rangeMultiplier.get().floatValue(), INSTANCE.bolt.armorPiercingFactor.get().floatValue());
		ModItems.TIPPED_BOLT.get().updateFromConfig(INSTANCE.bolt.baseDamage.get().floatValue(), INSTANCE.bolt.rangeMultiplier.get().floatValue(), INSTANCE.bolt.armorPiercingFactor.get().floatValue());
		ModItems.SPECTRAL_BOLT.get().updateFromConfig(INSTANCE.bolt.baseDamage.get().floatValue(), INSTANCE.bolt.rangeMultiplier.get().floatValue(), INSTANCE.bolt.armorPiercingFactor.get().floatValue());
		ModItems.COPPER_BOLT.get().updateFromConfig(INSTANCE.boltCopper.baseDamage.get().floatValue(), INSTANCE.boltCopper.rangeMultiplier.get().floatValue(), INSTANCE.boltCopper.armorPiercingFactor.get().floatValue());
		ModItems.TIPPED_COPPER_BOLT.get().updateFromConfig(INSTANCE.boltCopper.baseDamage.get().floatValue(), INSTANCE.boltCopper.rangeMultiplier.get().floatValue(), INSTANCE.boltCopper.armorPiercingFactor.get().floatValue());
		ModItems.DIAMOND_BOLT.get().updateFromConfig(INSTANCE.boltDiamond.baseDamage.get().floatValue(), INSTANCE.boltDiamond.rangeMultiplier.get().floatValue(), INSTANCE.boltDiamond.armorPiercingFactor.get().floatValue());
		ModItems.TIPPED_DIAMOND_BOLT.get().updateFromConfig(INSTANCE.boltDiamond.baseDamage.get().floatValue(), INSTANCE.boltDiamond.rangeMultiplier.get().floatValue(), INSTANCE.boltDiamond.armorPiercingFactor.get().floatValue());
		ModItems.NETHERITE_BOLT.get().updateFromConfig(INSTANCE.boltNetherite.baseDamage.get().floatValue(), INSTANCE.boltNetherite.rangeMultiplier.get().floatValue(), INSTANCE.boltNetherite.armorPiercingFactor.get().floatValue());
		ModItems.TIPPED_NETHERITE_BOLT.get().updateFromConfig(INSTANCE.boltNetherite.baseDamage.get().floatValue(), INSTANCE.boltNetherite.rangeMultiplier.get().floatValue(), INSTANCE.boltNetherite.armorPiercingFactor.get().floatValue());
		
		updateDisabledRecipe(TypeDisabledCondition.EXPLOSIVES, INSTANCE.disableRecipesExplosives.get());
		
		// Update Weapon Traits
		WeaponTraits.DAMAGE_BONUS_CHEST.get().setMagnitude(INSTANCE.damageBonusChestMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_HEAD.get().setMagnitude(INSTANCE.damageBonusHeadMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_RIDING.get().setMagnitude(INSTANCE.damageBonusRidingMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_THROWN_1.get().setMagnitude(INSTANCE.damageBonusThrowMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_THROWN_2.get().setMagnitude(INSTANCE.damageBonusThrowJavelinMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_UNARMOURED.get().setMagnitude(INSTANCE.damageBonusUnarmoredMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_UNDEAD.get().setMagnitude(INSTANCE.damageBonusUndeadMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_BONUS_BACKSTAB.get().setMagnitude(INSTANCE.damageBonusBackstabMultiplier.get().floatValue());
		WeaponTraits.DAMAGE_ABSORB.get().setMagnitude(INSTANCE.damageAbsorptionFactor.get().floatValue());
		WeaponTraits.REACH_1.get().setMagnitude(INSTANCE.reach1Value.get().floatValue());
		WeaponTraits.REACH_2.get().setMagnitude(INSTANCE.reach2Value.get().floatValue());
		WeaponTraits.SWEEP_2.get().setMagnitude(INSTANCE.sweep2Percentage.get().floatValue());
		WeaponTraits.SWEEP_3.get().setMagnitude(INSTANCE.sweep3Percentage.get().floatValue());
		WeaponTraits.ARMOUR_PIERCING.get().setMagnitude(INSTANCE.armorPiercePercentage.get().floatValue());
		WeaponTraits.QUICK_STRIKE.get().setMagnitude(INSTANCE.quickStrikeHurtResistTicks.get().floatValue());
		WeaponTraits.DECAPITATE.get().setMagnitude(INSTANCE.decapitateSkullDropPercentage.get().floatValue());
		
		// Update Oils
		ForgeRegistry<OilEffect> oilEffects = RegistryManager.ACTIVE.getRegistry(OilEffects.REGISTRY_KEY);
		for(OilEffect effect : oilEffects)
		{
			switch(effect.getType())
			{
			case STANDARD:
				effect.updateFromConfig(INSTANCE.oilUsesNormal.get(), INSTANCE.oilDamageModifierNormal.get().floatValue());
				break;
			case SUSTAINED:
				effect.updateFromConfig(INSTANCE.oilUsesLong.get(), INSTANCE.oilDamageModifierNormal.get().floatValue());
				break;
			case POTENT:
				effect.updateFromConfig(INSTANCE.oilUsesNormal.get(), INSTANCE.oilDamageModifierStrong.get().floatValue());
				break;
			case EFFECT_ONLY:
				effect.updateFromConfig(INSTANCE.oilUsesNormal.get(), 0.0f);
			default:
				break;
			}
		}
		
		// Update values required API-side
		APIConfigValues.damageBonusCheckArmorValue = INSTANCE.damageBonusCheckArmorValue.get();
		APIConfigValues.damageBonusMaxArmorValue = INSTANCE.damageBonusMaxArmorValue.get().floatValue();
//		APIConfigValues.damageBonusRidingVelocityForMaxBonus = INSTANCE.damageBonusRidingVelocityForMaxBonus.get().floatValue();
		
		WeaponsmithTrades.initTradeLists();
		
		// Debug crap
		/*Log.info("Disabled Recipes:");
		if(INSTANCE.disabledRecipeTypes.isEmpty())
			Log.info("- None!");
		for(String type : INSTANCE.disabledRecipeTypes)
		{
			Log.info("- " + type);
		}*/
	}
	
	public static void updateDisabledRecipe(String type, boolean disabled)
	{
		boolean containsValue = TypeDisabledCondition.disabledRecipeTypes.contains(type);
		if(!containsValue && disabled)
			TypeDisabledCondition.disabledRecipeTypes.add(type);
		else if(containsValue)
			TypeDisabledCondition.disabledRecipeTypes.remove(type);
	}
	
	private static void updateMaterialValues(WeaponMaterial material, float baseDamage, int durability)
	{
		material.setAttackDamage(baseDamage);
		material.setDurability(durability);
	}
	

	
	public class WeaponCategory
	{
		public BooleanValue disableRecipes;
		public DoubleValue speed;
		public DoubleValue baseDamage;
		public DoubleValue damageMultipler;
		private String typeDisabledName;
		
		protected WeaponCategory(ForgeConfigSpec.Builder builder, String weaponClass, String weaponPlural, float defaultSpeed, float defaultBaseDamage, float defaultDamageMuliplier, String typeDisabledNameIn)
		{
			builder.push(weaponClass);
			typeDisabledName = typeDisabledNameIn;
			disableRecipes = builder.comment("Disables all recipes for all " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.disable")
					.worldRestart()
					.define("disable", false);
			speed = builder.comment("Attack speed of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.speed")
					.defineInRange("speed", defaultSpeed, 0.0d, 4.0d);
			baseDamage = builder.comment("Base Damage of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.base_damage")
					.defineInRange("base_damage", defaultBaseDamage, 0.1d, 100.0d);
			damageMultipler = builder.comment("Damage Multiplier for " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.damage_multiplier")
					.defineInRange("damage_multiplier", defaultDamageMuliplier, 0.1d, 10.0d);
			builder.pop();
		}
		
		public void updateDisabledRecipeList()
		{
			updateDisabledRecipe(typeDisabledName, disableRecipes.get());
		}
	}
	
	public class RangedWeaponCategory
	{
		public BooleanValue disableRecipes;
		private String typeDisabledName;
		
		protected RangedWeaponCategory(ForgeConfigSpec.Builder builder, String weaponClass, String weaponPlural, String typeDisabledNameIn)
		{
			builder.push(weaponClass);
			typeDisabledName = typeDisabledNameIn;
			disableRecipes = builder.comment("Disables all recipes for all " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.disable")
					.worldRestart()
					.define("disable", false);
			builder.pop();
		}
		
		public void updateDisabledRecipeList()
		{
			updateDisabledRecipe(typeDisabledName, disableRecipes.get());
		}
	}
	
	public class ThrowingWeaponCategory
	{
		public BooleanValue disableRecipes;
		public DoubleValue speed;
		public DoubleValue baseDamage;
		public DoubleValue damageMultipler;
		public IntValue chargeTicks;
		private String typeDisabledName;
		
		protected ThrowingWeaponCategory(ForgeConfigSpec.Builder builder, String weaponClass, String weaponPlural, float defaultSpeed, float defaultBaseDamage, float defaultDamageMuliplier, int defaultChargeTicks, String typeDisabledNameIn)
		{
			builder.push(weaponClass);
			typeDisabledName = typeDisabledNameIn;
			disableRecipes = builder.comment("Disables all recipes for all " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.disable")
					.worldRestart()
					.define("disable", false);
			speed = builder.comment("Attack speed of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.speed")
					.defineInRange("speed", defaultSpeed, 0.0d, 4.0d);
			baseDamage = builder.comment("Base Damage of " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.base_damage")
					.defineInRange("base_damage", defaultBaseDamage, 0.1d, 100.0d);
			damageMultipler = builder.comment("Damage Multiplier for " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.damage_multiplier")
					.defineInRange("damage_multiplier", defaultDamageMuliplier, 0.1d, 10.0d);
			chargeTicks = builder.comment("Charge time in ticks for " + weaponPlural + ".")
					.translation("config." + ModSpartanWeaponry.ID + ".weapon.charge_ticks")
					.defineInRange("charge_ticks", defaultChargeTicks, 1, 1000);
			builder.pop();
		}
		
		public void updateDisabledRecipeList()
		{
			updateDisabledRecipe(typeDisabledName, disableRecipes.get());
		}
	}
	
	public class MaterialCategory
	{
		public DoubleValue damage;
		public IntValue durability;
		public BooleanValue disableRecipes;
		private String materialName;
		private String typeDisabledName;
		
		private MaterialCategory(ForgeConfigSpec.Builder builder, String materialName, float damage, int durability, String typeDisabledName)
		{
			builder.push(materialName);
			this.materialName = materialName;
			this.typeDisabledName = typeDisabledName;
			this.damage = builder.comment("Base Damage for " + this.materialName + " weapons")
						.translation("config." + ModSpartanWeaponry.ID + ".material.base_damage")
						.defineInRange("base_damage", damage, 0.1d, 100.0d);
			this.durability = builder.comment("Durability for " + this.materialName + " weapons")
					.translation("config." + ModSpartanWeaponry.ID + ".material.durability")
					.defineInRange("durability", durability, 1, 100000);
			this.disableRecipes = builder.comment("Set to true to disable " + this.materialName + " weapons")
					.translation("config." + ModSpartanWeaponry.ID + ".material.disable")
					.worldRestart()
					.define("disable", false);
			builder.pop();
		}
		
		public void updateDisabledRecipeList()
		{
			updateDisabledRecipe(typeDisabledName, disableRecipes.get());
		}
	}
	
	public class ProjectileCategory
	{
		public DoubleValue baseDamage;
		public DoubleValue rangeMultiplier;
		
		private ProjectileCategory(ForgeConfigSpec.Builder builder, String materialName, String projectileName, float baseDamage, float rangeMultiplier)
		{
			String projName = materialName == null || materialName == "" ? projectileName : materialName + " " + projectileName;
			String category = materialName == null || materialName == "" ? projectileName : materialName + "_" + projectileName;
			builder.push(category);
			this.baseDamage = builder.comment("Base damage for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".arrow.base_damage")
					.defineInRange("base_damage", baseDamage, 0.1d, 100.0d);
			this.rangeMultiplier = builder.comment("Range muliplier for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".arrow.range_multiplier")
					.defineInRange("range_multiplier", rangeMultiplier, 0.1d, 100.0d);
			builder.pop();
		}
	}
	
	public class BoltCategory
	{
		public DoubleValue baseDamage;
		public DoubleValue rangeMultiplier;
		public DoubleValue armorPiercingFactor;
		
		protected BoltCategory(ForgeConfigSpec.Builder builder, String materialName, String projectileName, float baseDamage, float rangeMultiplier, float armorPiercingFactor)
		{
			String projName = materialName == null || materialName == "" ? projectileName : materialName + " " + projectileName;
			String category = materialName == null || materialName == "" ? projectileName : materialName + "_" + projectileName;
			builder.push(category);
			this.baseDamage = builder.comment("Base damage for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".arrow.base_damage")
					.defineInRange("base_damage", baseDamage, 0.1d, 100.0d);
			this.rangeMultiplier = builder.comment("Range muliplier for " + projName + "s")
				.translation("config." + ModSpartanWeaponry.ID + ".arrow.range_multiplier")
				.defineInRange("range_multiplier", rangeMultiplier, 0.1d, 100.0d);
			this.armorPiercingFactor = builder.comment("Armor Piercing factor for " + projName + "s")
					.translation("config." + ModSpartanWeaponry.ID + ".bolt.armor_piercing_factor")
					.defineInRange("armor_piercing_factor", armorPiercingFactor, 0.0d, 1.0d);
			builder.pop();
		}
	}
}
