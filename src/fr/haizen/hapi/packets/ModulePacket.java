package fr.haizen.hapi.packets;

import lombok.Getter;
import net.minecraft.server.v1_8_R3.Packet;

@Getter
public class ModulePacket {

	private int id;
	private Packet packet;
	
	public ModulePacket(int id, Packet packet) {
		this.id = id;
		this.packet = packet;
	}
}
