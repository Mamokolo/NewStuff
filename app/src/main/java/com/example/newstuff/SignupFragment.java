package com.example.newstuff;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class SignupFragment extends Fragment {
    private Activity activity;
    private EditText signUpName,signUpPassword,confirmSignUpPassword;
    private Button confirmSignUpBtn;
    private boolean isUsernameFilled=false,isPasswordFilled=false,isConfirmPasswordFilled=false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(R.string.textSignUp);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        signUpName = view.findViewById(R.id.signUpName);
        signUpPassword = view.findViewById(R.id.signUpPassword);
        confirmSignUpPassword = view.findViewById(R.id.signUpConfirmPassword);
        confirmSignUpBtn = view.findViewById(R.id.confirmSignUpBtn);
        confirmSignUpBtn.setEnabled(false);
        //signUpUsername
        signUpName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(signUpName.getText().toString().length()==16){
                    signUpName.setError(getString(R.string.textUsernameMaxLengthWarning));
                }
                if(TextUtils.isEmpty(signUpName.getText().toString())){
                    isUsernameFilled=false;
                }
                else{
                    isUsernameFilled=true;
                }
                if(isUsernameFilled&&isPasswordFilled&&isConfirmPasswordFilled){
                    confirmSignUpBtn.setEnabled(true);
                }
                else{
                    confirmSignUpBtn.setEnabled(false);
                }
            }
        });
        //signUpPassword
        signUpPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(signUpPassword.getText().toString().length()==18){
                    signUpPassword.setError(getString(R.string.textPasswordMaxLengthWarning));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(signUpPassword.getText().toString().length()==18){
                    signUpPassword.setError(getString(R.string.textPasswordMaxLengthWarning));
                }
                if(signUpPassword.getText().toString().length()<6){
                    signUpPassword.setError(getString(R.string.textPasswordIsTooShort));
                }
                if(TextUtils.isEmpty(signUpPassword.getText().toString())){
                    isPasswordFilled=false;
                }
                else{
                    isPasswordFilled=true;
                }
                if(isUsernameFilled&&isPasswordFilled&&isConfirmPasswordFilled){
                    confirmSignUpBtn.setEnabled(true);
                }
                else{
                    confirmSignUpBtn.setEnabled(false);
                }
            }
        });
        //signUpPasswordConfirm
        confirmSignUpPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(confirmSignUpPassword.getText().toString())){
                    isConfirmPasswordFilled=false;
                }
                else{
                    isConfirmPasswordFilled=true;
                }
                if(isUsernameFilled&&isPasswordFilled&&isConfirmPasswordFilled){
                    confirmSignUpBtn.setEnabled(true);
                }
                else{
                    confirmSignUpBtn.setEnabled(false);
                }
            }
        });
        confirmSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = signUpName.getText().toString();
                String password = signUpPassword.getText().toString();
                String passwordConfirm = confirmSignUpPassword.getText().toString();

                if(password.equals(passwordConfirm)){
                    //TODO Check if there is duplicate username
                    if(name!=getString(R.string.textAuthName)){
                        Bundle bundle = new Bundle();
                        bundle.putString("name",name);
                        bundle.putString("password",password);
                        Navigation.findNavController(view).navigate(R.id.signUpSuccessFragment,bundle);
                    }
                }
                else{
                    confirmSignUpPassword.setError(getString(R.string.textConfirmPasswordFailed));
                }
            }
        });
    }
}