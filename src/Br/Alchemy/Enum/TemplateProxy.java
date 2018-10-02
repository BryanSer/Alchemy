/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */

package Br.Alchemy.Enum;

import Br.API.Data.ProxyUtil;
import Br.API.Data.ProxyUtil.ProxyInfo;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-9-5
 */
@ProxyInfo("Property.Config")
public class TemplateProxy implements ProxyUtil{
    @ProxyScript(file = "Property",fromJarFile = "Property.js",function = "writeAlchemyPureness")
    public static ProxiedScript<String> writeAlchemyPureness;
    @ProxyScript(file = "Property",fromJarFile = "Property.js",function = "writeAlchemyEssential")
    public static ProxiedScript<String> writeAlchemyEssential;
}
