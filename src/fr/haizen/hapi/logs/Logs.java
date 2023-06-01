package fr.haizen.hapi.logs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.bukkit.Bukkit;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;

public class Logs {

	@Getter @Setter private String logType;
	@Getter @Setter private List<String> logs;

    public Logs(String logType) {
    	this.logType = logType;
        this.logs = Lists.newArrayList();
    }
    
	public static void log(String prefix, String msg, LogType logType) {
		Bukkit.getConsoleSender().sendMessage(prefix + " " + msg);
	}

	public static void log(String[] messages, LogType type) {
		String[] arrayOfString;
		int j = (arrayOfString = messages).length;
		for (int i = 0; i < j; i++) {
			String message = arrayOfString[i];
			log("[H-API]", message, type);
			LogsManager.getInstance().getLog().getLogs().add("[H-API (" + type + ") / " + new SimpleDateFormat("dd-MM-yy HH:mm:ss").format(new Date()) + "] " + message);
		}
	}
}