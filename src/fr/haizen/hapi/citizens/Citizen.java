package fr.haizen.hapi.citizens;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;
import net.citizensnpcs.api.npc.NPC;

@Getter
public abstract class Citizen {
	
	private @Setter String alias, name;
	private EntityType type;
	private boolean onlyOne;
	private List<String> lines;
	
	public Citizen(String alias, String name, EntityType type) {
		this(alias, name, type, false);
	}

	public Citizen(String alias, String name, EntityType type, boolean onlyOne) {
		this.alias = alias;
		this.name = name;
		this.type = type;
		this.onlyOne = onlyOne;
		this.lines = Lists.newArrayList();
		
	}

	public void addLine(String line) {
		this.lines.add(line);
	}
	
	public abstract void onCitizenWasClicked(Player player, NPC npc, ClickType type);

	public void setupBeforeLoading() {}

	public NPC spawnCitizen(Location location) {
		this.setupBeforeLoading();
		NPC currentNpc = CitizenUtils.createNpc(this.name, this.type, location);
		currentNpc.data().setPersistent(this.alias, true);
		return currentNpc;
	}
	
	public List<NPC> getNpc() {
		return CitizenUtils.getNpcsByAlias(this.alias);
	}
}
