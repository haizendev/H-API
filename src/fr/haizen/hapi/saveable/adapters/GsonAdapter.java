package fr.haizen.hapi.saveable.adapters;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import fr.haizen.hapi.HPlugin;

public abstract class GsonAdapter<T> extends TypeAdapter<T> {

	private HPlugin plugin;

	public GsonAdapter(HPlugin plugin) {
		this.plugin = plugin;
	}

	public Gson getGson() {
		return this.plugin.getGson();
	}
}
