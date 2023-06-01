package fr.haizen.hapi.citizens.commands;

import java.util.List;

import org.bukkit.entity.Player;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.citizens.Citizen;
import fr.haizen.hapi.citizens.CitizenManager;
import fr.haizen.hapi.commands.CommandArgs;
import fr.haizen.hapi.commands.ICommand;
import fr.haizen.hapi.commands.annotations.Command;
import fr.haizen.hapi.utils.Utils;

public class CitizenListCommand extends ICommand {
	
	@Command(name = {"citizen.list"}, permissionNode = "core.citizens")
	public void onCommand(CommandArgs args) {
		Player player = args.getPlayer();
		
		CitizenManager citizenManager = HAPI.getAPI().getCitizenManager();
		List<Citizen> citizens = citizenManager.getCitizens();
		if(citizens.isEmpty()) {
			player.sendMessage("§eDésolé, il n'y a aucun Citizen enregisté !");
			return;
		}
		
		for(Citizen citizen : citizens) player.sendMessage("§7- " + citizen.getAlias());
	}
}
