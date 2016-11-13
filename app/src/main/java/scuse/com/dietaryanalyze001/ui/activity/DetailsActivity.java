package scuse.com.dietaryanalyze001.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.jsoup.nodes.Document;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.bean.MealDetailBean;
import scuse.com.dietaryanalyze001.logic.spider.Downloader;
import scuse.com.dietaryanalyze001.logic.spider.PDHaoDou;
import scuse.com.dietaryanalyze001.ui.fragment.ChartFragment;
import scuse.com.dietaryanalyze001.ui.view.FlowLayout;


public class DetailsActivity extends AppCompatActivity {

    public static final String MEAL_DETAIL_BEAN = "meal_detail_bean";
    private static final String TAG = "DiaryActivity";

    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.fab)
    FloatingActionButton mFab;
    @Bind(R.id.iv_bg_meal)
    ImageView ivMeal;
    @Bind(R.id.flow_container)
    FlowLayout mFlowLayout;
    @Bind(R.id.scorll_view)
    NestedScrollView mScrollView;
    @Bind(R.id.container)
    ViewPager mViewPager;
    @Bind(R.id.tab_charts)
    TabLayout mTabLayout;

    private MealDetailBean mealDetail;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mToolbarLayout.setTitleEnabled(true);
            mToolbarLayout.setTitle(mealDetail.getMealName());
            Picasso.with(DetailsActivity.this)
                    .load(mealDetail.getImgUrl())
                    .into(ivMeal);
            for (String comp : mealDetail.getMealComponent().keySet()) {
                Button button = new Button(DetailsActivity.this);

                button.setText(comp);
                mFlowLayout.addView(button);
            }
        }
    };
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Downloader downloader = new Downloader();
            Document document = downloader.download(mealDetail.getDetailUrl());
            PDHaoDou parser = new PDHaoDou(document);
            mealDetail = parser.fillMDB(mealDetail);
            Log.d(TAG, "run: " + mealDetail);
            handler.sendEmptyMessage(0);
        }
    };
    private ChartPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mScrollView.setFillViewport(true);
        mPagerAdapter = new ChartPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);

        Intent intent = getIntent();
        if (intent != null) {
            mealDetail = Parcels.unwrap(intent.getParcelableExtra(MEAL_DETAIL_BEAN));
        }

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        new Thread(runnable).start();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class ChartPagerAdapter extends FragmentPagerAdapter {

        public ChartPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ChartFragment.newInstance(ChartFragment.CHART_LINE, "Parameter 2");
                case 1:
                    return ChartFragment.newInstance(ChartFragment.CHART_PIE, "Parameter 2");
                case 2:
                    return ChartFragment.newInstance(ChartFragment.CHART_BAR, "Parameter 2");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "折线图";
                case 1:
                    return "饼图";
                case 2:
                    return "柱状图";
            }
            return null;
        }
    }
}
