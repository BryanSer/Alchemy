/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package br.bukkit.alchemy.item;

import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-8
 */
public interface Activateable {

    /**
     *
     * @param user
     * @return 是否成功使用(false将不会扣除物品 (如果物品是可消耗的话))
     */
    public boolean onActivate(Player user);
}
