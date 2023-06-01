package fr.haizen.hapi.packets;

import java.io.IOException;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class PacketUtils {

	public static void sendPacket(Player player, Packet packet) {
		PlayerConnection packetHandler = ((CraftPlayer) player).getHandle().playerConnection;
		packetHandler.sendPacket(packet);
	}

	public static void writeItem(PacketDataSerializer writer, ItemStack item) {
		writer.a(CraftItemStack.asNMSCopy(item));
	}

	public static String readString(PacketDataSerializer reader) throws IOException {
		return reader.c(999999);
	}
}
