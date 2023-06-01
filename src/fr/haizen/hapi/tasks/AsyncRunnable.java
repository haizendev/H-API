package fr.haizen.hapi.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.CustomTimingsHandler;

public abstract class AsyncRunnable extends BukkitRunnable {
	
	private CustomTimingsHandler timings = new CustomTimingsHandler("H-API Async Task - Class '" + getClass().getName() + "'");

	public void startTimings() {
		this.timings.startTiming();
	}

	public void stopTimings() {
		this.timings.stopTiming();
	}
}
