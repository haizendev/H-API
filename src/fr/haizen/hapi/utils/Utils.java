package fr.haizen.hapi.utils;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import fr.haizen.hapi.HPlugin;
import fr.haizen.hapi.chances.ChanceLoot;
import fr.haizen.hapi.cooldowns.CooldownUtils;

public class Utils {

	public static String LINE = "§7§m" + StringUtils.repeat("-", 53);
	public static String GOLD_LINE = "§6§m" + StringUtils.repeat("-", 53);
	public static String SCOREBOARD_LINE = "§7§m" + StringUtils.repeat("-", 20);
	
	public static String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public static String toStringOnOff(boolean value) {
		return value ? ("§aON") : ("§cOFF");
	}

	@SuppressWarnings("deprecation")
	public static Collection<? extends Player> getOnlinePlayers() {
		return Bukkit.getOnlinePlayers();
	}
	
    public static ItemStack[] collectItems(ItemStack[] items) {
        ItemStack[] result = new ItemStack[items.length];

        for(int i = 0; i < items.length; ++i) {
            ItemStack item = items[i];
            result[i] = item == null ? null : item.clone();
        }

        return result;
    }
    
    public static UUID playerToKey(Player player) {
        return player.getUniqueId();
    }
    
    public static String formatTime(long duration) {
        int secondsLeft = (int)(duration / 1000L);
        return secondsLeft <= 60 ? DurationFormatUtils.formatDuration(duration, secondsLeft > 10 ? "ss" : "s") + "s" : DurationFormatUtils.formatDuration(duration, "mm:ss");
    }
    
    public static String format(String message, Object... args) {
        return color(String.format(message, args));
    }
    
    public static String getItemStackName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) return item.getItemMeta().getDisplayName();
        
        String itemName = "";
        String separator = " ";
        String[] materialName = item.getType().name().replace("_", separator).split(separator);
        for (int length = materialName.length, i = 0; i < length; ++i) {
            itemName += Character.toUpperCase(materialName[i].charAt(0)) + materialName[i].substring(1).toLowerCase();
            if(i < (length - 1)) itemName += separator;
        }
        return String.valueOf(itemName);
    }
    
    public static boolean hasAnyInventoryItem(Player player) {
        ItemStack[] items = player.getInventory().getContents();

        int i;
        for(i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].getType() != Material.AIR) {
                return true;
            }
        }

        items = player.getInventory().getArmorContents();

        for(i = 0; i < items.length; ++i) {
            if (items[i] != null && items[i].getType() != Material.AIR) {
                return true;
            }
        }
        return false;
    }

	public static boolean hasPermission(Player player, List<String> permissions) {
		boolean hasPerm = false;
		for(String permission : permissions) {
			if(player.hasPermission(permission)) {
				hasPerm = true;
			}
		}
		return hasPerm;
	}
	
	public static boolean isSameWorld(World playerWorld, World targetWorld) {
		if(playerWorld.getName().equals(targetWorld.getName())) return true;
		return false;
	}

	// FILES
	public static File getFormatedFile(String fileName, HPlugin plugin) {
		return new File(plugin.getDataFolder(), fileName);
	}

	public static void deleteFile(File file) {
		if(file.exists()) {
			file.delete();
		}
	}

	public void createDirectory(String directory, HPlugin plugin) {
		File file = this.getFormatedFile(directory, plugin);
		if (!file.exists()) {
			try {
				file.mkdir();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}

	// INVENTORY
	public static ItemStack[] inventoryAsArray(List<ItemStack> inventory) {
		return inventory.<ItemStack>toArray(new ItemStack[inventory.size()]);
	}
	
	public static void addItemOrDrop(Player player, List<ItemStack> items) {
		for (int i = 0; i < items.size(); i++) {
			ItemStack item = items.get(i);
			if (item != null && item.getType() != Material.AIR) {
				ItemStack itemStack = items.get(i).clone();
				HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(new ItemStack[] { itemStack });
				if (!leftOver.isEmpty()) {
					player.getWorld().dropItem(player.getLocation(), itemStack); 
				}
			}
		} 
	}

	public static void addItemOrDrop(Player player, ItemStack itemStack) {
		if (itemStack != null && itemStack.getType() != Material.AIR) {
			HashMap<Integer, ItemStack> leftOver = player.getInventory().addItem(new ItemStack[] { itemStack });
			if (!leftOver.isEmpty()) {
				player.getWorld().dropItem(player.getLocation(), itemStack); 
			}
		}
	}


	public static void dropItems(Location location, List<ItemStack> items) {
		for (int i = 0; i < items.size(); i++) {
			ItemStack item = items.get(i);
			if (item != null && item.getType() != Material.AIR) {
				ItemStack itemStack = items.get(i).clone();
				location.getWorld().dropItem(location, itemStack); 
			} 
		} 
	}

	public static void dropItem(Location location, ItemStack itemStack) {
		if (itemStack != null && itemStack.getType() != Material.AIR) {
			location.getWorld().dropItem(location, itemStack); 
		}
	}
	
	public static boolean isInventoryEmpty(Inventory inventory) {
		for(ItemStack item : inventory.getContents()) {
			if(item != null && item.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}

	public static boolean isInventoryEmpty(ItemStack[] contents) {
		for(ItemStack item : contents) {
			if(item != null && item.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isInventoryEmpty(Player player) {
		for(ItemStack item : player.getInventory().getContents()) {
			if(item != null && item.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}

	public static boolean isArmorEmpty(Player player) {
		for(ItemStack item : player.getInventory().getArmorContents()) {
			if(item != null && item.getType() != Material.AIR) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNullItem(ItemStack item) {
		if (item != null && item.getType() != Material.AIR) return false; 
		return true;
	}

	public static void addItem(Player player, ItemStack item) {
		addItem(player, item, 1);
	}

	public static void addItem(Player player, ItemStack item, int quantity) {
		PlayerInventory playerInventory = player.getInventory();
		ItemStack current = new ItemStack(item);
		int max = current.getMaxStackSize();
		if (quantity > max) {
			int leftOver = quantity;
			while (leftOver > 0) {
				int add = 0;
				if (leftOver <= max) {
					add += leftOver;
				} else {
					add += max;
				} 
				current = current.clone();
				current.setAmount(add);
				playerInventory.addItem(new ItemStack[] { current });
				leftOver -= add;
			} 
		} else {
			current = current.clone();
			current.setAmount(quantity);
			playerInventory.addItem(new ItemStack[] { current });
		} 
	}
	
	public static void strikeLightningWithoutFire(Location locaction){
		locaction.getWorld().strikeLightningEffect(locaction);
	    for(LivingEntity e : locaction.getWorld().getLivingEntities()){
	           if(e.getLocation().distance(locaction) < 3D){
	                e.damage(2); //one heart
	           }
	     }
	}
	
	public static void knockbackPlayer(Player player) {
		player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(-1));
		CooldownUtils.addCooldown("knockbackFall", player, 5);
	}
	
	public static void knockbackPlayer(Player player, int knockback) {
		player.setVelocity(player.getEyeLocation().getDirection().normalize().multiply(-knockback));
		CooldownUtils.addCooldown("knockbackFall", player, 5);
	}
	
	public static boolean isRepairableItem(ItemStack item) {
		return item.getMaxStackSize() == 1 && item.getDurability() >= 1;
	}

	public static boolean haveRequiredItem(Player player, ItemStack item) {
		return haveRequiredItem(player, item, 1);
	}

	public static boolean haveRequiredItem(Player player, ItemStack item, int quantity) {
		int quantityInInventory = getItemCount(player, item);
		if (quantityInInventory >= quantity)
			return true; 
		return false;
	}

	public static boolean isFullInventory(Player player) {
		PlayerInventory playerInventory = player.getInventory();
		for (ItemStack current : playerInventory.getContents()) {
			if (isNullItem(current)) return false; 
		} 
		return true;
	}
	
	public static boolean isFullInventory(Inventory inventory) {
		for (ItemStack current : inventory.getContents()) {
			if (isNullItem(current)) return false; 
		} 
		return true;
	}

	public static boolean hasSpaceInventory(Player player, ItemStack item, int count) {
		int left = count;
		ItemStack[] items = player.getInventory().getContents();
		for (int i = 0; i < items.length; i++) {
			ItemStack stack = items[i];
			if (stack == null || stack.getType() == Material.AIR) {
				left -= item.getMaxStackSize();
			} else if (stack.getType() == item.getType() && stack.getData().getData() == item.getData().getData() && 
					item.getMaxStackSize() > 1 && stack.getAmount() < item.getMaxStackSize()) {
				left -= stack.getMaxStackSize() - stack.getAmount();
			} 
			if (left <= 0)
				break; 
		} 
		return (left <= 0);
	}

	public static int getItemCount(Player player, ItemStack item) {
		int quantityInInventory = 0;
		PlayerInventory playerInventory = player.getInventory();
		for (ItemStack current : playerInventory.getContents()) {
			if (!isNullItem(current) && 
					current.getType() == item.getType() && current.getData().getData() == item.getData().getData())
				quantityInInventory += current.getAmount(); 
		} 
		return quantityInInventory;
	}

	public static void decrementCurrentItem(Player player, ItemStack item, int quantity) {
		int currentAmount = item.getAmount();
		if (currentAmount <= 1) {
			player.setItemInHand(null);
		} else {
			int amount = currentAmount - quantity;
			item.setAmount(amount);
		} 
	}

	public static void decrementItem(Player player, ItemStack item, int quantity) {
		int toRemove = quantity;
		PlayerInventory playerInventory = player.getInventory();
		for (ItemStack is : playerInventory.getContents()) {
			if (toRemove <= 0)
				break; 
			if (is != null && is.getType() == item.getType() && is.getData().getData() == item.getData().getData()) {
				int amount = is.getAmount() - toRemove;
				toRemove -= is.getAmount();
				if (amount <= 0) {
					player.getInventory().removeItem(new ItemStack[] { is });
				} else {
					is.setAmount(amount);
				} 
			} 
		}
	}
	
	public static void damageItem(Player player, ItemStack item, int max) {
		player.setItemInHand(changeDurability(player, item, max));
	}
	
	public static ItemStack changeDurability(Player player, ItemStack item, int max) {
		item.setDurability((short)(item.getDurability() + 1));
		if(item.getDurability() >= max) {
			player.playSound(player.getLocation(), Sound.ITEM_BREAK, 1F, 1F);
			return new ItemStack(Material.AIR);
		}
		return item;
	}

	public static void addItems(Player player, List<ItemStack> items) {
		for (ItemStack item : items) addItem(player, item, item.getAmount()); 
	}

	public static boolean haveSpaceInInventory(Player player, List<ItemStack> items) {
		int itemsCount = 0;
		for (ItemStack item : items) {
			if (hasSpaceInventory(player, item, item.getAmount()))
				itemsCount++; 
		} 
		return (itemsCount >= items.size());
	}
	
	// 
	public static String getTitle(String title) {
		int titleLenght = title.length();
		int remeaning = 53 - titleLenght;
		String line = "§6§m" + StringUtils.repeat("-", remeaning);
		return "§6§m-§6" + title + line;
	}
	
	public static void broadcastMessage(String... messages) {
		int count = 0;
		while (count < messages.length) {
			String message = messages[count];
			Bukkit.broadcastMessage(Utils.color(message));
			++count;
		}
	}
	
	public static void broadcastMessage(List<Player> players, String... messages) {
		int count = 0;
		while (count < messages.length) {
			String message = messages[count];
			for(Player player : players) player.sendMessage(Utils.color(message));
			++count;
		}
	}

	public static void broadcastMessage(Player player, String... messages) {
		int count = 0;
		while (count < messages.length) {
			String message = messages[count];
			player.sendMessage(Utils.color(message));
			++count;
		}
	}

	public static void broadcastLineMessage(String... messages) {
		int count = 0;
		while (count < messages.length) {
			String message = messages[count];
			Bukkit.broadcastMessage(Utils.color(message));
			++count;
		}
	}

	public static void broadcastLineMessage(Player player, String... messages) {
		int count = 0;
		while (count < messages.length) {
			String message = messages[count];
			player.sendMessage(Utils.color(message));
			++count;
		}
	}
	
	public static Location getLocationString(String s) {
		if (s == null || s.trim().equals("")) {
			return null;
		}

		String[] parts = s.split(",");
		if (parts.length == 6) {
			World w = Bukkit.getServer().getWorld(parts[0].replace("Location{world=CraftWorld{", "").replace("}", ""));
			double x = Double.parseDouble(parts[1].replace("x=", ""));
			double y = Double.parseDouble(parts[2].replace("y=", ""));
			double z = Double.parseDouble(parts[3].replace("z=", ""));
			float pitch = Float.parseFloat(parts[4].replace("pitch=", ""));
			float yaw = Float.parseFloat(parts[5].replace("yaw=", "").replace("}", ""));
			return new Location(w, x, y, z, pitch, yaw);
		}
		return null;
	}

	public static long parse(String input) {
		if ((input == null) || (input.isEmpty())) {
			return -1L;
		}

		long result = 0L;
		StringBuilder number = new StringBuilder();
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (Character.isDigit(c)) {
				number.append(c);
			} else {
				String str;
				if ((Character.isLetter(c)) && (!(str = number.toString()).isEmpty())) {
					result += convert(Integer.parseInt(str), c);
					number = new StringBuilder();
				}
			}
		}
		return result;
	}

	private static long convert(int value, char unit) {
		switch (unit) {
		case 'y': 
			return value * TimeUnit.DAYS.toMillis(365L);
		case 'M': 
			return value * TimeUnit.DAYS.toMillis(30L);
		case 'd': 
			return value * TimeUnit.DAYS.toMillis(1L);
		case 'h': 
			return value * TimeUnit.HOURS.toMillis(1L);
		case 'm': 
			return value * TimeUnit.MINUTES.toMillis(1L);
		case 's': 
			return value * TimeUnit.SECONDS.toMillis(1L);
		}
		return -1L;
	}

	public static boolean isInteger(String string) {
		boolean isInteger = true;
		try {
			Integer.parseInt(string);
		} catch (NumberFormatException exception) {
			isInteger = false;
		}
		return isInteger;
	}

	public static int randomInt(int min, int max) {
		Random random = new Random();
		int range = max - min + 1;
		int randomNum = random.nextInt(range) + min;
		return randomNum;
	} 

	public static String generateString(Random random, String characters, int length) {
		char[] text = new char[length];
		for (int i = 0; i < length; i++) {
			text[i] = characters.charAt(random.nextInt(characters.length()));
		}
		return new String(text);
	}

	public static String formatLocation(Location location) {
		return color("&6X&7: &e" + location.getBlockX() + " &6Y&7: &e" + location.getBlockY() + " &6Z&7: &e" + location.getBlockZ());
	}
	
	public static ItemStack getRandomLoot(List<ChanceLoot> loots) {
		if(loots.isEmpty()) {
			return null;
		}	
		double randomValue = Math.random();
		double cumulativeProbability = 0.0D;
		for (ChanceLoot loot : loots) {
		    cumulativeProbability += loot.getChance();
		    if ((randomValue * 100) <= cumulativeProbability) {
		        return loot.getItem();
		    }
		}
		return null;
	}
}
