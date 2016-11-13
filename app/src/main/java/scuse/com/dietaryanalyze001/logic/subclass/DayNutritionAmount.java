package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;

import java.util.Date;

/**
 * Created by Polylanger on 2016/10/15.
 */
@AVClassName("DayNutritionAmount")
public class DayNutritionAmount extends AVObject {

    public static final String TAG = "DayNutritionAmount";
    public static final String DAY_USER = "user";
    public static final String DAY_NTRT_INTAKE = "ntrtIntake";
    public static final String DAY_DATE = "date";


    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public DayNutritionAmount(Parcel in) {
        super(in);
    }

    public DayNutritionAmount() {

    }

    public void putUser(String user) {
        this.put(DAY_USER, user);
    }

    public void putDate(Date date) {
        this.put(DAY_DATE, date);
    }

    public AVRelation getNtrtIntakes() {
        return this.getRelation(DAY_NTRT_INTAKE);
    }

    public void addNtrtAmount(NutritionIntake ntrtIntake) {
        getNtrtIntakes().add(ntrtIntake);
    }

    public void clearAllNtrtAmount() {
        this.put(DAY_NTRT_INTAKE, new AVRelation<NutritionAmount>());
    }

}
