/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Enum;

import Br.Alchemy.AlchemyInfo;
import java.util.function.Function;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-9-5
 */
public enum Property {
    Pureness(ai -> TemplateProxy.writeAlchemyPureness.proxy(ai));
    private Function<AlchemyInfo, String> Template;

    private Property(Function<AlchemyInfo, String> Template) {
        this.Template = Template;
    }

    public Function<AlchemyInfo, String> getTemplate() {
        return Template;
    }

}
