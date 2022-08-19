package ru.aslcraft.core.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import ru.aslcraft.api.ejcore.plugin.hook.HookManager;
import ru.aslcraft.api.ejcore.plugin.hook.PAPI;
import ru.aslcraft.core.Core;

public class ReloadPAPIExpansions extends BukkitRunnable {
	@Getter private static boolean started = false;

	public static void start() {
		if (started) return;

		new ReloadPAPIExpansions().runTaskLater(Core.instance(), 80L);
	}

	private ReloadPAPIExpansions() {
		started = true;
	}

	@Override
	public void run() {

		if (!HookManager.isPapiEnabled()) return;

		PAPI.getPreRegister().values().forEach(PlaceholderExpansion::register);

		started = false;
	}

}
