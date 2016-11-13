package scuse.com.dietaryanalyze001.logic.assess.kinds;

import android.util.Log;

import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.bean.AssessBean;

public class CerealAssess extends ComponentAssess {

    private static final String TAG = "CerealAssess";

    public CerealAssess(int energy) {
        super(energy);
    }

    @Override
    public void fillAssessBeans() {
        switch (level) {
            case Constant.LEVEL_1:
                addBean2Level(0, new AssessBean(0, -8));
                addBean2Level(0, new AssessBean(225, 0));
                addBean2Level(0, new AssessBean(500, 12));
                break;
            case Constant.LEVEL_2:
                addBean2Level(1, new AssessBean(0, -10));
                addBean2Level(1, new AssessBean(250, 0));
                addBean2Level(1, new AssessBean(525, 12));
                break;
            case Constant.LEVEL_3:
                addBean2Level(2, new AssessBean(0, -12));
                addBean2Level(2, new AssessBean(300, 0));
                addBean2Level(2, new AssessBean(575, 12));
                break;
            case Constant.LEVEL_4:
                addBean2Level(3, new AssessBean(0, -12));
                addBean2Level(3, new AssessBean(300, 0));
                addBean2Level(3, new AssessBean(575, 12));
                break;
            case Constant.LEVEL_5:
                addBean2Level(4, new AssessBean(0, -12));
                addBean2Level(4, new AssessBean(250, 0));
                addBean2Level(4, new AssessBean(625, 12));
                break;
            case Constant.LEVEL_6:
                addBean2Level(5, new AssessBean(0, -12));
                addBean2Level(5, new AssessBean(400, 0));
                addBean2Level(5, new AssessBean(675, 12));
                break;
            case Constant.LEVEL_7:
                addBean2Level(6, new AssessBean(0, -12));
                addBean2Level(6, new AssessBean(450, 0));
                addBean2Level(6, new AssessBean(725, 12));
                break;
            default:
                Log.d(TAG, "fillAssessBeans: set energy failed, be setted as : " + level);
                break;
        }
    }
}
