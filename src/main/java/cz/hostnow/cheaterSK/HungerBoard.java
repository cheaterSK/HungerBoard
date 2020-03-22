package cz.hostnow.cheaterSK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class HungerBoard extends JavaPlugin implements Listener {
    private static HungerBoard instance;
    public static FileConfiguration config;
    private HashMap<Player, Board> playerList = new HashMap<>();

    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        config = getConfig();

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("hbreload").setExecutor(new ReloadCommand());

        for (Player p : Bukkit.getOnlinePlayers()) {
            Board b = new Board(p);
            playerList.put(p, b);
        }

        Bukkit.getScheduler().runTaskTimer(HungerBoard.getInstance(), (Runnable)new Runnable() {
            @Override
            public void run() {
                for (Player p : playerList.keySet()) {
                    playerList.get(p).show();
                }
            }
        }, 0L, 7L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Board b = new Board(e.getPlayer());
        playerList.put(e.getPlayer(), b);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerList.remove(e.getPlayer());
    }

    static HungerBoard getInstance() {
        return HungerBoard.instance;
    }

    static String colorize(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
