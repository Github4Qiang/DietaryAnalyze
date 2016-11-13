package scuse.com.dietaryanalyze001.logic.assess;

import android.app.Activity;

import java.util.HashMap;

import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.bean.DayDBIIntakes;
import scuse.com.dietaryanalyze001.logic.subclass.Nutrition;

/**
 * Created by Polylanger on 2016/10/17.
 */
public class Constant {

    public static final String WORK_HEAVY = "高强度体力劳动";
    public static final String WORK_AVERAGE = "中度体力劳动";
    public static final String WORK_LIGHT = "轻度体力劳动";
    public static final String WORK_OFFICE = "脑力劳动";
    public static final String WORK_NONE = "无工作";

    public static final String ACTIVE_VERY = "较少 (包括运动量)";
    public static final String ACTIVE_QUITE = "一般 (包括运动量)";
    public static final String ACTIVE_NOT = "较多 (包括运动量)";

    public static final String LABOUR_HEAVY = "Heavy intensity";
    public static final String LABOUR_MIDDLE = "Middle intensity";
    public static final String LABOUR_LIGHT = "Light intensity";

    public static final String GENDER_MALE = "Male";
    public static final String GENDER_FEMALE = "Female";

    public static final double DAY_KILO_PROTEIN = 1.16;
    public static final double DAY_KILO_FAT = 1.05;
    public static final double DAY_KILO_CARB = 5.71;
    public static final double DAY_KILO_ENERGY = 38.09;

    public static final double BMI_IDEAL_FLOOR = 18.5;
    public static final double BMI_IDEAL_UPPER = 24.9;

    public static final int LEVEL_1 = 1601;
    public static final int LEVEL_2 = 1804;
    public static final int LEVEL_3 = 1996;
    public static final int LEVEL_4 = 2199;
    public static final int LEVEL_5 = 2402;
    public static final int LEVEL_6 = 2605;
    public static final int LEVEL_7 = 2796;
    public static final int[] ENERGY_LEVEL = {LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6, LEVEL_7};

    public static final HashMap<Integer, String> COMPONENT_KIND = new HashMap<>();
    public static final HashMap<String, Integer> ENERGY_TRANSTION = new HashMap<>();
    public static final HashMap<String, String> KIND_CHINESE = new HashMap<>();

    public static Constant instance;

    public static Constant getInstance() {
        if (instance == null) {
            COMPONENT_KIND.put(11, DayDBIIntakes.CEREALS);
            COMPONENT_KIND.put(21, DayDBIIntakes.SOYBEAN);
            COMPONENT_KIND.put(22, DayDBIIntakes.SOYBEAN);
            COMPONENT_KIND.put(31, DayDBIIntakes.VEGETABLE);
            COMPONENT_KIND.put(32, DayDBIIntakes.VEGETABLE);
            COMPONENT_KIND.put(33, DayDBIIntakes.VEGETABLE);
            COMPONENT_KIND.put(34, DayDBIIntakes.VEGETABLE);
            COMPONENT_KIND.put(41, DayDBIIntakes.FRUITS);
            COMPONENT_KIND.put(42, DayDBIIntakes.SOYBEAN); // 瓜子一类，待定分类，暂定水果
            COMPONENT_KIND.put(51, DayDBIIntakes.MEAT);
            COMPONENT_KIND.put(52, DayDBIIntakes.MEAT);
            COMPONENT_KIND.put(53, DayDBIIntakes.DAIRY);
            COMPONENT_KIND.put(54, DayDBIIntakes.EGG);
            COMPONENT_KIND.put(61, DayDBIIntakes.FISH);
            COMPONENT_KIND.put(62, DayDBIIntakes.FISH);
            COMPONENT_KIND.put(63, DayDBIIntakes.FISH);
            COMPONENT_KIND.put(71, DayDBIIntakes.CEREALS); // 糕点一类，待定分类，暂定谷物
            COMPONENT_KIND.put(81, DayDBIIntakes.OIL);
            COMPONENT_KIND.put(82, DayDBIIntakes.SALT);
            COMPONENT_KIND.put(83, DayDBIIntakes.ALCOHOL); // 零食糖类，待定分类，暂定酒精（对待结果一致）
            COMPONENT_KIND.put(84, DayDBIIntakes.SALT);
            COMPONENT_KIND.put(85, DayDBIIntakes.ALCOHOL); // 饮料一类，待定分类，暂定酒精（对待态度一致）
            COMPONENT_KIND.put(86, DayDBIIntakes.ALCOHOL);

            KIND_CHINESE.put(DayDBIIntakes.CEREALS, "谷类");
            KIND_CHINESE.put(DayDBIIntakes.SOYBEAN, "豆类");
            KIND_CHINESE.put(DayDBIIntakes.VEGETABLE, "蔬菜");
            KIND_CHINESE.put(DayDBIIntakes.FRUITS, "水果");
            KIND_CHINESE.put(DayDBIIntakes.ALCOHOL, "酒精");
            KIND_CHINESE.put(DayDBIIntakes.MEAT, "畜肉、禽肉");
            KIND_CHINESE.put(DayDBIIntakes.DAIRY, "奶类");
            KIND_CHINESE.put(DayDBIIntakes.EGG, "蛋类");
            KIND_CHINESE.put(DayDBIIntakes.FISH, "水产品");
            KIND_CHINESE.put(DayDBIIntakes.OIL, "食用油");
            KIND_CHINESE.put(DayDBIIntakes.SALT, "调味品");

            ENERGY_TRANSTION.put(Nutrition.NUTRITION_PROTEIN, 4);
            ENERGY_TRANSTION.put(Nutrition.NUTRITION_CARBOHYDRATE, 4);
            ENERGY_TRANSTION.put(Nutrition.NUTRITION_FAT, 9);
            ENERGY_TRANSTION.put(Nutrition.NUTRITION_ENERGY, 1);
            instance = new Constant();
        }
        return instance;
    }

    public String FindKind(int kind) {
        return COMPONENT_KIND.get(kind);
    }

    public String getKindChinese(String kind) {
        return KIND_CHINESE.get(kind);
    }

    public int Ntrt2Energy(String nutrition) {
        return ENERGY_TRANSTION.get(nutrition);
    }

}
