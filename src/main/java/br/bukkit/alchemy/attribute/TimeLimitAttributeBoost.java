 /*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
 package br.bukkit.alchemy.attribute;

 import org.bukkit.entity.Player;

 import java.util.ArrayList;
 import java.util.List;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class TimeLimitAttributeBoost {

    public static List<BoostingData> Boosting = new ArrayList<>();

    private Attributes Type;
    private double Value;
    private int TimeLength;

    public TimeLimitAttributeBoost(Attributes Type, double Value, int TimeLength) {
        this.Type = Type;
        this.Value = Value;
        this.TimeLength = TimeLength;
    }

    public TimeLimitAttributeBoost(String str) {
        String s[] = str.split("\\|");
        this.Type = Attributes.valueOf(s[0]);
        this.Value = Double.parseDouble(s[1]);
        this.TimeLength = Integer.parseInt(s[2]);
    }

    public Attributes getType() {
        return Type;
    }

    public double getValue() {
        return Value;
    }

    public int getTimeLength() {
        return TimeLength;
    }
    
    

    public void boost(Player p) {
        BoostingData data = new BoostingData(this, p.getName(), System.currentTimeMillis());
        Boosting.add(data);
    }
    
    public static class BoostingData{
        private TimeLimitAttributeBoost Boost;
        private String Booster;
        private long BoostTime;

        public BoostingData(TimeLimitAttributeBoost Boost, String Booster, long BoostTime) {
            this.Boost = Boost;
            this.Booster = Booster;
            this.BoostTime = BoostTime;
        }

        public TimeLimitAttributeBoost getBoost() {
            return Boost;
        }

        public String getBooster() {
            return Booster;
        }

        public long getBoostTime() {
            return BoostTime;
        }
        
        public boolean needDrop(){
            return (System.currentTimeMillis() - this.Boost.getTimeLength() * 1000) >= this.BoostTime;
        }
    }
}
