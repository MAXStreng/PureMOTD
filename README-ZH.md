# PureMOTD 中文说明

当前版本：`v1.2.0`

PureMOTD 是一个轻量级服务器列表 MOTD 插件，用于自定义玩家在多人游戏列表中看到的服务器描述。

## 适配范围

- Minecraft Java 服务端版本：`1.20` 及以上
- Bukkit API：`1.20+`
- Java：`17` 或更高版本
- 推荐核心：Spigot、Paper、Purpur、Folia、Mohist 及其他兼容 Bukkit API 的服务端

插件只使用 Bukkit 公共 API，不使用 NMS、CraftBukkit 内部类或版本包名，因此面向 `1.20+` 的跨版本兼容。

## 功能

- 自定义服务器列表 MOTD 两行文本。
- 支持 MOTD 轮换，可按固定秒数自动切换多组 MOTD。
- 支持 Minecraft 颜色符号 `&`。
- 可选自定义服务器列表显示的最大人数。
- 支持游戏内 TAB 玩家列表顶部/底部自定义内容。
- 支持 TAB 内容按秒自动刷新，可实时更新 `%time%`、`%online%`、`%max%` 等占位符。
- 支持 `/puremotd reload` 热重载配置。

## 指令

- `/puremotd reload`：重载配置文件。

## 权限

- `puremotd.admin`：允许使用 `/puremotd reload`，默认 OP 拥有。

## 配置文件

配置文件位置：

`plugins/PureMOTD/config.yml`

默认配置：

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
      - line-1: "&b&lPureblock &8| &f当前在线 &a%online%&7/&e%max%"
        line-2: "&7愿你在这片纯净世界里留下漂亮的建筑"

tab-list:
  enabled: true
  refresh-interval-seconds: 1
  header:
    - "&d&lPureblock &8| &b纯境方块"
    - "&7在线：&a%online%&7/&e%max%"
  footer:
    - "&7欢迎回来，&b%player%&7。"
    - "&8%time%"

max-players:
  enabled: false
  value: 100
```

修改配置后，执行 `/puremotd reload` 即可生效。`refresh-interval-seconds` 用于控制 TAB 自动刷新间隔，单位为秒，最低为 `1`。

支持占位符：

- `%player%`：玩家名，TAB 内容可用。
- `%online%`：当前在线人数。
- `%max%`：服务器最大人数。
- `%server%`：服务端名称。
- `%time%`：当前时间。
