package com.mc.puremotd;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public final class PureMOTDPlugin extends JavaPlugin implements Listener {
    private static final DateTimeFormatter DISPLAY_TIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("PureMOTD 已加载。使用 /puremotd reload 重载配置。");
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        if (!getConfig().getBoolean("motd.enabled", true)) {
            return;
        }

        String[] motd = currentMotd();
        String line1 = applyPlaceholders(null, motd[0]);
        String line2 = applyPlaceholders(null, motd[1]);
        event.setMotd(color(line1) + "\n" + color(line2));

        if (getConfig().getBoolean("max-players.enabled", false)) {
            event.setMaxPlayers(Math.max(0, getConfig().getInt("max-players.value", event.getMaxPlayers())));
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        applyTabList(event.getPlayer());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("puremotd")) {
            return false;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("puremotd.admin")) {
                sender.sendMessage(color("&c你没有权限重载 PureMOTD。"));
                return true;
            }
            reloadConfig();
            refreshTabList();
            sender.sendMessage(color("&aPureMOTD 配置已重载。"));
            return true;
        }
        sender.sendMessage(color("&e用法：/puremotd reload"));
        return true;
    }

    private String[] currentMotd() {
        if (!getConfig().getBoolean("motd.rotation.enabled", false)) {
            return new String[]{
                    getConfig().getString("motd.line-1", "&d&lPureblock &8| &b纯境方块"),
                    getConfig().getString("motd.line-2", "&7一个安静、纯净、认真建造的世界")
            };
        }

        List<Map<?, ?>> entries = getConfig().getMapList("motd.rotation.entries");
        if (entries.isEmpty()) {
            return new String[]{
                    getConfig().getString("motd.line-1", "&d&lPureblock &8| &b纯境方块"),
                    getConfig().getString("motd.line-2", "&7一个安静、纯净、认真建造的世界")
            };
        }

        long intervalSeconds = Math.max(1L, getConfig().getLong("motd.rotation.interval-seconds", 30L));
        int index = (int) ((System.currentTimeMillis() / 1000L / intervalSeconds) % entries.size());
        Map<?, ?> entry = entries.get(index);
        return new String[]{
                mapString(entry, "line-1", getConfig().getString("motd.line-1", "")),
                mapString(entry, "line-2", getConfig().getString("motd.line-2", ""))
        };
    }

    private String mapString(Map<?, ?> map, String key, String fallback) {
        Object value = map.get(key);
        return value == null ? fallback : String.valueOf(value);
    }

    private void applyTabList(Player player) {
        if (!getConfig().getBoolean("tab-list.enabled", true)) {
            return;
        }
        String header = joinLines(getConfig().getStringList("tab-list.header"));
        String footer = joinLines(getConfig().getStringList("tab-list.footer"));
        player.setPlayerListHeaderFooter(color(applyPlaceholders(player, header)), color(applyPlaceholders(player, footer)));
    }

    private void refreshTabList() {
        for (Player player : getServer().getOnlinePlayers()) {
            applyTabList(player);
        }
    }

    private String joinLines(List<String> lines) {
        return String.join("\n", lines);
    }

    private String applyPlaceholders(Player player, String text) {
        return (text == null ? "" : text)
                .replace("%player%", player == null ? "" : player.getName())
                .replace("%online%", String.valueOf(getServer().getOnlinePlayers().size()))
                .replace("%max%", String.valueOf(getServer().getMaxPlayers()))
                .replace("%server%", getServer().getName())
                .replace("%time%", DISPLAY_TIME.format(Instant.now()));
    }

    private String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text == null ? "" : text);
    }
}
