package fr.haizen.hapi.logs;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.HPlugin;
import fr.haizen.hapi.modules.Saveable;
import fr.haizen.hapi.saveable.DiscUtil;
import lombok.Getter;
import net.minecraft.util.com.google.gson.reflect.TypeToken;

public class LogsManager extends Saveable {
	
	@Getter public static LogsManager instance;
	@Getter Map<String, Logs> profiles = new HashMap<String, Logs>();
	
	public LogsManager(HPlugin plugin) {
		super(plugin, "Logs");
		this.setDesactivable(false);
		
		this.instance = this;
	}

	@Override
	public File getFile() {
		return new File(HAPI.getAPI().getDataFolder(), "logs.json");
	}

	@Override
	public void loadData() {
		long timeEnableStart = System.currentTimeMillis();
		String content = DiscUtil.readCatch(getFile());
		if (content == null) return;

		Type type = new TypeToken<Map<String, Logs>>(){}.getType();
		Map<String, Logs> map = HAPI.getAPI().getGson().fromJson(content, type);

		profiles.clear();
		profiles.putAll(map);
	}

	@Override
	public void saveData(boolean sync) {
		DiscUtil.writeCatch(getFile(), HAPI.getAPI().getGson().toJson(profiles), sync);
	}

	public Logs getLog() {
		return this.profiles.get("api_log");
	}
}
