/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.bukkit.alchemy;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-9-5
 */
public class Data {
    public static Main Plugin;

    private static final Map<String, Alchemy> RegisteredAlchemy = new HashMap<>();
    
    public static Alchemy getAlchemy(String AlchemyID){
        return RegisteredAlchemy.get(AlchemyID);
    }
}
