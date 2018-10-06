/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Attribute;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class AttributesListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent evt) {
        if (evt instanceof EntityDamageByEntityEvent) {
            handle((EntityDamageByEntityEvent) evt);
        } else {
            handle(evt);
        }
    }

    private void handle(EntityDamageByEntityEvent evt) {

    }

    private void handle(EntityDamageEvent evt) {

    }
}
