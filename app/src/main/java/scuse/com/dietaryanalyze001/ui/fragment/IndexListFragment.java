package scuse.com.dietaryanalyze001.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;

import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.dgo.IndexMealDatas;
import scuse.com.dietaryanalyze001.logic.subclass.Dishes;
import scuse.com.dietaryanalyze001.ui.activity.AddActivity;
import scuse.com.dietaryanalyze001.ui.activity.DetailsActivity;
import scuse.com.dietaryanalyze001.ui.adapter.IndexListAdapter;
import scuse.com.dietaryanalyze001.ui.view.WaveSideBarView;

public class IndexListFragment extends Fragment implements IndexListAdapter.OnIndexItemClickListener {

    private static final String TAG = "IndexListFragment";

    @Bind(R.id.rv_food_list)
    RecyclerView rvFoodList;
    @Bind(R.id.side_view)
    WaveSideBarView sideView;

    private IndexListAdapter mAdapter;
    private IndexMealDatas mDatas;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            rvFoodList.setLayoutManager(linearLayoutManager);
            mAdapter = new IndexListAdapter(getActivity(), mDatas);
            mAdapter.setOnIndexItemClickListener(IndexListFragment.this);
            sideView.setOnTouchLetterChangeListener(new WaveSideBarView.OnTouchLetterChangeListener() {
                @Override
                public void onLetterChange(String letter) {
                    int pos = mDatas.getClosestTitle(letter);
                    if (pos != -1) {
                        rvFoodList.scrollToPosition(pos);
                        LinearLayoutManager mLayoutManager = (LinearLayoutManager) rvFoodList.getLayoutManager();
                        mLayoutManager.scrollToPositionWithOffset(pos, 0);
                    }
                }
            });
            rvFoodList.setAdapter(mAdapter);
        }
    };

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            AVQuery<Dishes> query = new AVQuery<>("Dishes");
            query.limit(40);
            try {
                List<Dishes> dishes = query.find();
                mDatas.addAll(dishes);
            } catch (AVException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    };

    public IndexListFragment() {
    }

    public static IndexListFragment newInstance(String param1, String param2) {
        return new IndexListFragment();
    }

    @Override
    public void onDetailsClick(int position) {
        Log.d(TAG, "onDetailsClick: " + position);
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(DetailsActivity.MEAL_DETAIL_BEAN,
                Parcels.wrap(mDatas.getArrayDatas().get(position)));
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        ((AddActivity) getActivity()).showAddDialog((Dishes) mDatas.getArrayDatas().get(position));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_index_list, container, false);
        ButterKnife.bind(this, rootView);

        mDatas = new IndexMealDatas();

        new Thread(runnable).start();
        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
