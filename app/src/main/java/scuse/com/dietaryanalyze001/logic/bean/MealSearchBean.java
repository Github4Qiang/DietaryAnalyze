package scuse.com.dietaryanalyze001.logic.bean;

import org.parceler.Parcel;

/**
 * Created by Administrator on 2016/8/18.
 */

@Parcel
public class MealSearchBean {

    public String imgUrl;
    public String mealName;
    public String detailUrl;

    public MealSearchBean() {
    }

    public MealSearchBean(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    @Override
    public String toString() {
        return "detailUrl='" + detailUrl + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", mealName='" + mealName + '\'';
    }
}
