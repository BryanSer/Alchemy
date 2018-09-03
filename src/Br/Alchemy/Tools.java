/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public class Tools {

    public static String decodeColorCode(String s) {
        return s.replaceAll("§", "");
    }

    public static String encodeColorCode(String s) {
        char[] c = s.toCharArray();
        StringBuilder v = new StringBuilder();
        for (char d : c) {
            if (d == '§') {
                continue;
            }
            v.append('§').append(d);
        }
        return v.toString();
    }
}
