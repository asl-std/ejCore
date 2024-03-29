package ru.asl.modules.attributes;

import org.bukkit.entity.LivingEntity;

/**
 * <p>AffectingEntity interface.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public interface AffectingEntity {

	/**
	 * <p>affectEntity.</p>
	 *
	 * @param entity a {@link org.bukkit.entity.LivingEntity} object
	 */
	void affectEntity(LivingEntity entity);

}
