package ru.asl.api.ejcore.items;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

	public static boolean addItem(ItemStack stack, Player p) {
		if (stack == null) return false;

		for (ItemStack item : p.getInventory().getStorageContents())
			if (item == null || item.getType() == Material.AIR) {
				p.getInventory().addItem(stack);
				return true;
			}

		Item item = p.getWorld().dropItem(p.getLocation(), stack);
		item.setPickupDelay(0);
		return true;
	}

	public static void decreaseItemAmount(ItemStack stack, Player p, int amount) {
		ItemStack[] storage = p.getInventory().getStorageContents();
		String stackString = ItemStackUtil.toString(stack);
		for (int i = 0; i < storage.length; i++)
			if (stackString.equals(ItemStackUtil.toString(storage[i]))) {
				ItemStack inv = storage[i];

				if (inv.getAmount() > 1) {
					inv.setAmount(inv.getAmount() - amount);
					storage[i] = inv;
				} else
					storage[i] = new ItemStack(Material.AIR, 0);
				p.getInventory().setStorageContents(storage);
				return;
			} else continue;
	}

	public static void decreaseItemChecksNameAmount(ItemStack stack, String name, Player p, int amount) {
		ItemStack[] storage = p.getInventory().getStorageContents();
		String toCheck = stack == null ? name : ItemStackUtil.getDisplayName(stack);
		Material type = stack.getType();
		for (int i = 0; i < storage.length; i++)
			if (ItemStackUtil.getDisplayName(storage[i]).equals(toCheck) && storage[i].getType() == type) {
				ItemStack inv = storage[i];

				if (inv.getAmount() > 1) {
					inv.setAmount(inv.getAmount() - 1);
					storage[i] = inv;
				} else
					storage[i] = new ItemStack(Material.AIR, 0);
				p.getInventory().setStorageContents(storage);
				return;
			} else continue;
	}

	public static void decreaseItem(ItemStack stack, Player p) {
		InventoryUtil.decreaseItemAmount(stack,p,1);
	}

	public static void decreaseItemChecksName(ItemStack stack, Player p) {
		InventoryUtil.decreaseItemChecksNameAmount(stack,"",p,1);
	}

	public static void decreaseItemChecksName(String name, Player p) {
		InventoryUtil.decreaseItemChecksNameAmount(null,name,p,1);
	}

	public static void removeItem(ItemStack stack, Player p) {
		ItemStack[] storage = p.getInventory().getStorageContents();
		for (int i = 0; i < storage.length; i++)
			if (ItemStackUtil.toString(stack).equals(ItemStackUtil.toString(storage[i]))) {
				storage[i] = new ItemStack(Material.AIR, 0);
				p.getInventory().setStorageContents(storage);
				return;
			} else continue;
	}

}