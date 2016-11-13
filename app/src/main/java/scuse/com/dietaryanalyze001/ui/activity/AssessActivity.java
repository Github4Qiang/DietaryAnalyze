package scuse.com.dietaryanalyze001.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.assess.DBIScore;
import scuse.com.dietaryanalyze001.logic.bean.DayDBIIntakes;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;
import scuse.com.dietaryanalyze001.logic.utils.DateTimeUtils;
import scuse.com.dietaryanalyze001.ui.fragment.AssessDBIFragment;
import scuse.com.dietaryanalyze001.ui.fragment.AssessEnergyFragment;
import scuse.com.dietaryanalyze001.ui.fragment.AssessTimeFragment;

public class AssessActivity extends AppCompatActivity {

    private static final String TAG = "AssessActivity";
    public static final String DAY_DBI_INTAKE = "DayDBIIntakes";
    public static final String DAY_DBI_ENERGY = "Energy";
    public static final String DAY_DBI_NUTRITION_MAP = "NutritionMap";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.vp_assess)
    ViewPager viewPager;
    @Bind(R.id.tl_assess)
    TabLayout tabLayout;

    private AssessPagerAdapter mPagerAdapter;
    private DBIScore dbiScore;
    private String ntrtAmtMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assess);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(DateTimeUtils.getStrMonthDay(UserInfo.getInstance().getCurrentDate()));

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            DayDBIIntakes dbiIntakes = new DayDBIIntakes(new Gson().fromJson(bundle.getString(DAY_DBI_INTAKE), HashMap.class));
            int totalEnergy = bundle.getInt(DAY_DBI_ENERGY);
            this.dbiScore = new DBIScore(dbiIntakes, totalEnergy);
            this.ntrtAmtMap = bundle.getString(DAY_DBI_NUTRITION_MAP);

            Log.d(TAG, "onCreate: " + dbiIntakes + ", " + totalEnergy + ", " + ntrtAmtMap);
        } else {
            Log.e(TAG, "onCreate: " + bundle.toString());
        }

        mPagerAdapter = new AssessPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
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
        Intent intent = new Intent(AssessActivity.this, DiaryActivity.class);
        startActivity(intent);
        finish();
    }

    private class AssessPagerAdapter extends FragmentPagerAdapter {

        public AssessPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return AssessDBIFragment.newInstance(new Gson().toJson(dbiScore));
                case 1:
                    return AssessEnergyFragment.newInstance(ntrtAmtMap);
                case 2:
                    return AssessTimeFragment.newInstance(ntrtAmtMap);
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "DBI评价";
                case 1:
                    return "三大营养素";
                case 2:
                    return "三餐摄入";
            }
            return null;
        }
    }

    public final void clickHint(final String url) {
        Log.d(TAG, "clickHint: " + url);
        AssessActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View view = LayoutInflater.from(AssessActivity.this).inflate(R.layout.view_dialog_webview, null);
                final MaterialDialog materialDialog = new MaterialDialog(AssessActivity.this);
                final AssessDialogContentViewHolder holder = new AssessDialogContentViewHolder(view);
                holder.bind(url);
                materialDialog.setContentView(view)
                        .setCanceledOnTouchOutside(true)
                        .setNegativeButton(R.string.add_dialog_negative, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                materialDialog.dismiss();
                                Log.d(TAG, "onClick: setNegativeButton");
                            }
                        });
                materialDialog.show();

                Log.d(TAG, "clickHint: run");
            }
        });
    }

    public static class AssessDialogContentViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.webView)
        WebView webview;

        public AssessDialogContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(String url) {
            webview.getSettings().setAllowFileAccess(true);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.loadUrl(url);
        }
    }

}
