package com.example.newstuff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class ResultFragment extends Fragment {
    private Activity activity;
    private TextView textResult;
    private Button easyBtn,mediumBtn,hardBtn,logoutBtn;
    private ImageButton flash,heal,shield;
    private ImageView frameFlash,frameHeal,frameShield;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();//show title bar
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(R.string.textHellow);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle==null){
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }
        textResult = view.findViewById(R.id.loginResult);
        easyBtn = view.findViewById(R.id.easyBtn);
        mediumBtn = view.findViewById(R.id.mediumBtn);
        hardBtn = view.findViewById(R.id.hardBtn);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        flash = view.findViewById(R.id.flashBtn_2);
        heal = view.findViewById(R.id.healBtn2);
        shield = view.findViewById(R.id.shieldBtn2);
        frameFlash = view.findViewById(R.id.imageFrameFlash);
        frameHeal = view.findViewById(R.id.imageFrameHeal);
        frameShield = view.findViewById(R.id.imageFrameShield);

        hideNavigationBar();


        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.containsKey("skill")){
                    //System.out.println(bundle.getString("skill"));
                    bundle.putString("level","easy");

                    //Intent intent = new Intent(activity, GameActivity.class);
                    //startActivity(intent);

                    Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
                }
                else{

                }

            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert bundle != null;
                if(bundle.containsKey("skill")){
                    //System.out.println(bundle.getString("skill"));
                    bundle.putString("level","medium");
                    Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
                }
                else{
                    //do something someday
                }
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(bundle.getString("skill"));
                assert bundle != null;
                if(bundle.containsKey("skill")){
                    bundle.putString("level","hard");
                    Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
                }

            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert bundle != null;
                bundle.putString("skill",getString(R.string.textFlash));
                frameFlash.setVisibility(View.VISIBLE);
                frameHeal.setVisibility(View.INVISIBLE);
                frameShield.setVisibility(View.INVISIBLE);
                System.out.println("choose"+bundle.get("skill"));
            }
        });

        heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert bundle != null;
                bundle.putString("skill",getString(R.string.textHeal));
                frameFlash.setVisibility(View.INVISIBLE);
                frameHeal.setVisibility(View.VISIBLE);
                frameShield.setVisibility(View.INVISIBLE);
                System.out.println("choose"+bundle.get("skill"));
            }
        });

        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert bundle != null;
                bundle.putString("skill",getString(R.string.textShield));
                frameFlash.setVisibility(View.INVISIBLE);
                frameHeal.setVisibility(View.INVISIBLE);
                frameShield.setVisibility(View.VISIBLE);
                System.out.println("choose"+bundle.get("skill"));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack(R.id.resultFragment,true);
                navController.popBackStack(R.id.loginFragment,true);
                navController.popBackStack(R.id.signupFragment,true);
                navController.popBackStack(R.id.signUpSuccessFragment,true);
                navController.navigate(R.id.loginFragment);
            }
        });


        if(bundle!=null){
            String name = bundle.getString("name");
            String password  = bundle.getString("password");
            String Text = "name: "+name+"\npassword: "+password;
            textResult.setText(Text);

        }
    }
    public void hideNavigationBar(){
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }
}