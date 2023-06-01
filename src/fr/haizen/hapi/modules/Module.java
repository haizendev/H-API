package fr.haizen.hapi.modules;

import java.io.File;

import org.bukkit.event.Listener;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.HPlugin;
import fr.haizen.hapi.citizens.Citizen;
import fr.haizen.hapi.citizens.CitizenManager;
import fr.haizen.hapi.commands.CommandFramework;
import fr.haizen.hapi.commands.ICommand;
import fr.haizen.hapi.logs.LogType;
import fr.haizen.hapi.logs.Logs;
import fr.haizen.hapi.packets.PacketManager;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_7_R4.Packet;

@Getter @Setter
public abstract class Module {
	
	private HPlugin plugin;
	private boolean active, desactivable;
	private String moduleName;
	
	public Module(HPlugin plugin, String moduleName) {
		this.plugin = plugin;
		this.active = true;
		this.desactivable = true;
		this.moduleName = moduleName;
	}
	
	public void onLoad() {
		Logs.log("(Module)", "Le module " + this.moduleName + " à été load avec succès.", LogType.SUCCESS);
	}
	
	public void onUnload() {
		Logs.log("(Module)", "Le module " + this.moduleName + " à été unload avec succès.", LogType.SUCCESS);
	}

	public void registerCommand(ICommand command) {
		CommandFramework framework = this.plugin.getFramework();
		framework.registerCommands(command);
	}
	
	public void registerListener(Listener listener) {
		this.plugin.registerListener(listener);
	}
	
	public void registerPacket(int id, Packet packet) {
		PacketManager packetManager = this.plugin.getPacketManager();
		packetManager.registerPacket(id, packet);
	}
	
	public void registerCitizen(Citizen citizen) {
		CitizenManager citizenManager = HAPI.getAPI().getCitizenManager();
		citizenManager.registerCitizen(citizen);
	}
	
    public File getModuleFolder() {
        File folder = new File(this.getPlugin().getDataFolder() + File.separator + this.getClass().getSimpleName());
        if (!folder.exists()) {
            folder.mkdir();
        }

        return folder;
    }
}
