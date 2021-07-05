package me.twoleggedcat.actioncoordinates;

import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class Main extends JavaPlugin implements Listener {
    boolean alwaysEnabled = false;
    boolean useDirectionNames = false;
    ArrayList<String> names = new ArrayList<String>(); // Leave it, it works

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        alwaysEnabled = this.getConfig().getBoolean("alwaysEnabled", true);
        alwaysEnabled = this.getConfig().getBoolean("useDirectionNames", true);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("coords")) {
            if (names.contains(sender.getName())) {
                names.remove(sender.getName());
                sender.sendMessage("Coordinates disabled.");
            } else {
                names.add(sender.getName());
                sender.sendMessage("Coordinates enabled.");
            }
        }
        return false;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (!alwaysEnabled && !names.contains(player.getName()))
            return;
        Location loc = player.getLocation();
        Vector direction = loc.getDirection();
        String message = "X: " + Math.round(loc.getX())
                + " Y: " + Math.round(loc.getY())
                + " Z: " + Math.round(loc.getZ());
        if (useDirectionNames)
            message += (Math.abs(direction.getX()) > Math.abs(direction.getZ()) ? (direction.getX() > 0 ? " +X" : " -X") : (direction.getZ() > 0 ? " +Z" : " -Z"));
        else
            message += (Math.abs(direction.getX()) > Math.abs(direction.getZ()) ? (direction.getX() > 0 ? " East" : " West") : (direction.getZ() > 0 ? " South" : " North"));
        player.sendActionBar(Component.text(message));
    }
}