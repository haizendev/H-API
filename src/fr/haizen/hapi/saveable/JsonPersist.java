package fr.haizen.hapi.saveable;

import java.io.File;

import fr.haizen.hapi.HAPI;
import net.minecraft.util.com.google.gson.Gson;

public interface JsonPersist {
	
	public Gson gson = HAPI.getAPI().getGson();
	public File getFile();
	public void loadData();
	public void saveData(boolean sync);
}
