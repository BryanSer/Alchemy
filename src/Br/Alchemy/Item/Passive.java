/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Item;

import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-8
 */
public interface Passive {

    public enum PassiveType {
        Inventory,
        Handing,
        Equiping;
    }
    
    public PassiveType getPassiveType();
    
    public void onPassive(Player p);
}
