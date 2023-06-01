package fr.haizen.hapi.menu;

import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

import lombok.Getter;

@Getter
public class MenuManager implements Listener {
	
    private static @Getter MenuManager instance;
    private Plugin plugin;
    private Map<UUID, GUI> guis;

    public MenuManager(Plugin plugin) {
        this.instance = this;
        this.plugin = plugin;
        this.guis = Maps.newHashMap();
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player && event.getInventory().getHolder() instanceof VirtualHolder) {
            ((VirtualHolder)event.getInventory().getHolder()).getGui().onInventoryClick(event);
        }
    }

    @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player && event.getInventory().getHolder() instanceof VirtualHolder) {
            GUI gui = ((VirtualHolder)event.getInventory().getHolder()).getGui();
            gui.onInventoryClose(event);
            if (gui instanceof VirtualGUI) {
                VirtualGUI virtualGUI = (VirtualGUI)gui;
                this.guis.put(event.getPlayer().getUniqueId(), virtualGUI);
            }
        }
    }

    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPluginDisable(PluginDisableEvent event) {
        if (event.getPlugin().equals((Object)this.plugin)) {
            this.closeOpenMenus();
            this.plugin = null;
        }
    }

	public static void closeOpenMenus() {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getOpenInventory() != null) {
				Inventory inventory = player.getOpenInventory().getTopInventory();
				if ((inventory.getHolder() instanceof VirtualHolder)) {
					player.closeInventory();
				}
			}
		}
	}

	 public static void closeOpenMenus(String name) {
   
        for (Player player : Bukkit.getOnlinePlayers()) {
            Inventory inventory = player.getOpenInventory().getTopInventory();
            if (player.getOpenInventory() != null 
            		&& inventory.getHolder() instanceof VirtualHolder 
            		&& inventory.getName() != null 
            		&& inventory.getName().equalsIgnoreCase(name)) {
            	
                player.closeInventory();
            }
        }
    }
}

