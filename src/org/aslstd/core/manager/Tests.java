package org.aslstd.core.manager;

import java.util.Arrays;
import java.util.List;

import org.aslstd.api.bukkit.items.ItemStackUtil;
import org.aslstd.api.bukkit.message.EText;
import org.aslstd.api.ejcore.player.Options;
import org.bukkit.inventory.ItemStack;

/**
 * <p>Tests class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class Tests {

	private static boolean tested = false;

	/**
	 * <p>start.</p>
	 */
	public static void start() {
		if (!tested) {
			testStringBuffer();
			testSettings();
			tested = true;
		}
	}

	private static void testStringBuffer() {
		EText.debug("ItemHashConverter System: Checking..♥♦♣♠");
		final List<String> hashes = Arrays.asList("IRON_SWORD:1:0♦&4Уровень: 1◘&4Крит-Шанс: +3.0◘&5&m--===[&6&l  Аттрибуты &5&m]===--&3Урон: +6.0-8.0♥&9Iron Sword");

		for (final String hash : hashes) {
			final ItemStack stack = ItemStackUtil.toStack(hash);
			if (!ItemStackUtil.toString(stack).equalsIgnoreCase("IRON_SWORD:1:0♥&9Iron Sword♦&4Уровень: 1◘&4Крит-Шанс: +3.0◘&5&m--===[&6&l  Аттрибуты &5&m]===--&3Урон: +6.0-8.0")) {
				EText.warn("ItemHashConverter System: Item cannot be converted from Hash! Serialiser not works properly");
				EText.sendRaw("IN:  " + hashes.get(0));
				EText.sendRaw("REQ: IRON_SWORD:1:0♥&9Iron Sword♦&4Уровень: 1◘&4Крит-Шанс: +3.0◘&5&m--===[&6&l  Аттрибуты &5&m]===--&3Урон: +6.0-8.0");
				EText.sendRaw("OUT: " + ItemStackUtil.toString(stack));
			} else
				EText.debug("ItemHashConverter System: No any problem found, remember: ♥♦♣♠ not is good game!");
		}
	}

	private static void testSettings() {
		EText.debug("Settings System: Checking..");
		final Options options = new Options();

		options.writeBase("player.equip.chestplate.health", 2);
		options.writeBase("player.equip.chestplate.damage", 2);
		options.writeBase("player.equip.helmet.health", 2);
		options.writeBase("player.equip.helmet.damage", 2);
		options.writeBase("player.equip.leggings.health", 2);
		options.writeBase("player.equip.leggings.damage", 2);
		options.writeBase("player.equip.boots.health", 2);
		options.writeBase("player.equip.boots.damage", 2);
		options.writeBase("player.equip.hand.health", 2);
		options.writeBase("player.equip.hand.damage", 2);
		options.writeBase("player.equip.offhand.health", 2);
		options.writeBase("player.equip.offhand.damage", 2);
		options.eraseValues("helmet");
		options.eraseValues("chestplate");
		options.eraseValues("leggings");
		options.eraseValues("boots");
		options.eraseValues("equip");
		options.writeValue("player.equip.chestplate.health", 2, 2.2);
		options.writeRange("player.equip.chestplate.damage", 2, 3.3);
		options.writeBase("player.equip.helmet.health", 2);
		options.writeBase("player.equip.helmet.damage", 2);
		options.writeBase("player.equip.leggings.health", 2);
		options.writeBase("player.equip.leggings.damage", 2);
		options.writeBase("player.equip.boots.health", 2);
		options.writeBase("player.equip.boots.damage", 2);
		options.writeBase("player.equip.hand.health", 2);
		options.writeBase("player.equip.hand.damage", 2);
		options.writeBase("player.equip.offhand.health", 2);
		options.writeBase("player.equip.offhand.damage", 2);

		if (options.tempStore().getSettingsSize() == 14)
			EText.debug("Settings System: Works correctly!");
		else {
			EText.warn("Settings System: Some problem found.. Please tell to author about with text above!");
			EText.dumpConsole(options.tempStore());
		}
	}

}