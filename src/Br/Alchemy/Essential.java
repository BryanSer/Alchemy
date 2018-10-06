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
 * @since 2018-8-26
 */
public enum Essential {
    Earth("地", 'E'),
    Metal("钢", 'M'),
    Water("水", 'W'),
    Fire("火", 'F'),
    Wood("木", 'W');
    private String DisplayName;
    private char Key;

    public static final Map<Character, Essential> EssentialMap = new HashMap<>();

    static {
        for (Essential e : Essential.values()) {
            EssentialMap.put(e.getKey(), e);
        }
    }
    
    public static Essential getEssential(char key){
        return EssentialMap.get(key);
    }

    private Essential(String DisplayName, char Key) {
        this.DisplayName = DisplayName;
        this.Key = Key;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public char getKey() {
        return Key;
    }

}
