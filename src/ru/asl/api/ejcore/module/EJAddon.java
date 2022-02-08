package ru.asl.api.ejcore.module;

import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

import ru.asl.api.ejcore.yaml.YAML;
import ru.asl.core.Core;

public abstract class EJAddon implements EJModule {

	protected YAML moduleConfiguration;

	public boolean isModuleRegistered() {
		return Core.isRegistered(getModuleName());
	}

	public EJAddon() { }

	@Override
	public YAML getModuleConfig() {
		if (moduleConfiguration == null) {
			moduleConfiguration = new YAML(getModuleName().toLowerCase() + ".yml", Core.instance());
			if (!moduleConfiguration.getFile().exists()) try {
				moduleConfiguration.getFile().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return moduleConfiguration;
	}

	@Override
	public void reloadModule() {
		try {
			moduleConfiguration.load();
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

}