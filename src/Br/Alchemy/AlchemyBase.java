/*
 * 开发者:Bryan_lzh
 * QQ:390807154
 * 保留一切所有权
 * 若为Bukkit插件 请前往plugin.yml查看剩余协议
 */
package Br.Alchemy;

import java.util.Random;
import org.bukkit.entity.Player;

/**
 *
 * @author Bryan_lzh
 * @version 1.0
 */
public abstract class AlchemyBase {

    public static final String ALCHEMY_KEY = "ALCHEMY";

    private String Name;//内部名
    private String DisplayName;//显示名
    private Pureness MasPureness;//最大纯度
    private PhysicalState State;
    private int RandomSeed;

    public AlchemyBase(String Name, String DisplayName, Pureness MasPureness, PhysicalState State) {
        this.Name = Name;
        this.DisplayName = DisplayName;
        this.MasPureness = MasPureness;
        this.State = State;
        this.RandomSeed = Name.hashCode();
    }

    public AlchemyBase(String Name, String DisplayName, Pureness MasPureness, PhysicalState State, int RandomSeed) {
        this.Name = Name;
        this.DisplayName = DisplayName;
        this.MasPureness = MasPureness;
        this.State = State;
        this.RandomSeed = RandomSeed;
    }

    public abstract int calcTime(Pureness pure, PhysicalState state, float weight);

    public abstract int calcPower(Pureness pure, PhysicalState state, float weight);

    public abstract void onEffect(Player p, int time, int power);

}
