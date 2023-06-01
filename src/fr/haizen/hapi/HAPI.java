package fr.haizen.hapi;

import fr.haizen.hapi.citizens.CitizenListener;
import fr.haizen.hapi.citizens.commands.CitizenCreateCommand;
import fr.haizen.hapi.citizens.commands.CitizenListCommand;
import fr.haizen.hapi.logs.Logs;
import fr.haizen.hapi.logs.LogsManager;
import fr.haizen.hapi.menu.MenuManager;
import fr.haizen.hapi.modules.ModuleManager;
import fr.haizen.hapi.modules.commands.ModuleCommand;
import fr.haizen.hapi.scoreboard.ScoreboardManager;
import lombok.Getter;

@Getter
public class HAPI extends HPlugin {

	//TODO Instance
	@Getter public static HAPI API;
	
	public HAPI() {
		super("H-API");
		API = this;
	}

	public void registerManagers() {
		ModuleManager moduleManager = this.getModuleManager();
		moduleManager.registerManager(new ScoreboardManager());
		
		this.registerPersists(new LogsManager(this));
	}
	
	public void registerOthers() {
		this.registerCommand(new ModuleCommand());
		this.registerListener(new MenuManager(this));
		
		this.registerCommand(new CitizenCreateCommand());
		this.registerCommand(new CitizenListCommand());
		this.registerListener(new CitizenListener());
		
		if(LogsManager.getInstance().getLog() == null) LogsManager.getInstance().getProfiles().put("api_log", new Logs("api_log"));
	}
}
