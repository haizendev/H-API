package fr.haizen.hapi.packets;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import com.google.common.collect.BiMap;
import com.google.common.collect.Lists;

import lombok.Getter;
import net.minecraft.server.v1_7_R4.EnumProtocol;
import net.minecraft.server.v1_7_R4.Packet;

@Getter
public class PacketManager {

	private static @Getter PacketManager instance;
	private List<ModulePacket> packets;
	
	public PacketManager() {
		this.instance = this;
		this.packets = Lists.newArrayList();
	}
	
	public void registerPacket(int id, Packet packet) {
		ModulePacket modulePacket = new ModulePacket(id, packet);
		this.packets.add(modulePacket);
	}
	
	public void registerPackets() {
		try {
			for(ModulePacket modulePacket : this.packets) {
				Packet packet = modulePacket.getPacket();
				int id = modulePacket.getId();
				Class<EnumProtocol> clazz = EnumProtocol.class;
				Field f = null;
				BiMap<Integer, Class<?>> packetsMap = null;
				f = clazz.getDeclaredField("h");
				f.setAccessible(true);
				packetsMap = (BiMap) f.get(EnumProtocol.PLAY);
				packetsMap.put(id, packet.getClass());
				f = clazz.getDeclaredField("i");
				f.setAccessible(true);
				packetsMap = (BiMap) f.get(EnumProtocol.PLAY);
				packetsMap.put(id, packet.getClass());
				setAsPlayPacket(packet.getClass());
			}
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException exception) {
			exception.printStackTrace();
		}
	}

	public void setAsPlayPacket(Class<?> clazz) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = EnumProtocol.class.getDeclaredField("f");
		field.setAccessible(true);
		Map<Class<?>, EnumProtocol> map = (Map) field.get(EnumProtocol.PLAY);
		map.put(clazz, EnumProtocol.PLAY);
	}
}
