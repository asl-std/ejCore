package org.aslstd.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.bukkit.value.Pair;
import org.aslstd.api.ejcore.plugin.BukkitListener;
import org.aslstd.api.ejcore.plugin.Named;
import org.aslstd.api.ejcore.util.Obj;
import org.aslstd.core.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.AvailableSince;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AvailableSince(value = "1.2.19")
public class ListenerRegistrator {

	private static ConcurrentMap<String, Pair<BukkitListener,Plugin>> listeners = new ConcurrentHashMap<>();

	public static void register() {
		for (final Pair<BukkitListener,Plugin> value : listeners.values())
			register(value);
	}

	public static void register(@NotNull String key) {
		Obj.checkNull(key);
		if (listeners.containsKey(key))
			register(listeners.get(key));
	}

	public static void register(@NotNull Plugin plugin) {
		Obj.checkNull(plugin);
		for (final Entry<String, Pair<BukkitListener,Plugin>> entry : listeners.entrySet())
			if (entry.getValue().getSecond().equals(plugin))
				register(entry.getValue());
	}

	public static void unregister() {
		for (final Pair<BukkitListener,Plugin> value : listeners.values())
			unregister(value);
	}

	public static void unregister(@NotNull String key) {
		Obj.checkNull(key);
		if (listeners.containsKey(key))
			unregister(listeners.get(key));
	}

	public static void unregister(@NotNull Plugin plugin) {
		Obj.checkNull(plugin);
		for (final Entry<String, Pair<BukkitListener,Plugin>> entry : listeners.entrySet())
			if (entry.getValue().getSecond().equals(plugin))
				unregister(entry.getValue());
	}

	public static @Nullable String add(@NotNull BukkitListener listener, @NotNull Plugin plugin) {
		Obj.checkNull("listener and plugin cannot be a null", listener, plugin);
		final Named name = listener.getClass().getAnnotation(Named.class);
		String keyName = null;
		if (name.key().equalsIgnoreCase(""))
			while(keyName != null && !listeners.containsKey(keyName))
				keyName = String.valueOf(plugin.getName().hashCode()*new Random(plugin.getName().hashCode()).nextDouble());
		else
			keyName = name.key();

		listeners.put(keyName, new Pair<>(listener,plugin));
		return keyName;
	}

	public static @Nullable Pair<BukkitListener,Plugin> remove(@NotNull String key) {
		Obj.checkNull(key);
		if (listeners.containsKey(key))
			return listeners.remove(key);
		return null;
	}

	public static void remove(@NotNull Plugin plugin) {
		Obj.checkNull(plugin);
		for (final Entry<String, Pair<BukkitListener,Plugin>> entry : listeners.entrySet())
			if (entry.getValue().getSecond().equals(plugin))
				listeners.remove(entry.getKey());
	}

	private static void register(@NotNull Pair<BukkitListener,Plugin> pair) {
		Bukkit.getPluginManager().registerEvents(pair.getFirst(), pair.getSecond());
		if (Core.getCfg().DEBUG_RUNNING)
			EText.debug("Loaded listener: " + pair.getFirst().getClass().getName());
	}

	private static void unregister(Pair<BukkitListener,Plugin> pair) {
		HandlerList.unregisterAll(pair.getFirst());
	}

	public static class Collector {

		private Plugin plugin;
		private List<BukkitListener> listeners;

		private Collector(Plugin plugin) {
			this.plugin = plugin;
			listeners = new ArrayList<>();
		}

		public static Collector forPlugin(@NotNull Plugin plugin) {
			return new Collector(plugin);
		}

		public Collector collect(@NotNull BukkitListener listener) {
			listeners.add(listener);
			return this;
		}

		public Collector collect(@NotNull BukkitListener... listeners ) {
			Stream.of(listeners).filter(Objects::nonNull).forEach(l -> this.listeners.add(l));
			return this;
		}

		public void push() {
			for (final BukkitListener listener : listeners)
				add(listener, plugin);
		}

	}

}