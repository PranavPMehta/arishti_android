package com.example.arishti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.arishti.Retrofit.IMyService;
import com.example.arishti.Retrofit.RetrofitClient;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class login_page extends AppCompatActivity {
    private static final int PROGRESSBAR_REQUEST = 1;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;
    Button btn_register;
    MaterialEditText edit_login_email, edit_login_password, edit_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        final MaterialEditText edt_register_email = (MaterialEditText) findViewById(R.id.edit_emailid);
        final MaterialEditText edt_register_name = (MaterialEditText) findViewById(R.id.edit_name);
        final MaterialEditText edt_register_password = (MaterialEditText) findViewById(R.id.edit_password);
        final MaterialEditText edt_register_username = (MaterialEditText) findViewById(R.id.edit_username);
        final MaterialEditText edt_register_mobile = (MaterialEditText) findViewById(R.id.edit_mobileno);
        final MaterialEditText edt_register_designation = (MaterialEditText) findViewById(R.id.edit_designation);


        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        Button register = findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Register button clicked", Toast.LENGTH_SHORT).show();
                if (TextUtils.isEmpty(edt_register_email.getText().toString())) {
                    Toast.makeText(login_page.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edt_register_name.getText().toString())) {
                    Toast.makeText(login_page.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(edt_register_password.getText().toString())) {
                    Toast.makeText(login_page.this, "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                registerUser(edt_register_email.getText().toString(),
                        edt_register_name.getText().toString(),
                        edt_register_password.getText().toString(),
                        edt_register_username.getText().toString(),
                        edt_register_mobile.getText().toString(),
                        edt_register_designation.getText().toString());
            }
        });
    }

    private void registerUser(String email, String name, String password, String username, String mobileno, String designation) {
        compositeDisposable.add(iMyService.registerUser(email, name, password,username,mobileno,designation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Toast.makeText(login_page.this, "" + s, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login_page.this, profile_page.class);
                        startActivityForResult(intent, PROGRESSBAR_REQUEST);
                    }
                }));
    }
}
