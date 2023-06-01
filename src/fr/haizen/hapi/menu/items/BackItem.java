package fr.haizen.hapi.menu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.haizen.hapi.menu.MenuManager;
import fr.haizen.hapi.menu.VirtualGUI;
import fr.haizen.hapi.menu.actions.ClickAction;
import fr.haizen.hapi.utils.ItemBuilder;

public class BackItem extends VirtualItem {
    public BackItem() {
        super(new ItemBuilder(Material.ARROW).displayname("&c&lRetour").build());
        this.onItemClick(new ClickAction(){
            @Override
            public void onClick(InventoryClickEvent event) {
                Player player = (Player)event.getWhoClicked();
                VirtualGUI gui = (VirtualGUI) MenuManager.getInstance().getGuis().get(player.getUniqueId());
                if (gui != null) {
                    gui.open(player);
                }
            }
        });
    }

}

