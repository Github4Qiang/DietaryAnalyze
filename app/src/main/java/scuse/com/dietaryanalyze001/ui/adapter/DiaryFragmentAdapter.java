package scuse.com.dietaryanalyze001.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;

import java.util.ArrayList;

import scuse.com.dietaryanalyze001.ui.fragment.DiaryFragment;

/**
 * Created by Polylanger on 2016/10/18.
 */
public class DiaryFragmentAdapter extends FragmentStatePagerAdapter {

    ArrayList<DiaryFragment> fragments;
    Context context;

    public DiaryFragmentAdapter(FragmentManager fm, Context context, ArrayList<DiaryFragment> fragments) {
        super(fm);
        this.context = context;
        this.fragments = fragments;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }
}
