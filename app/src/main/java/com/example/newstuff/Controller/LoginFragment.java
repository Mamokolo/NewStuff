package com.example.newstuff.Controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.newstuff.R;
/*
import org.jetbrains.annotations.NotNull;
import org.json.JSONStringer;
*/
import java.io.IOException;
/*
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
*/

public class LoginFragment extends Fragment {

    private Activity activity;
    private EditText loginName,loginPassword;
    private Button loginBtn,signUpBtn,exitBtn,websiteStn;
    private boolean isNameFilled=false,isPasswordFilled=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();//show the title
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(R.string.textLogin);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);

        loginName = view.findViewById(R.id.LoginName);
        loginPassword = view.findViewById(R.id.lgoinPassword);
        loginBtn = view.findViewById(R.id.loginBtn);
        exitBtn = view.findViewById(R.id.exitBtn);
        websiteStn = view.findViewById(R.id.buttonWebsite);


        loginBtn.setEnabled(false);
        loginName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginName.getText().toString().isEmpty()) {
                    isNameFilled = false;
                }
                else{
                    isNameFilled = true;
                }
                if(isNameFilled&&isPasswordFilled){
                    loginBtn.setEnabled(true);
                }
                else{
                    loginBtn.setEnabled(false);
                }
            }
        });
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (loginPassword.getText().toString().isEmpty()) {
                    isPasswordFilled = false;
                }
                else{
                    isPasswordFilled = true;
                }
                if(isNameFilled&&isPasswordFilled){
                    loginBtn.setEnabled(true);
                }
                else{
                    loginBtn.setEnabled(false);
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String name = loginName.getText().toString();
                String password = loginPassword.getText().toString();


                if(name.equals(getString(R.string.textAuthName))&&password.equals(getString(R.string.textAuthPassword))){
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("password",password);
                    bundle.putBoolean("loginFlag",true);
                    Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_resultFragment,bundle);
                }
                else{
                    DialogInit();
                }
            }
        });
        signUpBtn = view.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_signupFragment);
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(getString(R.string.textWarning));
                dialog.setCancelable(false);
                dialog.setMessage(getString(R.string.textLeaveGame));
                dialog.setPositiveButton(getString(R.string.textExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                    }
                });
                dialog.setNegativeButton(getString(R.string.textCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }
                });
                dialog.show();
            }
        });
        websiteStn.setOnClickListener(v -> {
            //sendGet();

            //sendPost("https://jsonplaceholder.typicode.com/posts", "[{\"userId\": \"1\",\"id\": \"1\",\"title\": \"Test okHttp\"}]");

        });

    }
    /*
    private void sendGet(){
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/posts")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                /*AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(getString(R.string.textWarning));
                dialog.setCancelable(false);
                dialog.setPositiveButton(getString(R.string.textExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                    }
                });
                dialog.show();

                System.out.println("FAIL "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(getString(R.string.textSuccess));
                dialog.setCancelable(false);
                dialog.setMessage(response.body().string());
                dialog.setPositiveButton(getString(R.string.textExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.show();

                System.out.println("SUCCESS "+response.body().string());
            }
        });
    }
    private void sendPost(String url, String json){
        final MediaType JSON = MediaType.parse("application/json; charset:utf-8");
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
        RequestBody body = RequestBody.create(JSON,json);//read json

        RequestBody requestBody = new MultipartBody.Builder()//hand in parameter
                .addFormDataPart("userid","1")
                .addFormDataPart("id","1")
                .addFormDataPart("title","Test okHttp")
                .build();

        Request request = new Request.Builder().url(url).post(requestBody).build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("FAIL "+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                System.out.println("SUCCESS "+response.body().string());
            }
        });


    }
    */
    public void DialogInit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginFragment.this.activity);
        alertDialog.setTitle(R.string.textLoginFailed);
        alertDialog.setMessage(R.string.textLoginFailedText);
        alertDialog.setPositiveButton("確定", ((dialog, which) -> {
        }));
        AlertDialog dialog = alertDialog.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener((v -> {
            setToast("確定");
            dialog.dismiss();
        }));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);

    }

    private void setToast(String 確定) {
    }
}