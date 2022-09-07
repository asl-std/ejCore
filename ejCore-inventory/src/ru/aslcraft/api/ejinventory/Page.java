package ru.aslcraft.api.ejinventory;

import java.util.List;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * <p>Page interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface Page {

	/**
	 * <p>getTitle.</p>
	 *
	 * @return a {@link java.lang.String} object
	 */
	String getTitle();

	List<Element> getElements();

	/**
	 * <p>add.</p>
	 *
	 * @param elements a {@link ru.aslcraft.api.ejinventory.Element} object
	 * @return an array of {@link ru.aslcraft.api.ejinventory.Element} objects
	 */
	Element[] add(Element... elements);

	/**
	 * <p>add.</p>
	 *
	 * @param element a {@link ru.aslcraft.api.ejinventory.Element} object
	 * @return a boolean
	 */
	boolean add(Element element);

	/**
	 * <p>add.</p>
	 *
	 * @param element a {@link ru.aslcraft.api.ejinventory.Element} object
	 * @param locX a int
	 * @param locY a int
	 * @param ignore a boolean
	 * @return a boolean
	 */
	boolean add(Element element, int locX, int locY, boolean ignore);

	/**
	 * <p>contains.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @return a boolean
	 */
	boolean contains(ItemStack icon);

	/**
	 * <p>display.</p>
	 *
	 * @param inv a {@link org.bukkit.inventory.Inventory} object
	 */
	void display(Inventory inv);

	default void update(Inventory inv, int locX, int locY) {
		getElements().forEach(e -> e.update(inv, locX, locY));
	}

	/**
	 * <p>fill.</p>
	 *
	 * @param element a {@link ru.aslcraft.api.ejinventory.Element} object
	 */
	void fill(Element element);

	/**
	 * Executes internal functions
	 *
	 * @param {@link org.bukkit.event.inventory.InventoryClickEvent} instance
	 * @return true if execution is done correctly
	 */
	boolean fire(InventoryClickEvent event);

	/**
	 * @return a page height
	 */
	int height();

	/**
	 * <p>remove.</p>
	 *
	 * @param locX x coordinate
	 * @param locY y coordinate
	 */
	void remove(int locX, int locY);

	/**
	 * Removes an Element from this Page
	 */
	void remove(ItemStack stack);

	/**
	 * @return a page width
	 */
	int width();

	List<Integer> getUnlocked();

}
