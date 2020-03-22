package cz.hostnow.cheaterSK;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.Collections;
import java.util.List;

public class Board {
    private Player p;
    private Scoreboard board;
    private Objective obj;
    private Score score;
    private Team t;

    public Board(Player pl) {
        this.p = pl;
        this.createScoreboard();
    }

    public void createScoreboard() {
        final String header = placeholder(p, HungerBoard.colorize(HungerBoard.config.getString("board.header")));
        final List<String> body = HungerBoard.config.getStringList("board.body");
        Collections.reverse(body);
        this.board = Bukkit.getScoreboardManager().getNewScoreboard();
        (this.obj = this.board.registerNewObjective("HungerDimension", "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        this.obj.setDisplayName(header);
        for (int i = 0; i < body.size(); ++i) {
            String entry = "";
            for (int n = 0; n <= i; ++n) {
                entry += ChatColor.WHITE;
            }
            (this.t = this.board.registerNewTeam(Integer.toString(i))).addEntry(entry);
            this.t.setPrefix(placeholder(this.p, HungerBoard.colorize(body.get(i))));
            this.obj.getScore(entry).setScore(i);
        }
    }

    public void show() {
        this.refresh();
        this.p.setScoreboard(this.board);
    }

    public void refresh() {
        final List<String> body = HungerBoard.config.getStringList("board.body");
        Collections.reverse(body);
        for (int i = 0; i < body.size(); ++i) {
            (this.t = this.board.getTeam(Integer.toString(i))).setPrefix(placeholder(this.p, HungerBoard.colorize(body.get(i))));
            for (final String s : this.t.getEntries()) {
                this.obj.getScore(s).setScore(i);
            }
        }
    }

    private static String placeholder(final Player p, final String input) {
        if (HungerBoard.getInstance().getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            return PlaceholderAPI.setPlaceholders(p, input);
        }
        return input;
    }
}
