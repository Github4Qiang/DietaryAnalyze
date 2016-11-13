package scuse.com.dietaryanalyze001.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.FindCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.ui.activity.AddActivity;
import scuse.com.dietaryanalyze001.ui.adapter.SearchListAdapter;

/**
 * Created by Polylanger on 2016/9/23.
 */
public class SearchListFragment extends Fragment implements SearchListAdapter.OnSearchItemClickListener, AddActivity.SearchFoodListListener {

    private static final String TAG = "SearchListFragment";

    @Bind(R.id.rv_food_list)
    RecyclerView rvFoodList;

    private Context context;
    private SearchListAdapter mAdapter;
    private List<Dishes> mdatas;

    public static SearchListFragment newInstance() {
        return new SearchListFragment();
    }

    public SearchListFragment() {
    }


    @Override
    public void search(String title) {
        this.context = getActivity();
        FindCallback<Dishes> callback = new FindCallback<Dishes>() {
            @Override
            public void done(List<Dishes> list, AVException e) {
                if (e == null) {
                    mdatas = list;
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    // 瀑布流模型显示
                    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
                    rvFoodList.setLayoutManager(staggeredGridLayoutManager);
                    mAdapter = new SearchListAdapter(context, mdatas);
                    mAdapter.setOnSearchItemClickListener(SearchListFragment.this);
                    rvFoodList.setAdapter(mAdapter);
                } else {
                    Log.e(TAG, "done: search", e);
                }
            }
        };
        Dishes.FuzzyQuery(title, callback);
    }

    @Override
    public void onDetailsClick(int position) {
        Log.d(TAG, "onDetailsClick: ");
    }

    @Override
    public void onItemClick(int position) {
        ((AddActivity) getActivity()).showAddDialog(mdatas.get(position));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search_list, container, false);
        ButterKnife.bind(this, rootView);
        ((AddActivity) getActivity()).setupSearchFoodListListener(SearchListFragment.this);
        return rootView;
    }


}
