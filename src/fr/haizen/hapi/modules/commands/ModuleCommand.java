package fr.haizen.hapi.modules.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.commands.CommandArgs;
import fr.haizen.hapi.commands.ICommand;
import fr.haizen.hapi.commands.annotations.Command;
import fr.haizen.hapi.modules.Module;
import fr.haizen.hapi.modules.ModuleManager;
import fr.haizen.hapi.utils.Utils;

public class ModuleCommand extends ICommand {
	
	@Command(name = {"module", "modules", "system"}, isConsole = true)
	public void onCommand(CommandArgs args) {
		CommandSender sender = args.getSender();
		this.openModules(sender);
	}
	
	@Command(name = {"module.toggle", "modules.toggle", "system.toggle"}, isConsole = true)
	public void onCommandToggle(CommandArgs args) {
		CommandSender player = args.getSender();
		
		if (!player.isOp()) {
			player.sendMessage("§cSeul un Administrateur peu excécuter cette commande.");
			return;
		}
		
		if (args.length() < 1) {
			player.sendMessage("§e/module toggle <type>");
			return;
		}

		Module module = HAPI.getAPI().getModuleManager().getModuleByName(args.getArgs(0));
		
		if(module == null) {
			player.sendMessage("§7(§d§lModule§7) §cCe module est introuvable !");
			return;
		}
		
		if(!module.isDesactivable()) {
			player.sendMessage("§7(§d§lModule§7) §cCe module n'est pas désactivable !");
			return;
		}
		
		module.setActive(!module.isActive());
		
		String isActive = module.isActive() ? "§ad'activer" : "§ede §cdésactiver";
		player.sendMessage("§7(§d§lModule§7) §eVous venez " + isActive + " §ele module §6" + module.getModuleName() + " §e!");
	}

	public void openModules(CommandSender player) {
		ModuleManager moduleManager = HAPI.getAPI().getModuleManager();
		List<Module> modules = moduleManager.getModules(); 
		
		System.out.println(modules.size());
		
		player.sendMessage("§7(§d§lModule§7) §eVoici la liste des modules §7(/module toggle pour changer l'état):");
		for(Module module : modules) {
			if(!module.isDesactivable()) continue;
			String color = module.isActive() ? "§a " : "§c ";
			String name = !module.isDesactivable() ? color + module.getModuleName() + " §4(NON DESACTIVABLE)" : color + module.getModuleName();
			player.sendMessage("§7- " + name);
		}	
	}
}
