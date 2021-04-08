package com.example.newstuff.Model;


import android.os.CountDownTimer;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Random;

public class monsters{
    private int distance, screenW, screenH;
    public String level;
    public ImageView monster, godtone;
    public CountDownTimer moveTimer;
    private ArrayList<Integer> monsterY;
    private float currentX;
    private long speedTime;
    private int godtoneHeight, godtoneWidth;
    Random random = new Random();

    public monsters(String level, ImageView monster, int distance, ImageView godtone, ArrayList<Integer> monsterY, int screenW, int screenH, int godtoneHeight){
        this.level = level;
        this.monster = monster;
        this.distance = distance;
        this.godtone = godtone;
        this.monsterY = monsterY;
        this.screenW = screenW;
        this.screenH = screenH;

        this.monster.setX(monsterY.get(random.nextInt(4)));
        this.monster.setY(0);
        this.currentX = this.monster.getX();
        this.godtoneHeight = godtoneHeight;
        System.out.println(godtoneHeight);

    }

    public void move(){
        switch (level){
            case "easy":
                speedTime = 10000;
                break;
            case "middle":
                speedTime = 6000;
                break;
            case "hard":
                speedTime = 3000;
                break;
        }
        moveTimer = new CountDownTimer(speedTime,50) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!conflict()) {
                    //System.out.println("move"+distance/100);
                    monster.setY(monster.getY()+distance/(speedTime/50));
                }
                else if(conflict()){
                    moveTimer.cancel();
                    moveTimer.onFinish();
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
    public boolean conflict(){
        if(monster.getX()==godtone.getX() && monster.getY()-(godtone.getY()-godtoneHeight)>10){
            System.out.println("Conflict!");
            return true;
        }
        else{
            //System.out.println("Don't conflict");
            return false;
        }
    }
}