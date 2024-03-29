package ru.asl.api.ejcore.blocks;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Block;

import ru.asl.api.bukkit.location.Vector3D;

/**
 * <p>BlockUtil class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class BlockUtil {

	/**
	 * <p>getBlocksCuboid.</p>
	 *
	 * @param target a {@link org.bukkit.block.Block} object
	 * @param radius a int
	 * @return a {@link java.util.List} object
	 */
	public static List<Block> getBlocksCuboid(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		if (radius%2 == 0)
			radius-=1;

		if (radius > 1)
			radius /= 2;

		for (int x = -radius ; x < radius+1 ; x++)
			for (int y = -radius ; y < radius+1 ; y++)
				for (int z = -radius ; z < radius+1 ; z++)
					blocks.add(target.getWorld().getBlockAt(new Vector3D(x,y,z).addTo(target.getLocation())));

		return blocks;
	}

	/**
	 * <p>getBlocksFlat.</p>
	 *
	 * @param target a {@link org.bukkit.block.Block} object
	 * @param radius a int
	 * @return a {@link java.util.List} object
	 */
	public static List<Block> getBlocksFlat(Block target, int radius) {
		final List<Block> blocks = new ArrayList<>();

		if (radius%2 == 0)
			radius-=1;

		if (radius > 1)
			radius /= 2;

		for (int x = -radius ; x < radius+1 ; x++)
			for (int z = -radius ; z < radius+1 ; z++)
				blocks.add(target.getWorld().getBlockAt(new Vector3D(x,0,z).addTo(target.getLocation())));

		return blocks;

	}

}
