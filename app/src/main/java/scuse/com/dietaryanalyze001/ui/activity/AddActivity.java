package scuse.com.dietaryanalyze001.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.facebook.drawee.view.SimpleDraweeView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.subclass.DayNutritionAmount;
import scuse.com.dietaryanalyze001.logic.subclass.DiaryDishes;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.logic.subclass.NutritionAmount;
import scuse.com.dietaryanalyze001.logic.subclass.NutritionIntake;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;
import scuse.com.dietaryanalyze001.logic.utils.DateTimeUtils;
import scuse.com.dietaryanalyze001.ui.fragment.IndexListFragment;
import scuse.com.dietaryanalyze001.ui.fragment.SearchListFragment;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddActivity";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.fab_add)
    FloatingActionButton fab;
    @Bind(R.id.search_view)
    MaterialSearchView searchView;
    @Bind(R.id.shadow_view)
    View shadow;

    private List<String> searchHints;

    private FoodListPagerAdapter mPagerAdapter;
    private SearchFoodListListener searchFoodListListener;

    private DiaryDishes addedDiary;
    private DayNutritionAmount dayNtrtAmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mPagerAdapter = new FoodListPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);


        setupAnimation();
        setupSearchField();
    }

    private void setupAnimation() {
        fab.setTranslationY(2 * getResources().getDimensionPixelOffset(R.dimen.btn_fab_size));
        fab.animate().translationY(0).setInterpolator(new OvershootInterpolator(1.0f))
                .setStartDelay(200).setDuration(1000).start();
    }

    private void setupSearchField() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.showSearch();
            }
        });
        searchHints = new ArrayList<>();
        searchView.setEllipsize(true);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: ");
                searchFoodListListener.search(query);
                mViewPager.setCurrentItem(1);
                return false;
            }

            /***
             * search.setSuggestions() 只能显示一条提示
             * debug 发现在 MaterialSearchView 源码中的 SearchAdapter
             * 的 getView(int position, View convertView, ViewGroup parent) 方法中
             * 获得的参数 position 始终为 0。
             * 尚未解决！？
             * 另外，由于 LeanCloud 查询较慢，暂时不提供建议功能。可以考虑用 HaoDou 的查询 URL，获取 HTML 后解析
             * @param newText
             * @return
             */
            @Override
            public boolean onQueryTextChange(String newText) {
//                Log.d(TAG, "onQueryTextChange: ");
//                if (newText != null && newText.length() > 2) {
//                    FindCallback<Dishes> callback = new FindCallback<Dishes>() {
//                        @Override
//                        public void done(List<Dishes> list, AVException e) {
//                            if (e == null) {
//                                String[] suggesstions = new String[list.size()];
//                                for (int i = 0; i < list.size(); i++) {
//                                    suggesstions[i] = list.get(i).getName();
//                                }
//                                searchView.setSuggestions(suggesstions);
//                            } else {
//                                Log.e(TAG, "done: search", e);
//                            }
//                        }
//                    };
//                    Dishes.FuzzyQuery(newText, callback);
//                    return true;
//                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                Log.d(TAG, "onSearchViewShown: ");
                searchView.setVisibility(View.VISIBLE);
                shadow.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                Log.d(TAG, "onSearchViewClosed: ");
                searchView.setVisibility(View.GONE);
                shadow.setVisibility(View.GONE);
                fab.setVisibility(View.VISIBLE);
            }
        });
    }

    public void showAddDialog(Dishes dish) {
        final MaterialDialog materialDialog = new MaterialDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.view_dialog_add, null);
        final AddDialogContentViewHolder holder = new AddDialogContentViewHolder(view);
        holder.bind(this, dish);
        materialDialog.setTitle(R.string.add_dialog_title)
                .setContentView(view)
                .setPositiveButton(R.string.add_dialog_positive, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Observable
                                // 先保存当前添加了的菜品
                                .create(new Observable.OnSubscribe<Void>() {
                                    @Override
                                    public void call(Subscriber<? super Void> subscriber) {
                                        addedDiary = new DiaryDishes();
                                        addedDiary.putUser(AVUser.getCurrentUser().getObjectId());
                                        addedDiary.putDish(holder.dish);
                                        addedDiary.putAmount(Integer.parseInt(holder.mQuality.getText().toString()));
                                        addedDiary.putUnit(holder.mUnit.getSelectedItem().toString());
                                        addedDiary.putTime(holder.mTime.getSelectedItem().toString());
                                        addedDiary.putDate(UserInfo.getInstance().getCurrentDate());
                                        try {
                                            addedDiary.save();
                                            subscriber.onNext(null);
                                            subscriber.onCompleted();
                                        } catch (AVException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                // 搜索取得当前用户今天的 DayNutritionAmount
                                .map(new Func1<Void, List<DayNutritionAmount>>() {
                                    @Override
                                    public List<DayNutritionAmount> call(Void aVoid) {
                                        AVQuery<DayNutritionAmount> query = new AVQuery<>("DayNutritionAmount");
                                        query.whereEqualTo(DayNutritionAmount.DAY_USER, AVUser.getCurrentUser().getObjectId());
                                        query.whereGreaterThanOrEqualTo("date", DateTimeUtils.floor(UserInfo.getInstance().getCurrentDate()));
                                        query.whereLessThanOrEqualTo("date", DateTimeUtils.nextTo(UserInfo.getInstance().getCurrentDate()));
                                        try {
                                            return query.find();
                                        } catch (AVException e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                    }
                                })
                                // 若当前用户今天还没有记录，新建一个
                                .map(new Func1<List<DayNutritionAmount>, Void>() {
                                    @Override
                                    public Void call(List<DayNutritionAmount> dayList) {
                                        if (dayList.size() == 1) {
                                            dayNtrtAmt = dayList.get(0);
                                        } else {
                                            dayNtrtAmt = new DayNutritionAmount();
                                            dayNtrtAmt.putUser(AVUser.getCurrentUser().getObjectId());
                                            dayNtrtAmt.putDate(UserInfo.getInstance().getCurrentDate());
                                        }
                                        return null;
                                    }
                                })
                                .observeOn(Schedulers.io())         // 以下操作发生在 io 线程
                                // 根据 addedDiary 对象，查询其 Relation 属性 NutritionAmount
                                // 并在结果中包含 Nutrition 对象的数据
                                // 遍历每一个 NutritionAmount 对象并分发下去
                                .flatMap(new Func1<Void, Observable<NutritionAmount>>() {
                                    @Override
                                    public Observable<NutritionAmount> call(Void aVoid) {
                                        AVQuery<NutritionAmount> query = addedDiary.getDish().getNtrtAmtRelations().getQuery();
                                        query.include(NutritionAmount.NA_NUTRITION);
                                        try {
                                            return Observable.from(query.find());
                                        } catch (AVException e) {
                                            e.printStackTrace();
                                            return null;
                                        }
                                    }
                                })
                                .map(new Func1<NutritionAmount, NutritionIntake>() {
                                    @Override
                                    public NutritionIntake call(NutritionAmount nutritionAmount) {
                                        double coef = addedDiary.getGram() / addedDiary.getDish().getUnitAmount();
                                        final NutritionIntake ntrtItk = new NutritionIntake();
                                        ntrtItk.putNutrition(nutritionAmount.getNutrition());
                                        ntrtItk.putIntake(nutritionAmount.getAmount() * coef);
                                        try {
                                            ntrtItk.save();
                                        } catch (AVException e) {
                                            e.printStackTrace();
                                        }
                                        return ntrtItk;
                                    }
                                })
                                // 观察者获取到数据，并给予最终的响应
                                .observeOn(AndroidSchedulers.mainThread())      // 以下操作发生在 UI 线程
                                .subscribe(new Subscriber<NutritionIntake>() {
                                    // 数据获取完毕，保存并且让提示框消失
                                    @Override
                                    public void onCompleted() {
                                        materialDialog.dismiss();
                                        dayNtrtAmt.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(AVException e) {
                                                if (e == null) {

                                                } else {
                                                    Log.e(TAG, "done: ", e);
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e(TAG, "onError: ", e);
                                    }

                                    // 正在依次获取相应数据
                                    @Override
                                    public void onNext(NutritionIntake nutritionIntake) {
                                        dayNtrtAmt.addNtrtAmount(nutritionIntake);
                                    }
                                });
                    }
                })
                .setNegativeButton(R.string.add_dialog_negative, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                        Log.d(TAG, "onClick: setNegativeButton");
                    }
                });
        materialDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            return true;
        } else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
            Log.d(TAG, "onBackPressed: ");
        } else {
            Intent intent = new Intent(AddActivity.this, DiaryActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void setupSearchFoodListListener(SearchFoodListListener listener) {
        this.searchFoodListListener = listener;
    }


    public interface SearchFoodListListener {
        void search(String title);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class FoodListPagerAdapter extends FragmentPagerAdapter {

        public FoodListPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return IndexListFragment.newInstance("Case 2 Parameter 1", "Parameter 2");
                case 1:
                    return SearchListFragment.newInstance();
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

    public static class AddDialogContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_dialog_add)
        SimpleDraweeView mImage;
        @Bind(R.id.tv_dialog_add_name)
        TextView mName;
        @Bind(R.id.tv_dialog_add_info)
        TextView mDetail;
        @Bind(R.id.et_dialog_quality)
        AutoCompleteTextView mQuality;
        @Bind(R.id.spinner_dialog_unit)
        Spinner mUnit;
        @Bind(R.id.spinner_dialog_time)
        Spinner mTime;

        public Dishes dish;

        public AddDialogContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Context context, Dishes dish) {
            Log.d(TAG, "bind: " + dish.getImgURL());
            this.dish = dish;

            mName.setText(dish.getName());
            mDetail.setText(dish.getAllIngtsName());
            mImage.setImageURI(dish.getImgFile());
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line);
            adapter.addAll(context.getResources().getStringArray(R.array.dialog_quality));
            mQuality.setAdapter(adapter);

            mQuality.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        mQuality.showDropDown();
                    }
                }
            });

            ArrayAdapter<String> mUnitAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.getResources().getStringArray(R.array.dialog_unit));
            mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mUnit.setAdapter(mUnitAdapter);
            mUnit.setSelection(0, true);

            ArrayAdapter<String> mTimeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, context.getResources().getStringArray(R.array.dialog_time));
            mTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mTime.setAdapter(mTimeAdapter);
            mTime.setSelection(2, true);
        }
    }

}
