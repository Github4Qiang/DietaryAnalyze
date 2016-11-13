package scuse.com.dietaryanalyze001;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.facebook.drawee.backends.pipeline.Fresco;

import scuse.com.dietaryanalyze001.logic.subclass.DayNutritionAmount;
import scuse.com.dietaryanalyze001.logic.subclass.DiaryDishes;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.logic.subclass.EnergyStandard;
import scuse.com.dietaryanalyze001.logic.subclass.Nutrition;
import scuse.com.dietaryanalyze001.logic.subclass.NutritionAmount;
import scuse.com.dietaryanalyze001.logic.subclass.NutritionIntake;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;

/**
 * Created by Administrator on 8/17/2016.
 */
public class LeanHelperApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);

//        AVObject.registerSubclass(DayDBIIntakes.class);
        AVObject.registerSubclass(Nutrition.class);
        AVObject.registerSubclass(NutritionAmount.class);
        AVObject.registerSubclass(NutritionIntake.class);
        AVObject.registerSubclass(DayNutritionAmount.class);
        AVObject.registerSubclass(UserInfo.class);
        AVObject.registerSubclass(DiaryDishes.class);
        AVObject.registerSubclass(Dishes.class);
        AVObject.registerSubclass(EnergyStandard.class);

        AVOSCloud.useAVCloudCN();
        AVOSCloud.initialize(this, "yxOr8lGU3DYgVNWQSlCq60hM-gzGzoHsz", "uDq2NHtRddKHfjuzoXk772zh");
    }

}
