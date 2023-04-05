package org.aslstd.modules.attribute.weapon;

import org.aslstd.api.bukkit.entity.EPlayer;
import org.aslstd.api.bukkit.events.combat.CombatEvent;
import org.aslstd.api.bukkit.events.combat.CombatEvent.CombatType;
import org.aslstd.api.bukkit.utils.ServerVersion;
import org.aslstd.core.Core;
import org.aslstd.modules.MAttributes;
import org.aslstd.modules.attribute.AttrType;
import org.aslstd.modules.attribute.BasicAttr;
import org.aslstd.modules.attribute.ListeningCombat;
import org.aslstd.modules.player.PlayerUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 * <p>PhysicalDamage class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public final class PhysicalDamage extends BasicAttr implements ListeningCombat {

	/**
	 * <p>Constructor for PhysicalDamage.</p>
	 *
	 * @param keyName a {@link java.lang.String} object
	 * @param path a {@link java.lang.String} object
	 * @param defBase a double
	 * @param defPerLevel a double
	 */
	public PhysicalDamage(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel, AttrType.RANGE);
	}

	/** {@inheritDoc} */
	@SuppressWarnings({ "null" })
	@Override
	public void listen(CombatEvent e) {
		if (e.isRanged()) return;

		double fistDmg = 0;
		double vanillaDmg = e.getDamage();
		double statDmg = 0;
		double statDef = 0;

		final EPlayer receiver = e.getReceiver().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getReceiver() ) : null;
		final EPlayer attacker = e.getAttacker().getType() == EntityType.PLAYER ? EPlayer.getEPlayer( (Player) e.getAttacker() ) : null;

		if (e.getReceiver().getType() == EntityType.PLAYER)
			statDef = PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PHYSICAL_DEFENCE"))[0];

		if (e.getAttacker().getType() == EntityType.PLAYER) {
			statDmg = PlayerUtils.getDamage(attacker, this);

			if (!Core.getCfg().getBoolean("use-vanilla-damage", false, true))
				vanillaDmg = 0;

			if (MAttributes.getWeaponAttributes().getByKey("FIST_DAMAGE").isEnabled())
				fistDmg = PlayerUtils.getDamage(attacker, MAttributes.getWeaponAttributes().getByKey("FIST_DAMAGE"));
		}

		if (e.getType() == CombatType.PLAYER_TO_PLAYER) {
			statDmg = statDmg*(PlayerUtils.getStatValue(attacker, MAttributes.getWeaponAttributes().getByKey("PVP_DAMAGE_MODIFIER"))[0]/100);
			statDef = statDef*(PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PVP_DEFENCE_MODIFIER"))[0]/100);
		}

		if (e.getType() == CombatType.PLAYER_TO_ENTITY)
			statDmg = statDmg*(PlayerUtils.getStatValue(attacker, MAttributes.getWeaponAttributes().getByKey("PVE_DAMAGE_MODIFIER"))[0]/100);

		if (e.getType() == CombatType.ENTITY_TO_PLAYER)
			statDef = statDef*(PlayerUtils.getStatValue(receiver, MAttributes.getWeaponAttributes().getByKey("PVE_DEFENCE_MODIFIER"))[0]/100);

		double summdmg = (fistDmg+statDmg+vanillaDmg);

		if (ServerVersion.isVersionAtMost(ServerVersion.VER_1_15_2))
			if (e.getAttacker().getType() == EntityType.PLAYER)
				summdmg *= attacker.getPlayer().getAttackCooldown();

		e.setDamage(summdmg*(1-0.00675*statDef/(1+0.00675*statDef)));
	}

}