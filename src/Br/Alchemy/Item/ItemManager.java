/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Item;

import Br.Alchemy.Data;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class ItemManager {

    private static Map<String, EzItem> EzItems = new HashMap<>();
    
    public static void loadFile(){
        for (String key : EzItems.keySet()) {
            if(EzItems.get(key) instanceof EzItem){
                EzItems.remove(key);
            }
        }
        File folder = new File(Data.Plugin.getDataFolder(), File.separator + "EzItems" + File.separator);
        loadFile(folder);
    }
    
    private static void loadFile(File folder){
        for (File f : folder.listFiles()) {
            if(f.isDirectory()){
                loadFile(f);
                continue;
            }
            YamlConfiguration config = YamlConfiguration.loadConfiguration(f);
            for (String key : config.getKeys(false)) {
                EzItems.put(key, new EzItem(config.getConfigurationSection(key)));
            }
        }
    }
    
    
}
