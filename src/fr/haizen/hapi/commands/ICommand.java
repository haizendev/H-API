package fr.haizen.hapi.commands;

import org.bukkit.entity.Player;

import fr.haizen.hapi.cooldowns.CooldownUtils;
import fr.haizen.hapi.cooldowns.DurationFormatter;
import fr.haizen.hapi.utils.Utils;

public abstract class ICommand {

	public abstract void onCommand(CommandArgs args);

	public boolean checkCooldown(Player target, int seconds) {
		String name = getClass().getName().substring(0, 16);
		String cooldownName = String.valueOf(name) + "Cmd";
		if (CooldownUtils.isOnCooldown(cooldownName, target) && !target.hasPermission("core.cooldown")) {
			String cooldown = DurationFormatter.getRemaining(CooldownUtils.getCooldownForPlayerLong(cooldownName, target), true);
			target.sendMessage("§eVeuillez patienter §c" + cooldown + " §eavant de pouvoir utiliser cette commande!");
			return false;
		} 
		CooldownUtils.addCooldown(cooldownName, target, seconds);
		return true;
	}
}

