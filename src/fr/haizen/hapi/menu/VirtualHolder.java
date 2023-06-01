package fr.haizen.hapi.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import lombok.Getter;

@Getter
public class VirtualHolder implements InventoryHolder {
	
	private GUI gui;
	private Inventory inventory;
	private int nextPage;

	public VirtualHolder(GUI gui, Inventory inventory) {
		this.gui = gui;
		this.inventory = inventory;
		this.nextPage = -1;
	}

	@Override
	public Inventory getInventory() {
		return this.inventory;
	}
}
