package com.example.newstuff;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class LoginFragment extends Fragment {

    private Activity activity;
    private EditText loginName,loginPassword;
    private Button loginBtn,signUpBtn;
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


                if(name.equals(getString(R.string.textauthName))&&password.equals(getString(R.string.textauthPassword))){
                    Bundle bundle = new Bundle();
                    bundle.putString("name",name);
                    bundle.putString("password",password);
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

    }
    public void DialogInit() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginFragment.this.activity);
        alertDialog.setTitle(R.string.textloginFailed);
        alertDialog.setMessage(R.string.textloginFailedText);
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