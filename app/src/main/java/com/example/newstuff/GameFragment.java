package com.example.newstuff;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GameFragment extends Fragment {

    private Activity activity;
    private TextView score,time;
    private Button pause;
    private ImageButton turnLeftbtn,turnRightbtn,skillBtn;
    private ImageView godtone;
    private int width,height,change;
    private int relatedPosition=0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 螢幕寬度（畫素）
        height = metric.heightPixels;   // 螢幕高度（畫素）

        System.out.println(width+" "+height);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity.setTitle(null);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        score = view.findViewById(R.id.textScore);
        time = view.findViewById(R.id.textTime);
        pause = view.findViewById(R.id.pauseBtn);
        turnLeftbtn = view.findViewById(R.id.imageButtonLeft);
        turnRightbtn = view.findViewById(R.id.imageButtonRight);
        skillBtn = view.findViewById(R.id.imageButtonSkill);
        godtone = view.findViewById(R.id.imageGodtone);
        change = width/6;

        if(bundle!=null){
            if(bundle.getString("skill").equals("flash")){
                System.out.println("flash");
                skillBtn.setImageDrawable(getResources().getDrawable(R.drawable.flash));
            }
            else if(bundle.getString("skill").equals("heal")){
                System.out.println("heal");
                skillBtn.setImageDrawable(getResources().getDrawable(R.drawable.heal));
            }
            else if(bundle.get("skill").equals("shield")){
                System.out.println("shield");
                skillBtn.setImageDrawable(getResources().getDrawable(R.drawable.shield));
            }
            else{
                System.out.println("Worng");
                AlertDialog.Builder wrongDialog = new AlertDialog.Builder(activity);
                wrongDialog.setTitle("SOMETHING WRONG");
                wrongDialog.setMessage(getString(R.string.textSkillWrong));
                wrongDialog.setCancelable(false);
                wrongDialog.setNeutralButton(getString(R.string.textOK), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bundle.remove("skill");
                        Navigation.findNavController(view).navigate(R.id.resultFragment,bundle);
                    }
                });
                wrongDialog.show();
            }
        }
        else{
            AlertDialog.Builder bundleNullDialog = new AlertDialog.Builder(activity);
            bundleNullDialog.setTitle("SOMETHING WRONG");
            bundleNullDialog.setMessage(getString(R.string.textBundleNull));
            bundleNullDialog.setCancelable(false);
            bundleNullDialog.setNeutralButton(getString(R.string.textOK), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    bundle.clear();
                    Navigation.findNavController(view).navigate(R.id.mainFragment);
                }
            });
            bundleNullDialog.show();
        }


        Animation turnLeftAnim = new TranslateAnimation(godtone.getX(),godtone.getX()-change,godtone.getY(),godtone.getY());
        Animation turnRightAnim = new TranslateAnimation(godtone.getX(),godtone.getX()+change,godtone.getY(),godtone.getY());

        turnLeftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relatedPosition>-2){
                    godtone.setAnimation(turnLeftAnim);
                    turnLeftAnim.startNow();
                    godtone.setX(godtone.getX() - change);
                    System.out.println("turn left");
                    relatedPosition -= 1;
                }
            }
        });
        turnRightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(relatedPosition<2){godtone.setAnimation(turnRightAnim);
                    turnRightAnim.startNow();
                    godtone.setX(godtone.getX() + change);
                    System.out.println("turn right");
                    relatedPosition += 1;
                }
            }
        });
        skillBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //godtone.setAnimation(AnimationUtils.loadAnimation(activity,R.anim.anim_turn_left));
                System.out.println("use skill");
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPause();
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("PAUSE");
                dialog.setCancelable(false);
                dialog.setPositiveButton(getString(R.string.textExit), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bundle.remove("skill");
                        Navigation.findNavController(view).navigate(R.id.resultFragment,bundle);
                    }
                });
                dialog.setNegativeButton(getString(R.string.textResume), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }
                });
                dialog.show();
            }
        });
    }
}