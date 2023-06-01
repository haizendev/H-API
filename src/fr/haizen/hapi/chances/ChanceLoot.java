package fr.haizen.hapi.chances;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;

@Getter
public class ChanceLoot {

	private ItemStack item;
	private double chance;
	
	public ChanceLoot(ItemStack item, double chance) {
		this.item = item;
		this.chance = chance;
	}
}
