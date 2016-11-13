package scuse.com.dietaryanalyze001.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.drakeet.materialdialog.MaterialDialog;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.logic.subclass.EnergyStandard;
import scuse.com.dietaryanalyze001.logic.subclass.Nutrition;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;
import scuse.com.dietaryanalyze001.ui.activity.AssessActivity;

/**
 * Created by Polylanger on 2016/10/27.
 */
public class AssessTimeFragment extends Fragment {
    private static final String TAG = "AssessDBIFragment";
    public static final String DBI_SCORE = "DBIScore";

    @Bind(R.id.time_pie_chart)
    WebView timePieChart;
    @Bind(R.id.table_assess_ntrt)
    TableLayout ntrtTable;

    private HashMap<String, Integer> ntrtAmtMap = new HashMap<>();

    public AssessTimeFragment() {
    }

    public static AssessTimeFragment newInstance(String ntrtAmtMap) {
        AssessTimeFragment fragment = new AssessTimeFragment();
        Bundle bundle = new Bundle();
        bundle.putString(AssessActivity.DAY_DBI_NUTRITION_MAP, ntrtAmtMap);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assess_time, container, false);
        ButterKnife.bind(this, rootView);
        if (getArguments() != null) {
            HashMap<String, Double> tmp = new Gson().fromJson(getArguments().getString(AssessActivity.DAY_DBI_NUTRITION_MAP), new HashMap<String, Double>().getClass());
            for (String key : tmp.keySet()) {
                ntrtAmtMap.put(key, tmp.get(key).intValue() * Constant.getInstance().Ntrt2Energy(key));
            }
            Log.d(TAG, "onCreateView: score" + ntrtAmtMap);
        }

        // 将三大营养素的表现形式从 XXg 变成 XXkcal

        setupChart();
        setupTable();

        return rootView;
    }

    private void setupTable() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        String[] ntrts = {Nutrition.NUTRITION_PROTEIN, Nutrition.NUTRITION_FAT, Nutrition.NUTRITION_CARBOHYDRATE};

        for (String kind : ntrts) {
            View view = inflater.inflate(R.layout.item_assess_ntrt_row, null);
            NtrtRowViewHolder holder = new NtrtRowViewHolder(getContext(), view);
            holder.bind(kind, ntrtAmtMap.get(kind) / Constant.getInstance().Ntrt2Energy(kind), UserInfo.getInstance().getEnergyStandard().getTimeEnergy(kind));
            ntrtTable.addView(view);
        }
    }

    private void setupChart() {

        timePieChart.getSettings().setAllowFileAccess(true);
        timePieChart.getSettings().setJavaScriptEnabled(true);
        timePieChart.addJavascriptInterface(new TimeHintClick(), "timeHint");
        timePieChart.loadUrl("file:///android_asset/assess_energy_time.html");
        timePieChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                timePieChart.loadUrl("javascript:createChart(" + new Gson().toJson(ntrtAmtMap) + ");");
                Log.d(TAG, "onPageFinished: " + ntrtAmtMap.toString());
            }
        });

    }

    private class TimeHintClick {

        @JavascriptInterface
        void onClick() {
            ((AssessActivity) getActivity()).clickHint("file:///android_asset/hint_energy_time.html");
        }
    }


    public static class NtrtRowViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.view_ntrt_color)
        View colorBlock;
        @Bind(R.id.tv_ntrt)
        TextView tvNtrt;
        @Bind(R.id.tv_intakes)
        TextView tvIntakes;
        @Bind(R.id.tv_level)
        TextView tvLevel;
        @Bind(R.id.img_level)
        ImageView imgLevel;
        @Bind(R.id.img_detail)
        ImageView imgDetail;

        Context context;

        public NtrtRowViewHolder(Context context, View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.context = context;
        }

        public void bind(String kind, int intakes, int standard) {
            Log.d(TAG, "bind: " + kind + ", " + intakes + ", " + standard);
//            tvNtrt.setText(kind);
//            tvIntakes.setText(intakes + "kcal/" + standard + "kcal");
            imgDetail.setVisibility(View.INVISIBLE);
            switch (kind) {
                case Nutrition.NUTRITION_PROTEIN:
                    colorBlock.setBackgroundColor(Color.parseColor("#BF3630"));
                    tvNtrt.setText(EnergyStandard.ENG_STDRD_BREAKFAST);
                    intakes *= 4;
                    break;
                case Nutrition.NUTRITION_FAT:
                    colorBlock.setBackgroundColor(Color.parseColor("#6AB1B5"));
                    tvNtrt.setText(EnergyStandard.ENG_STDRD_DINNER);
                    intakes *= 9;
                    break;
                case Nutrition.NUTRITION_CARBOHYDRATE:
                    colorBlock.setBackgroundColor(Color.parseColor("#2E4451"));
                    tvNtrt.setText(EnergyStandard.ENG_STDRD_LUNCH);
                    intakes *= 4;
                    break;
            }
            if ((double) intakes / (double) standard > 1.2) {
                tvLevel.setText(context.getResources().getString(R.string.assess_ntrt_high));
                imgLevel.setImageResource(R.drawable.ic_assess_high);
                imgDetail.setVisibility(View.VISIBLE);
            } else if ((double) intakes / (double) standard < 0.8) {
                tvLevel.setText(context.getResources().getString(R.string.assess_ntrt_low));
                imgLevel.setImageResource(R.drawable.ic_assess_low);
                imgDetail.setVisibility(View.VISIBLE);
            }
            tvIntakes.setText(intakes + "/" + standard + "kcal");

        }
    }
}
