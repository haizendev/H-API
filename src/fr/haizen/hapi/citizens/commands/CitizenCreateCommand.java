package fr.haizen.hapi.citizens.commands;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.citizens.Citizen;
import fr.haizen.hapi.citizens.CitizenManager;
import fr.haizen.hapi.commands.CommandArgs;
import fr.haizen.hapi.commands.ICommand;
import fr.haizen.hapi.commands.annotations.Command;
import fr.haizen.hapi.utils.Utils;

public class CitizenCreateCommand extends ICommand {

	@Command(name = {"citizen.create"}, permissionNode = "core.citizens")
	public void onCommand(CommandArgs args) {
		Player player = args.getPlayer();
		
		if (args.length() < 1) {
			player.sendMessage("§e/citizen create <alias>");
			return;
		}
		
		CitizenManager citizenManager = HAPI.getAPI().getCitizenManager();
		String alias = args.getArgs(0);
		Citizen citizen = citizenManager.getCitizen(alias);
		
		if (citizen == null) {
			player.sendMessage("§eDésolé, le type de §9§lCitizen §espécifié n'existe pas!");
			return;
		}
		
		if (citizen.isOnlyOne()) {
			player.sendMessage("§eDésolé, vous ne pouvez pas faire apparaitre ce §9§lCitizen §f§ede cette manière!");
			return;
		}
		
		Location location = player.getLocation();
		citizen.spawnCitizen(location);
		player.sendMessage("§eVous venez de faire apparaitre un §9§lCitizen §eà votre emplacement!");
	}
}
