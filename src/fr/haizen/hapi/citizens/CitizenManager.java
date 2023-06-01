package fr.haizen.hapi.citizens;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.Packet;

@Getter
public class CitizenManager {
	
	@Getter private static CitizenManager instance;
	private List<Citizen> citizens;
	private Packet packetToSend;

	public CitizenManager() {
		this.instance = this;
		this.citizens = Lists.newArrayList();
	}

	public void registerCitizen(Citizen citizen) {
		this.citizens.add(citizen);
	}

	public Citizen getCitizen(String alias) {
		if(this.citizens.isEmpty()) return null;
		
		for (Citizen citizen : this.citizens) {
			String citizenAlias = citizen.getAlias();
			if (citizenAlias.equalsIgnoreCase(alias)) {
				return citizen;
			}
		}
		return null;
	}
}
