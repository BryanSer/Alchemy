/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Attribute;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class AttributesData {

    private static Map<String, Map<Attributes, Double>> Cache = new HashMap<>();

    public static void dropCache(CommandSender p) {
        Cache.remove(p.getName());
    }

    private Map<Attributes, Double> Values = new EnumMap<>(Attributes.class);
    private LivingEntity Owner;

    public AttributesData() {
    }

    public AttributesData(LivingEntity owner) {
        this.Owner = owner;
        if (owner instanceof Player) {
            Player p = (Player) owner;
            Map<Attributes, Double> get = Cache.get(p.getName());
            if (get != null) {
                Values.putAll(get);
                extraAttributes();
                return;
            }
        }
        EntityEquipment equip = owner.getEquipment();
        for (ItemStack is : equip.getArmorContents()) {
            analyzeLore(is, this);
        }
        analyzeLore(equip.getItemInMainHand(), this);
//        if (equip.getItemInMainHand() != null) {
        analyzeLore(equip.getItemInOffHand(), this);
//        }

        if (owner instanceof Player) {
            Cache.put(((Player) owner).getName(), new EnumMap<>(Values));
        }
        extraAttributes();
    }

    public final void extraAttributes() {
        if (this.Owner instanceof Player) {
            Iterator<TimeLimitAttributeBoost.BoostingData> iter = TimeLimitAttributeBoost.Boosting.iterator();
            while (iter.hasNext()) {
                TimeLimitAttributeBoost.BoostingData data = iter.next();
                if (data.needDrop()) {
                    iter.remove();
                    continue;
                }
                if (data.getBooster().equals(this.Owner.getName())) {
                    this.add(data.getBoost().getType(), data.getBoost().getValue());
                }
            }
        }
        AttributesEvent evt = new AttributesEvent(Owner, this);
        Bukkit.getPluginManager().callEvent(evt);
    }

    public static void analyzeLore(ItemStack is, AttributesData writeto) {
        if (is != null && is.hasItemMeta() && is.getItemMeta().hasLore()) {
            analyzeLore(is.getItemMeta().getLore(), writeto);
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
            if (!s[1].matches("[+-][0-9.%]*")) {
                continue;
            }
            s[1] = s[1].replaceAll("%", "");
            double value = Double.parseDouble(s[1]);
            for (Attributes at : Attributes.values()) {
                if (s[0].equals(at.getDisplayName())) {
                    if (at.isRate()) {
                        value *= 0.01;
                    }
                    writeto.add(at, value);
                    break;
                }
            }
        }
    }

    public LivingEntity getOwner() {
        return Owner;
    }

    public double get(Attributes type) {
        return Values.get(type);
    }

    public void add(Attributes type, double value) {
        Double get = Values.get(type);
        if (get == null) {
            get = value;
        } else {
            get += value;
        }
        Values.put(type, value);
    }

    public void dealValue() {
        for (Attributes at : Attributes.values()) {
            Double get = Values.get(at);
            if (get == null) {
                Values.put(at, 0d);
            } else {
                Values.put(at, at.dealValue(get));
            }
        }
    }

    public void add(AttributesData data) {
        for (Map.Entry<Attributes, Double> e : data.Values.entrySet()) {
            this.add(e.getKey(), e.getValue());
        }
    }

    public void sendMessage(String msg) {
        if (this.Owner != null) {
            this.Owner.sendMessage(msg);
        }
    }
}
