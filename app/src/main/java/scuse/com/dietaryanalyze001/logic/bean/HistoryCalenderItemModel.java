package scuse.com.dietaryanalyze001.logic.bean;

import com.kelin.calendarlistview.library.BaseCalendarItemModel;

/**
 * Created by Administrator on 2016/8/25.
 */
public class HistoryCalenderItemModel extends BaseCalendarItemModel {

    private int mealCount;
    private boolean isReached;

    public int getMealCount() {
        return mealCount;
    }

    public void setMealCount(int mealCount) {
        this.mealCount = mealCount;
    }

    public boolean isReached() {
        return isReached;
    }

    public void setReached(boolean reached) {
        isReached = reached;
    }
}
