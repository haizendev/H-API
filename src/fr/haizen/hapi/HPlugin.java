package fr.haizen.hapi;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import com.google.common.collect.Lists;

import fr.haizen.hapi.citizens.CitizenManager;
import fr.haizen.hapi.commands.CommandFramework;
import fr.haizen.hapi.modules.Module;
import fr.haizen.hapi.modules.ModuleManager;
import fr.haizen.hapi.packets.PacketManager;
import fr.haizen.hapi.saveable.EnumTypeAdapter;
import fr.haizen.hapi.saveable.JsonPersist;
import fr.haizen.hapi.saveable.adapters.ItemStackAdapter;
import fr.haizen.hapi.saveable.adapters.LocationAdapter;
import fr.haizen.hapi.saveable.adapters.PotionEffectAdapter;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.com.google.gson.Gson;
import net.minecraft.util.com.google.gson.GsonBuilder;

@Getter
public abstract class HPlugin extends JavaPlugin {

	private String pluginName;

	//TODO Managers
	private CommandFramework framework;
	private PacketManager packetManager;
	private ModuleManager moduleManager;
	private CitizenManager citizenManager;

	//TODO JSON
	@Setter private Gson gson;
	private List<JsonPersist> persists;

	public HPlugin(String pluginName) {
		this.pluginName = pluginName;
	}

	public void onEnable() {	
		this.getDataFolder().mkdir();
		this.gson = this.getGsonBuilder().create();
		this.persists = Lists.newArrayList();
		this.framework = new CommandFramework(this);
		this.packetManager = new PacketManager();
		this.moduleManager = new ModuleManager();
		this.citizenManager = new CitizenManager();
		this.registerManagers();
		this.registerOthers();
		this.packetManager.registerPackets();
		this.loadPersists();

		for(Module modules : HAPI.getAPI().getModuleManager().getModules()) {
			modules.onLoad();
		}
		super.onEnable();
	}

	public void onDisable() {
		this.savePersists(true);

		for(Module modules : HAPI.getAPI().getModuleManager().getModules()) {
			modules.onUnload();
		}
		super.onDisable();
	}

	public abstract void registerManagers();
	public abstract void registerOthers();

	public void registerPersists(JsonPersist persist) {
		this.persists.add(persist);
	}

	public void registerListener(Listener listener) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		pluginManager.registerEvents(listener, this);
	}

	public void registerCommand(Object command) {
		this.framework.registerCommands(command);
	}

	public void loadPersists() {
		for (JsonPersist persist : this.persists) {
			persist.loadData();
		}
	}

	public void savePersists(boolean value) {
		for (JsonPersist persist : this.persists) {
			persist.saveData(value);
		}
	}

	public GsonBuilder getGsonBuilder() {
		return (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping()
				.excludeFieldsWithModifiers(new int[] { 128, 64 }).registerTypeAdapterFactory(EnumTypeAdapter.ENUM_FACTORY)
				.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackAdapter(this))
				.registerTypeAdapter(PotionEffect.class, new PotionEffectAdapter(this))
				.registerTypeAdapter(Location.class, new LocationAdapter(this));
	}
}
