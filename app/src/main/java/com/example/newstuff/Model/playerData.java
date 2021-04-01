package com.example.newstuff.Model;

public class playerData{
    public int life;
    public int score;
    public String speed,level;
    public playerData(int life, String speed, String level, int score){
        this.life = life;
        this.level = level;
        this.speed = speed;
        this.score = score;
    }
}