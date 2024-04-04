package com.oblivioussp.spartanweaponry.compat.shouldersurfing;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;
import com.teamderpy.shouldersurfing.client.ShoulderInstance;
import com.teamderpy.shouldersurfing.client.ShoulderRenderer;

public class ShoulderSurfingCompat
{
	public static boolean isShoulderSurfing()
	{
		return ShoulderInstance.getInstance().doShoulderSurfing();
	}
	
	public static void offsetCrosshairs(PoseStack poseStack, Window window, float partialTicks)
	{
		ShoulderRenderer.getInstance().offsetCrosshair(poseStack, window, partialTicks);
	}
	
	public static void clearCrosshairOffset(PoseStack poseStack)
	{
		ShoulderRenderer.getInstance().clearCrosshairOffset(poseStack);
	}
}
