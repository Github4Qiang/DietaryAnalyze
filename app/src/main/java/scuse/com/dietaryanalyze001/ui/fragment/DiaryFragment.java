package scuse.com.dietaryanalyze001.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;

import org.parceler.Parcels;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.bean.DiaryMealBean;
import scuse.com.dietaryanalyze001.logic.dgo.DiaryDatas;
import scuse.com.dietaryanalyze001.logic.subclass.DiaryDishes;
import scuse.com.dietaryanalyze001.logic.utils.DateTimeUtils;
import scuse.com.dietaryanalyze001.ui.activity.DetailsActivity;
import scuse.com.dietaryanalyze001.ui.activity.DiaryActivity;
import scuse.com.dietaryanalyze001.ui.adapter.DiaryDishesAdapter;

/**
 * Created by Polylanger on 2016/10/18.
 */
public class DiaryFragment extends Fragment implements DiaryDishesAdapter.OnMealItemClickListener {

    private static final String TAG = "DiaryFragment";
    private static final String DIARY_DATE_TIME = "dateTime";

    private static final String COLOR_RGB_STR_WAVE_TEXT = "#F5F6EB";
    private static final String COLOR_RGB_STR_WAVE_1 = "#FFEB3B";
    private static final String COLOR_RGB_STR_WAVE_2 = "#FFC107";
    private static final String COLOR_RGB_STR_WAVE_3 = "#FF9800";

    @Bind(R.id.rv_diary)
    RecyclerView rvDiary;

    private DiaryDishesAdapter diaryDishesAdapter;
    private DiaryDatas mDatas;
    private Date fragmentDate;
    private DiaryActivity parentActivity;

    int index = 0;

    public DiaryFragment() {
    }

    public static DiaryFragment newInstance(long dateTime) {
        DiaryFragment fragment = new DiaryFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(DIARY_DATE_TIME, dateTime);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diary, container, false);
        ButterKnife.bind(this, rootView);
        fragmentDate = new Date(getArguments().getLong(DIARY_DATE_TIME));
        Log.d(TAG, "onCreateView: " + fragmentDate);
        if (index == 0) {
            setupDiaryDishes();
            index += 1;
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: " + fragmentDate);
        this.parentActivity = (DiaryActivity) context;
    }

    public void setupDiaryDishes() {
        mDatas = new DiaryDatas();

        // 根据日期获取当天的饮食记录
        Observable.just(getFragmentDate())
                // 查询当前用户在 date 这天的所有 DiaryDishes 记录
                // 并且将一系列记录包装成 Observable 对象返回
                .flatMap(new Func1<Date, Observable<DiaryDishes>>() {
                    @Override
                    public Observable<DiaryDishes> call(Date date) {
                        AVQuery<DiaryDishes> query = new AVQuery<>("DiaryDishes");
                        query.include(DiaryDishes.DIARY_DISH);
                        query.whereEqualTo(DiaryDishes.DIARY_USER, AVUser.getCurrentUser().getObjectId());
                        query.whereGreaterThanOrEqualTo("date", DateTimeUtils.floor(date));
                        query.whereLessThanOrEqualTo("date", DateTimeUtils.nextTo(date));
                        try {
                            List<DiaryDishes> diaryDishes = query.find();
                            return Observable.from(diaryDishes);
                        } catch (AVException e) {
                            e.printStackTrace();
                            return null;
                        }
                    }
                })
                .subscribeOn(Schedulers.io())       // 以上操作发生在 io 线程
                .observeOn(Schedulers.io())         // 以下操作发生在 io 线程
                // 将传来的 Observable 对象加上它对应的时刻（早餐、午餐。。。）
                // 包装成 DiaryMealBean 对象
                // 以备后面 Adapter 使用
                .map(new Func1<DiaryDishes, DiaryMealBean>() {
                    @Override
                    public DiaryMealBean call(DiaryDishes diaryDishes) {
                        return new DiaryMealBean(diaryDishes.getTime(), diaryDishes);
                    }
                })
                // 观察者获取到数据，并给予最终的响应
                .observeOn(AndroidSchedulers.mainThread())      // 以下操作发生在 UI 线程
                .subscribe(new Subscriber<DiaryMealBean>() {
                    // 数据获取完毕，在 UI 上显示数据
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted: " + fragmentDate + mDatas);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(parentActivity);
                        rvDiary.setLayoutManager(linearLayoutManager);
                        diaryDishesAdapter = new DiaryDishesAdapter(parentActivity, mDatas);
                        diaryDishesAdapter.setOnMealItemClickListener(DiaryFragment.this);
                        rvDiary.setAdapter(diaryDishesAdapter);
                        index = 0;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    // 将刚刚包装好的 DiaryMealBean 加入Adapter对应的数据集中
                    // 并且返回其中的 Dishes 对象
                    @Override
                    public void onNext(DiaryMealBean diaryMealBean) {
                        mDatas.insertData(diaryMealBean);
                    }
                });

    }


    @Override
    public void onDetailsClick(int position) {
        Log.d(TAG, "onDetailsClick: Detail Click position:" + position);
        Intent intent = new Intent(parentActivity, DetailsActivity.class);
        intent.putExtra(DetailsActivity.MEAL_DETAIL_BEAN,
                Parcels.wrap(((DiaryMealBean) mDatas.getArrayDatas().get(position)).getDiaryDish()));
        startActivity(intent);
    }

    public Date getFragmentDate() {
        return fragmentDate;
    }

    public void setFragmentDate(Date fragmentDate) {
        this.fragmentDate = fragmentDate;
    }

}
