package com.example.mattiaskarlander.app1;

import android.app.Activity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import android.content.SharedPreferences;


/**
 * Created by MattKarl on 11/8/2016.
 */

public class Play extends Activity implements OnClickListener {

    private int level = 0, answer = 0, operator = 0, operand1 = 0, operand2 = 0;

    private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1, MULTIPLY_OPERATOR = 2, DIVIDE_OPERATOR = 3;

    private String[] operators = {"+", "-", "x", "/"};

    private int[][] levelLow = {
            {1, 12, 23}, {1, 5, 10}, {3, 6, 11}, {2, 3, 5}};
    private int[][] levelHigh = {
            {10, 25, 50}, {10, 20, 30}, {5, 10, 15}, {10, 50, 100}};

    private Random random;

    private TextView
            question,
            answerText,
            scoreText,
            response;

    private Button button1, button2,
                   button3, button4,
                   button5, button6,
                   button7, button8,
                   button9, button0,
                   enterButton, clearButton;

    private SharedPreferences gamePrefs;

    public static final String GAME_PREFS = "MathFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        question =  (TextView)findViewById(R.id.question);
        answerText = (TextView)findViewById(R.id.answer);
        response =  (TextView)findViewById(R.id.response);
        scoreText =  (TextView)findViewById(R.id.score);

        response.setVisibility(View.INVISIBLE);

        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);
        button5 = (Button)findViewById(R.id.button5);
        button6 = (Button)findViewById(R.id.button6);
        button7 = (Button)findViewById(R.id.button7);
        button8 = (Button)findViewById(R.id.button8);
        button9 = (Button)findViewById(R.id.button9);
        button0 = (Button)findViewById(R.id.button0);
        enterButton = (Button)findViewById(R.id.enterButton);
        clearButton = (Button)findViewById(R.id.clearButton);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);
        enterButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);

        if(savedInstanceState!=null){
            //restore state
            level=savedInstanceState.getInt("level");
            int yourScore = savedInstanceState.getInt("score");
            scoreText.setText("Score: "+yourScore);
        }
        else{
            Bundle extras = getIntent().getExtras();
            if(extras !=null)
            {
                int passedLevel = extras.getInt("level", -1);
                if(passedLevel>=0) level = passedLevel;
            }
        }
        random = new Random();


        questionSelect();

    }

    //Genererar en fråga
    private void questionSelect(){
        answerText.setText("=");
        operator = random.nextInt(operators.length);
        operand1 = getOperand();
        operand2 = getOperand();

        if(operator == SUBTRACT_OPERATOR){
            while(operand2>operand1){
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }else if(operator==DIVIDE_OPERATOR){
            while((((double)operand1/(double)operand2)%1 > 0) || (operand1==operand2))
            {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }
        switch(operator)
        {
            case ADD_OPERATOR:
                answer = operand1+operand2;
                break;
            case SUBTRACT_OPERATOR:
                answer = operand1-operand2;
                break;
            case MULTIPLY_OPERATOR:
                answer = operand1*operand2;
                break;
            case DIVIDE_OPERATOR:
                answer = operand1/operand2;
                break;
            default:
                break;
        }
        question.setText(operand1+" "+operators[operator]+" "+operand2);

    }

    private int getOperand(){
        return random.nextInt(levelHigh[operator][level] - levelLow[operator][level] + 1)
                + levelLow[operator][level];
    }

//Metod för registrering av highscores
    private void setScore(){
        int yourScore = getScore();
        if(yourScore>0){
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = gamePrefs.getString("highScores", "");

            if(scores.length()>0){
            }
            else{
                scoreEdit.putString("highScores", ""+dateOutput+" - "+yourScore);
                scoreEdit.commit();
            }
            List<Score> scoreStrings = new ArrayList<Score>();
            String[] yourScores = scores.split("\\|");
            for(String eSc : yourScores){
                String[] parts = eSc.split(" - ");
                scoreStrings.add(new Score(parts[0], Integer.parseInt(parts[1])));
            }

            Score newScore = new Score(dateOutput, yourScore);
            scoreStrings.add(newScore);
            Collections.sort(scoreStrings);

            StringBuilder scoreBuild = new StringBuilder("");
            for(int s=0; s<scoreStrings.size(); s++){
                if(s>=15) break;
                if(s>0) scoreBuild.append("|");
                scoreBuild.append(scoreStrings.get(s).getScoreText());
            }
//Higscore sparas
            scoreEdit.putString("highScores", scoreBuild.toString());
            scoreEdit.commit();

        }

    }




    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.enterButton){
            //Svara

            String answerContent = answerText.getText().toString();
            if(!answerContent.endsWith("?"))
            {


            }
            int enteredAnswer = Integer.parseInt(answerContent.substring(2));
            int yourScore = getScore();


            if(enteredAnswer==answer){
                //Rätt svar
                scoreText.setText("Score: "+(yourScore+1));
                response.setText("Rätt svar!");
                response.setVisibility(View.VISIBLE);
            }else{
                //Fel svar
                setScore();
                scoreText.setText("Score: 0");
                response.setText("Nepp nepp!");
                response.setVisibility(View.VISIBLE);
            }

            questionSelect();
        }
        else if(view.getId()==R.id.clearButton){
            //Rensa
            answerText.setText("=");
        }
        else {
            //Nummer
            response.setVisibility(View.INVISIBLE);
            int enteredNum = Integer.parseInt(view.getTag().toString());
            if(answerText.getText().toString().endsWith("="))
                answerText.setText("= "+enteredNum);
            else
                answerText.append(""+enteredNum);
        }
    }

    private int getScore(){
        String scoreStr = scoreText.getText().toString();

        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }

//Registrerar highscore ifall appen stoppas
    protected void onDestroy(){
        setScore();
        super.onDestroy();
    }


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
//Sparar state
        int yourScore = getScore();
        savedInstanceState.putInt("score", yourScore);
        savedInstanceState.putInt("level", level);

        super.onSaveInstanceState(savedInstanceState);

    }




}
