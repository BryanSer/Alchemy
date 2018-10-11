/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Item;

import Br.API.Utils;
import Br.Alchemy.Attribute.TimeLimitAttributeBoost;
import Br.Alchemy.Data;
import Br.Alchemy.Tools;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class EzItem extends Item implements Activateable, Consume {

    public static final String EzItem_PREFIX = "EzItem";

    public static void init() {
        File folder = new File(Data.Plugin.getDataFolder(), File.separator + "EzItems" + File.separator);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            Utils.OutputFile(Data.Plugin, "Item_Example.yml", folder);
        } catch (IOException ex) {
            Logger.getLogger(EzItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String Key;
    private ItemStack Item;
    private int ColdDown = -1;
    private Requirement Require;
    private EffectData Effect;
    private Cost CostData;

    public EzItem(ConfigurationSection config) {
        Key = config.getName();
        Item = new ItemStack(Material.getMaterial(config.getInt("Type")));
        Item.setDurability((short) config.getInt("Durability"));
        if (config.contains("Meta")) {
            ConfigurationSection meta = config.getConfigurationSection("Meta");
            ItemMeta im = Item.getItemMeta();
            if (meta.contains("DisplayName")) {
                im.setDisplayName(ChatColor.translateAlternateColorCodes('&', meta.getString("DisplayName")));
            }
            if (meta.contains("Lore")) {
                im.setLore(
                        meta.getStringList("Lore")
                                .stream()
                                .map(s -> ChatColor.translateAlternateColorCodes('&', s))
                                .collect(Collectors.toList())
                );
            }
            if (meta.contains("Ench")) {
                Item.addUnsafeEnchantments(
                        meta.getStringList("Ench")
                                .stream()
                                .map(s -> s.split("-"))
                                .map(v -> new int[]{Integer.parseInt(v[0]), Integer.parseInt(v[1])})
                                .collect(
                                        Collectors.toMap(a -> Enchantment.getById(a[0]), a -> a[1]
                                        )
                                )
                );
            }
            if (meta.getBoolean("Unbreakable", false)) {
                im.spigot().setUnbreakable(true);
            }
        }
        Require = new Requirement(config.getConfigurationSection("Use.Requirement"));
        Effect = new EffectData(config.getConfigurationSection("Use.Effect"));
        ColdDown = config.getInt("Use.ColdDown", -1);
        CostData = new Cost(config.getConfigurationSection("Use.Cost"));
    }

    @Override
    protected ItemStack getOriginalItem() {
        return Item;
    }

    public int getColdDown() {
        return ColdDown;
    }

    public Requirement getRequire() {
        return Require;
    }

    public EffectData getEffect() {
        return Effect;
    }

    @Override
    public boolean onActivate(Player user) {
        if (!this.Effect.hasEffect()) {
            return false;
        }
        if (!this.Require.isUsable(user)) {
            user.sendMessage("§c你不满足这个物品的使用条件");
            return false;
        }
        if(!this.CostData.hasEnough(user)){
            user.sendMessage("§c你消耗不起这个物品");
            return false;
        }
        CostData.cost(user);
        this.Effect.effect(user);
        return true;
    }

    @Override
    public boolean isConsume() {
        return this.CostData.isConsume();
    }

    @Override
    public String getItemKey() {
        return this.Key;
    }

    public static class EffectData {

        private List<String> Commands = new ArrayList<>();
        private List<TimeLimitAttributeBoost> Attributes = new ArrayList<>();

        public EffectData(ConfigurationSection config) {
            if (config == null) {
                return;
            }
            if (config.contains("Commands")) {
                Commands.addAll(config.getStringList("Commands"));
            }
            if (config.contains("Attributes")) {
                config.getStringList("Attributes")
                        .stream()
                        .map(TimeLimitAttributeBoost::new)
                        .forEach(Attributes::add);
            }
        }

        public List<String> getCommands() {
            return Commands;
        }

        public List<TimeLimitAttributeBoost> getAttributes() {
            return Attributes;
        }

        public void effect(Player p) {
            for (String s : this.Commands) {
                if (s.startsWith("p:")) {
                    s = s.replaceAll("p:", "").replaceAll("%p", p.getName());
                    Bukkit.dispatchCommand(p, s);
                    continue;
                }
                if (s.startsWith("c:")) {
                    s = s.replaceAll("c:", "").replaceAll("%p", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
                    continue;
                }
                if (s.startsWith("tell:")) {
                    s = s.replaceAll("tell:", "");
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', s));
                    continue;
                }
                if (s.startsWith("pasop:")) {
                    s = s.replaceAll("pasop:", "");
                    if (p.isOp()) {
                        Bukkit.dispatchCommand(p, s);
                        continue;
                    }
                    p.setOp(true);
                    try {
                        Bukkit.dispatchCommand(p, s);
                    } catch (Exception e) {
                    }
                    p.setOp(false);
                }
            }
        }

        public boolean hasEffect() {
            return !this.Commands.isEmpty() || !this.Attributes.isEmpty();
        }
    }

    public static class Requirement {

        private String Permission = "";
        private int Level = 0;
        private int AlchemyLevel = 0;

        public Requirement(ConfigurationSection config) {
            if (config == null) {
                return;
            }
            Permission = config.getString("Permission", "");
            Level = config.getInt("Level", 0);
            AlchemyLevel = config.getInt("AlchemyLevel", 0);
        }

        public String getPermission() {
            return Permission;
        }

        public int getLevel() {
            return Level;
        }

        public int getAlchemyLevel() {
            return AlchemyLevel;
        }

        public boolean isUsable(Player p) {
            if (!Permission.isEmpty() && !p.hasPermission(this.Permission)) {
                return false;
            }
            if (p.getLevel() < Level) {
                return false;
            }
            //TODO: 炼金等级检测
            return true;
        }
    }

    public static class Cost {

        private int Money = 0;
        private int Point = 0;
        private boolean ConsumeSelf = false;

        public Cost(ConfigurationSection config) {
            if (config == null) {
                return;
            }
            Money = config.getInt("Money", 0);
            Point = config.getInt("Point", 0);
            ConsumeSelf = config.getBoolean("Self", false);
        }

        public int getMoney() {
            return Money;
        }

        public int getPoint() {
            return Point;
        }

        public boolean isConsume() {
            return ConsumeSelf;
        }

        public void cost(Player p) {
            if (Money > 0) {
                Utils.getEconomy().withdrawPlayer(p.getName(), Money);
            }
            if(Point > 0){
                Tools.getPlayerPointsAPI().take(p.getName(), Point);
            }
            //TODO: 消耗炼金要素
        }

        public boolean hasEnough(Player p) {
            if (Money > 0 && !Utils.getEconomy().has(p.getName(), this.Money)) {
                return false;
            }
            if (Point > 0 && Tools.getPlayerPointsAPI().look(p.getName()) < this.Point) {
                return false;
            }
            //TODO: 消耗炼金要素
            return true;
        }

    }
}
