package fr.haizen.hapi.chances;

import java.util.Collections;
import java.util.List;

import org.bukkit.inventory.ItemStack;

public class ChanceUtils {

	public static ChanceLoot getRandomLoot(List<ChanceLoot> loots) {
		if(loots.isEmpty()) {
			return null;
		}	
		Collections.shuffle(loots);
		double randomValue = Math.random();
		double cumulativeProbability = 0.0D;
		for (ChanceLoot loot : loots) {
		    cumulativeProbability += loot.getChance();
		    if ((randomValue * 100) <= cumulativeProbability) {
		        return loot;
		    }
		}
		return null;
	}
	
}
