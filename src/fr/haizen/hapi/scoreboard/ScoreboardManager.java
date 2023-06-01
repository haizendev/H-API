package fr.haizen.hapi.scoreboard;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import fr.haizen.hapi.HAPI;
import fr.haizen.hapi.modules.Module;

public class ScoreboardManager extends Module {

    private static ScoreboardManager instance;

    public static ScoreboardManager get() {
        return instance;
    }

    private Assemble scoreboardHandler;

    private List<AssembleAdapter> scoreboards = Lists.newArrayList();

    public ScoreboardManager() {
        super(HAPI.getAPI(), "Scoreboard");
        instance = this;
    }

    public void addScoreboard(AssembleAdapter adapter) {
        this.destroyScoreboard(adapter.getClass());
        this.scoreboards.add(adapter);

        this.setScoreboard(adapter);
    }
    
    public void addScoreboard(AssembleAdapter adapter, List<Player> players) {
        this.destroyScoreboard(adapter.getClass());
        this.scoreboards.add(adapter);

        this.setScoreboard(adapter, players);
    }

    private void setScoreboard(AssembleAdapter adapter) {
        if (this.scoreboardHandler != null) {
            this.scoreboardHandler.cleanup();
        }

        this.scoreboardHandler = new Assemble(this.getPlugin(), adapter, Lists.newArrayList(Bukkit.getOnlinePlayers()));
        this.scoreboardHandler.setTicks(20);
        this.scoreboardHandler.setAssembleStyle(AssembleStyle.MODERN);
    }
    
    private void setScoreboard(AssembleAdapter adapter, List<Player> players) {
        if (this.scoreboardHandler != null) {
            this.scoreboardHandler.cleanup();
        }

        this.scoreboardHandler = new Assemble(this.getPlugin(), adapter, players);
        this.scoreboardHandler.setTicks(20);
        this.scoreboardHandler.setAssembleStyle(AssembleStyle.MODERN);
    }

    public void destroyScoreboard(Class<? extends AssembleAdapter> clazz) {
        if (this.scoreboardHandler == null) return;
        
        this.scoreboards.removeIf(el -> el.getClass() == clazz);
        this.scoreboardHandler.cleanup();

        // Show the first scoreboard in list.
        if (this.scoreboards.size() > 0) {
            this.setScoreboard(this.scoreboards.get(0));
        } else this.scoreboardHandler = null;
    }
}
