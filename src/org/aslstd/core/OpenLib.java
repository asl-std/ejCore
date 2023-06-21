package org.aslstd.core;

import java.util.LinkedHashSet;
import java.util.Set;

import org.aslstd.api.bukkit.command.BasicCommandHandler;
import org.aslstd.api.bukkit.message.Text;
import org.aslstd.api.bukkit.redstone.RedstoneParts;
import org.aslstd.api.bukkit.storage.PlayerFileStorage;
import org.aslstd.api.bukkit.utils.ServerVersion;
import org.aslstd.api.openlib.player.OPlayer;
import org.aslstd.api.openlib.plugin.Incompatibility;
import org.aslstd.api.openlib.plugin.OpenPlugin;
import org.aslstd.api.openlib.plugin.hook.Placeholders;
import org.aslstd.api.openlib.util.Obj;
import org.aslstd.api.openlib.worker.WorkerService;
import org.aslstd.api.provider.permission.PermProvider;
import org.aslstd.core.command.CoreCommandHandler;
import org.aslstd.core.config.EConfig;
import org.aslstd.core.config.LangConfig;
import org.aslstd.core.expansion.DataExpansion;
import org.aslstd.core.listener.CombatListener;
import org.aslstd.core.listener.EquipListener;
import org.aslstd.core.listener.PaneInteractListener;
import org.aslstd.core.listener.PlayerListener;
import org.aslstd.core.listener.temp.CancelJoinBeforeFullLoading;
import org.aslstd.core.platform.scheduler.provider.BukkitSchedulerProvider;
import org.aslstd.core.platform.scheduler.provider.FoliaSchedulerProvider;
import org.aslstd.core.platform.scheduler.provider.SchedulerProvider;
import org.aslstd.core.service.ListenerRegistrator;
import org.aslstd.core.service.ListenerRegistrator.Collector;
import org.aslstd.core.task.LoadOpenPlugins;
import org.aslstd.core.task.Test;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.experimental.Accessors;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

/**
 * <p>Core class.</p>
 *
 * @author Snoop1CattZ69
 */
@Accessors(fluent = true)
public class OpenLib extends OpenPlugin {

	public static final String[]
			ANCIITAG = {
			            "&5######################################################################",
			            "&9",
			            "&9         ██████╗ ██████╗ ███████╗███╗   ██╗ ██╗     ██╗██████╗        ",
			            "&9        ██╔═══██╗██╔══██╗██╔════╝████╗  ██║ ██║     ██║██╔══██╗       ",
			            "&9        ██║   ██║██████╔╝█████╗  ██╔██╗ ██║ ██║     ██║██████╔╝       ",
			            "&9        ██║   ██║██╔═══╝ ██╔══╝  ██║╚██╗██║ ██║     ██║██╔══██╗       ",
			            "&9        ╚██████╔╝██║     ███████╗██║ ╚████║ ███████╗██║██████╔╝       ",
			            "&9         ╚═════╝ ╚═╝     ╚══════╝╚═╝  ╚═══╝ ╚══════╝╚═╝╚═════╝        ",
			            "&9",
			            "&b           MY DISCORD SUPPORT CHANNEL  |  DISCORD.ASLSTD.ORG          ",
			            "&5######################################################################", };

	@Getter private static EConfig config;
	@Getter private static LangConfig lang;
	@Getter private static PlayerFileStorage playerDatabase;
	private static Set<OpenPlugin> plugins = new LinkedHashSet<>();

	@Getter private static PermProvider permission;

	@Getter private static WorkerService workers;

	@Getter private static BasicCommandHandler handler;

	@Getter private static OpenLib instance = null;

	@Getter private static SchedulerProvider scheduler = null;

	@Override public void preInit() {
		final NBTItem it = new NBTItem(new ItemStack(Material.IRON_INGOT, 1));
		it.setBoolean("init", true);
		init();
	}

	@Override public int getPriority() {
		return 0;
	}

	@Override
	public void onLoad() {
		instance = this;
		workers = new WorkerService(Runtime.getRuntime().availableProcessors());

		if (Obj.classExist("io.papermc.paper.threadedregions.RegionizedServer")) {
			scheduler = new FoliaSchedulerProvider();
			Text.debug("Folia found - Using Folia Scheduler");
		} else {
			scheduler = new BukkitSchedulerProvider();
			Text.debug("Folia not found - Using Bukkit Scheduler");
		}
	}

	@Override public void init() {
		final long bef = System.nanoTime();
		OpenLib.config = new EConfig(getDataFolder() + "/config.yml", this);
		OpenLib.lang = new LangConfig(getDataFolder() + "/lang.yml", this);
		CancelJoinBeforeFullLoading.register();

		permission = PermProvider.request(this);

		resourceId = 38074;
		playerDatabase = PlayerFileStorage.createDatabase(this);

		new Metrics(instance, 2908);

		RedstoneParts.init();

		if (!config.LESS_CONSOLE)
			for (final String str : ANCIITAG) Text.send(str);

		ServerVersion.init(Bukkit.getBukkitVersion(), Bukkit.getName());

		if (Placeholders.enabled()) {
			Text.fine("PAPI expansion loaded!");
			new DataExpansion();
		} else
			Text.warn("I can't create new PAPI expansion because PlaceholderAPI not installed.");

		Test.start();
		Collector.forPlugin(this).collect(new PlayerListener(), new PaneInteractListener(), new CombatListener(), new EquipListener()).push();

		handler = new CoreCommandHandler().registerHandler();

		for (final Plugin plugin : Bukkit.getPluginManager().getPlugins())
			if (plugin instanceof OpenPlugin && !plugin.getName().equalsIgnoreCase(this.getName()))
				plugins.add((OpenPlugin) plugin);

		if (plugins.size() > 0) {
			Text.fine("&amoLibrary found OpenPlugins, wait while all plugins enables.. ");
			new LoadOpenPlugins(plugins).runTaskTimer(this, 0, 40L);
		} else {
			ListenerRegistrator.register();
			CancelJoinBeforeFullLoading.unregister();
		}

		Text.fine("&a" + getName() + " succesfuly loaded in " + Text.format((System.nanoTime() - bef) / 1e9) + " sec.");
		Text.sendLB();
		Incompatibility.check();
		if (Placeholders.enabled() && !Placeholders.preRegister().isEmpty())
			Placeholders.preRegister().values().forEach(PlaceholderExpansion::register);
	}

	@Override
	public void disable() {
		OPlayer.stash().clear();
		ListenerRegistrator.unregister();
		workers.shutdown();
	}

	public void reloadPlugins() {
		plugins.forEach(OpenPlugin::reloadPlugin);
	}

}