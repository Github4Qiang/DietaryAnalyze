package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Polylanger on 2016/10/17.
 */
@AVClassName("EnergyStandard")
public class EnergyStandard extends AVObject {
    public static final String TAG = "EnergyStandard";
    public static final String ENG_STDRD_ENERGY = "energy";
    public static final String ENG_STDRD_PROTEIN = "protein";
    public static final String ENG_STDRD_FAT = "fat";
    public static final String ENG_STDRD_CARBOHYDRATE = "carbohydrate";

    public static final String ENG_STDRD_BREAKFAST = "早餐";
    public static final String ENG_STDRD_LUNCH = "午餐";
    public static final String ENG_STDRD_DINNER = "晚餐";

    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public EnergyStandard(Parcel in) {
        super(in);
    }

    public EnergyStandard() {

    }

    public int getTimeEnergy(String time) {
        switch (time) {
            case ENG_STDRD_PROTEIN:
                return (int) (getEnergy() * 0.3);
            case ENG_STDRD_CARBOHYDRATE:
                return (int) (getEnergy() * 0.4);
            case ENG_STDRD_FAT:
                return (int) (getEnergy() * 0.3);
            default:
                Log.e(TAG, "getTimeEnergy: " + time);
                break;
        }
        return (int) (getEnergy() * 0.3);
    }

    public void putEnergy(int energy) {
        this.put(ENG_STDRD_ENERGY, energy);
    }

    public void putProtein(int protein) {
        this.put(ENG_STDRD_PROTEIN, protein);
    }

    public void putFat(int fat) {
        this.put(ENG_STDRD_FAT, fat);
    }

    public void putCarbohydrate(int carbohydrate) {
        this.put(ENG_STDRD_CARBOHYDRATE, carbohydrate);
    }

    public int getEnergy() {
        return this.getInt(ENG_STDRD_ENERGY);
    }

    public int getProtein() {
        return this.getInt(ENG_STDRD_PROTEIN);
    }

    public int getFat() {
        return this.getInt(ENG_STDRD_FAT);
    }

    public int getCarbohydrate() {
        return this.getInt(ENG_STDRD_CARBOHYDRATE);
    }

}
