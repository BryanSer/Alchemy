/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Attribute;

import Br.Alchemy.Data;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class AttributesListener implements Listener {

    private Map<Integer, AttributesData> ProjecteltData = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent evt) {
        if (evt.getDamage() <= 0.5) {
            return;
        }
        AttributesData dam = null, ent = null;
        if (evt.getDamager() instanceof Projectile) {
            AttributesData get = ProjecteltData.get(evt.getDamager().getEntityId());
            if (get != null) {
                dam = get;
            }
        } else if (evt.getDamager() instanceof LivingEntity) {
            dam = new AttributesData((LivingEntity) evt.getDamager());
        }
        if (evt.getEntity() instanceof LivingEntity) {
            ent = new AttributesData((LivingEntity) evt.getEntity());
        }
        if (dam == null) {
            dam = new AttributesData();
        }
        if (ent == null) {
            ent = new AttributesData();
        }

        double damage = evt.getDamage();
        //攻击力加成
        damage += dam.get(Attributes.ATK);
        //攻击增幅
        damage *= (1 + dam.get(Attributes.ATKBoost));

//        int crit_state = 0;//暴击情况 1表示正暴击 -1表示负暴击
//        double crit_damage_effect = 0;//暴击造成的影响
        double crit = dam.get(Attributes.Crit);
        if (crit > 0 && Math.random() < crit) {
            double d = damage * (1.5d + dam.get(Attributes.CritExtraDamage));
//            crit_state = 1;
//            crit_damage_effect = d - damage;
            damage = d;
            //TODO: 播放特效
        } else if (crit < 0 && Math.random() < -crit) {
            double d = damage * 0.5;
//            crit_state = -1;
//            crit_damage_effect = d - damage;
            damage = d;
            //TODO: 播放特效
        }

        double r_dodge = Tools.calcDodge(ent.get(Attributes.Dodge));
        r_dodge -= dam.get(Attributes.Accuracy);

        if (Math.random() < r_dodge) {
            evt.setCancelled(true);
            ent.sendMessage("§6你闪避了一次攻击");
            dam.sendMessage("§c对方闪避了你的攻击");
            return;
        }
        double def = ent.get(Attributes.DEF);
        def *= (1 - dam.get(Attributes.Penetrate));
        def -= dam.get(Attributes.FixPenetrate);
        def = Tools.calcDef(def);
        damage *= (1 - def);
        damage += dam.get(Attributes.RealDamage);
        evt.setDamage(damage);
    }

    @EventHandler
    public void onLunch(ProjectileLaunchEvent evt) {
        if (evt.getEntity().getShooter() instanceof LivingEntity) {
            LivingEntity e = (LivingEntity) evt.getEntity().getShooter();
            AttributesData data = new AttributesData(e);
            ProjecteltData.put(evt.getEntity().getEntityId(), data);
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent evt) {
        if (ProjecteltData.containsKey(evt.getEntity().getEntityId())) {
            Bukkit.getScheduler().runTaskLater(Data.Plugin, new Runnable() {
                @Override
                public void run() {
                    ProjecteltData.remove(evt.getEntity().getEntityId());
                }
            }, 1);
        }
    }

    @EventHandler
    public void onSwap(PlayerSwapHandItemsEvent evt) {
        AttributesData.dropCache(evt.getPlayer());
    }

    @EventHandler
    public void onHeld(PlayerItemHeldEvent evt) {
        AttributesData.dropCache(evt.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent evt) {
        AttributesData.dropCache(evt.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryEvent evt) {
        for (HumanEntity p : evt.getViewers()) {
            AttributesData.dropCache(p);
        }
    }
}
