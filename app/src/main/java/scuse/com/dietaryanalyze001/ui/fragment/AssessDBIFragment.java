package scuse.com.dietaryanalyze001.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.assess.Constant;
import scuse.com.dietaryanalyze001.logic.assess.DBIScore;
import scuse.com.dietaryanalyze001.ui.activity.AssessActivity;

/**
 * Created by Polylanger on 2016/10/27.
 */
public class AssessDBIFragment extends Fragment {
    private static final String TAG = "AssessDBIFragment";
    public static final String DBI_SCORE = "DBIScore";

    @Bind(R.id.pn_axis_chart)
    WebView wvPNAxisChart;
    @Bind(R.id.tv_comment_summarize)
    TextView tvSummarize;
    @Bind(R.id.tv_comment_less)
    TextView tvLess;
    @Bind(R.id.tv_comment_more)
    TextView tvMore;
    @Bind(R.id.tv_sggst_1)
    TextView tvSggst1;
    @Bind(R.id.tv_sggst_2)
    TextView tvSggst2;
    @Bind(R.id.tv_sggst_3)
    TextView tvSggst3;
    @Bind(R.id.tv_sggst_4)
    TextView tvSggst4;
    @Bind(R.id.tv_sggst_5)
    TextView tvSggst5;

    private DBIScore score;

    public AssessDBIFragment() {
    }

    public static AssessDBIFragment newInstance(String jsonDBIScore) {
        AssessDBIFragment fragment = new AssessDBIFragment();
        Bundle bundle = new Bundle();
        bundle.putString(DBI_SCORE, jsonDBIScore);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assess_dbi, container, false);
        ButterKnife.bind(this, rootView);

        final String dbiScore = getArguments().getString(DBI_SCORE);
        Log.d(TAG, "onCreateView: score" + dbiScore);

        wvPNAxisChart.getSettings().setAllowFileAccess(true);
        wvPNAxisChart.getSettings().setJavaScriptEnabled(true);
        wvPNAxisChart.addJavascriptInterface(new DBIHintClick(), "dbiHint");
        wvPNAxisChart.loadUrl("file:///android_asset/assess_dbi.html");
        wvPNAxisChart.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wvPNAxisChart.loadUrl("javascript:createChart(" + dbiScore + ");");
            }
        });

        score = new Gson().fromJson(dbiScore, DBIScore.class);

        setupComment();
        setupSuggestion();

        return rootView;
    }

    private class DBIHintClick {
        @JavascriptInterface
        void onClick() {
            ((AssessActivity) getActivity()).clickHint("file:///android_asset/hint_energy_dbi.html");
        }
    }

    private void setupSuggestion() {
        String suggestion1 = getResources().getString(R.string.DBI_kind_suggestions1);
        String suggestion2 = getResources().getString(R.string.DBI_kind_suggestions2);
        String suggestion3 = getResources().getString(R.string.DBI_kind_suggestions3);
        String suggestion4 = getResources().getString(R.string.DBI_kind_suggestions4);
        String suggestion5 = getResources().getString(R.string.DBI_kind_suggestions5);

        int minScore = 0;
        int maxScore = 0;
        for (String kind : score.getKindScores().keySet()) {
            int kindScore = score.getKindScores().get(kind);
            if (minScore > kindScore) {
                minScore = kindScore;
                tvSggst2.setText(String.format(suggestion2, Constant.getInstance().getKindChinese(kind)));
            }
            if (maxScore < kindScore) {
                maxScore = kindScore;
                tvSggst3.setText(String.format(suggestion3, Constant.getInstance().getKindChinese(kind)));
            }
        }
        tvSggst1.setText(suggestion1);
        tvSggst4.setText(suggestion4);
        tvSggst5.setText(suggestion5);
    }

    private void setupComment() {

        String summarize = strcatSummarize();
        String muchComment = strcatMuchComment();
        String lessComment = strcatLessComment();

        tvSummarize.setText(summarize);
        tvMore.setText(muchComment);
        tvLess.setText(lessComment);

    }

    private String strcatMuchComment() {
        String content1 = getResources().getString(R.string.DBI_kind_comments_content1);
        String content2 = "";
        String more = "";
        int maxScore = 0;
        for (String kind : score.getKindScores().keySet()) {
            int kindScore = score.getKindScores().get(kind);
            if (kindScore >= 4) {
                String cKind = Constant.getInstance().getKindChinese(kind);
                more += String.format(content1, cKind, kindScore) + "、";
                if (kindScore > maxScore) {
                    content2 = getResources().getString(R.string.DBI_kind_comments_content2, getResources().getString(R.string.DBI_kind_comments_intake_much), cKind);
                    maxScore = kindScore;
                }
            }
        }
        more = more.substring(0, more.length() - 1);
        return more + content2;
    }

    private String strcatLessComment() {
        String content1 = getResources().getString(R.string.DBI_kind_comments_content1);
        String content2 = "";
        String less = "";
        int minScore = 0;
        for (String kind : score.getKindScores().keySet()) {
            int kindScore = score.getKindScores().get(kind);
            if (kindScore <= -4) {
                String cKind = Constant.getInstance().getKindChinese(kind);
                less += String.format(content1, cKind, kindScore) + "、";
                if (kindScore < minScore) {
                    content2 = getResources().getString(R.string.DBI_kind_comments_content2, getResources().getString(R.string.DBI_kind_comments_intake_less), cKind);
                    minScore = kindScore;
                }
            }
        }
        less = less.substring(0, less.length() - 1);
        return less + content2;
    }

    private String strcatSummarize() {
        String summarize1;
        String summarize3;
        String summarize2;
        String summarize4;

        int LBS = score.getLBScore();
        int HBS = score.getHBScore();
        int TTS = score.getTotalScore();
        int distance = score.getDQDistance();

        if (TTS > 0) {
            summarize1 = "呈摄入不足趋势";
        } else if (TTS < 0) {
            summarize1 = "呈摄入过量趋势";
        } else {
            summarize1 = "基本平衡";
        }

        if (Math.abs(LBS) > Math.abs(HBS)) {
            summarize3 = getResources().getString(R.string.DBI_kind_comments_intake_less);
            int lbs = Math.abs(LBS);
            if (lbs < 14) {
                summarize2 = "轻度";
            } else if (lbs < 29) {
                summarize2 = "中度";
            } else {
                summarize2 = "高度";
            }
        } else {
            summarize3 = getResources().getString(R.string.DBI_kind_comments_intake_much);
            if (HBS < 13) {
                summarize2 = "轻度";
            } else if (HBS < 19) {
                summarize2 = "中度";
            } else {
                summarize2 = "高度";
            }
        }

        if (distance < 17) {
            summarize4 = "较适宜";
        } else if (distance < 34) {
            summarize4 = "存在低度不平衡问题";
        } else {
            summarize4 = "存在严重不平衡问题";
        }

        return getResources().getString(R.string.DBI_kind_comments_summarize, summarize1, summarize2, summarize3, summarize4);
    }
}
