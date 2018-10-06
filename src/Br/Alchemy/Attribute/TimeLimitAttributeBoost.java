/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package Br.Alchemy.Attribute;

import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class TimeLimitAttributeBoost {
    private Attributes Type;
    private double Value;
    private int TimeLength;

    public TimeLimitAttributeBoost(Attributes Type, double Value, int TimeLength) {
        this.Type = Type;
        this.Value = Value;
        this.TimeLength = TimeLength;
    }
    
    public TimeLimitAttributeBoost(String str){
        String s[] = str.split("\\|");
        this.Type = Attributes.valueOf(s[0]);
        this.Value = Double.parseDouble(s[1]);
        this.TimeLength = Integer.parseInt(s[2]);
    }
    
    public void boost(Player p){
        
    }
}
