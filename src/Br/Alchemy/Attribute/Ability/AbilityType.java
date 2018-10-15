/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy.Attribute.Ability;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 * @since 2018-10-11
 */
public enum AbilityType {
    ;

    public static final int MAX_ABILITY_AMOUNT = 5;
    private String DisplayName;
    private Ability[] Abilities = new Ability[MAX_ABILITY_AMOUNT];
    private int Index;

    private AbilityType(String dis, int index) {
        this.DisplayName = dis;
        this.Index = index;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public Ability[] getAbilities() {
        return Abilities;
    }

    public int getIndex() {
        return Index;
    }
}
