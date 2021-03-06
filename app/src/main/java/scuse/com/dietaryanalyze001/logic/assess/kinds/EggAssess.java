package scuse.com.dietaryanalyze001.logic.assess.kinds;

import android.util.Log;

import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.bean.AssessBean;

public class EggAssess extends ComponentAssess {

    private static final String TAG = "EggAssess";

    public EggAssess(int energy) {
        super(energy);
    }

    @Override
    public void fillAssessBeans() {
        switch (level) {
            case Constant.LEVEL_1:
                addBean2Level(0, new AssessBean(0, -4));
                addBean2Level(0, new AssessBean(25, 0));
                addBean2Level(0, new AssessBean(50, 2));
                addBean2Level(0, new AssessBean(75, 4));
                break;
            case Constant.LEVEL_2:
                addBean2Level(1, new AssessBean(0, -4));
                addBean2Level(1, new AssessBean(25, 0));
                addBean2Level(1, new AssessBean(50, 2));
                addBean2Level(1, new AssessBean(75, 4));
                break;
            case Constant.LEVEL_3:
                addBean2Level(2, new AssessBean(0, -4));
                addBean2Level(2, new AssessBean(25, 0));
                addBean2Level(2, new AssessBean(50, 2));
                addBean2Level(2, new AssessBean(75, 4));
                break;
            case Constant.LEVEL_4:
                addBean2Level(3, new AssessBean(0, -4));
                addBean2Level(3, new AssessBean(25, 0));
                addBean2Level(3, new AssessBean(50, 2));
                addBean2Level(3, new AssessBean(75, 4));
                break;
            case Constant.LEVEL_5:
                addBean2Level(4, new AssessBean(0, -4));
                addBean2Level(4, new AssessBean(25, 0));
                addBean2Level(4, new AssessBean(50, 2));
                addBean2Level(4, new AssessBean(75, 4));
                break;
            case Constant.LEVEL_6:
                addBean2Level(5, new AssessBean(0, -4));
                addBean2Level(5, new AssessBean(25, 0));
                addBean2Level(5, new AssessBean(50, 2));
                addBean2Level(5, new AssessBean(75, 4));
                break;
            case Constant.LEVEL_7:
                addBean2Level(6, new AssessBean(0, -4));
                addBean2Level(6, new AssessBean(25, 0));
                addBean2Level(6, new AssessBean(50, 2));
                addBean2Level(6, new AssessBean(75, 4));
                break;
            default:
                Log.d(TAG, "fillAssessBeans: set energy failed, be setted as : " + level);
                break;
        }
    }
}
