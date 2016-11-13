package scuse.com.dietaryanalyze001.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.assess.DRIsStandard;
import scuse.com.dietaryanalyze001.logic.subclass.EnergyStandard;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;
import scuse.com.dietaryanalyze001.ui.fragment.LifestyleFragment;
import scuse.com.dietaryanalyze001.ui.fragment.PersonalInfoFragment;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    @Bind(R.id.main_content)
    CoordinatorLayout content;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.fab_register)
    FloatingActionButton fab;

    private RegisterPagerAdapter mPagerAdapter;

    onRegisterForPersonalInfoListener personalInfoListener;
    onRegisterForLifestyleListener lifestyleListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

//        checkInterfaceImplement();

        setSupportActionBar(mToolbar);

        mPagerAdapter = new RegisterPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        fab.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        fab.animate()
                .translationY(0)
                .setInterpolator(new OvershootInterpolator(1.0f))
                .setStartDelay(200)
                .setDuration(1000)
                .start();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        onFabClickedInPager0();
                        break;
                    case 1:
                        onFabClickedInPager1();
                        break;
                    default:
                        Log.w(TAG, "mFab.onClick: mViewPager.getCurrentItem()=" + mViewPager.getCurrentItem());
                        break;
                }
            }
        });
    }

    public void showDatePickerDialog(final EditText etDob) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etDob.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, 2016, 8, 17);
        dialog.setTitle("选择您的出生日期");
        dialog.show();
    }

    public void onPersonalInfoSwitcher(EditText etWeight) {
        // Hide soft keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etWeight.getWindowToken(), 0);
        // Switch view to lifestyle page
        onFabClickedInPager0();
    }

    public void remodifier(View view, int pager) {
        mViewPager.setCurrentItem(pager);
        view.requestFocus();
    }

    public void setDRIsStandard(EnergyStandard standard) {
        ArrayList<DRIsStandard.DRIsCell> cells = new DRIsStandard().getCells();
        for (DRIsStandard.DRIsCell cell : cells) {
            if (UserInfo.getInstance().equals(cell)) {
                standard.putEnergy(cell.getEng());
                standard.putProtein(cell.getPrt());
                standard.putFat(cell.getFat());
                standard.putCarbohydrate(cell.getCab());
            }
        }
    }


    private void onFabClickedInPager0() {
        mViewPager.setCurrentItem(1);
        fab.animate()
                .scaleX(0.3f)
                .scaleY(0.3f)
                .rotation(90)
                .setDuration(300)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        fab.setImageResource(R.drawable.ic_fab_complete);
                        fab.animate()
                                .scaleX(1.f)
                                .scaleY(1.f)
                                .rotation(0)
                                .setDuration(10)
                                .start();
                    }
                })
                .start();
    }

    private void onFabClickedInPager1() {
        UserInfo.getInstance().putGender(personalInfoListener.getInfoGender());
        UserInfo.getInstance().putDob(personalInfoListener.getInfoDob());
        UserInfo.getInstance().putWeight(personalInfoListener.getInfoWeight());
        UserInfo.getInstance().putHeight(personalInfoListener.getInfoHeight());

        UserInfo.getInstance().putSleepHours(lifestyleListener.getSleepHours());
        UserInfo.getInstance().putJobKind(lifestyleListener.getJobKind());
        UserInfo.getInstance().putWorkDays(lifestyleListener.getWorkDays());
        UserInfo.getInstance().putWorkHours(lifestyleListener.getWorkHours());
        UserInfo.getInstance().putFreeIntensity(lifestyleListener.getFreeIntensity());

        if (personalInfoListener.isCanceled() || lifestyleListener.isCanceled()) {
            Log.e(TAG, "onFabClickedInPager1: page0:" + personalInfoListener.isCanceled()
                    + "\npage1:" + lifestyleListener.isCanceled());
            return;
        }

        final EnergyStandard standard = new EnergyStandard();
        // 18 - 50 岁年龄段的人给予设定目标体重的选择（可以设置高的 <增重>，也可以设置低的 <减肥>）
        if (UserInfo.getInstance().getAge() >= 18 && UserInfo.getInstance().getAge() <= 50) {
            final MaterialDialog materialDialog = new MaterialDialog(this);
            View view = LayoutInflater.from(this).inflate(R.layout.view_dialog_register, null);
            final RegisterDialogViewHolder holder = new RegisterDialogViewHolder(view);
            // 在对话框里弹出 标准体重，符合 BMI 的体重上、下限
            holder.mBMI.setText(String.format(getResources().getString(R.string.register_dialog_bmi), UserInfo.getInstance().getStandardWeight(), UserInfo.getInstance().getStandardWeigtFloor(), UserInfo.getInstance().getStandardWeightUpper()));
            materialDialog.setContentView(view)
                    .setPositiveButton("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            double weight = UserInfo.getInstance().getWeight();
                            standard.putEnergy((int) (weight * Constant.DAY_KILO_ENERGY));
                            standard.putProtein((int) (weight * Constant.DAY_KILO_PROTEIN));
                            standard.putFat((int) (weight * Constant.DAY_KILO_FAT));
                            standard.putCarbohydrate((int) (weight * Constant.DAY_KILO_CARB));
                            setupEnergyStandard(standard);
                        }
                    })
                    .setNegativeButton("CANCEL", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 取消对话框则默认按照 DRIs 来设置设置标准摄入量
                            setDRIsStandard(standard);
                            setupEnergyStandard(standard);
                        }
                    });
            materialDialog.show();
        } else {
            // 18 - 50 岁年龄段之外的人只能按照 DRIs 来设置设置标准摄入量
            setDRIsStandard(standard);
            setupEnergyStandard(standard);
        }
    }

    public void setupEnergyStandard(final EnergyStandard standard) {
        Observable.create(new Observable.OnSubscribe<EnergyStandard>() {
            @Override
            public void call(Subscriber<? super EnergyStandard> subscriber) {
                subscriber.onNext(standard);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(new Func1<EnergyStandard, Void>() {
                    @Override
                    public Void call(EnergyStandard standard) {
                        try {
                            standard.save();
                            Log.d(TAG, "call: " + standard);
                            UserInfo.getInstance().putEnergyStandard(standard);
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .map(new Func1<Void, Void>() {
                    @Override
                    public Void call(Void aVoid) {
                        try {
                            UserInfo.getInstance().save();
                            AVUser.getCurrentUser().put("userInfo", UserInfo.getInstance());
                            AVUser.getCurrentUser().save();
                        } catch (AVException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override
                    public void onCompleted() {
                        Intent intent = new Intent(RegisterActivity.this, DiaryActivity.class);
                        startActivity(intent);
                        RegisterActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(Void aVoid) {
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class RegisterPagerAdapter extends FragmentPagerAdapter {

        public RegisterPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    Fragment personalFragment = PersonalInfoFragment.newInstance("Case 2 Parameter 1", "Parameter 2");
                    personalInfoListener = (onRegisterForPersonalInfoListener) personalFragment;
                    return personalFragment;
                case 1:
                    Fragment lifestyleFragment = LifestyleFragment.newInstance("Case 1 Parameter 1", "Parameter 2");
                    lifestyleListener = (onRegisterForLifestyleListener) lifestyleFragment;
                    return lifestyleFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
            }
            return null;
        }
    }

    public interface onRegisterForPersonalInfoListener {
        String getInfoGender();

        Date getInfoDob();

        Integer getInfoHeight();

        Double getInfoWeight();

        boolean isCanceled();
    }

    public interface onRegisterForLifestyleListener {
        Integer getSleepHours();

        String getJobKind();

        Integer getWorkHours();

        Integer getWorkDays();

        String getFreeIntensity();

        boolean isCanceled();
    }

    public static class RegisterDialogViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.et_goal_weight)
        EditText mGoal;
        @Bind(R.id.tv_register_dialog_bmi)
        TextView mBMI;

        public RegisterDialogViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
