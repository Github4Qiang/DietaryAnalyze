package scuse.com.dietaryanalyze001.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.fanrunqi.waveprogress.WaveProgressView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.bean.DayDBIIntakes;
import scuse.com.dietaryanalyze001.logic.subclass.DayNutritionAmount;
import scuse.com.dietaryanalyze001.logic.subclass.Nutrition;
import scuse.com.dietaryanalyze001.logic.subclass.NutritionIntake;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;
import scuse.com.dietaryanalyze001.logic.utils.DateTimeUtils;
import scuse.com.dietaryanalyze001.ui.adapter.DiaryFragmentAdapter;
import scuse.com.dietaryanalyze001.ui.fragment.DiaryFragment;


public class DiaryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "DiaryActivity";
    private static final String COLOR_RGB_STR_WAVE_TEXT = "#F5F6EB";
    private static final String COLOR_RGB_STR_WAVE_1 = "#F2FE28";
    private static final String COLOR_RGB_STR_WAVE_2 = "#D2F557";
    private static final String COLOR_RGB_STR_WAVE_3 = "#5AF580";
    private static final String COLOR_RGB_STR_WAVE_4 = "#61FF69";
    private static final String COLOR_RGB_STR_WAVE_5 = "#B8F788";
    private static final String COLOR_RGB_STR_WAVE_6 = "#58D2E8";

    @Bind(R.id.vp_diary)
    ViewPager viewPager;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawer;
    @Bind(R.id.nav_view)
    NavigationView navigationView;
    @Bind(R.id.wp_diary_carbs)
    WaveProgressView wpCarbs;
    @Bind(R.id.wp_diary_proteins)
    WaveProgressView wpProteins;
    @Bind(R.id.wp_diary_fats)
    WaveProgressView wpFats;
    @Bind(R.id.tv_diary_total_intake)
    TextView tvTotalIntake;
    @Bind(R.id.tv_diary_could_intake)
    TextView tvCouldIntake;
    @Bind(R.id.tv_diary_already_intake)
    TextView tvAlreadyIntake;
    @Bind(R.id.pb_diary_total)
    ProgressBar progressBar;

    private DiaryFragmentAdapter fragmentAdapter;
    private ArrayList<DiaryFragment> fragments;
    private DayDBIIntakes dbiIntakes;
    private HashMap<String, Double> ntrtAmtMap;

    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        ButterKnife.bind(this);
        Log.d(TAG, "onCreate: " + UserInfo.getInstance().getCurrentDate());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, AddActivity.class);
                startActivity(intent);
                finish();
            }
        });


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        setupFragments();

//        setupDiaryDishes();

    }

    private void setupFragments() {
        fragments = new ArrayList<>();
        fragments.add(DiaryFragment.newInstance(DateTimeUtils.beforeOf(UserInfo.getInstance().getCurrentDate()).getTime()));
        fragments.add(DiaryFragment.newInstance(UserInfo.getInstance().getCurrentDate().getTime()));
        fragments.add(DiaryFragment.newInstance(DateTimeUtils.nextTo(UserInfo.getInstance().getCurrentDate()).getTime()));
        fragmentAdapter = new DiaryFragmentAdapter(getSupportFragmentManager(), this, fragments);
        viewPager.setAdapter(fragmentAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: " + position);
                DiaryFragment currentFragment = (DiaryFragment) fragmentAdapter.getItem(position);
                if (currentFragment.getFragmentDate() == null) {
                    currentFragment.setFragmentDate(UserInfo.getInstance().getCurrentDate());
                }
                UserInfo.getInstance().setCurrentDate(currentFragment.getFragmentDate());
                getSupportActionBar().setTitle(DateTimeUtils.getStrMonthDay(currentFragment.getFragmentDate()));
                if (index == 0) {
                    setupWaveProgresses(position);
                    index += 1;
                }
                if (position == 0) {
                    Log.d(TAG, "onPageSelected: position == 0");
                    /**
                     * 当滑到最前面的 Fragment，则在 Fragment List 的头部插入新的 Fragment
                     * 新的 Fragment 的日期设为当前 Fragment 日期的前一天
                     * 因为头部添加 Fragment 之后，当前 Fragment 的 position 会 +1
                     * 所以设置当前 Fragment 为 1
                     */
                    Log.d(TAG, "onPageSelected: " + currentFragment.getFragmentDate());
                    fragments.add(0, DiaryFragment.newInstance(DateTimeUtils.beforeOf(currentFragment.getFragmentDate()).getTime()));
                    fragmentAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(1);
                } else if (position == fragments.size() - 1) {
                    Log.d(TAG, "onPageSelected: position == fragments.size() - 1: " + position);
                    /**
                     * 当滑到最后面的 Fragment，则在 Fragment List 的尾部添加新的 Fragment
                     * 新的 Fragment 的日期设为当前 Fragment 日期的后一天
                     */
                    fragments.add(DiaryFragment.newInstance(DateTimeUtils.nextTo(currentFragment.getFragmentDate()).getTime()));
                    fragmentAdapter.notifyDataSetChanged();
                    viewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(1);
    }

    private void setupWaveProgresses(final int position) {
        this.ntrtAmtMap = new HashMap<>();

        dbiIntakes = new DayDBIIntakes();

        Log.d(TAG, "setupWaveProgresses: " + UserInfo.getInstance().getCurrentDate());

        ntrtAmtMap.put(Nutrition.NUTRITION_ENERGY, 0.0);
        ntrtAmtMap.put(Nutrition.NUTRITION_PROTEIN, 0.0);
        ntrtAmtMap.put(Nutrition.NUTRITION_FAT, 0.0);
        ntrtAmtMap.put(Nutrition.NUTRITION_CARBOHYDRATE, 0.0);

        Observable.create(new Observable.OnSubscribe<List<DayNutritionAmount>>() {
            @Override
            public void call(Subscriber<? super List<DayNutritionAmount>> subscriber) {
                AVQuery<DayNutritionAmount> query = new AVQuery<>("DayNutritionAmount");
                query.whereEqualTo(DayNutritionAmount.DAY_USER, AVUser.getCurrentUser().getObjectId());
                query.whereGreaterThanOrEqualTo("date", DateTimeUtils.floor(UserInfo.getInstance().getCurrentDate()));
                query.whereLessThanOrEqualTo("date", DateTimeUtils.nextTo(UserInfo.getInstance().getCurrentDate()));
                try {
                    List<DayNutritionAmount> list = query.find();
                    subscriber.onNext(list);
                    subscriber.onCompleted();
                } catch (AVException e) {
                    Log.e(TAG, "call: ", e);
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .map(new Func1<List<DayNutritionAmount>, DayNutritionAmount>() {
                    @Override
                    public DayNutritionAmount call(List<DayNutritionAmount> dayNutritionAmounts) {
                        if (dayNutritionAmounts.size() != 0) {
                            return dayNutritionAmounts.get(0);
                        } else {
                            Log.d(TAG, "map call: null");
                        }
                        return null;
                    }
                })
                .flatMap(new Func1<DayNutritionAmount, Observable<NutritionIntake>>() {
                    @Override
                    public Observable<NutritionIntake> call(DayNutritionAmount dayNutritionAmount) {
                        if (dayNutritionAmount != null) {
                            AVQuery<NutritionIntake> query = dayNutritionAmount.getNtrtIntakes().getQuery();
                            query.include(NutritionIntake.NI_NUTRITION);
                            try {
                                List<NutritionIntake> intakes = query.find();
                                return Observable.from(intakes);
                            } catch (AVException e) {
                                Log.e(TAG, "call: ", e);
                            }
                        } else {
                            Log.d(TAG, "flatMap call: NULL");
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NutritionIntake>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: ");
                        double totalIntake = UserInfo.getInstance().getEnergyStandard().getEnergy();
                        double alreadyIntake = ntrtAmtMap.get(Nutrition.NUTRITION_ENERGY).intValue();
                        tvAlreadyIntake.setText(alreadyIntake + getResources().getString(R.string.already_eaten));
                        tvTotalIntake.setText(totalIntake + "");
                        tvCouldIntake.setText(getResources().getString(R.string.could_eaten_front)
                                + (totalIntake - alreadyIntake) + getResources().getString(R.string.could_eaten_behind));
                        progressBar.setMax((int) totalIntake);
                        progressBar.setProgress((int) alreadyIntake);

                        waveColorChanged(position);

                        double carb_percent = (double) ntrtAmtMap.get(Nutrition.NUTRITION_CARBOHYDRATE).doubleValue() * 100
                                / (double) UserInfo.getInstance().getEnergyStandard().getCarbohydrate();
                        wpCarbs.setCurrent((int) carb_percent,
                                (int) carb_percent + getResources().getString(R.string.diary_carb));
                        wpCarbs.setText(COLOR_RGB_STR_WAVE_TEXT, 32);
                        wpCarbs.setmWaveSpeed(10);//The larger the value, the slower the vibration

                        double protein_percent = (double) ntrtAmtMap.get(Nutrition.NUTRITION_PROTEIN).doubleValue() * 100
                                / (double) UserInfo.getInstance().getEnergyStandard().getProtein();
                        wpProteins.setCurrent((int) protein_percent,
                                (int) protein_percent + getResources().getString(R.string.diary_protein));
                        wpProteins.setText(COLOR_RGB_STR_WAVE_TEXT, 32);
                        wpProteins.setmWaveSpeed(10);

                        double fat_percent = (double) ntrtAmtMap.get(Nutrition.NUTRITION_FAT).doubleValue() * 100
                                / (double) UserInfo.getInstance().getEnergyStandard().getFat();
                        wpFats.setCurrent((int) fat_percent,
                                (int) fat_percent + getResources().getString(R.string.diary_fat));
                        wpFats.setText(COLOR_RGB_STR_WAVE_TEXT, 32);
                        wpFats.setmWaveSpeed(10);

                        index = 0;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(NutritionIntake nutritionIntake) {
                        if(nutritionIntake == null){
                            Log.d(TAG, "onNext: nutritionIntake null");
                        }
                        dbiIntakes.putNutrition(nutritionIntake.getNutrition().getKind(), nutritionIntake.getIntake());

                        ntrtAmtMap.put(Nutrition.NUTRITION_ENERGY,
                                ntrtAmtMap.remove(Nutrition.NUTRITION_ENERGY)
                                        + nutritionIntake.getNutritionIntake(Nutrition.NUTRITION_ENERGY));
                        ntrtAmtMap.put(Nutrition.NUTRITION_PROTEIN,
                                ntrtAmtMap.remove(Nutrition.NUTRITION_PROTEIN)
                                        + nutritionIntake.getNutritionIntake(Nutrition.NUTRITION_PROTEIN));
                        ntrtAmtMap.put(Nutrition.NUTRITION_FAT,
                                ntrtAmtMap.remove(Nutrition.NUTRITION_FAT)
                                        + nutritionIntake.getNutritionIntake(Nutrition.NUTRITION_FAT));
                        ntrtAmtMap.put(Nutrition.NUTRITION_CARBOHYDRATE,
                                ntrtAmtMap.remove(Nutrition.NUTRITION_CARBOHYDRATE)
                                        + nutritionIntake.getNutritionIntake(Nutrition.NUTRITION_CARBOHYDRATE));
                    }
                });
    }

    /**
     * 重新绘制 waveProgressBar
     *
     * @param position
     */
    public void waveColorChanged(int position) {
        wpCarbs.requestLayout();
        wpProteins.requestLayout();
        wpFats.requestLayout();
        if (position % 2 == 0) {
            wpCarbs.setWaveColor(COLOR_RGB_STR_WAVE_1);
            wpProteins.setWaveColor(COLOR_RGB_STR_WAVE_2);
            wpFats.setWaveColor(COLOR_RGB_STR_WAVE_3);
        } else {
            wpCarbs.setWaveColor(COLOR_RGB_STR_WAVE_4);
            wpProteins.setWaveColor(COLOR_RGB_STR_WAVE_5);
            wpFats.setWaveColor(COLOR_RGB_STR_WAVE_6);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_assess) {
            Intent intent = new Intent(DiaryActivity.this, AssessActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(AssessActivity.DAY_DBI_INTAKE, new Gson().toJson(dbiIntakes.getKinds()));
            bundle.putInt(AssessActivity.DAY_DBI_ENERGY, UserInfo.getInstance().getEnergyStandard().getEnergy());
            bundle.putString(AssessActivity.DAY_DBI_NUTRITION_MAP, new Gson().toJson(this.ntrtAmtMap));

            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
//            Log.d(TAG, "onNavigationItemSelected: nav_history");
//            Intent intent = new Intent(DiaryActivity.this, HistoryActivity.class);
//            startActivity(intent);
        } else if (id == R.id.nav_performance) {
            Log.d(TAG, "onNavigationItemSelected: nav_performance");
        } else if (id == R.id.nav_setting) {
            Log.d(TAG, "onNavigationItemSelected: nav_setting");
        } else if (id == R.id.nav_diary) {
            Log.d(TAG, "onNavigationItemSelected: nav_diary");
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
