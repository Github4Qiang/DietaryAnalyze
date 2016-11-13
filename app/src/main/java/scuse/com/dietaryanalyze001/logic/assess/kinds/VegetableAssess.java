package scuse.com.dietaryanalyze001.logic.assess.kinds;

import android.util.Log;

import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.bean.AssessBean;

/**
 * Created by Polylanger on 2016/10/20.
 */
public class VegetableAssess extends ComponentAssess {

    private static final String TAG = "VegetableAssess";

    public VegetableAssess(int energy) {
        super(energy);
    }

    @Override
    public void fillAssessBeans() {
        switch (level) {
            case Constant.LEVEL_1:
                addBean2Level(0, new AssessBean(0, -6));
                addBean2Level(0, new AssessBean(300, 0));
                break;
            case Constant.LEVEL_2:
                addBean2Level(1, new AssessBean(0, -6));
                addBean2Level(1, new AssessBean(300, 0));
                break;
            case Constant.LEVEL_3:
                addBean2Level(2, new AssessBean(0, -6));
                addBean2Level(2, new AssessBean(350, 0));
                break;
            case Constant.LEVEL_4:
                addBean2Level(3, new AssessBean(0, -6));
                addBean2Level(3, new AssessBean(400, 0));
                break;
            case Constant.LEVEL_5:
                addBean2Level(4, new AssessBean(0, -6));
                addBean2Level(4, new AssessBean(450, 0));
                break;
            case Constant.LEVEL_6:
                addBean2Level(5, new AssessBean(0, -6));
                addBean2Level(5, new AssessBean(500, 0));
                break;
            case Constant.LEVEL_7:
                addBean2Level(6, new AssessBean(0, -6));
                addBean2Level(6, new AssessBean(500, 0));
                break;
            default:
                Log.d(TAG, "fillAssessBeans: set energy failed, be setted as : " + level);
                break;
        }
    }
}
