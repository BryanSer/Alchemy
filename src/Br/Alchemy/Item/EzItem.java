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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class EzItem {

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

    private ItemStack Item;
    private Requirement Require;
    
    public static class Effect{
        private List<String> Commands;
        private List<TimeLimitAttributeBoost> Attributes;
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
            if(p.getLevel() < Level){
                return false;
            }
            //TODO
            return true;
        }
    }
}
