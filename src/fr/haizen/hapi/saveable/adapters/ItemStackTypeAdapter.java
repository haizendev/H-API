package fr.haizen.hapi.saveable.adapters;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import fr.haizen.hapi.HAPI;
import net.minecraft.util.com.google.gson.JsonDeserializationContext;
import net.minecraft.util.com.google.gson.JsonDeserializer;
import net.minecraft.util.com.google.gson.JsonElement;
import net.minecraft.util.com.google.gson.JsonObject;
import net.minecraft.util.com.google.gson.JsonParseException;
import net.minecraft.util.com.google.gson.JsonSerializationContext;
import net.minecraft.util.com.google.gson.JsonSerializer;

public class ItemStackTypeAdapter implements JsonSerializer<ItemStack>, JsonDeserializer<ItemStack> {

	private static final String MATERIAL = "material";
	private static final String AMOUNT = "amount";
	private static final String DURABILITY = "durability";
	private static final String DISPLAY_NAME = "displayName";
	private static final String LORE = "lore";
	private static final String ENCHANTMENTS = "enchants";

	@SuppressWarnings("unchecked")
	@Override
	public ItemStack deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
		try {
			JsonObject obj = json.getAsJsonObject();
			Material material = Material.getMaterial(obj.get(ItemStackTypeAdapter.MATERIAL).getAsInt());
			ItemStack itemStack = new ItemStack(material, 1, (short) 0);
			itemStack.setAmount(obj.get(ItemStackTypeAdapter.AMOUNT).getAsInt());
			itemStack.setDurability((short) obj.get(ItemStackTypeAdapter.DURABILITY).getAsInt());

			if (itemStack.getAmount() <= 0) {
				itemStack.setAmount(1);
			}

			JsonElement displayName = obj.get(ItemStackTypeAdapter.DISPLAY_NAME);
			JsonElement lore = obj.get(ItemStackTypeAdapter.LORE);
			ItemMeta meta = Bukkit.getItemFactory().getItemMeta(material);
			if (displayName != null) {
				meta.setDisplayName(displayName.getAsString());
			}
			if (lore != null) {
				meta.setLore(HAPI.getAPI().getGson().fromJson(lore, List.class));
			}
			JsonElement enchants = obj.get(ItemStackTypeAdapter.ENCHANTMENTS);
			if (enchants != null) {
				Map<String, Double> enchantsMap = HAPI.getAPI().getGson().fromJson(enchants, Map.class);
				for (Map.Entry<String, Double> entry : enchantsMap.entrySet()) {
					Enchantment enchant = Enchantment.getByName(entry.getKey());
					int level = entry.getValue().intValue();
					if (material == Material.ENCHANTED_BOOK) {
						((EnchantmentStorageMeta) meta).addStoredEnchant(enchant, level, true);
					} else {
						meta.addEnchant(enchant, level, true);
					}
				}
			}

			itemStack.setItemMeta(meta);
			return itemStack;
		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public JsonElement serialize(final ItemStack src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject obj = new JsonObject();
		try {
			final ItemMeta meta = src.getItemMeta();
			obj.addProperty(ItemStackTypeAdapter.MATERIAL, src.getType().getId());
			obj.addProperty(ItemStackTypeAdapter.AMOUNT, src.getAmount() == 0 ? 1 : src.getAmount());
			obj.addProperty(ItemStackTypeAdapter.DURABILITY, src.getDurability());
			obj.addProperty(ItemStackTypeAdapter.DISPLAY_NAME, (meta == null ? null : meta.getDisplayName()));
			obj.add(ItemStackTypeAdapter.LORE, HAPI.getAPI().getGson().toJsonTree(meta == null ? null : meta.getLore(), List.class));

			// - enchants
			Map<Enchantment, Integer> srcEnchants = null;
			if (src.getType() == Material.ENCHANTED_BOOK) {
				srcEnchants = ((EnchantmentStorageMeta) meta).getStoredEnchants();
			} else {
				srcEnchants = src.getEnchantments();
			}
			Map<String, Integer> enchants = new HashMap<>();
			for (Map.Entry<Enchantment, Integer> entry : srcEnchants.entrySet()) {
				enchants.put(entry.getKey().getName(), entry.getValue());
			}
			obj.add(ItemStackTypeAdapter.ENCHANTMENTS, HAPI.getAPI().getGson().toJsonTree(enchants, Map.class));

			return obj;
		} catch (final Exception ex) {
			ex.printStackTrace();
			return obj;
		}
	}
}
