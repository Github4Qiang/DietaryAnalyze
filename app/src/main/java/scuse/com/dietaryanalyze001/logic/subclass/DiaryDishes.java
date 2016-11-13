package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

import java.util.Date;

/**
 * Created by Polylanger on 2016/9/26.
 */
@AVClassName("DiaryDishes")
public class DiaryDishes extends AVObject {

    public static final String DIARY_USER = "user";
    public static final String DIARY_AMOUNT = "amount";
    public static final String DIARY_UNIT = "unit";
    public static final String DIARY_TIME = "time";
    public static final String DIARY_DISH = "dish";
    public static final String DIARY_DATE = "date";

    public static final Parcelable.Creator CREATOR = AVObject.AVObjectCreator.instance;

    public DiaryDishes(Parcel in) {
        super(in);
    }

    public DiaryDishes() {

    }

    public int getAmount() {
        return this.getInt(DIARY_AMOUNT);
    }

    public String getUnit() {
        return this.getString(DIARY_UNIT);
    }

    public String getTime() {
        return this.getString(DIARY_TIME);
    }

    public Dishes getDish() {
        return this.getAVObject(DIARY_DISH);
    }

    public void putUser(String user) {
        this.put(DIARY_USER, user);
    }

    public void putAmount(int amount) {
        this.put(DIARY_AMOUNT, amount);
    }

    public void putUnit(String unit) {
        this.put(DIARY_UNIT, unit);
    }

    public void putTime(String time) {
        this.put(DIARY_TIME, time);
    }

    public void putDish(Dishes dish) {
        this.put(DIARY_DISH, dish);
    }

    public void putDate(Date date) {
        this.put(DIARY_DATE, date);
    }

    // 获取该菜品的摄入量（通过 amount 和 unit 属性共同构成）
    // 要记得改！！！！！！！！！
    public double getGram() {
        return getAmount();
    }
}
