/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.bukkit.alchemy.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import Br.Alchemy.Tools;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public abstract class Item {

    public abstract String getItemKey();

    protected abstract ItemStack getOriginalItem();

    public ItemStack toItemStack() {
        return toItemStack(1);
    }

    public ItemStack toItemStack(int amount) {
        ItemStack is = this.getOriginalItem();
        if (is == null) {
            return null;
        }
        is = is.clone();
        is.setAmount(amount);
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
        lore.add(Tools.encodeColorCode(ItemManager.ITEM_LORE_PREFIX + "|" + this.getItemKey()));
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

}
