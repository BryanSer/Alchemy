/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.bukkit.alchemy;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import Br.API.Data.ProxyUtil;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-9-4
 */
public abstract class Alchemy implements ProxyUtil {

    public static final String ALCHEMY_KEY = "Alchemy";

    protected String AlchemyID;
    protected String DisplayName;
    protected Map<Essential, Integer> Mate = new EnumMap<>(Essential.class);

    protected abstract ItemStack getDefaultItem();

    public abstract void onUse();

    @SuppressWarnings("Duplicates")
    public ItemStack toItemStack(AlchemyInfo info, int amount) {
        ItemStack is = this.getDefaultItem().clone();
        is.setAmount(amount);
        ItemMeta im = is.getItemMeta();
        List<String> lore = im.hasLore() ? im.getLore() : new ArrayList<>();
        lore.add(info.toLore());
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack toItemStack(AlchemyInfo info) {
        return toItemStack(info, 1);
    }

    public static String getALCHEMY_KEY() {
        return ALCHEMY_KEY;
    }

    public String getAlchemyID() {
        return AlchemyID;
    }

    public Map<Essential, Integer> getMate() {
        return Mate;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    
}
