package fr.haizen.hapi.modules;

import java.io.File;

import fr.haizen.hapi.HPlugin;
import fr.haizen.hapi.saveable.JsonPersist;
import net.minecraft.util.com.google.gson.Gson;

public abstract class Saveable extends Module implements JsonPersist {
	
	public boolean needDir, needFirstSave;
	
	public Saveable(HPlugin plugin, String name) {
		this(plugin, name, false, false);
	}
	
	public Saveable(HPlugin plugin, String name, boolean needDir, boolean needFirstSave) {
		super(plugin, name);
		this.needDir = needDir;
		this.needFirstSave = needFirstSave;
		if(this.needDir) {
			if(this.needFirstSave) {
				this.saveData(false);
			} else {
				File directory = this.getFile();
				if (!directory.exists()) {
					try {
						directory.mkdir();
					} catch (Exception exception) {
						exception.printStackTrace();
					}
				}
			}
		}
	}
	
	public Gson getGson() {
		return this.getPlugin().getGson();
	}
}
