package fr.haizen.hapi.saveable.adapters;

import fr.haizen.hapi.HPlugin;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.TypeAdapter;

public abstract class GsonAdapter<T> extends TypeAdapter<T> {

	private HPlugin plugin;

	public GsonAdapter(HPlugin plugin) {
		this.plugin = plugin;
	}

	public Gson getGson() {
		return this.plugin.getGson();
	}
}
