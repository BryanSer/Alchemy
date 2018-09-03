/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public class AlchemyData {

    private static final Map<Integer, BiConsumer<AlchemyData, String[]>> OPT = new HashMap<>();

    static {
        OPT.put(4, (ad, t) -> {
            ad.AlchemyName = t[0];
            ad.Pure = Pureness.getPureness(t[1].charAt(0));
            ad.State = PhysicalState.getPhysicalState(t[2].charAt(0));
            ad.Weight = Integer.parseInt(t[3]);
        });
    }
    

    private String AlchemyName;
    private Pureness Pure;
    private PhysicalState State;
    private int Weight;

    public AlchemyData(String s) {
        String v[] = s.split("\\|");
        OPT.get(v.length).accept(this, v);
    }

    public String getAlchemyName() {
        return AlchemyName;
    }

    public Pureness getPure() {
        return Pure;
    }

    public PhysicalState getState() {
        return State;
    }

    public int getWeight() {
        return Weight;
    }

}
