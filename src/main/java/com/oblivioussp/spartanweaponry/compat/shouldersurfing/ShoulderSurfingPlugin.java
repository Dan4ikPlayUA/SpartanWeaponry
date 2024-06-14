package com.oblivioussp.spartanweaponry.compat.shouldersurfing;

import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingPlugin;
import com.github.exopandora.shouldersurfing.api.plugin.IShoulderSurfingRegistrar;
import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;

public class ShoulderSurfingPlugin implements IShoulderSurfingPlugin
{
	@Override
	public void register(IShoulderSurfingRegistrar registrar)
	{
		registrar.registerAdaptiveItemCallback(stack -> stack.is(ModItemTags.HAS_CUSTOM_CROSSHAIR));
	}
}
