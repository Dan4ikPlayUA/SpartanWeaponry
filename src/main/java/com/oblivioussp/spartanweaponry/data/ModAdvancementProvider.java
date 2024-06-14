package com.oblivioussp.spartanweaponry.data;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.advancement.criterion.BrewOilTrigger;
import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.oblivioussp.spartanweaponry.init.ModItems;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;

public class ModAdvancementProvider extends ForgeAdvancementProvider 
{
	public ModAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelperIn) 
	{
		super(output, registries, existingFileHelperIn, List.of(new SpartanWeaponryAdvancements()));
	}
	
	public static class SpartanWeaponryAdvancements implements AdvancementGenerator
	{
		@Override
		public void generate(Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) 
		{
			Advancement root = Advancement.Builder.advancement().display(ModItems.LONGSWORDS.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".root.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".root.desc"),
					new ResourceLocation("minecraft", "textures/block/anvil.png"), FrameType.TASK, false, false, false).addCriterion("has_handle", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HANDLE.get())).addCriterion("has_pole", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.POLE.get())).requirements(RequirementsStrategy.OR).
					save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "root"), existingFileHelper);
			
			Advancement daggers = Advancement.Builder.advancement().parent(root).display(ModItems.DAGGERS.stone.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_dagger.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_dagger.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_dagger", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.DAGGERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "dagger"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.PARRYING_DAGGERS.gold.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_parrying_dagger.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_parrying_dagger.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_parrying_dagger", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.PARRYING_DAGGERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "parrying_dagger"), existingFileHelper);
			Advancement longswords = Advancement.Builder.advancement().parent(root).display(ModItems.LONGSWORDS.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_longsword.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_longsword.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_longsword", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.LONGSWORDS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "longsword"), existingFileHelper);
			Advancement katanas = Advancement.Builder.advancement().parent(root).display(ModItems.KATANAS.stone.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_katana.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_katana.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_katana", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.KATANAS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "katana"), existingFileHelper);
			Advancement sabers = Advancement.Builder.advancement().parent(katanas).display(ModItems.SABERS.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_saber.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_saber.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_saber", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.SABERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "saber"), existingFileHelper);
			Advancement.Builder.advancement().parent(sabers).display(ModItems.RAPIERS.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_rapier.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_rapier.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_rapier", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.RAPIERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "rapier"), existingFileHelper);
			Advancement.Builder.advancement().parent(longswords).display(ModItems.GREATSWORDS.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_greatsword.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_greatsword.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_greatsword", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.GREATSWORDS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "greatsword"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.WOODEN_CLUB.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_club.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_club.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_club", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.CLUBS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "club"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.CESTUS.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_cestus.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_cestus.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_cestus", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.CESTUSAE).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "cestus"), existingFileHelper);
			Advancement battleHammers = Advancement.Builder.advancement().parent(root).display(ModItems.BATTLE_HAMMERS.gold.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_battle_hammer.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_battle_hammer.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_battle_hammer", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.BATTLE_HAMMERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "battle_hammer"), existingFileHelper);
			Advancement.Builder.advancement().parent(battleHammers).display(ModItems.WARHAMMERS.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_warhammer.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_warhammer.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_warhammer", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.WARHAMMERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "warhammer"), existingFileHelper);
			Advancement spears = Advancement.Builder.advancement().parent(root).display(ModItems.SPEARS.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_spear.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_spear.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_spear", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.SPEARS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "spear"), existingFileHelper);
			Advancement.Builder.advancement().parent(spears).display(ModItems.HALBERDS.gold.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_halberd.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_halberd.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_halberd", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.HALBERDS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "halberd"), existingFileHelper);
			Advancement.Builder.advancement().parent(spears).display(ModItems.PIKES.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_pike.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_pike.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_pike", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.PIKES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "pike"), existingFileHelper);
			Advancement.Builder.advancement().parent(spears).display(ModItems.LANCES.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_lance.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_lance.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_lance", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.LANCES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "lance"), existingFileHelper);
			Advancement longbows = Advancement.Builder.advancement().parent(root).display(ModItems.LONGBOWS.wood.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_longbow.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_longbow.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_longbow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.LONGBOWS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "longbow"), existingFileHelper);
			Advancement.Builder.advancement().parent(longbows).display(ModItems.HEAVY_CROSSBOWS.wood.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_heavy_crossbow.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_heavy_crossbow.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_heavy_crossbow", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.HEAVY_CROSSBOWS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "heavy_crossbow"), existingFileHelper);
			Advancement.Builder.advancement().parent(daggers).display(ModItems.THROWING_KNIVES.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_throwing_knife.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_throwing_knife.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_throwing_knife", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.THROWING_KNIVES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "throwing_knife"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.TOMAHAWKS.gold.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_tomahawk.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_tomahawk.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_tomahawk", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.TOMAHAWKS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "tomahawk"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.JAVELINS.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_javelin.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_javelin.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_javelin", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.JAVELINS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "javelin"), existingFileHelper);
			Advancement.Builder.advancement().parent(daggers).display(ModItems.BOOMERANGS.wood.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_boomerang.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_boomerang.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_boomerang", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.BOOMERANGS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "boomerang"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.BATTLEAXES.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_battleaxe.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_battleaxe.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_battleaxe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.BATTLEAXES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "battleaxe"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.FLANGED_MACES.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_flanged_mace.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_flanged_mace.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_flanged_mace", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.FLANGED_MACES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "flanged_mace"), existingFileHelper);
			Advancement glaives = Advancement.Builder.advancement().parent(spears).display(ModItems.GLAIVES.iron.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_glaive.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_glaive.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_glaive", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.GLAIVES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "glaive"), existingFileHelper);
			Advancement.Builder.advancement().parent(root).display(ModItems.QUARTERSTAVES.gold.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_quarterstaff.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_quarterstaff.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_quarterstaff", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.QUARTERSTAVES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "quarterstaff"), existingFileHelper);
			Advancement scythes = Advancement.Builder.advancement().parent(glaives).display(ModItems.SCYTHES.diamond.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_scythe.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_scythe.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_scythe", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.SCYTHES).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "scythe"), existingFileHelper);

			Advancement quivers = Advancement.Builder.advancement().parent(longbows).display(ModItems.SMALL_ARROW_QUIVER.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_quiver.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".craft_quiver.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_quiver", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.SMALL_QUIVERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "small_quiver"), existingFileHelper);
			Advancement upgradeQuiver = Advancement.Builder.advancement().parent(quivers).display(ModItems.LARGE_ARROW_QUIVER.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".upgrade_quiver.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".upgrade_quiver.desc"), 
					null, FrameType.TASK, true, true, false).addCriterion("has_upgraded_quiver", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.UPGRADED_QUIVERS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "upgrade_quiver"), existingFileHelper);
			Advancement.Builder.advancement().parent(upgradeQuiver).display(ModItems.HUGE_ARROW_QUIVER.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".upgrade_quiver_max.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".upgrade_quiver_max.desc"), 
					null, FrameType.GOAL, true, true, false).addCriterion("has_max_upgraded_quiver", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.UPGRADED_QUIVERS_MAX).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "upgrade_quiver_max"), existingFileHelper);

			Advancement.Builder.advancement().parent(root).display(ModItems.BATTLEAXES.netherite.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".upgrade_netherite.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".upgrade_netherite.desc"), 
					null, FrameType.GOAL, true, true, false).addCriterion("has_netherite_weapon", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(ModItemTags.NETHERITE_WEAPONS).build())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "upgrade_netherite_weapon"), existingFileHelper);
			
			Advancement.Builder.advancement().parent(scythes).display(ModItems.PIGLIN_HEAD.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".collect_heads.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".collect_heads.desc"),
					null, FrameType.CHALLENGE, true, true, false).rewards(AdvancementRewards.Builder.experience(200).build()).
					addCriterion("creeper_head", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CREEPER_HEAD)).addCriterion("skeleton_skull", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SKELETON_SKULL)).
					addCriterion("wither_skeleton_skull", InventoryChangeTrigger.TriggerInstance.hasItems(Items.WITHER_SKELETON_SKULL)).addCriterion("zombie_head", InventoryChangeTrigger.TriggerInstance.hasItems(Items.ZOMBIE_HEAD)).
					addCriterion("blaze_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.BLAZE_HEAD.get())).addCriterion("enderman_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ENDERMAN_HEAD.get())).
					addCriterion("spider_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SPIDER_HEAD.get())).addCriterion("cave_spider_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CAVE_SPIDER_HEAD.get())).
					addCriterion("piglin_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.PIGLIN_HEAD.get())).addCriterion("zombified_piglin_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ZOMBIFIED_PIGLIN_HEAD.get())).
					addCriterion("husk_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.HUSK_HEAD.get())).addCriterion("stray_skull", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.STRAY_SKULL.get())).
					addCriterion("drowned_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.DROWNED_HEAD.get())).addCriterion("illager_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ILLAGER_HEAD.get())).
					addCriterion("witch_head", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.WITCH_HEAD.get())).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "collect_heads"), existingFileHelper);
		
			Advancement.Builder.advancement().parent(root).display(ModItems.WEAPON_OIL.get(), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".brew_oil.title"), Component.translatable("advancement." + ModSpartanWeaponry.ID + ".brew_oil.desc"),
					null, FrameType.TASK, true, true, false).addCriterion("has_brewed_oil", BrewOilTrigger.TriggerInstance.brewedOil()).save(saver, new ResourceLocation(ModSpartanWeaponry.ID, "brew_oil"), existingFileHelper);
		}
		
	}
}
