package fr.haizen.hapi.utils;

import java.lang.reflect.Field;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public final class FakeEnchantment extends Enchantment {
	public FakeEnchantment() {
		super(100);
	}

	public String getName() {
		return "CA_FAKE";
	}

	public int getMaxLevel() {
		return 0;
	}

	public int getStartLevel() {
		return 0;
	}

	public EnchantmentTarget getItemTarget() {
		return null;
	}

	public boolean conflictsWith(Enchantment enchantment) {
		return false;
	}

	public boolean canEnchantItem(ItemStack itemStack) {
		return false;
	}

	static {
		try {
			Field field = Enchantment.class.getDeclaredField("acceptingNew");
			field.setAccessible(true);
			field.set(null, Boolean.valueOf(true));
		} catch (Exception e) {
			throw new RuntimeException("Can't register enchantment", e);
		}
		if (Enchantment.getByName("CA_FAKE") == null) {
			Enchantment.registerEnchantment(new FakeEnchantment());
		}
	}
}
