package scuse.com.dietaryanalyze001.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RefreshCallback;
import com.avos.avoscloud.SignUpCallback;

import butterknife.Bind;
import butterknife.ButterKnife;
import scuse.com.dietaryanalyze001.R;
import scuse.com.dietaryanalyze001.logic.subclass.UserInfo;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String[] mailDomain = {"@qq.com", "@gmail.com", "@163.com", "@sina.com"};

    @Bind(R.id.content)
    CoordinatorLayout mContent;
    @Bind(R.id.email)
    AutoCompleteTextView mEmail;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.email_sign_in_button)
    Button mSignIn;
    @Bind(R.id.email_register_button)
    Button mRegister;

    private ArrayAdapter<String> twAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loadFromPreferences();

        twAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line);
        mEmail.addTextChangedListener(getTextWatcher());
        mEmail.setAdapter(twAdapter);

        mPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.login || actionId == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    private void attemptLogin() {

        mPassword.setError(null);
        mEmail.setError(null);

        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password address.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!isPasswordValid(email)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            // 登录 -> 根据 id 刷新内存中的单例 UserInfo 对象
            AVUser.logInInBackground(email, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null && avUser != null) {
                        UserInfo.setInstanceById(avUser.getAVObject("userInfo").getObjectId(), new RefreshCallback<AVObject>() {
                            @Override
                            public void done(AVObject userInfo, AVException e) {
                                if (e == null) {
                                    saveToPreferences(email, password);

                                    Intent intent = new Intent(LoginActivity.this, DiaryActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                } else {
                                    Log.e(TAG, "done: for RefreshCallback\n", e);
                                }
                            }
                        });
                    } else if (e == null && avUser == null) {
                        Log.d(TAG, "done: Password or Email error!");
                        Snackbar.make(mContent, getResources().getString(R.string.error_incorrect_password), Snackbar.LENGTH_SHORT).show();
                    } else {
                        Log.e(TAG, "done: from logInInBackground\n", e);
                        Snackbar.make(mContent, "您输入的邮箱不存在或密码错误!", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void loadFromPreferences() {
        SharedPreferences preferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        mEmail.setText(preferences.getString("email", ""));
        mPassword.setText(preferences.getString("password", ""));
    }

    private void saveToPreferences(String email, String password) {
        SharedPreferences preferences = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();
    }

    private void attemptRegister() {

        mPassword.setError(null);
        mEmail.setError(null);

        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password address.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!isPasswordValid(email)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmail.setError(getString(R.string.error_invalid_email));
            focusView = mEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            final AVUser user = new AVUser();
            user.setUsername(email);
            user.setEmail(email);
            user.setPassword(password);
            SignUpCallback signUpCallback = new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        AVUser.logInInBackground(email, password, new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e == null) {
                                    saveToPreferences(email, password);

                                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                                    startActivity(intent);
                                    LoginActivity.this.finish();
                                } else {
                                    Log.e(TAG, "done: logInInBackground" + avUser.toString(), e);
                                }
                            }
                        });
                    } else {
                        switch (e.getCode()) {
                            case 202:
                            case 203:
                                Snackbar.make(mContent, "该邮箱已被注册!", Snackbar.LENGTH_SHORT).show();
                                break;
                            default:
                                Snackbar.make(mContent, "请检查网络连接后重新注册!", Snackbar.LENGTH_SHORT).show();
                                Log.e(TAG, "done: ", e);
                                break;
                        }
                    }
                }
            };

            user.signUpInBackground(signUpCallback);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    private TextWatcher getTextWatcher() {
        TextWatcher mTextWatcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                twAdapter.clear();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                for (String mail : mailDomain) {
                    twAdapter.add(s.toString() + mail);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        return mTextWatcher;
    }

}
