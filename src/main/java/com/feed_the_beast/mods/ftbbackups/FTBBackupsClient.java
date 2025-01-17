package com.feed_the_beast.mods.ftbbackups;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Supplier;

public class FTBBackupsClient
{
	private static Supplier<Minecraft> minecraftSupplier;
	private static int currentBackupFile = 0;
	private static int totalBackupFiles = 0;

	public static void init(FMLClientSetupEvent event)
	{
		minecraftSupplier = event.getMinecraftSupplier();
		//MinecraftForge.EVENT_BUS.addListener(FTBBackupsClient::onClientDisconnected);
		MinecraftForge.EVENT_BUS.addListener(FTBBackupsClient::onDebugInfoEvent);
	}

	public static void setFiles(int current, int total)
	{
		currentBackupFile = current;
		totalBackupFiles = total;
	}

	/*private static void onClientDisconnected(FMLNetworkEvent.ClientDisconnectionFromServerEvent event)
	{
		currentBackupFile = 0;
		totalBackupFiles = 0;
	}*/

	private static void onDebugInfoEvent(RenderGameOverlayEvent.Text event)
	{
		if (minecraftSupplier.get().gameSettings.showDebugInfo)
		{
			return;
		}

		if (totalBackupFiles > 0 && totalBackupFiles > currentBackupFile)
		{
			event.getLeft().add(TextFormatting.LIGHT_PURPLE + I18n.format("ftbbackups.lang.timer_progress", currentBackupFile * 100 / totalBackupFiles, currentBackupFile, totalBackupFiles));
		}
	}
}