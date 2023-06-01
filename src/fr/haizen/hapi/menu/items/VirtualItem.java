package fr.haizen.hapi.menu.items;

import org.bukkit.inventory.ItemStack;

import fr.haizen.hapi.menu.actions.ClickAction;
import fr.haizen.hapi.utils.ItemBuilder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VirtualItem {

	private final ItemStack item;
	private ClickAction action;
	private boolean allowClick;

	public VirtualItem(ItemStack item) {
		this.item = item;
		this.action = null;
	}

	public VirtualItem(ItemBuilder item) {
		this.item = item.build();
		this.action = null;
		this.allowClick = false;
	}

	public VirtualItem onItemClick(ClickAction action) {
		this.action = action;
		return this;
	}
}
