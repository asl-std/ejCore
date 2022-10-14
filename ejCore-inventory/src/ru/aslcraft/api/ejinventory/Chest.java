package ru.aslcraft.api.ejinventory;

import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface Chest extends InventoryHolder {

	Map<Integer,String> save();

	void load(Map<Integer,String> map);

	void addItem(ItemStack stack);

	void addItem(ItemStack... stacks);

	void remove(ItemStack stack);

	void remove(int slot);

	default void onClick(InventoryClickEvent e) {}

	default void onDrag(InventoryDragEvent e) {}

	default void onOpen(InventoryOpenEvent e) {}

	default void onClose(InventoryCloseEvent e) {}

	default void open(Player p) {
		p.closeInventory();
		p.openInventory(getInventory());
	}

}
