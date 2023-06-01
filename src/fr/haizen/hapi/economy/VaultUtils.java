package fr.haizen.hapi.economy;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import net.milkbowl.vault.economy.Economy;

@SuppressWarnings("deprecation")

public class VaultUtils {

	private static Economy economy = (Economy) Bukkit.getServer().getServicesManager().getRegistration(Economy.class).getProvider();

	public static double getBalance(String user) {
		return economy.getBalance(user);
	}

	public static double getBalance(Player player) {
		return economy.getBalance((OfflinePlayer) player);
	}

	public static void setBalance(Player player, double value) {
		economy.withdrawPlayer((OfflinePlayer) player, value);
		economy.depositPlayer((OfflinePlayer) player, value);
	}

	public static boolean has(Player player, double value) {
		return economy.has((OfflinePlayer) player, value);
	}

	public static void depositMoney(Player player, double value) {
		economy.depositPlayer((OfflinePlayer) player, value);
	}

	public static void depositMoney(String player, double value) {
		economy.depositPlayer(player, value);
	}

	public static void withdrawMoney(Player player, double value) {
		economy.withdrawPlayer((OfflinePlayer) player, value);
	}

	public static Economy getEconomy() {
		return economy;
	}
}
