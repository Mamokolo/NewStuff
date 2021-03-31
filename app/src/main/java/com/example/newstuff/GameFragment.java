package com.example.newstuff;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameFragment extends Fragment {

    private Activity activity;
    private TextView textScore,textTime;
    private Button pause;
    private ImageButton turnLeftbtn,turnRightbtn,skillBtn;
    private ImageView godtone,imageRoad,NL;
    private TextView textCoolDown,readyCountDown;
    private int width,height,change;
    private int relatedPosition=0;
    private CountDownTimer coolDownTimer,readyTimer;
    private Chronometer gameTimer;
    private long howLongToCD =0;
    private static final String TAG = null;
    private List<ImageView> obstacleList=null;
    private int score=0;
    private long lastPause;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = getActivity();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();//hide the title bar
        super.onCreate(savedInstanceState);
        System.out.println("save: "+ savedInstanceState);

        //TODO: Catch onBackPressed
        Bundle currentBundle = getArguments();
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                onPause();

                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(getString(R.string.textWarning));
                dialog.setCancelable(false);
                dialog.setMessage(getString(R.string.textEndGameWarn));
                dialog.setPositiveButton(getString(R.string.textExit), new DialogInterface.OnClickListener() {


                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e(TAG,"click exit btn on warning page on pause func");
                        currentBundle.remove("skill");
                        Navigation.findNavController(GameFragment.this.getView()).navigate(R.id.resultFragment,currentBundle);
                    }
                });
                dialog.setNegativeButton(getString(R.string.textCancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onResume();
                    }
                });
                dialog.show();
                System.out.println("Back Catch");
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // 螢幕寬度（畫素）
        height = metric.heightPixels;   // 螢幕高度（畫素）

        //System.out.println(width+" "+height);
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
        //existed value
        textScore = view.findViewById(R.id.textScore);
        gameTimer = view.findViewById(R.id.textTime);
        pause = view.findViewById(R.id.pauseBtn);
        turnLeftbtn = view.findViewById(R.id.imageButtonLeft);
        turnRightbtn = view.findViewById(R.id.imageButtonRight);
        skillBtn = view.findViewById(R.id.imageButtonSkill);
        godtone = view.findViewById(R.id.imageGodtone);
        imageRoad = view.findViewById(R.id.imageRoad);
        NL = view.findViewById(R.id.imageNL1);
        NL.setVisibility(View.INVISIBLE);
        textCoolDown = view.findViewById(R.id.coolDownText);
        readyCountDown = view.findViewById(R.id.readyCountDownText);
        change = width/5;
        ArrayList<Integer> monsterY= new ArrayList<Integer>();
        for(int i=0;i<5;i++){
            monsterY.add(width/9+((width/9)*i*2));
        }
        //New value
        Bundle bundle = getArguments();
        Animation turnLeftAnim = new TranslateAnimation(godtone.getX(),godtone.getX()-change,godtone.getY(),godtone.getY());
        Animation turnRightAnim = new TranslateAnimation(godtone.getX(),godtone.getX()+change,godtone.getY(),godtone.getY());


        informationInit(bundle,view);
        //TODO: Player's parameters
        PlayerData player = new PlayerData(0,null,null,0);
        assert bundle != null;
        initPlayer(player,bundle.getString("level"));
        //TODO: Monster parameter
        Random ran = new Random();
        monsters nl = new monsters(bundle.getString("level"),NL,height-70,godtone,monsterY);


        turnLeftbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                godtone.setScaleX(-1);
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
                godtone.setScaleX(1);
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
                //System.out.println("use skill");
                switch (Objects.requireNonNull(bundle).getString("skill")){
                    case "flash":
                        //System.out.println("use flash");
                        useFlash();
                        setFlashCoolDown();
                        break;
                    case "heal":
                        //System.out.println("use heal");
                        useHeal();
                        setHealCoolDown();
                        break;
                    case "shield":
                        //System.out.println("use shield");
                        useShield();
                        setShieldCoolDown();
                        break;
                }
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(howLongToCD);
                if(coolDownTimer!=null){
                    coolDownTimer.cancel();
                }
                lastPause = SystemClock.elapsedRealtime();
                gameTimer.stop();

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
                        coolDownTimerStart();
                        gameTimer.setBase(gameTimer.getBase() + SystemClock.elapsedRealtime() - lastPause);
                        gameTimer.start();
                    }
                });
                dialog.show();
            }
        });

        //TODO: Game ready
        hideNavigationBar();

        System.out.println(bundle.get("skill"));
        TextView readyTitle = new TextView(activity);
        readyTitle.setText(getString(R.string.textGetReady));
        readyTitle.setGravity(Gravity.CENTER);
        readyTitle.setTextSize(24);

        AlertDialog.Builder readyDialog = new AlertDialog.Builder(activity);
        readyDialog.setCustomTitle(readyTitle);
        readyDialog.setCancelable(false);
        readyDialog.setNeutralButton(getResources().getString(R.string.textExit), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bundle.remove("skill");
                Navigation.findNavController(view).navigate(R.id.resultFragment,bundle);
            }
        });
        readyDialog.setPositiveButton(getString(R.string.textIAmReady), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setReadyTimer(nl);
            }
        });
        readyDialog.show();

        //TODO: Game start!

        textScore.setText(String.valueOf(score));

        //obstacleList.add();
        System.out.println(nl.monster.getX()+" "+nl.monster.getY()+" "+nl.level);
        System.out.println(imageRoad.getTop()+" "+imageRoad.getBottom());



    }

    @Override
    public void onPause() {
        super.onPause();

    }

    private void initPlayer(PlayerData playerData, String level){
        //TODO: Initialize player's status
        switch (level) {
            case "easy":
                playerData.life = 5;
                playerData.speed = "low";
                break;
            case "middle":
                playerData.life = 3;
                playerData.speed = "middle";
                break;
            case "hard":
                playerData.life = 1;
                playerData.speed = "high";
                break;
        }
        //return
    }
    private void useFlash(){
        //TODO: avoid any conflict with object for next 1.5 sec (CD 40 sec)

    }
    private void useHeal(){
        //TODO: Recover 1 life (CD 30 sec)

    }
    private void useShield(){
        //TODO: Build a shield to against 2 times conflict with object in 5 second (CD 40 sec)

    }
    private void coolDownTimerStart(){
        textCoolDown.setBackgroundColor(Color.argb(150,0,0,0));//TODO: 設定半透明
        coolDownTimer = new CountDownTimer(howLongToCD, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(getActivity()!=null){
                    howLongToCD=millisUntilFinished;
                    textCoolDown.setText(String.valueOf(howLongToCD/1000.0));
                    //System.out.println(howLongToCD/1000.0);
                }
                else {
                    //System.out.println("cancel to pause");
                    coolDownTimer.cancel();
                }
            }
            @Override
            public void onFinish() {
                skillBtn.setEnabled(true);
                textCoolDown.setBackgroundColor(Color.argb(0,0,0,0));
                textCoolDown.setText("");
                //System.out.println("finish");
            }
        }.start();
    }
    private void setReadyTimer(monsters nl){

        turnLeftbtn.setClickable(false);
        turnRightbtn.setClickable(false);
        skillBtn.setClickable(false);
        pause.setClickable(false);
        readyCountDown.setBackgroundColor(Color.argb(150,0,0,0));//TODO: 設定半透明
        readyTimer = new CountDownTimer(4100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(millisUntilFinished/1000>1){
                    readyCountDown.setText(String.valueOf((millisUntilFinished/1000)-1));
                }
                else{
                    readyCountDown.setText(getResources().getString(R.string.textStartBeforeGame));
                }
            }
            @Override
            public void onFinish() {
                readyCountDown.setBackgroundColor(Color.argb(0,0,0,0));//TODO: 設定半透明
                readyCountDown.setText("");
                //TODO: unForbid bottom and image
                turnLeftbtn.setClickable(true);
                turnRightbtn.setClickable(true);
                skillBtn.setClickable(true);
                pause.setClickable(true);
                NL.setVisibility(View.VISIBLE);
                nl.move();
                //TODO: Reset gameTimer
                gameTimer.setBase(SystemClock.elapsedRealtime());
                gameTimer.start();
            }
        }.start();
    }
    private void setFlashCoolDown(){
        //TODO: Set CD of flash
        skillBtn.setEnabled(false);
        howLongToCD = 40000;
        coolDownTimerStart();
    }
    private void setHealCoolDown(){
        //TODO: Set CD of heal
        skillBtn.setEnabled(false);
        howLongToCD = 30000;
        coolDownTimerStart();
    }
    private void setShieldCoolDown(){
        //TODO: Set CD of shield
        skillBtn.setEnabled(false);
        howLongToCD = 40000;
        coolDownTimerStart();
    }
    private void informationInit(Bundle bundle,View view){
        //TODO: Initialize skills and check condition of bundle. If bundle is empty, go back to login page.
        // If something went wrong with bundle, give a dialog to user.
        if(bundle!=null){
            if(bundle.getString("skill").equals("flash")){
                //System.out.println("flash");
                skillBtn.setImageDrawable(getResources().getDrawable(R.drawable.flash));
            }
            else if(bundle.getString("skill").equals("heal")){
                //System.out.println("heal");
                skillBtn.setImageDrawable(getResources().getDrawable(R.drawable.heal));
            }
            else if(bundle.get("skill").equals("shield")){
                //System.out.println("shield");
                skillBtn.setImageDrawable(getResources().getDrawable(R.drawable.shield));
            }
            else{
                //System.out.println("Wrong");
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
                    Navigation.findNavController(view).navigate(R.id.loginFragment);
                }
            });
            bundleNullDialog.show();
        }
    }
    public void hideNavigationBar(){
        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void conflict(){

    }
    public void chooseObjective(){

    }
    private static class PlayerData {
        private int life,score;
        private String speed,level;
        public PlayerData(int life, String speed, String level, int score){
            this.life = life;
            this.level = level;
            this.speed = speed;
            this.score = score;
        }
    }

    private static class monsters{
        private int distance;
        private String level;
        private ImageView monster, godtone;
        private CountDownTimer moveTimer;
        private ArrayList<Integer> monsterY;
        private float currentX;
        Random random = new Random();
        monsters(String level,ImageView monster,int distance,ImageView godtone,ArrayList<Integer> monsterY){
            this.level = level;
            this.monster = monster;
            this.distance = distance;
            this.godtone = godtone;
            this.monsterY = monsterY;

            this.monster.setX(monsterY.get(random.nextInt(4)));
            this.monster.setY(0);
            this.currentX = this.monster.getX();
        }

        private void move(){
            moveTimer = new CountDownTimer(10000,100) {
                @Override
                public void onTick(long millisUntilFinished) {
                    if(conflict()){
                        //System.out.println("move"+distance/100);
                        monster.setY(monster.getY()+distance/100);
                    }
                    else if(monster.getX()==godtone.getX()&&monster.getY()==godtone.getY()+110){
                        monster.setX(monsterY.get(random.nextInt(4)));
                        if(monster.getX()==currentX){
                            monster.setX(monster.getX()+2*monsterY.get(0));
                            currentX = monster.getX();
                        }
                        monster.setY(0);
                        move();
                    }
                }
                @Override
                public void onFinish() {
                    monster.setX(monsterY.get(random.nextInt(4)));
                    if(monster.getX()==currentX){
                        monster.setX(monster.getX()+2*monsterY.get(0));
                        currentX = monster.getX();
                    }
                    monster.setY(0);
                    move();
                }
            }.start();
        }
        private boolean conflict(){
            return true;
        }
    }
}
