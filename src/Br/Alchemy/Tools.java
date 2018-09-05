/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy;

import java.util.Arrays;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public class Tools {

    private static final char[] ThiFNum = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final int Length = ThiFNum.length;

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

    public static String toThiF(int num) {
        StringBuffer sb = new StringBuffer();
        boolean m = false;
        if (num == 0) {
            return "0";
        } else if (num < 0) {
            m = true;
            num = -num;
        }
        for (; num > 0; num /= Tools.Length) {
            sb.append(Tools.ThiFNum[num % Tools.Length]);
        }
        if (m) {
            sb.append('-');
        }
        sb.reverse();
        return sb.toString();
    }

    public static int toThiFNum(String str) {
        boolean m = str.startsWith("-");
        str = str.replaceFirst("-", "");
        char[] c = str.toCharArray();
        int num = 0;
        for (int i = c.length - 1, pow = 1; i >= 0; i--, pow *= Tools.Length) {
            num += (getThiFNum(c[i]) * pow);
        }
        return m ? -num : num;
    }

    private static int getThiFNum(char c) {
        return Arrays.binarySearch(Tools.ThiFNum, c);
    }
}
