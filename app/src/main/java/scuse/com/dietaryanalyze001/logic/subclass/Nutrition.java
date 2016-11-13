package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by Polylanger on 2016/10/14.
 */
@AVClassName("Nutrition")
public class Nutrition extends AVObject {

    public static final String TAG = "Nutrition";
    public static final String NUTRITION_NAME = "name";
    public static final String NUTRITION_VITAMIN_A = "vitaminA";
    public static final String NUTRITION_VITAMIN_B1 = "vitaminB1";
    public static final String NUTRITION_VITAMIN_B2 = "vitaminB2";
    public static final String NUTRITION_VITAMIN_C = "vitaminC";
    public static final String NUTRITION_VITAMIN_E = "vitaminE";
    public static final String NUTRITION_CA = "Ca";
    public static final String NUTRITION_EDIBLE_PART = "ediblePart";
    public static final String NUTRITION_FAT = "fat";
    public static final String NUTRITION_ENERGY = "energy";
    public static final String NUTRITION_CHOLESTEROL = "cholesterol";
    public static final String NUTRITION_NA = "Na";
    public static final String NUTRITION_NICOTINIC_ACID = "nicotinicAcid";
    public static final String NUTRITION_KIND = "kind";
    public static final String NUTRITION_DIETARY_FIBER = "dietaryFiber";
    public static final String NUTRITION_PROTEIN = "protein";
    public static final String NUTRITION_FE = "Fe";
    public static final String NUTRITION_WATER = "water";
    public static final String NUTRITION_CARBOHYDRATE = "carbohydrate";


    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public Nutrition(Parcel in) {
        super(in);
    }

    public Nutrition() {

    }

    public double getProtein() {
        return this.getDouble(NUTRITION_PROTEIN);
    }

    public double getCarb() {
        return this.getDouble(NUTRITION_CARBOHYDRATE);
    }

    public double getFat() {
        return this.getDouble(NUTRITION_FAT);
    }

    public double getEdiblePart() {
        return this.getDouble(NUTRITION_EDIBLE_PART);
    }

    public int getKind() {
        return this.getInt(NUTRITION_KIND);
    }

}
