package fr.haizen.hapi.saveable.adapters;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.haizen.hapi.HPlugin;
import net.minecraft.util.com.google.gson.reflect.TypeToken;
import net.minecraft.util.com.google.gson.stream.JsonReader;
import net.minecraft.util.com.google.gson.stream.JsonToken;
import net.minecraft.util.com.google.gson.stream.JsonWriter;

public class PotionEffectAdapter extends GsonAdapter<PotionEffect> {

	private static Type seriType = new TypeToken<Map<String, Object>>() {}.getType();
	private static String TYPE = "effect";
	private static String DURATION = "duration";
	private static String AMPLIFIER = "amplifier";
	private static String AMBIENT = "ambient";

	public PotionEffectAdapter(HPlugin plugin) {
		super(plugin);
	}

	public void write(JsonWriter jsonWriter, PotionEffect potionEffect) throws IOException {
		if (potionEffect == null) {
			jsonWriter.nullValue();
			return;
		}
		jsonWriter.value(getRaw(potionEffect));
	}

	public PotionEffect read(JsonReader jsonReader) throws IOException {
		if (jsonReader.peek() == JsonToken.NULL) {
			jsonReader.nextNull();
			return null;
		}
		return fromRaw(jsonReader.nextString());
	}

	private String getRaw(PotionEffect potion) {
		Map<String, Object> serial = potion.serialize();

		return getGson().toJson(serial);
	}

	@SuppressWarnings("deprecation")
	private PotionEffect fromRaw(String raw) {
		Map<String, Object> keys = getGson().fromJson(raw, seriType);
		return new PotionEffect(PotionEffectType.getById(((Double) keys.get(TYPE)).intValue()),
				((Double) keys.get(DURATION)).intValue(), ((Double) keys.get(AMPLIFIER)).intValue(),
				((Boolean) keys.get(AMBIENT)).booleanValue());
	}
}
