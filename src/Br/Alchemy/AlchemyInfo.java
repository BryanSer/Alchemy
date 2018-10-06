/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.bukkit.ChatColor;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-9-4
 */
public class AlchemyInfo {

    private String AlchemyID;
    private Map<Essential, Integer> ContainsEssentials = new EnumMap<>(Essential.class);

    public AlchemyInfo(String s) {
        String[] var = s.split("-");
        this.AlchemyID = var[0];
        if (!var[2].equals("null")) {
            String m[] = var[2].split(",");
            for (String e : m) {
                ContainsEssentials.put(Essential.getEssential(e.charAt(0)), Tools.toThiFNum(e.split(";")[1]));
            }
        }
    }

    public String toLore() {
        StringBuffer sb = new StringBuffer(Alchemy.ALCHEMY_KEY);
        sb.append("|");
        if (ContainsEssentials.isEmpty()) {
            sb.append("null");
        } else {
            for (Map.Entry<Essential, Integer> e : ContainsEssentials.entrySet()) {
                sb.append(e.getKey().getKey()).append(":").append(Tools.toThiF(e.getValue())).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        return Tools.encodeColorCode(sb.toString());
    }

    public static AlchemyInfo toInfo(List<String> lore) {
        for (String s : lore) {
            s = ChatColor.stripColor(s);
            if (s.startsWith(Alchemy.ALCHEMY_KEY)) {
                s = s.split("\\|", 2)[1];
                return new AlchemyInfo(s);
            }
        }
        return null;
    }

    public String getAlchemyID() {
        return AlchemyID;
    }

    public Map<Essential, Integer> getContainsEssentials() {
        return ContainsEssentials;
    }
    
    public Alchemy getAlchemy(){
        return Data.getAlchemy(this.AlchemyID);
    }

}
