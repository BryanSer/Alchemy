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
public enum Pureness {
    Weird("§c微乎其微的", 'W', 1),
    Turbid("§b浑浊的", 'T', 3),
    Slightly("§a略纯的", 'S', 5),
    HighPurity("§a§l高纯度的", 'H', 8),
    Pure("§b§l纯的", 'P', 12);

    private final static Map<Character, Pureness> Index = new HashMap<>();

    static {
        for (Pureness p : Pureness.values()) {
            Index.put(p.getKey(), p);
        }
    }
    
    public static Pureness getPureness(char c){
        return Index.get(c);
    }

    private final String display;
    private final char key;
    private final int hard;//提纯难度 表示要对应的多少基本素材

    private Pureness(String display, char key, int hard) {
        this.display = display;
        this.key = key;
        this.hard = hard;
    }

    public String getDisplay() {
        return display;
    }

    public char getKey() {
        return key;
    }

    public int getHard() {
        return hard;
    }

}
