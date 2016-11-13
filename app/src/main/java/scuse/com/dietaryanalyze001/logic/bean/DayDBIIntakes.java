package scuse.com.dietaryanalyze001.logic.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVObject;

import java.util.HashMap;

import scuse.com.dietaryanalyze001.logic.assess.Constant;

/**
 * Created by Polylanger on 2016/10/20.
 */
public class DayDBIIntakes {

    public static final String TAG = "DayDBIIntakes";

    public static final String CEREALS = "Cereals";
    public static final String VEGETABLE = "Vegetable";
    public static final String FRUITS = "Fruits";
    public static final String DAIRY = "Dairy";
    public static final String SOYBEAN = "Soybean";
    public static final String MEAT = "Meat";
    public static final String FISH = "Fish";
    public static final String EGG = "Egg";
    public static final String OIL = "Oil";
    public static final String SALT = "Salt";
    public static final String ALCOHOL = "Alcohol";

    public static final String[] KINDS = {CEREALS, VEGETABLE, FRUITS, DAIRY, SOYBEAN, MEAT,
            FISH, EGG, OIL, SALT, ALCOHOL};

    private HashMap<String, Double> kinds;

    public DayDBIIntakes() {
        this.kinds = new HashMap<>();
    }

    public DayDBIIntakes(HashMap<String, Double> kinds) {
        this.kinds = kinds;
    }

    public void putNutrition(int kindNum, double intakes) {
        String kind = Constant.getInstance().FindKind(kindNum);
        if (kinds.containsKey(kind)) {
            kinds.put(kind, kinds.remove(kind) + intakes);
        } else {
            kinds.put(kind, intakes);
        }
    }

    public double getIntake(String kind) {
        return kinds.get(kind);
    }

    public HashMap<String, Double> getKinds() {
        return kinds;
    }

    @Override
    public String toString() {
        return TAG + ": " + kinds.toString();
    }
}
