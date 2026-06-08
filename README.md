# PureMOTD

Current version: `v1.1.0`

PureMOTD is a lightweight server-list MOTD plugin for customizing the description players see in the Minecraft multiplayer server list.

## Compatibility

- Minecraft Java server versions: `1.20` and newer
- Bukkit API: `1.20+`
- Java: `17` or newer
- Recommended servers: Spigot, Paper, Purpur, Folia, Mohist, and other Bukkit API compatible servers

PureMOTD only uses public Bukkit API. It does not use NMS, CraftBukkit internals, or versioned server package names, so it is designed for broad `1.20+` compatibility.

## Features

- Custom two-line server-list MOTD.
- Rotating MOTD entries with a configurable interval.
- Minecraft `&` color code support.
- Optional custom displayed max-player count.
- Custom in-game TAB player-list header and footer.
- `/puremotd reload` hot reload.

## Command

- `/puremotd reload`: reload the configuration file.

## Permission

- `puremotd.admin`: allows `/puremotd reload`, granted to OP by default.

## Configuration

Config path:

`plugins/PureMOTD/config.yml`

Default config:

```yaml
motd:
  enabled: true
  line-1: "&d&lPureblock &8| &b纯境方块"
  line-2: "&7一个安静、纯净、认真建造的世界 &8- &a欢迎回来"
  rotation:
    enabled: true
    interval-seconds: 30
    entries:
      - line-1: "&d&lPureblock &8| &b纯境方块"
        line-2: "&7一个安静、纯净、认真建造的世界 &8- &a欢迎回来"
      - line-1: "&b&lPureblock &8| &fOnline &a%online%&7/&e%max%"
        line-2: "&7Leave a clean mark on this world"

tab-list:
  enabled: true
  header:
    - "&d&lPureblock &8| &bPureblock"
    - "&7Online: &a%online%&7/&e%max%"
  footer:
    - "&7Welcome back, &b%player%&7."
    - "&8%time%"

max-players:
  enabled: false
  value: 100
```

After editing the config, run `/puremotd reload`.

Supported placeholders:

- `%player%`: player name, available in TAB text.
- `%online%`: current online count.
- `%max%`: server max players.
- `%server%`: server name.
- `%time%`: current time.
