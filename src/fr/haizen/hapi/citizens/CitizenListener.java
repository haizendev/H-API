package fr.haizen.hapi.citizens;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.haizen.hapi.HAPI;
import net.citizensnpcs.api.event.NPCDamageEntityEvent;
import net.citizensnpcs.api.event.NPCLeftClickEvent;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.npc.NPC;

public class CitizenListener implements Listener {
	
	@EventHandler
	public void onCitizenRightClick(NPCRightClickEvent event) {
		if(event.isCancelled()) return;
		
		Player player = event.getClicker();
		NPC npc = event.getNPC();
		
		CitizenManager citizenManager = HAPI.getAPI().getCitizenManager();
		List<Citizen> citizens = citizenManager.getCitizens();
		for (Citizen citizen : citizens) {
			String alias = citizen.getAlias();
			if (npc.data().has(alias)) {
				citizen.onCitizenWasClicked(player, npc, ClickType.RIGHT);
			}
		}
	}
	
	@EventHandler
	public void onCitizenLeftClick(NPCLeftClickEvent event) {
		if(event.isCancelled()) {
			return;
		}
		Player player = event.getClicker();
		NPC npc = event.getNPC();
		CitizenManager citizenManager = HAPI.getAPI().getCitizenManager();
		List<Citizen> citizens = citizenManager.getCitizens();
		for (Citizen citizen : citizens) {
			String alias = citizen.getAlias();
			if (npc.data().has(alias)) {
				citizen.onCitizenWasClicked(player, npc, ClickType.LEFT);
			}
		}
	}
	
	@EventHandler
	public void onDamage(NPCDamageEntityEvent event) {
		event.setCancelled(true);
	}
}
