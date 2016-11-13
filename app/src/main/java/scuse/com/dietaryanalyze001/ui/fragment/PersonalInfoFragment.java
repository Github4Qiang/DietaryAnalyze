package scuse.com.dietaryanalyze001.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.ui.activity.RegisterActivity;

public class PersonalInfoFragment extends Fragment implements RegisterActivity.onRegisterForPersonalInfoListener {

    @Bind(R.id.rg_register_gender)
    RadioGroup rgGender;
    @Bind(R.id.rb_register_male)
    RadioButton rbMale;
    @Bind(R.id.rb_register_famale)
    RadioButton rbFamale;
    @Bind(R.id.et_register_dob)
    EditText etDob;
    @Bind(R.id.et_register_height)
    EditText etHeight;
    @Bind(R.id.et_register_weight)
    EditText etWeight;

    public boolean cancel = false;

    public PersonalInfoFragment() {
    }

    public static PersonalInfoFragment newInstance(String param1, String param2) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnPersonalInfoSwitcherListener) {
//            mListener = (OnPersonalInfoSwitcherListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnPersonalInfoSwitcherListener");
//        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        ButterKnife.bind(this, rootView);

        rgGender.requestFocus();
        etDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((RegisterActivity) getActivity()).showDatePickerDialog(etDob);
                }
            }
        });
        etWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.ime_register_next_page || id == EditorInfo.IME_NULL) {
                    ((RegisterActivity) getActivity()).onPersonalInfoSwitcher(etWeight);
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public String getInfoGender() {
        switch (rgGender.getCheckedRadioButtonId()) {
            case R.id.rb_register_famale:
                return getResources().getString(R.string.gender_female);
            case R.id.rb_register_male:
                return getResources().getString(R.string.gender_male);
        }
        return null;
    }

    @Override
    public Date getInfoDob() {
        String str = etDob.getText().toString();
        if (!str.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = sdf.parse(str);
                return date;
            } catch (ParseException e) {
                e.printStackTrace();
                ((RegisterActivity) getActivity()).remodifier(etDob, 0);
                etDob.setError(getResources().getString(R.string.error_field_required));
            }
        } else {
            ((RegisterActivity) getActivity()).remodifier(etDob, 0);
            etDob.setError(getResources().getString(R.string.error_field_required));
        }
        cancel = true;
        return null;
    }

    @Override
    public Integer getInfoHeight() {
        String str = etHeight.getText().toString();
        if (!str.equals("")) {
            return Integer.parseInt(str);
        } else {
            ((RegisterActivity) getActivity()).remodifier(etHeight, 0);
            etHeight.setError(getResources().getString(R.string.error_field_required));
            cancel = true;
            return null;
        }
    }

    @Override
    public Double getInfoWeight() {
        String str = etWeight.getText().toString();
        if (!str.equals("")) {
            return Double.parseDouble(str);
        } else {
            ((RegisterActivity) getActivity()).remodifier(etWeight, 0);
            etWeight.setError(getResources().getString(R.string.error_field_required));
            cancel = true;
            return null;
        }
    }

    @Override
    public boolean isCanceled() {
        return cancel;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
