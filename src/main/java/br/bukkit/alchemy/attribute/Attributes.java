/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package br.bukkit.alchemy.attribute;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.bukkit.alchemy.Data;
import br.kt.welore.attribute.Attribute;
import br.kt.welore.attribute.AttributeInfo;

/**
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-6
 */
public enum Attributes {

    /**
     * 攻击伤害 +
     */
    ATK("附加伤害", null, 1000, 0, false),
    /**
     * 防御力 和实际防御转换公式待定 防御力为负值时增加所受伤害
     */
    DEF("防御力", null, 500, -500, false),
    /**
     * 固定穿透敌方防御力, 最多穿透到0
     */
    FixPenetrate("固定物理穿透", null, 500, 0, false),
    /**
     * 比例防御力穿透 防御小于0时不穿透 防御穿透为负值时 增加敌方护甲参与属性计算
     */
    Penetrate("物理穿透", null, 1, -1, true),
    /**
     * 比例增加伤害 运算在暴击前 负值表示减少造成的伤害
     */
    ATKBoost("攻击增幅", null, 2, -0.5, true),
    /**
     * 负暴击时造成的伤害是原伤害的 0.5倍
     */
    Crit("暴击率", null, 1, -1, true),
    /**
     * 注 暴击默认1.5倍伤害 额外伤害是在此基础上增加 负暴击不计算此属性
     */
    CritExtraDamage("额外暴击伤害", null, 3, 0, true),
    /**
     * 闪避属性 注意 闪避值不是绝对闪避概率 和绝对闪避概率中存在公式转换待定
     */
    Dodge("闪避", null, 500, 0, false),
    /**
     * 攻击精准度, 直接参与绝对闪避率计算
     */
    Accuracy("精准", null, 0.3, -0.25, true),
    Health("生命值", null, 1000, 0, false),
    /**
     * 计算生命值加成后的百分比转换生命值
     */
    HealthBoost("生命值提升", null, 1, -1, true),
    /**
     * 最后加成的伤害
     */
    RealDamage("真实伤害", null, 1000, -1000, false);
    private String DisplayName;
    private boolean Rate = false;
    private double Min;
    private double Max;
    private Attribute<? extends AttributeInfo> attribute;

    public static void init() {
        File file = new File(Data.Plugin.getDataFolder(), "Attributes.yml");
        YamlConfiguration config;
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
                for (Attributes at : Attributes.values()) {
                    config.set(at.name() + ".DisplayName", at.getDisplayName());
                    config.set(at.name() + ".Max", at.getMax());
                    config.set(at.name() + ".Min", at.getMin());
                }
                config.save(file);
            } catch (IOException ex) {
                Logger.getLogger(Attributes.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
        } else {
            config = YamlConfiguration.loadConfiguration(file);
        }
        for (Attributes at : Attributes.values()) {
            at.DisplayName = config.getString(at.name() + ".DisplayName", at.getDisplayName());
            at.Max = config.getDouble(at.name() + ".Max", at.getMax());
            at.Min = config.getDouble(at.name() + ".Min", at.getMin());
        }

    }

    private Attributes(String defname, Attribute<? extends AttributeInfo> attr, double defmax, double defmin, boolean rate) {
        this.DisplayName = defname;
        this.Max = defmax;
        this.Min = defmin;
        this.Rate = rate;
        this.attribute = attr;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public boolean isRate() {
        return Rate;
    }

    public double getMin() {
        return Min;
    }

    public double getMax() {
        return Max;
    }

    public double dealValue(double value) {
        value = value > this.Max ? this.Max : value;
        return value < this.Min ? this.Min : value;
    }

}
