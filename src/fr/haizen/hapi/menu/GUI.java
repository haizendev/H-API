package fr.haizen.hapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import fr.haizen.hapi.menu.items.VirtualItem;

public interface GUI {
    public GUI setItem(int slot, VirtualItem item);

    public GUI addItem(VirtualItem item);

    public void open(Player player);

    public void apply(Inventory inventory, Player player);

    public void onInventoryClick(InventoryClickEvent event);

    public void onInventoryClose(InventoryCloseEvent event);
}

