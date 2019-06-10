package com.example.practise;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practise.Retrofit.IMyService;
import com.example.practise.Retrofit.RetrofitClient;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Button btn_login,btn_register;
    EditText edit_login_email,edit_login_password;

    CompositeDisposable compositeDisposable=new CompositeDisposable();
    IMyService iMyService;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //String name="Somnath";

        //Init Service
        Retrofit retrofit=RetrofitClient.getInstance();
        iMyService=retrofit.create(IMyService.class);

        //Init View
        edit_login_email=findViewById(R.id.et1);
        edit_login_password=findViewById(R.id.et2);
        btn_login=findViewById(R.id.login);

    }

    public void login_function(View view) {
        String email=edit_login_email.getText().toString();
        String password=edit_login_password.getText().toString();

        compositeDisposable.add(iMyService.loginUser(email,password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String response) throws Exception {
                Toast.makeText(MainActivity.this, "yahooo!!", Toast.LENGTH_SHORT).show();
            }
        }));
    }
}
