package com.example.mattiaskarlander.app1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
/**
 * Created by MattKarl on 11/8/2016.
 */

public class Score extends Activity implements Comparable<Score> {
    public Score() {

    }

    private String scoreDate;
    public int scoreNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high);

        TextView scoreView = (TextView)findViewById(R.id.high_scores_list);

        SharedPreferences scorePrefs = getSharedPreferences(Play.GAME_PREFS, 0);

        String[] savedScores = scorePrefs.getString("highScores", "").split("\\|");

        StringBuilder scoreBuild = new StringBuilder("");
        for(String score : savedScores){
            scoreBuild.append(score+"\n");
        }
        scoreView.setText(scoreBuild.toString());
    }

    public Score(String date, int num){
        scoreDate=date;
        scoreNum=num;
    }

    public int compareTo(Score sc){

        return sc.scoreNum>scoreNum? 1 : sc.scoreNum<scoreNum? -1 : 0;
    }

    public String getScoreText()
    {
        return scoreDate+" - "+scoreNum;
    }


}
