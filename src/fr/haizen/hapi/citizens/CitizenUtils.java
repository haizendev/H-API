package fr.haizen.hapi.citizens;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.metadata.FixedMetadataValue;

import com.google.common.collect.Lists;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.utils.Utils;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;

public class CitizenUtils {
	
	private static NPCRegistry citizensRegistry = CitizensAPI.getNPCRegistry();

	public static NPC createNpc(String name, EntityType type, Location location) {
		NPC npc = citizensRegistry.createNPC(type, "-");
		npc.spawn(location);
		npc.setName(Utils.color(name));
		if(npc.getBukkitEntity() != null && npc.getBukkitEntity().getCustomName() != null) npc.getBukkitEntity().setCustomNameVisible(true);
		npc.getBukkitEntity().setMetadata("npc", new FixedMetadataValue(HAPI.getAPI(), npc));
		return npc;
	}

	public static void removeNPC(int id) {
		NPC npc = CitizenUtils.getNPC(id);
		if (npc == null) {
			return;
		}
		citizensRegistry.deregister(npc);
	}

	public static NPC getNPC(int id) {
		return citizensRegistry.getById(id);
	}
	
	public static List<NPC> getAllNpcs() {
		return Lists.newArrayList(citizensRegistry.iterator());
	}
	
	public static List<NPC> getNpcsByAlias(String alias) {
		List<NPC> npcs = Lists.newArrayList();
		List<NPC> allNpcs = getAllNpcs();
		for(NPC npc : allNpcs) {
			if(npc.data().has(alias)) {
				npcs.add(npc);
			}
		}
		return npcs;
	}
}
