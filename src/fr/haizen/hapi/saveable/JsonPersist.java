package fr.haizen.hapi.saveable;

import java.io.File;

import com.google.gson.Gson;

import fr.haizen.hapi.HAPI;

public interface JsonPersist {
	
	public Gson gson = HAPI.getAPI().getGson();
	public File getFile();
	public void loadData();
	public void saveData(boolean sync);
}
