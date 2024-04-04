package com.oblivioussp.spartanweaponry.compat.shouldersurfing;

import com.oblivioussp.spartanweaponry.api.tags.ModItemTags;
import com.teamderpy.shouldersurfing.api.IShoulderSurfingPlugin;
import com.teamderpy.shouldersurfing.api.IShoulderSurfingRegistrar;

public class ShoulderSurfingPlugin implements IShoulderSurfingPlugin
{
	@Override
	public void register(IShoulderSurfingRegistrar registrar)
	{
		registrar.registerAdaptiveItemCallback(stack -> stack.is(ModItemTags.HAS_CUSTOM_CROSSHAIR));
	}
}
