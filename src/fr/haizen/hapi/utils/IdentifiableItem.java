package fr.haizen.hapi.utils;

import java.util.UUID;
import org.bukkit.inventory.ItemStack;

public class IdentifiableItem {
	
	private String uniqueId;
	private ItemStack item;

	public String getUniqueId() {
		return this.uniqueId;
	}

	public ItemStack getItem() {
		return this.item;
	}

	public IdentifiableItem(ItemStack item) {
		this.uniqueId = UUID.randomUUID().toString().substring(0, 16);
		this.item = item;
	}
}
