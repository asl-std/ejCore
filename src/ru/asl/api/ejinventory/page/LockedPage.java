package ru.asl.api.ejinventory.page;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import ru.asl.api.ejinventory.Element;
import ru.asl.api.ejinventory.Page;
import ru.asl.api.ejinventory.element.SimpleElement;

/**
 * <p>LockedPage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class LockedPage implements Page {
	/**
	 * <p>emptyElement.</p>
	 *
	 * @return a {@link ru.asl.api.ejinventory.Element} object
	 */
	public static final Element emptyElement() { return new SimpleElement(new ItemStack(Material.AIR), true); }

	private final Element[][] elements;
	private final boolean[][] unlockedSlots;

	private List<Integer> unlocked = new ArrayList<>();

	@Setter @Getter protected String title;

	/**
	 * <p>Constructor for LockedPage.</p>
	 *
	 * @param height a int
	 */
	public LockedPage(int height) {
		elements = new Element[9][height];
		unlockedSlots = new boolean[9][height];
		fill(LockedPage.emptyElement());
	}

	/**
	 * <p>Constructor for LockedPage.</p>
	 *
	 * @param height a int
	 * @param title a {@link java.lang.String} object
	 */
	public LockedPage(int height, @NonNull String title) {
		this(height);
		this.title = title;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param locX a int
	 * @param locY a int
	 * @return a {@link ru.asl.api.ejinventory.Element} object
	 */
	public Element get(int locX, int locY) {
		return elements[locX][locY];
	}

	/**
	 * <p>unlockAll.</p>
	 */
	public void unlockAll() {
		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (!isUnlocked(x+y*9)) {
					unlockedSlots[x][y] = true;
					unlocked.add(x+y*9);
				}
	}

	public void unlockEmpty() {
		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if ((get(x,y) == null || get(x,y).getIcon().getType().name().contains("AIR")) && !isUnlocked(x+y*9))
					unlock(x,y);
	}

	/**
	 * <p>lockAll.</p>
	 */
	public void lockAll() {
		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (isUnlocked(x+y*9)) {
					unlockedSlots[x][y] = true;
					unlocked.remove(x+y*9);
				}
	}

	/**
	 * <p>unlock.</p>
	 *
	 * @param x a int
	 * @param y a int
	 */
	public void unlock(int x, int y) {
		if (isUnlocked(x+y*9)) return;

		unlockedSlots[x][y] = true;
		unlocked.add(x+y*9);
	}

	/**
	 * <p>lock.</p>
	 *
	 * @param x a int
	 * @param y a int
	 */
	public void lock(int x, int y) {
		if (!isUnlocked(x+y*9)) return;

		unlockedSlots[x][y] = false;
		unlocked.remove(x+y*9);
	}

	/**
	 * <p>Getter for the field <code>unlocked</code>.</p>
	 *
	 * @return a {@link java.util.List} object
	 */
	public List<Integer> getUnlocked() {
		final List<Integer> copy = new ArrayList<>(unlocked);
		return copy;
	}

	/** {@inheritDoc} */
	@Override
	public Element[] add(Element... elements) {
		final List<Element> elem = new ArrayList<>();

		for (final Element e : elements)
			if (!this.add(e)) elem.add(e);

		return elem.toArray(new Element[] {});
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(Element element) {
		Objects.requireNonNull(element);

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (elements[x][y].equals(LockedPage.emptyElement())) {
					elements[x][y] = element;
					return true;
				}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean add(Element element, int locX, int locY, boolean force) {
		if (isInBounds(locX, locY))
			if (elements[locX][locY].equals(LockedPage.emptyElement()) && !force) {
				elements[locX][locY] = element;
				return true;
			} else {
				elements[locX][locY] = element;
				return true;
			}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean contains(ItemStack icon) {
		if (icon == null) return false;

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (elements[x][y].equals(icon)) return true;

		return false;
	}

	/**
	 * <p>getElement.</p>
	 *
	 * @param icon a {@link org.bukkit.inventory.ItemStack} object
	 * @return a {@link ru.asl.api.ejinventory.Element} object
	 */
	public Element getElement(ItemStack icon) {
		if (icon == null) return null;

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (elements[x][y].equals(icon)) return elements[x][y];

		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void display(Inventory inv) {
		if (inv == null) return;
		for (int x = 0; isInBounds(x, 0); x++)
			for (int y = 0; isInBounds(x, y); y++)
				elements[x][y].placeOn(inv, x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void fill(Element element) {
		Objects.requireNonNull(element);

		for (int y = 0; y < height(); y++)
			for (int x = 0; x < width(); x++)
				elements[x][y] = element;
	}

	/**
	 * <p>fillRow.</p>
	 *
	 * @param row a int
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 */
	public void fillRow(int row, Element element) { fillRow(row, element, false); }

	/**
	 * <p>fillRow.</p>
	 *
	 * @param row a int
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 * @param force a boolean
	 */
	public void fillRow(int row, Element element, boolean force) {
		for (int x = 0 ; isInBounds(x, row) ; x++)
			add(element, x, row, force);
	}

	/**
	 * <p>fillColumn.</p>
	 *
	 * @param col a int
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 */
	public void fillColumn(int col, Element element) { fillColumn(col, element, false); }

	/**
	 * <p>fillColumn.</p>
	 *
	 * @param col a int
	 * @param element a {@link ru.asl.api.ejinventory.Element} object
	 * @param force a boolean
	 */
	public void fillColumn(int col, Element element, boolean force) {
		for (int y = 0 ; isInBounds(col, y) ; y++)
			add(element, col, y, force);
	}

	/** {@inheritDoc} */
	@Override
	public boolean fire(InventoryClickEvent event) {
		Objects.requireNonNull(event);

		final int x = event.getSlot()%9;
		final int y = event.getSlot()/9;

		if (x + y * 9 == event.getSlot()) {
			if (isUnlocked(x+y*9)) return false;
			final Element elem = elements[x][y];

			if (elem.equals(event.getCursor()) || elem.equals(LockedPage.emptyElement())) return false;

			if (elem.equals(event.getCurrentItem())) {
				elem.accept(event);
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>isUnlocked.</p>
	 *
	 * @param slot a int
	 * @return a boolean
	 */
	public boolean isUnlocked(int slot) {
		return unlocked.contains(slot);
	}

	/** {@inheritDoc} */
	@Override
	public int height() { return elements[0].length; }
	/** {@inheritDoc} */
	@Override
	public int width() { return elements.length; }

	private boolean isInBounds(int locX, int locY) {
		return locX < width() && locY < height() && locX >= 0 && locY >= 0;
	}

	/** {@inheritDoc} */
	@Override
	public void remove(int locX, int locY) {
		elements[locX][locY] = LockedPage.emptyElement();
	}

	/** {@inheritDoc} */
	@Override
	public void remove(ItemStack stack) {
		Objects.requireNonNull(stack);

		for (int y = 0; isInBounds(0, y); y++)
			for (int x = 0; isInBounds(x, y); x++)
				if (!elements[x][y].equals(LockedPage.emptyElement()))
					if (elements[x][y].equals(stack)) {
						elements[x][y] = LockedPage.emptyElement();
						return;
					}
	}

}
