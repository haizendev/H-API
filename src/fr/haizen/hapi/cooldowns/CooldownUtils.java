package fr.haizen.hapi.cooldowns;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class CooldownUtils {
	
	private static HashMap<String, HashMap<UUID, Long>> cooldown = new HashMap();

	public static void clearCooldowns() {
		cooldown.clear();
	}

	public static void createCooldown(String k) {
		if (cooldown.containsKey(k)) {
			throw new IllegalArgumentException("This cooldown already exist!");
		}
		cooldown.put(k, new HashMap());
	}

	public static HashMap<UUID, Long> getCooldownMap(String k) {
		if (cooldown.containsKey(k)) {
			return (HashMap)cooldown.get(k);
		}
		return null;
	}

	public static void addCooldown(String k, Player player, int seconds) {
		if (!cooldown.containsKey(k)) {
			createCooldown(k);
		}
		long next = System.currentTimeMillis() + seconds * 1000L;
		((HashMap)cooldown.get(k)).put(player.getUniqueId(), Long.valueOf(next));
	}

	public static boolean isOnCooldown(String k, Player player) {
		return (cooldown.containsKey(k)) && (((HashMap)cooldown.get(k)).containsKey(player.getUniqueId())) && (System.currentTimeMillis() <= ((Long)((HashMap)cooldown.get(k)).get(player.getUniqueId())).longValue());
	}

	public static int getCooldownForPlayerInt(String k, Player player) {
		if(((HashMap)cooldown.get(k)) == null || !cooldown.containsKey(k)) return 0;
		return (int)((((Long)((HashMap)cooldown.get(k)).get(player.getUniqueId())).longValue() - System.currentTimeMillis()) / 1000L);
	}

	public static long getCooldownForPlayerLong(String k, Player player) {
		if(((HashMap)cooldown.get(k)) == null || !cooldown.containsKey(k)) return 0;
		return ((Long)((HashMap)cooldown.get(k)).get(player.getUniqueId())).longValue() - System.currentTimeMillis();
	}

	public static void removeCooldown(String k, Player player) {
		if (!cooldown.containsKey(k)) {
			throw new IllegalArgumentException(String.valueOf(k) + " doesn't exist!");
		}
		((HashMap)cooldown.get(k)).remove(player.getUniqueId());
	}
}
