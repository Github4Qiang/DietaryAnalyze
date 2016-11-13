package scuse.com.dietaryanalyze001.logic.assess.kinds;

import java.util.ArrayList;
import java.util.HashMap;

import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.bean.AssessBean;

/**
 * Created by Polylanger on 2016/10/20.
 */

public abstract class ComponentAssess {

    protected int level;
    protected HashMap<Integer, ArrayList<AssessBean>> levelScores;

    public ComponentAssess(int energy) {
        // 通过摄取量获得能量等级
        level = getEnergyLevel(energy);
        // 初始化 level/quantity:score 数据
        levelScores = new HashMap<>();
        for (int i : Constant.ENERGY_LEVEL) {
            levelScores.put(i, new ArrayList<AssessBean>());
        }
        // 填充评分数据（子类中实现该方法）, 只填充对应能量等级的数据
        fillAssessBeans();
    }

    // 根据用户能量的标准摄入量获取其对应的能量等级
    public int getEnergyLevel(int energy) {
        int[] levels = Constant.ENERGY_LEVEL;
        for (int i = levels.length - 1; i >= 0; i--) {
            if (energy > levels[i]) {
                return levels[i];
            }
        }
        // energy 比所有的能量等级都小，则返回虽小的等级
        return levels[0];
    }

    protected void addBean2Level(int index, AssessBean bean) {
        levelScores.get(Constant.ENERGY_LEVEL[index]).add(bean);
    }

    public int assess(int quantity) {
        // 获得对应能量等级的 质量：得分 列表
        ArrayList<AssessBean> scoreList = levelScores.get(level);
        AssessBean first = null, second = null;
        for (int i = 0; i < scoreList.size() - 1; i++) {
            first = scoreList.get(i);
            second = scoreList.get(i + 1);
            if (first.getQuantity() <= quantity && quantity <= second.getQuantity()) {
                // 两个两个对比，直到找到 quantity 对应的区间
                // 找到后跳出循环，first/second 记载着区间的两端
                break;
            }
        }
        if (first != null && second != null) {
            // 利用 quantity/score 之间的等比关系求出评分
            return (quantity - first.getQuantity()) * (second.getScore() - first.getScore())
                    / (second.getQuantity() - first.getQuantity()) + first.getScore();
        }
        return second.getScore();
    }

    public abstract void fillAssessBeans();
}

