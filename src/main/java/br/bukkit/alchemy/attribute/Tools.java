/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.bukkit.alchemy.attribute;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public class Tools {

    private static double ROOT_2 = Math.sqrt(2);
    private static double A = -9.523809e-7;
    private static double B = 1.9523809e-3;

    public static double calcDodge(double dodge) {
        if (dodge <= 0) {
            return 0;
        }
        return -Math.pow(ROOT_2, -dodge * 0.01) + 1;
    }

    public static double calcDef(double def) {
        if (def >= 1000) {
            return 1;
        }
        return A * def * def + B * def;
    }
}
