package scuse.com.dietaryanalyze001.logic.bean;

import org.parceler.Parcel;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/8/18.
 */
@Parcel
public class MealDetailBean extends MealSearchBean {

    public HashMap<String, String> mealComponent;

    public MealDetailBean() {
        mealComponent = new HashMap<>();
    }

    public MealDetailBean(String detailUrl) {
        super(detailUrl);
    }

    public MealDetailBean(HashMap<String, String> mealComponent) {
        this.mealComponent = mealComponent;
    }

    public HashMap<String, String> getMealComponent() {
        return mealComponent;
    }

    public void setMealComponent(HashMap<String, String> mealComponent) {
        this.mealComponent = mealComponent;
    }

    @Override
    public String toString() {
        return "mealComponent=" + mealComponent + ", " + super.toString();
    }
}
