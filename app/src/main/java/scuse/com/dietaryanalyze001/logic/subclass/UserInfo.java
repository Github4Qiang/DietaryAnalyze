package scuse.com.dietaryanalyze001.logic.subclass;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RefreshCallback;

import java.util.Date;

import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.assess.DRIsStandard;
import scuse.com.dietaryanalyze001.logic.assess.LabourIntensity;
import scuse.com.dietaryanalyze001.logic.utils.DateTimeUtils;

/**
 * Created by Polylanger on 2016/9/19.
 */
@AVClassName("UserInfo")
public class UserInfo extends AVObject {

    private static final String TAG = "UserInfo";
    private static final String INFO_GENDER = "gender";
    private static final String INFO_DOB = "dob";
    private static final String INFO_HEIGHT = "height";
    private static final String INFO_WEIGHT = "weight";
    private static final String INFO_SLEEP_HOURS = "sleepHours";
    private static final String INFO_JOB_KIND = "jobKind";
    private static final String INFO_WORK_HOURS = "workHours";
    private static final String INFO_WORK_DAYS = "workDays";
    private static final String INFO_FREE_INTENSITY = "freeIntensity";
    private static final String INFO_ENERGY_STANDARD = "energyStandard";
    private static final String INFO_GOAL_ENERGY_STANDARD = "goalEnergyStandard";

    public static final Parcelable.Creator CREATOR = AVObjectCreator.instance;

    private static UserInfo ourInstance;
    private Date currentDate;

    public static UserInfo getInstance() {
        if (ourInstance == null) {
            ourInstance = new UserInfo();
        }
//        Log.d(TAG, "getInstance: " + ourInstance);
        return ourInstance;
    }

    public static UserInfo setInstanceById(String id, RefreshCallback<AVObject> callback) {
        try {
            ourInstance = UserInfo.createWithoutData(UserInfo.class, id);
            ourInstance.refreshInBackground(INFO_ENERGY_STANDARD, callback);
        } catch (AVException e) {
            e.printStackTrace();
        }
        return ourInstance;
    }

    public UserInfo(Parcel in) {
        super(in);
    }

    public UserInfo() {
        if (this.currentDate == null) {
            this.currentDate = new Date();
        }
    }

    public void putGender(String gender) {
        ourInstance.put(INFO_GENDER, gender);
    }

    public String getGender() {
        return ourInstance.getString(INFO_GENDER);
    }

    public void putDob(Date dob) {
        ourInstance.put(INFO_DOB, dob);
    }

    public Date getDob() {
        return ourInstance.getDate(INFO_DOB);
    }

    public void putHeight(Number height) {
        ourInstance.put(INFO_HEIGHT, height);
    }

    public int getHeight() {
        return ourInstance.getInt(INFO_HEIGHT);
    }

    public void putWeight(Number weight) {
        ourInstance.put(INFO_WEIGHT, weight);
    }

    public double getWeight() {
        return ourInstance.getDouble(INFO_WEIGHT);
    }

    public void putSleepHours(Number hours) {
        ourInstance.put(INFO_SLEEP_HOURS, hours);
    }

    public int getSleepHours() {
        return ourInstance.getInt(INFO_SLEEP_HOURS);
    }

    public void putJobKind(String jobKind) {
        ourInstance.put(INFO_JOB_KIND, jobKind);
    }

    public String getJobKind() {
        return ourInstance.getString(INFO_JOB_KIND);
    }

    public void putWorkHours(Number hours) {
        ourInstance.put(INFO_WORK_HOURS, hours);
    }

    public int getWorkHours() {
        return ourInstance.getInt(INFO_WORK_HOURS);
    }

    public void putWorkDays(Number days) {
        ourInstance.put(INFO_WORK_DAYS, days);
    }

    public int getWorkDays() {
        return ourInstance.getInt(INFO_WORK_DAYS);
    }

    public void putFreeIntensity(String intensity) {
        ourInstance.put(INFO_FREE_INTENSITY, intensity);
    }

    public String getFreeIntensity() {
        return ourInstance.getString(INFO_FREE_INTENSITY);
    }

    public void putGoalEnergyStandard(EnergyStandard standard) {
        ourInstance.put(INFO_GOAL_ENERGY_STANDARD, standard);
    }

    public EnergyStandard getGoalEnergyStandard() {
        return ourInstance.getAVObject(INFO_GOAL_ENERGY_STANDARD);
    }

    public void putEnergyStandard(EnergyStandard standard) {
        ourInstance.put(INFO_ENERGY_STANDARD, standard);
    }

    public EnergyStandard getEnergyStandard() {
        return ourInstance.getAVObject(INFO_ENERGY_STANDARD);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DRIsStandard.DRIsCell) {
            DRIsStandard.DRIsCell cell = (DRIsStandard.DRIsCell) obj;
            LabourIntensity intensity = new LabourIntensity();
            if (cell.getAgeStt() <= this.getAge() && this.getAge() < cell.getAgeEnd()
                    && cell.getGender().equals(this.getGender())) {
                if (cell.getLabourIntensity() == null) {
                    return true;
                } else if (cell.getLabourIntensity().equals(intensity.getLabourIntensity())) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getAge() {
        return (int) ((getCurrentDate().getTime() - getDob().getTime()) / DateTimeUtils.DAY_MILLIONSECOND / 365);
    }

    public int getWorkTime() {
        return getWorkDays() * getWorkHours();
    }

    public int getSpareTime() {
        return (7 - getWorkDays()) * (24 - getSleepHours() - getWorkHours());
    }

    public double getStandardWeigtFloor() {
        double height = (double) getHeight() / 100;
        return height * height * Constant.BMI_IDEAL_FLOOR;
    }

    public double getStandardWeightUpper() {
        double height = (double) getHeight() / 100;
        return height * height * Constant.BMI_IDEAL_UPPER;
    }

    public double getStandardWeight() {
        double standardWeight = (getHeight() - 100) * 0.85;
        return standardWeight;
    }

    public Date getCurrentDate() {
        if(currentDate == null){
            currentDate = new Date();
        }
        return this.currentDate;
    }

    public void setCurrentDate(Date date) {
        this.currentDate = date;
    }
}
