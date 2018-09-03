/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public enum PhysicalState {
    Solid("§a固体", 'S'),
    Liquid("§b液体", 'L'),
    @Deprecated
    Mixing("§c固液混合", 'M');

    private final static Map<Character, PhysicalState> PhysicalStates = new HashMap<>();

    static {
        for (PhysicalState st : PhysicalState.values()) {
            PhysicalStates.put(st.getKey(), st);
        }
    }
    
    public static PhysicalState getPhysicalState(char c){
        return PhysicalStates.get(c);
    }

    private final String display;
    private final char key;

    private PhysicalState(String display, char key) {
        this.display = display;
        this.key = key;
    }

    public String getDisplay() {
        return display;
    }

    public char getKey() {
        return key;
    }

}
