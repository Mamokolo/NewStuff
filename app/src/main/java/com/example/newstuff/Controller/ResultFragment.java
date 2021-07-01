package com.example.newstuff.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import com.example.newstuff.GameActivity;
import com.example.newstuff.R;


public class ResultFragment extends Fragment {
    private Activity activity;
    private TextView textResult;
    private Button easyBtn,mediumBtn,hardBtn,logoutBtn;
    private ImageButton flash,heal,shield;
    private ImageView frameFlash,frameHeal,frameShield;
    private int whichFragment = 0;
    private boolean loginFlag=true;
    private Bundle bundle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(R.string.textDifficulty);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getArguments();
        if(bundle==null){
            Navigation.findNavController(view).navigate(R.id.loginFragment);
        }
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


        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.containsKey("skill")){
                    if(bundle.getString("name") == null || bundle.getString("password") == null){
                        System.out.println("WRONG");
                    }
                    //System.out.println(bundle.getString("skill"));
                    bundle.putString("difficulty","easy");
                    //Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
                    Intent intent = new Intent(getActivity(), GameActivity.class);
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }
                else{

                }

            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.containsKey("skill")){
                    //System.out.println(bundle.getString("skill"));
                    bundle.putString("difficulty","medium");
                    Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
                }
                else{
                }
            }
        });

        hardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(bundle.getString("skill"));
                if(bundle.containsKey("skill")){
                    bundle.putString("difficulty","hard");
                    Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
                }

            }
        });

        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("skill",getString(R.string.textFlash));
                frameFlash.setVisibility(View.VISIBLE);
                frameHeal.setVisibility(View.INVISIBLE);
                frameShield.setVisibility(View.INVISIBLE);
                System.out.println(bundle.get("skill"));
            }
        });

        heal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("skill",getString(R.string.textHeal));
                frameFlash.setVisibility(View.INVISIBLE);
                frameHeal.setVisibility(View.VISIBLE);
                frameShield.setVisibility(View.INVISIBLE);
            }
        });

        shield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("skill",getString(R.string.textShield));
                frameFlash.setVisibility(View.INVISIBLE);
                frameHeal.setVisibility(View.INVISIBLE);
                frameShield.setVisibility(View.VISIBLE);
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
        }
    }

    @Override
    public void onStart() {//接取GameActivity 回來的資料 確認是藉由哪一種形式回到MainActivity
        super.onStart();
        Bundle bundleFromGameActivity=null;
        if(activity.getIntent().getBundleExtra("bundle")!=null){
            bundleFromGameActivity = activity.getIntent().getBundleExtra("bundle");
            bundle.putBoolean("loginFlag",bundleFromGameActivity.getBoolean("loginFlag"));
            activity.getIntent().removeExtra("bundle");
        }
        System.out.println(bundle.getBoolean("loginFlag"));
        if(!bundle.getBoolean("loginFlag")){
            whichFragment = getActivity().getIntent().getIntExtra("whichFragment",0);
            System.out.println("whichFragment: "+ whichFragment);
            switch (whichFragment){
                case 1://logout
                    System.out.println(1);
                    NavController navController = Navigation.findNavController(getView());
                    navController.popBackStack(R.id.resultFragment,true);
                    navController.popBackStack(R.id.loginFragment,true);
                    navController.popBackStack(R.id.signupFragment,true);
                    navController.popBackStack(R.id.signUpSuccessFragment,true);
                    navController.navigate(R.id.loginFragment);
                    break;
                case 2://StayResultFragment
                    break;
                case 3:
                    Navigation.findNavController(getView()).navigate(R.id.scoreFragment,bundleFromGameActivity);
                    break;
            }
        }
    }
}