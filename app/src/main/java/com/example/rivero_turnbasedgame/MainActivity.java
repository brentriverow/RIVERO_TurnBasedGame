package com.example.rivero_turnbasedgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textHeroName, textMonsName, textHeroHP, textMonsHP, textHeroMP, textMonsMP, textHeroDPS, textMonsDPS, textCombatLog;
    Button btnNextTurn;
    ImageButton skill1, skill2, skill3, skill4;


    //Hero stats
    String heroName = "Brent Rivero";
    int heroHP = 1500;
    int heroMP = 1000;
    int heroMinDamage = 100;
    int heroMaxDamage = 150;

    //Monster stats
    String monsName = "Gangster";
    int monsterHP = 3000;
    int monsterMP = 400;
    int monsterMinDamage = 40;
    int monsterMaxDamage = 55;
    //Game Turn
    int turnNumber= 1;

    boolean disabledstatus = false;
    int statuscounter = 0;
    int buttoncounter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //XML ids for text and button
        textHeroName = findViewById(R.id.textHeroName);
        textMonsName = findViewById(R.id.textMonsName);
        textHeroHP = findViewById(R.id.textHeroHP);
        textMonsHP = findViewById(R.id.textMonsHP);
        textHeroMP = findViewById(R.id.textHeroMP);
        textMonsMP = findViewById(R.id.textMonsMP);
        btnNextTurn = findViewById(R.id.btnNextTurn);
        textHeroDPS = findViewById(R.id.textHeroDPS);
        textMonsDPS = findViewById(R.id.textMonsDPS);


        textHeroName.setText(heroName);
        textHeroHP.setText(String.valueOf(heroHP));
        textHeroMP.setText(String.valueOf(heroMP));

        textMonsName.setText(monsName);
        textMonsHP.setText(String.valueOf(monsterHP));
        textMonsMP.setText(String.valueOf(monsterMP));

        textHeroDPS.setText(String.valueOf(heroMinDamage)+" ~ " + String.valueOf(heroMaxDamage));
        textMonsDPS.setText(String.valueOf(monsterMinDamage)+" ~ " + String.valueOf(monsterMaxDamage));

        skill1 = findViewById(R.id.btnskill1);
        skill2 = findViewById(R.id.btnskill2);
        skill3 = findViewById(R.id.btnskill3);
        skill4 = findViewById(R.id.btnskill4);


        textCombatLog = findViewById(R.id.textCombatLog);

        //button onClick listener
        btnNextTurn.setOnClickListener(this);
        skill1.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {


        Random randomizer = new Random();
        int herodps = randomizer.nextInt(heroMaxDamage - heroMinDamage) + heroMinDamage;
        int monsdps = randomizer.nextInt(monsterMaxDamage - monsterMinDamage) + monsterMinDamage;


        if(turnNumber % 2 != 1){//if it is enemy's turn, disable button
            skill1.setEnabled(false);

        }
        else if(turnNumber%2 == 1){
            skill1.setEnabled(true);
        }
        if(buttoncounter>0){
            skill1.setEnabled(false);
            buttoncounter--;
        }

        else if(buttoncounter==0){
            skill1.setEnabled(true);
        }

        switch (v.getId()) {
            case R.id.btnskill1:


                monsterHP = monsterHP - 250;
                turnNumber++;
                textMonsHP.setText(String.valueOf(monsterHP));
                btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+ ")");

                textCombatLog.setText("Our Hero " + String.valueOf(heroName) +" used stun!. It dealt "+String.valueOf(250) + " damage to the enemy. The enemy is stunned for 4 turns");

                disabledstatus = true;
                statuscounter = 4;

                if (monsterHP < 0){ //even
                    textCombatLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The player is victorious!");
                    heroHP = 1500;
                    monsterHP = 3000;
                    turnNumber = 1;
                    btnNextTurn.setText("Reset Game");

                }
                buttoncounter=12;

                break;
            case R.id.btnNextTurn:
                //

                if (turnNumber % 2 == 1){ //odd
                    monsterHP = monsterHP - herodps;
                    turnNumber++;
                    textMonsHP.setText(String.valueOf(monsterHP));
                    btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+")");

                    textCombatLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy.");


                    if (monsterHP < 0){ //even
                        textCombatLog.setText("Our Hero "+String.valueOf(heroName) +" dealt "+String.valueOf(herodps) + " damage to the enemy. The player is victorious!");
                        heroHP = 1500;
                        monsterHP = 3000;
                        turnNumber = 1;
                        btnNextTurn.setText("Reset Game");

                    }
                    if(statuscounter>0) { //if the enemy is still stunned, reduce the stun for 1 turn
                        statuscounter--;
                        if(statuscounter==0){
                            disabledstatus=false;
                        }



                    }
                    buttoncounter--;

                }
                else if (turnNumber%2 != 1){ //even

                    if(disabledstatus==true){
                        textCombatLog.setText("The enemy is still stunned for "+String.valueOf(statuscounter)+ "turns");
                        statuscounter--;
                        if(statuscounter==0){
                            disabledstatus=false;
                        }


                    }
                    else{
                        heroHP = heroHP - monsdps;
                        turnNumber++;
                        textHeroHP.setText(String.valueOf(heroHP));
                        btnNextTurn.setText("Next Turn ("+ String.valueOf(turnNumber)+ ")");

                        textCombatLog.setText("The Monster "+String.valueOf(monsName)+" dealt "+String.valueOf(monsdps)+ " damage to the enemy.");

                        if (heroHP < 0){
                            textCombatLog.setText("The Monster "+String.valueOf(monsName) +" dealt "+String.valueOf(monsdps)+ " damage to the enemy. Game Over!");
                            heroHP = 1500;
                            monsterHP = 3000;
                            turnNumber = 1;
                            btnNextTurn.setText("Reset Game");
                    }


                    }
                    buttoncounter--;

                    }
                    break;


                }

        }
    }