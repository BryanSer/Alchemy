/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package Br.Alchemy.Attribute.Ability;

import Br.Alchemy.Attribute.AttributeEvent;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-11
 */
public abstract class Ability {

    protected String Name;
    protected String[] Description;
    protected int Level;
    protected AbilityType Type;
    
    protected Ability(){
        this.init();
    }

    public final void init() {
        this.Type.getAbilities()[Level] = this;
    }
    
    public abstract void onAttribute(AttributeEvent evt);

    public String getName() {
        return Name;
    }

    public String[] getDescription() {
        return Description;
    }

    public int getLevel() {
        return Level;
    }

    public AbilityType getType() {
        return Type;
    }
    
    
}
