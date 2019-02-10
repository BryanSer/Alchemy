/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package br.bukkit.alchemy;

import org.bukkit.plugin.java.JavaPlugin;

import br.bukkit.alchemy.item.ItemManager;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public class Main extends JavaPlugin{

    @Override
    public void onEnable() {
        ItemManager.init();
    }
    
}
