package ru.asl.modules.playerattr;

import ru.asl.api.bukkit.events.CombatEvent;
import ru.asl.modules.playerattr.basic.BasicAttr;
import ru.asl.modules.playerattr.basic.interfaze.ListeningCombat;

public final class Root extends BasicAttr implements ListeningCombat {

	public Root(String keyName, String path, double defBase, double defPerLevel) {
		super(keyName, path, defBase, defPerLevel);
	}

	@Override
	public void listen(CombatEvent e) {
	}

}