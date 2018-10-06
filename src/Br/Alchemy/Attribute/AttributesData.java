/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Attribute;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class AttributesData {

    private Map<Attributes, Double> Values = new EnumMap<>(Attributes.class);
    private LivingEntity Owner;

    public AttributesData(LivingEntity owner) {
        this.Owner = owner;
    }
    
    public static void analyzeLore(ItemStack is,AttributesData writeto){
        if(is != null && is.hasItemMeta() && is.getItemMeta().hasLore()){
            analyzeLore(is.getItemMeta().getLore(),writeto);
        }
    }

    public static void analyzeLore(List<String> lore, AttributesData writeto) {
        if (writeto == null) {
            throw new IllegalArgumentException();
        }
        for (String line : lore) {
            line = ChatColor.stripColor(line).replaceAll(" ", "");
            String[] s = line.split("[:：]", 2);
            if (s == null || s.length != 2) {
                continue;
            }
            if(!s[1].matches("[+-][0-9.%]*")){
                continue;
            }
            s[1] = s[1].replaceAll("%", "");
            double value = Double.parseDouble(s[1]);
            for (Attributes at : Attributes.values()) {
                if(s[0].equals(at.getDisplayName())){
                    if(at.isRate()){
                        value *= 0.01;
                    }
                    writeto.add(at, value);
                    break;
                }
            }
        }
    }

    public Map<Attributes, Double> getValues() {
        return Values;
    }

    public LivingEntity getOwner() {
        return Owner;
    }
    
    public double get(Attributes type){
        return Values.get(type);
    }

    public void add(Attributes type, double value) {
        Double get = Values.get(type);
        if(get == null){
            get = value;
        }else {
            get += value;
        }
        Values.put(type, value);
    }
}
