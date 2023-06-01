package fr.haizen.hapi.menu.items;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.haizen.hapi.menu.actions.ClickAction;
import fr.haizen.hapi.utils.ItemBuilder;

public class CloseItem extends VirtualItem {
    public CloseItem() {
        super(new ItemBuilder(Material.WOOD_DOOR).displayname("&6&lFermer le menu").build());
        this.onItemClick(new ClickAction(){
            @Override
            public void onClick(InventoryClickEvent event) {
                event.getWhoClicked().closeInventory();
            }
        });
    }

}

