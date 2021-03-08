package com.example.newstuff;

import android.app.Activity;
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


public class ResultFragment extends Fragment {
    private Activity activity;
    private TextView textResult;
    private Button easyBtn,mediumBtn,hardBtn,logoutBtn;
    private ImageButton flash,heal,shield;
    private ImageView frameFlash,frameHeal,frameShield;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static ResultFragment newInstance(String param1, String param2) {
        ResultFragment fragment = new ResultFragment();
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
        activity.setTitle(R.string.textHellow);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle==null){
            Navigation.findNavController(view).navigate(R.id.mainFragment);
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


        easyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bundle.containsKey("skill")){
                    //System.out.println(bundle.getString("skill"));
                    bundle.putString("difficulty","easy");
                    Navigation.findNavController(view).navigate(R.id.GameFragment,bundle);
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
                navController.popBackStack(R.id.mainFragment,true);
                navController.popBackStack(R.id.signupFragment,true);
                navController.popBackStack(R.id.signUpSuccessFragment,true);
                navController.navigate(R.id.mainFragment);
            }
        });


        if(bundle!=null){
            String name = bundle.getString("name");
            String password  = bundle.getString("password");
            String Text = "name: "+name+"\npassword: "+password;
            textResult.setText(Text);

        }
    }
}