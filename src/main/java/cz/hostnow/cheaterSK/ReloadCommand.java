package cz.hostnow.cheaterSK;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("hungerboard.reload")) {
            HungerBoard.getInstance().reloadConfig();
            HungerBoard.config = HungerBoard.getInstance().getConfig();

            sender.sendMessage(HungerBoard.colorize(HungerBoard.config.getString("msg.reload")));

            return true;
        }
        else {
            sender.sendMessage(HungerBoard.colorize(HungerBoard.config.getString("msg.perm")));
        }
        return false;
    }
}
