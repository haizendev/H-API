package fr.haizen.hapi.modules;

import java.util.List;

import org.bukkit.command.CommandSender;

import com.google.common.collect.Lists;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.HPlugin;
import lombok.Getter;

@Getter
public class ModuleManager {
	
	@Getter private static ModuleManager instance;
	public List<Module> modules;

	public ModuleManager() {
		this.instance = this;
		this.modules = Lists.newArrayList();
	}

	public void registerManager(Module module) {
		this.modules.add(module);
	}
	
	public void registerPersist(Saveable module) {
		this.registerManager(module);
		HPlugin plugin = module.getPlugin();
		plugin.registerPersists(module);
	}
	
	public static String moduleDesactivate(CommandSender sender, Module module) {
		String errorMessage = "§7(§d§lModule§7) §eLe module §6§l" + module.getModuleName() + " §erencontre un §6problème §eet a été §6§ldésactivé§e temporairement.";
		sender.sendMessage(errorMessage);
		return errorMessage;
	}
	
	public Module getModuleByName(String name) {
		for (Module modules : this.getModules()) {
			if(modules.getModuleName().equalsIgnoreCase(name)) {
				return modules;
			}
		}
		return null;
	}
}
