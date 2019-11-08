package com.example.arishti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arishti.Retrofit.IMyService;
import com.example.arishti.Retrofit.RetrofitClient;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private static final int SIGNUP_REQUEST = 1;
    private static final int LOGIN_REQUEST = 1;
    MaterialEditText edit_login_email, edit_login_password;
    Button btn_login, btn_signup;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    public static int stringCompare(String str1, String str2) {
        int l1 = str1.length();
        int l2 = str2.length();
        int lmin = Math.min(l1, l2);

        for (int i = 0; i < lmin; i++) {
            int str1_ch = (int) str1.charAt(i);
            int str2_ch = (int) str2.charAt(i);

            if (str1_ch != str2_ch) {
                return str1_ch - str2_ch;
            }
        }

        // Edge case for strings like
        // String 1="Geeks" and String 2="Geeksforgeeks"
        if (l1 != l2) {
            return l1 - l2;
        }

        // If none of the above conditions is true,
        // it implies both the strings are equal
        else {
            return 0;
        }
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        edit_login_email = (MaterialEditText) findViewById(R.id.edit_uid);
        edit_login_password = (MaterialEditText) findViewById(R.id.edit_password);

        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this, profile_page.class);
                startActivityForResult(intent, LOGIN_REQUEST);*/
                if (TextUtils.isEmpty(edit_login_email.getText())) {
                    Toast.makeText(MainActivity.this, "Email id cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(edit_login_password.getText())) {
                    Toast.makeText(MainActivity.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                loginUser(edit_login_email.getText().toString(),
                        edit_login_password.getText().toString());
            }
        });

        Button submit = findViewById(R.id.signup_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, login_page.class);
                startActivityForResult(intent, SIGNUP_REQUEST);
            }
        });
    }

    private void loginUser(String email, String password) {
        compositeDisposable.add(iMyService.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(MainActivity.this, "" + s, Toast.LENGTH_SHORT).show();
                        JSONObject j = (JSONObject) new JSONObject(s);
                        String x = j.getString("message");
                        String suc = "Login Success";
                        if (stringCompare(x, suc) == 0) {
                            Intent intent = new Intent(MainActivity.this, threetab_slider.class);
                            startActivity(intent);
                        }
                    }
                }));
    }
}
