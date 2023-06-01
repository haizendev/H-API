package fr.haizen.hapi.tasks;

import org.bukkit.Bukkit;

import fr.haizen.hapi.HPlugin;

public enum TaskRunner {

    SYNC {
        public void runTask(Runnable runnable, HPlugin plugin) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable);
        }

        public void runTaskWithDelay(Runnable runnable, long delay, HPlugin plugin) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, runnable, delay);
        }

        public void runRepetatingTask(Runnable runnable, long period, long delay, HPlugin plugin) {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, runnable, period, delay);
        }
    },
    ASYNC {
        @SuppressWarnings("deprecation")
        public void runTask(Runnable runnable, HPlugin plugin) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, runnable);
        }

        @SuppressWarnings("deprecation")
        public void runTaskWithDelay(Runnable runnable, long delay, HPlugin plugin) {
            Bukkit.getScheduler().scheduleAsyncDelayedTask(plugin, runnable, delay);
        }

        @SuppressWarnings("deprecation")
        public void runRepetatingTask(Runnable runnable, long period, long delay, HPlugin plugin) {
            Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, runnable, period, delay);
        }
    };

    public void runTask(Runnable runnable, HPlugin plugin) {
        throw new AbstractMethodError("not AUTHORIZED");
    }

    public void runTaskWithDelay(Runnable runnable, long delay, HPlugin plugin) {
        throw new AbstractMethodError("not AUTHORIZED");

    }

    public void runRepetatingTask(Runnable runable, long period, long delay, HPlugin plugin) {
        throw new AbstractMethodError("not AUTHORIZED");
    }

}
