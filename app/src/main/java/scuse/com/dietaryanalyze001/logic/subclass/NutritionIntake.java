package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Polylanger on 2016/10/15.
 */
@AVClassName("NutritionIntake")
public class NutritionIntake extends AVObject {

    public static final String TAG = "NutritionIntake";
    public static final String NI_NUTRITION = "nutrition";
    public static final String NI_INTAKE = "intake";


    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public NutritionIntake(Parcel in) {
        super(in);
    }

    public NutritionIntake() {

    }

    public void putNutrition(Nutrition nutrition) {
        this.put(NI_NUTRITION, nutrition);
    }

    public void putIntake(double intake) {
        this.put(NI_INTAKE, intake);
    }

    public Nutrition getNutrition() {
        return this.getAVObject(NI_NUTRITION);
    }

    public double getIntake() {
        return this.getInt(NI_INTAKE);
    }

    public double getNutritionIntake(String key) {
        return this.getIntake()
                * this.getNutrition().getDouble(key) / 100
                * this.getNutrition().getEdiblePart() / 100;
    }
}
