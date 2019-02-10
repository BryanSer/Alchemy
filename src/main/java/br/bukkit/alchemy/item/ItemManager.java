/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.bukkit.alchemy.item;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import br.bukkit.alchemy.Data;
import br.bukkit.alchemy.Tools;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class ItemManager implements Listener {

    public static final String ITEM_LORE_PREFIX = "RBAIILPR";

    private static Map<String, Item> Items = new HashMap<>();

    public static void loadFile() {
        for (String key : Items.keySet()) {
            if (Items.get(key) instanceof EzItem) {
                Items.remove(key);
            }
        }
        File folder = new File(Data.Plugin.getDataFolder(), File.separator + "EzItems" + File.separator);
        loadFile(folder);
    }

    private static void loadFile(File folder) {
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                loadFile(f);
                continue;
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
            for (String key : config.getKeys(false)) {
                Items.put(key, new EzItem(config.getConfigurationSection(key)));
            }
        }
    }

    public static void init() {
        EzItem.init();
        ItemManager.loadFile();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent evt) {
        if (!evt.hasItem()) {
            return;
        }
        boolean mainhand = true;
        ItemStack is = evt.getItem();
        Player p = evt.getPlayer();
        if(is.equals(p.getInventory().getItemInOffHand())){
            mainhand = false;
        }
    }

    public static Item toItem(ItemStack is) {
        if (!is.hasItemMeta() || !is.getItemMeta().hasLore()) {
            return null;
        }
        for (String s : is.getItemMeta().getLore()) {
            s = Tools.decodeColorCode(s);
            if (s.startsWith(ItemManager.ITEM_LORE_PREFIX)) {
                s = s.split("\\|", 2)[1];
                return getItem(s);
            }
        }
        return null;
    }

    public static Item getItem(String key) {
        return Items.get(key);
    }
}
