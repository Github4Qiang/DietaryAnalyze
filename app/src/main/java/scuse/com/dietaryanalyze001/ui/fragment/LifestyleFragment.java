package scuse.com.dietaryanalyze001.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.ui.activity.RegisterActivity;

public class LifestyleFragment extends Fragment implements RegisterActivity.onRegisterForLifestyleListener {

    private static final String HOURS_OF_SLEEP = " 小时/天";
    private static final String DAYS_PER_WEEK = " 天/周";
    private static final String HOURS_PER_DAY = " 小时/天";

    @Bind(R.id.spinner_register_sleep_time)
    Spinner spSleepHours;
    @Bind(R.id.spinner_register_job_kind)
    Spinner spJobKind;
    @Bind(R.id.spinner_register_days)
    Spinner spWorkDays;
    @Bind(R.id.spinner_register_hours)
    Spinner spWorkHours;
    @Bind(R.id.spinner_register_free_time)
    Spinner spFreeIntensity;

    private boolean cancel = false;

    private ArrayList<String> arrayTime = new ArrayList<>();
    private ArrayList<String> arrayDays = new ArrayList<>();
    private ArrayList<String> arrayHours = new ArrayList<>();

    public LifestyleFragment() {
    }

    public static LifestyleFragment newInstance(String param1, String param2) {
        LifestyleFragment fragment = new LifestyleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i <= 24; i++) {
            arrayTime.add(i + HOURS_OF_SLEEP);
        }
        for (int i = 0; i <= 7; i++) {
            arrayDays.add(i + DAYS_PER_WEEK);
        }
        for (int i = 0; i <= 24; i++) {
            arrayHours.add(i + HOURS_PER_DAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lifestyle, container, false);
        ButterKnife.bind(this, rootView);

        ArrayAdapter<String> adapterTime = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayTime);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSleepHours.setAdapter(adapterTime);
        spSleepHours.setSelection(8, true);
        ArrayAdapter<String> adapterDays = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayDays);
        adapterDays.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWorkDays.setAdapter(adapterDays);
        spWorkDays.setSelection(5, true);
        ArrayAdapter<String> adapterHours = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arrayHours);
        adapterHours.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWorkHours.setAdapter(adapterHours);
        spWorkHours.setSelection(8, true);

        return rootView;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public Integer getSleepHours() {
        String str = spSleepHours.getSelectedItem().toString();
        return Integer.parseInt((str.split(" "))[0]);
    }

    @Override
    public String getJobKind() {
        return spJobKind.getSelectedItem().toString();
    }

    @Override
    public Integer getWorkHours() {
        String str = spWorkHours.getSelectedItem().toString();
        return Integer.parseInt((str.split(" "))[0]);
    }

    @Override
    public Integer getWorkDays() {
        String str = spWorkDays.getSelectedItem().toString();
        return Integer.parseInt((str.split(" "))[0]);
    }

    @Override
    public String getFreeIntensity() {
        return spFreeIntensity.getSelectedItem().toString();
    }

    @Override
    public boolean isCanceled() {
        return cancel;
    }
}
