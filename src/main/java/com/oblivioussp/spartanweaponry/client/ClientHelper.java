package com.oblivioussp.spartanweaponry.client;

import com.oblivioussp.spartanweaponry.ModSpartanWeaponry;
import com.oblivioussp.spartanweaponry.api.ModToolActions;
import com.oblivioussp.spartanweaponry.api.ModelOverrides;
import com.oblivioussp.spartanweaponry.api.WeaponTraits;
import com.oblivioussp.spartanweaponry.api.oil.OilEffect;
import com.oblivioussp.spartanweaponry.block.ExtendedSkullBlock;
import com.oblivioussp.spartanweaponry.client.gui.HudCrosshair;
import com.oblivioussp.spartanweaponry.client.gui.HudLoadState;
import com.oblivioussp.spartanweaponry.client.gui.HudOilUses;
import com.oblivioussp.spartanweaponry.client.gui.HudQuiverAmmo;
import com.oblivioussp.spartanweaponry.client.gui.container.QuiverArrowScreen;
import com.oblivioussp.spartanweaponry.client.gui.container.QuiverBoltScreen;
import com.oblivioussp.spartanweaponry.client.inventory.ClientOilCoatingTooltip;
import com.oblivioussp.spartanweaponry.client.inventory.ClientQuiverTooltip;
import com.oblivioussp.spartanweaponry.client.model.CurioRenderer;
import com.oblivioussp.spartanweaponry.client.model.LargeArrowQuiverModel;
import com.oblivioussp.spartanweaponry.client.model.LargeBoltQuiverModel;
import com.oblivioussp.spartanweaponry.client.model.MediumArrowQuiverModel;
import com.oblivioussp.spartanweaponry.client.model.MediumBoltQuiverModel;
import com.oblivioussp.spartanweaponry.client.model.SmallArrowQuiverModel;
import com.oblivioussp.spartanweaponry.client.model.SmallBoltQuiverModel;
import com.oblivioussp.spartanweaponry.client.renderer.entity.ArrowBaseRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.BoltRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.BoomerangRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.EndermanHeadModel;
import com.oblivioussp.spartanweaponry.client.renderer.entity.ExtendedSkullHelper;
import com.oblivioussp.spartanweaponry.client.renderer.entity.IllagerHeadModel;
import com.oblivioussp.spartanweaponry.client.renderer.entity.JavelinRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.ModelLayers;
import com.oblivioussp.spartanweaponry.client.renderer.entity.PiglinHeadModel;
import com.oblivioussp.spartanweaponry.client.renderer.entity.SimpleArrowRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.ThrowingWeaponRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.TomahawkRenderer;
import com.oblivioussp.spartanweaponry.client.renderer.entity.WitchHeadModel;
import com.oblivioussp.spartanweaponry.entity.projectile.ArrowExplosiveEntity;
import com.oblivioussp.spartanweaponry.init.ModBlockEntities;
import com.oblivioussp.spartanweaponry.init.ModEntities;
import com.oblivioussp.spartanweaponry.init.ModItems;
import com.oblivioussp.spartanweaponry.init.ModMenus;
import com.oblivioussp.spartanweaponry.inventory.tooltip.OilCoatingTooltip;
import com.oblivioussp.spartanweaponry.inventory.tooltip.QuiverTooltip;
import com.oblivioussp.spartanweaponry.item.HeavyCrossbowItem;
import com.oblivioussp.spartanweaponry.item.LongbowItem;
import com.oblivioussp.spartanweaponry.item.QuiverBaseItem;
import com.oblivioussp.spartanweaponry.item.SwordBaseItem;
import com.oblivioussp.spartanweaponry.item.ThrowingWeaponItem;
import com.oblivioussp.spartanweaponry.util.Log;
import com.oblivioussp.spartanweaponry.util.OilHelper;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.model.SkullModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.CuriosApi;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHelper 
{
	
	public static final IGuiOverlay LOAD_STATE = HudLoadState::render;
	public static final IGuiOverlay QUIVER_AMMO = HudQuiverAmmo::render;
	public static final IGuiOverlay OIL_USES = HudOilUses::render;
	public static final IGuiOverlay NEW_CROSSHAIR = HudCrosshair::render;
	
	public static final ItemColor COLOR_TIPPED_PROJECTILE = (stack, idx) -> idx == 1 ? PotionUtils.getColor(stack) : 0xFFFFFF;
	public static final ItemColor COLOR_OIL = (stack, idx) ->
		{
			OilEffect oilEffect = OilHelper.getOilFromStack(stack);
			return idx == 1 ? oilEffect.getColor(stack) : 0xFFFFFF;
		};
	
	@SubscribeEvent
	public static void registerItemColoursHandler(RegisterColorHandlersEvent.Item ev)
	{
		ev.register(COLOR_TIPPED_PROJECTILE, ModItems.TIPPED_WOODEN_ARROW.get(), 
				ModItems.TIPPED_COPPER_ARROW.get(),
				ModItems.TIPPED_IRON_ARROW.get(),
				ModItems.TIPPED_DIAMOND_ARROW.get(),
				ModItems.TIPPED_NETHERITE_ARROW.get(),
				ModItems.TIPPED_BOLT.get(),
				ModItems.TIPPED_COPPER_BOLT.get(),
				ModItems.TIPPED_DIAMOND_BOLT.get(),
				ModItems.TIPPED_NETHERITE_BOLT.get());
		ev.register(COLOR_OIL, ModItems.WEAPON_OIL.get());
	}
	
	public static void registerCurioRenders()
	{
		if(ModList.get().isLoaded(CuriosApi.MODID))
		{
			CurioRenderer.register();
		}
	}
	
	public static void registerMeleeWeaponPropertyOverrides(SwordBaseItem meleeWeapon)
	{
		ItemProperties.register(meleeWeapon, ModelOverrides.BLOCKING, (stack, world, living, value) ->
		{
			return meleeWeapon.canPerformAction(stack, ModToolActions.MELEE_BLOCK) &&  living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
		Item weapon = meleeWeapon.getAsItem();
		ItemProperties.register(weapon, ModelOverrides.THROWING, (stack, world, living, value) ->
		{
			if(living == null || !meleeWeapon.hasWeaponTrait(WeaponTraits.THROWABLE.get()) || !stack.is(living.getUseItem().getItem()))	return 0.0f;
			return living.getTicksUsingItem() > 0 ? 1.0f : 0.0f;
		});
	}

	public static void registerHeavyCrossbowPropertyOverrides(HeavyCrossbowItem crossbow)
	{
		ItemProperties.register(crossbow, ModelOverrides.PULL, (stack, world, living, value) ->
		{
			if(living != null /*&& stack.getItem() == crossbow*/)
				return crossbow.isLoaded(stack) ? 0.0f : (float)(crossbow.getLoadingTicks(stack, living)) / crossbow.getFullLoadTicks(stack);
			return 0.0f;
		});
		ItemProperties.register(crossbow, ModelOverrides.PULLING, (stack, world, living, value) ->
		{
			return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
		ItemProperties.register(crossbow, ModelOverrides.CHARGED, (stack, world, living, value) ->
		{
			return crossbow.isLoaded(stack) ? 1.0f : 0.0f;
		});
	}
	
	public static void registerLongbowPropertyOverrides(LongbowItem longbow)
	{
		ItemProperties.register(longbow, ModelOverrides.PULLING, (stack, world, living, value) ->
		{
			return living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
		ItemProperties.register(longbow, ModelOverrides.PULL, (stack, world, shooter, value) -> 
		{
			return shooter != null && shooter.getUseItem() == stack ? longbow.getNockProgress(stack, shooter) : 0.0f;
		});
	}
	
	public static void registerThrowingWeaponPropertyOverrides(ThrowingWeaponItem throwingWeapon)
	{
//		Log.debug("Registering Throwing Weapon Property Overrides for item: \"" + throwingWeapon.getRegistryName().toString() + "\"");
		ItemProperties.register(throwingWeapon, ModelOverrides.THROWING, (stack, world, living, value) ->
		{
			if(living == null || !stack.is(living.getUseItem().getItem()))	return 0.0f;
			return living.getTicksUsingItem() > 0 ? 1.0f : 0.0f;
		});
		ItemProperties.register(throwingWeapon, ModelOverrides.EMPTY, (stack, world, living, value) ->
		{
			return stack.getOrCreateTag().getInt(ThrowingWeaponItem.NBT_AMMO_USED) == ((ThrowingWeaponItem)throwingWeapon).getMaxAmmo(stack) ? 1 : 0;
		});
	}
	
	public static void registerQuiverPropertyOverrides(QuiverBaseItem quiver)
	{
		ItemProperties.register(quiver, ModelOverrides.ARROW, (stack, world, living, value) ->
		{
			return quiver.getAmmoCount(stack);
		});
	}
	
/*	public static void registerMeleeWeaponBlockingPropertyOverrides(Item meleeWeapon)
	{
		ItemProperties.register(meleeWeapon, Const.MODEL, (stack, world, living, value) ->
		{
			return GearData.getModelIndex(stack);
		});
		ItemProperties.register(meleeWeapon, Const.BROKEN_PROPERTY, (stack, world, living, value) ->
		{
			return GearHelper.isBroken(stack) ? 0 : 1;
		});
		ItemProperties.register(meleeWeapon, ModelOverrides.BLOCKING, (stack, world, living, value) ->
		{
			return meleeWeapon.canPerformAction(stack, ModToolActions.MELEE_BLOCK) && living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0f : 0.0f;
		});
	}*/
	
	@SubscribeEvent
	public static void registerEntityRenders(EntityRenderersEvent.RegisterRenderers ev)
	{
		Log.info("Registering Entity Renderers!");
		ev.registerEntityRenderer(ModEntities.ARROW_SW.get(), ArrowBaseRenderer::new);
		ev.registerEntityRenderer(ModEntities.ARROW_EXPLOSIVE.get(), (rendererProvider) -> 
			new SimpleArrowRenderer<ArrowExplosiveEntity>(rendererProvider, new ResourceLocation(ModSpartanWeaponry.ID, "textures/entity/projectiles/explosive_arrow.png")));
		ev.registerEntityRenderer(ModEntities.BOLT.get(), BoltRenderer::new);
		ev.registerEntityRenderer(ModEntities.BOLT_SPECTRAL.get(), BoltRenderer::new);
		ev.registerEntityRenderer(ModEntities.THROWING_WEAPON.get(), ThrowingWeaponRenderer::new);
		ev.registerEntityRenderer(ModEntities.THROWING_KNIFE.get(), ThrowingWeaponRenderer::new);
		ev.registerEntityRenderer(ModEntities.TOMAHAWK.get(), TomahawkRenderer::new);
		ev.registerEntityRenderer(ModEntities.JAVELIN.get(), JavelinRenderer::new);
		ev.registerEntityRenderer(ModEntities.BOOMERANG.get(), BoomerangRenderer::new);
		ev.registerEntityRenderer(ModEntities.DYNAMITE.get(), ThrownItemRenderer::new);
		
		ev.registerBlockEntityRenderer(ModBlockEntities.EXTENDED_SKULL_TYPE.get(), SkullBlockRenderer::new);
	}
	
	@SubscribeEvent
	public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions ev)
	{
		Log.info("Registering Model Layers!");
		ev.registerLayerDefinition(ModelLayers.SMALL_ARROW_QUIVER, SmallArrowQuiverModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.MEDIUM_ARROW_QUIVER, MediumArrowQuiverModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.LARGE_ARROW_QUIVER, LargeArrowQuiverModel::createLayer);
		
		ev.registerLayerDefinition(ModelLayers.SMALL_BOLT_QUIVER, SmallBoltQuiverModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.MEDIUM_BOLT_QUIVER, MediumBoltQuiverModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.LARGE_BOLT_QUIVER, LargeBoltQuiverModel::createLayer);
		
		ev.registerLayerDefinition(ModelLayers.BLAZE_HEAD, SkullModel::createMobHeadLayer);
		ev.registerLayerDefinition(ModelLayers.ENDERMAN_HEAD, EndermanHeadModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.SPIDER_HEAD, ExtendedSkullHelper::createSpiderLayer);
		ev.registerLayerDefinition(ModelLayers.CAVE_SPIDER_HEAD, ExtendedSkullHelper::createSpiderLayer);
		ev.registerLayerDefinition(ModelLayers.PIGLIN_HEAD, PiglinHeadModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.ZOMBIFIED_PIGLIN_HEAD, PiglinHeadModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.HUSK_HEAD, ExtendedSkullHelper::createHuskLayer);
		ev.registerLayerDefinition(ModelLayers.STRAY_SKULL, ExtendedSkullHelper::createHeadWithHatLayer);
		ev.registerLayerDefinition(ModelLayers.DROWNED_HEAD, ExtendedSkullHelper::createHeadWithHatLayer);
		ev.registerLayerDefinition(ModelLayers.ILLAGER_HEAD, IllagerHeadModel::createLayer);
		ev.registerLayerDefinition(ModelLayers.WITCH_HEAD, WitchHeadModel::createLayer);
		
		Log.info("Model Layer registration complete!");
	}
	
	@SubscribeEvent
	public static void reload(RegisterClientReloadListenersEvent ev)
	{
		if(ModList.get().isLoaded(CuriosApi.MODID))
			ev.registerReloadListener(CurioRenderer.INSTANCE);
	}
	
	@SubscribeEvent
	public static void registerSkullModels(EntityRenderersEvent.CreateSkullModels ev)
	{
		EntityModelSet entityModelSet = ev.getEntityModelSet();
		ev.registerSkullModel(ExtendedSkullBlock.Types.BLAZE, new SkullModel(entityModelSet.bakeLayer(ModelLayers.BLAZE_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.ENDERMAN, new EndermanHeadModel(entityModelSet.bakeLayer(ModelLayers.ENDERMAN_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.SPIDER, new SkullModel(entityModelSet.bakeLayer(ModelLayers.SPIDER_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.CAVE_SPIDER, new SkullModel(entityModelSet.bakeLayer(ModelLayers.CAVE_SPIDER_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.PIGLIN, new SkullModel(entityModelSet.bakeLayer(ModelLayers.PIGLIN_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.ZOMBIE_PIGLIN, new SkullModel(entityModelSet.bakeLayer(ModelLayers.ZOMBIFIED_PIGLIN_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.HUSK, new SkullModel(entityModelSet.bakeLayer(ModelLayers.HUSK_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.STRAY, new SkullModel(entityModelSet.bakeLayer(ModelLayers.STRAY_SKULL)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.DROWNED, new SkullModel(entityModelSet.bakeLayer(ModelLayers.DROWNED_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.ILLAGER, new SkullModel(entityModelSet.bakeLayer(ModelLayers.ILLAGER_HEAD)));
		ev.registerSkullModel(ExtendedSkullBlock.Types.WITCH, new SkullModel(entityModelSet.bakeLayer(ModelLayers.WITCH_HEAD)));
	}
	
	public static void registerSkullTextures()
	{
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.BLAZE, new ResourceLocation("minecraft", "textures/entity/blaze.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.ENDERMAN, new ResourceLocation(ModSpartanWeaponry.ID, "textures/entity/skull/enderman_head.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.SPIDER, new ResourceLocation("minecraft", "textures/entity/spider/spider.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.CAVE_SPIDER, new ResourceLocation("minecraft", "textures/entity/spider/cave_spider.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.PIGLIN, new ResourceLocation("minecraft", "textures/entity/piglin/piglin.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.ZOMBIE_PIGLIN, new ResourceLocation("minecraft", "textures/entity/piglin/zombified_piglin.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.HUSK, new ResourceLocation("minecraft", "textures/entity/zombie/husk.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.STRAY, new ResourceLocation(ModSpartanWeaponry.ID, "textures/entity/skull/stray_skull.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.DROWNED, new ResourceLocation(ModSpartanWeaponry.ID, "textures/entity/skull/drowned_head.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.ILLAGER, new ResourceLocation("minecraft", "textures/entity/illager/pillager.png"));
		SkullBlockRenderer.SKIN_BY_TYPE.put(ExtendedSkullBlock.Types.WITCH, new ResourceLocation("minecraft", "textures/entity/witch.png"));
	}
	
	public static void registerScreens()
	{
		MenuScreens.register(ModMenus.QUIVER_ARROW.get(), QuiverArrowScreen::new);
		MenuScreens.register(ModMenus.QUIVER_BOLT.get(), QuiverBoltScreen::new);
	}
	
	@SubscribeEvent
	public static void registerHudOverlays(RegisterGuiOverlaysEvent ev)
	{
		ev.registerAboveAll("load_state", LOAD_STATE);
		ev.registerAboveAll("quiver_ammo", QUIVER_AMMO);
		ev.registerAboveAll("oil_uses", OIL_USES);
		ev.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "crosshair", NEW_CROSSHAIR);
	}

	@SubscribeEvent
	public static void registerTooltipComponents(RegisterClientTooltipComponentFactoriesEvent ev)
	{
		ev.register(QuiverTooltip.class, ClientQuiverTooltip::new);
		ev.register(OilCoatingTooltip.class, ClientOilCoatingTooltip::new);
	}
}
