/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package Br.Alchemy.Attribute;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class AttributeEvent extends Event{
    private static HandlerList Handlers = new HandlerList();
    private LivingEntity Owner;
    private AttributesData Data;

    public AttributeEvent(LivingEntity Owner, AttributesData Data) {
        this.Owner = Owner;
        this.Data = Data;
    }
    
    public boolean hasOwner(){
        return Owner != null;
    }

    public LivingEntity getOwner() {
        return Owner;
    }

    public AttributesData getData() {
        return Data;
    }
    
    

    public static HandlerList getHandlerList() {
        return Handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return Handlers;
    }
    
    
}
