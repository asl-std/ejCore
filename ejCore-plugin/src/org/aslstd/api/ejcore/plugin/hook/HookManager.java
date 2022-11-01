package org.aslstd.api.ejcore.plugin.hook;

import org.aslstd.core.Core;

import lombok.Getter;

/**
 * <p>HookManager class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class HookManager {

	@Getter private static boolean papiEnabled = false;


	/**
	 * <p>tryHookPAPI.</p>
	 */
	public static boolean tryHookPAPI() {
		if (!isPluginEnabled("PlaceholderAPI")) {
			return (papiEnabled = false);
		} else {
			return (papiEnabled = true);
		}
	}

	/**
	 * <p>isPluginEnabled.</p>
	 *
	 * @param pluginName a {@link java.lang.String} object
	 * @return a boolean
	 */
	public static boolean isPluginEnabled(String pluginName) {
		return Core.instance().getServer().getPluginManager().isPluginEnabled(pluginName);
	}

}