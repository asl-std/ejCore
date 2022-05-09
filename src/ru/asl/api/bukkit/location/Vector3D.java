package ru.asl.api.bukkit.location;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.math.BlockVector3;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import ru.asl.api.ejcore.value.util.ValueUtil;

@EqualsAndHashCode
@AllArgsConstructor
public class Vector3D implements Cloneable {

	@Getter private double x = 0, y = 0, z = 0;

	@Getter private World world;

	public Vector3D(double x, double y, double z) {
		this.x = x; this.y = y; this.z = z;
	}

	public static Vector3D fromLocation(Location loc) {
		return new Vector3D(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), loc.getWorld());
	}

	public static @Nullable Vector3D fromString(String loc) {
		if (loc == null) return null;
		final String[] prep = loc.split(";");
		if (prep == null || prep.length < 3) return null;

		final Vector3D vec = new Vector3D(0,0,0);
		if (prep.length >= 4)
			vec.world = Bukkit.getWorld(prep[3]);

		try {
			return vec.add(new Vector3D(ValueUtil.parseInteger(prep[0]),ValueUtil.parseInteger(prep[1]),ValueUtil.parseInteger(prep[2]))).clone();
		} catch (final NumberFormatException e) {
			return null;
		}
	}

	public static Vector3D getMaximum(Vector3D fst, Vector3D scd) {
		return new Vector3D(
				Math.max(fst.getX(), scd.getX()),
				Math.max(fst.getY(), scd.getY()),
				Math.max(fst.getZ(), scd.getZ())
				);
	}

	public static Vector3D getMinimum(Vector3D fst, Vector3D scd) {
		return new Vector3D(
				Math.min(fst.getX(), scd.getX()),
				Math.min(fst.getY(), scd.getY()),
				Math.min(fst.getZ(), scd.getZ())
				);
	}

	public static boolean blockEquals(Location from, Location to) {
		return from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ();
	}

	public double getDistance(Vector3D vec) {
		final double x = this.x-vec.x;
		final double y = this.y-vec.y;
		final double z = this.z-vec.z;

		return Math.sqrt(x * x + y * y + z * z);
	}

	public Vector3D setX(int x) { this.x = x; return clone(); }
	public Vector3D setY(int y) { this.y = y; return clone(); }
	public Vector3D setZ(int z) { this.z = z; return clone(); }

	public Vector3D multiply() { return multiply(1); }
	public Vector3D multiply(int mod) { x *= mod; y *= mod; z *= mod; return clone(); }

	public Vector3D incrementX() { return incrementX(1); }
	public Vector3D incrementX(int x) { this.x += x; return clone(); }

	public Vector3D incrementY() { return incrementY(1); }
	public Vector3D incrementY(int y) { this.y += y; return clone(); }

	public Vector3D incrementZ() { return incrementZ(1); }
	public Vector3D incrementZ(int z) { this.z += z; return clone(); }

	public Vector3D decrementX() { return decrementX(1); }
	public Vector3D decrementX(int x) { this.x -= x; return clone(); }

	public Vector3D decrementY() { return decrementY(1); }
	public Vector3D decrementY(int y) { this.y -= y; return clone(); }

	public Vector3D decrementZ() { return decrementZ(1); }
	public Vector3D decrementZ(int z) { this.z -= z; return clone(); }

	public Vector3D add(Vector3D vec) { x += vec.x; y += vec.y; z += vec.z; return clone(); }
	public Vector3D substract(Vector3D vec) { x -= vec.x; y -= vec.y; z -= vec.z; return clone(); }

	public Location addTo(Location loc) {
		loc.setX(loc.getX()+x);
		loc.setY(loc.getY()+y);
		loc.setZ(loc.getZ()+z);

		return loc;
	}

	public Vector3D reverse() {x *= -1; y *= -1; z *= -1; return this; }

	/*@Override
	public boolean equals(Object obj) {

		if (obj instanceof Location) {
			final Location loc = (Location) obj;
			return loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z;
		}

		if (obj instanceof Vector3D) return toString().equalsIgnoreCase(obj.toString());

		return super.equals(obj);
	} */

	public Location toLocation(Location playerLocation) {
		final Location copy = playerLocation;
		copy.setX(x+0.5);
		copy.setY(y);
		copy.setZ(z+0.5);

		return copy;
	}

	public Location toLocation() {
		return new Location(world, x, y, z);
	}

	public BlockVector3 toBlockVector3() {
		return BlockVector3.at(x,y,z);
	}

	@Override
	public Vector3D clone() {
		return new Vector3D(x,y,z);
	}

	@Override
	public String toString() {
		return x + ";" + y + ";" + z + (world == null ? "" : ";" + world.getName());
	}

	public String toIntString() {
		return ((int) x) + ";" + ((int) y) + ";" + ((int) z) + (world == null ? "" : ";" + world.getName());
	}
}
