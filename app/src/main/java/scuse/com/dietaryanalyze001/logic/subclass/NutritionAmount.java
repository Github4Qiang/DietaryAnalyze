package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Polylanger on 2016/10/14.
 */
@AVClassName("NutritionAmount")
public class NutritionAmount extends AVObject {

    public static final String TAG = "NutritionAmount";
    public static final String NA_NUTRITION = "nutrition";
    public static final String NA_AMOUNT = "amount";


    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public NutritionAmount(Parcel in) {
        super(in);
    }

    public NutritionAmount() {

    }

    public int getAmount() {
        return this.getInt(NA_AMOUNT);
    }

    public Nutrition getNutrition() {
        return this.getAVObject(NA_NUTRITION);
    }

}
