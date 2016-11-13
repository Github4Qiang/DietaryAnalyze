package scuse.com.dietaryanalyze001.logic.bean;

import scuse.com.dietaryanalyze001.logic.subclass.DiaryDishes;

public class DiaryMealBean {

    private String time;
    private DiaryDishes diaryDish;

    public DiaryMealBean(final String time, DiaryDishes dish) {
        this.time = time;
        this.diaryDish = dish;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public DiaryDishes getDiaryDish() {
        return diaryDish;
    }

    public void setDiaryDish(DiaryDishes diaryDish) {
        this.diaryDish = diaryDish;
    }

    @Override
    public String toString() {
        return "<" + time + ": " + diaryDish + ">";
    }
}
