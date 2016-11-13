package scuse.com.dietaryanalyze001.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;

public class ChartFragment extends Fragment {

    public static final String CHART_KIND = "chart kind";
    public static final String CHART_LINE = "line";
    public static final String CHART_PIE = "pie";
    public static final String CHART_BAR = "bar";
    private static final String TAG = "ChartFragment";
    @Bind(R.id.webview)
    WebView mWebView;

    private String chartKind;

    public ChartFragment() {
        this.chartKind = CHART_LINE;
    }

    public static ChartFragment newInstance(String chartKind, String param2) {
        ChartFragment fragment = new ChartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CHART_KIND, chartKind);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        ButterKnife.bind(this, rootView);
        this.chartKind = getArguments().getString(CHART_KIND);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("file:///android_asset/histogram.html");
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                switch (chartKind) {
                    case CHART_LINE:
                        Log.d(TAG, "onCreateView: " + chartKind);
                        mWebView.loadUrl("javascript:createChart('line', [89, 78, 77]);");
                        break;
                    case CHART_PIE:
                        Log.d(TAG, "onCreateView: " + chartKind);
                        mWebView.loadUrl("javascript:createChart('pie', [89, 78, 77]);");
                        break;
                    case CHART_BAR:
                        Log.d(TAG, "onCreateView: " + chartKind);
                        mWebView.loadUrl("javascript:createChart('bar', [89, 78, 77]);");
                        break;
                    default:
                        Log.e(TAG, "onCreateView: chart kind Error" + chartKind);
                        break;
                }
            }
        });
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
