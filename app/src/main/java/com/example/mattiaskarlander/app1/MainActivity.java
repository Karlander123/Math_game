package com.example.mattiaskarlander.app1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity implements OnClickListener {

    private Button playButton, helpButton, highButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = (Button)findViewById(R.id.play_btn);
        helpButton = (Button)findViewById(R.id.help_btn);
        highButton = (Button)findViewById(R.id.high_btn);

        playButton.setOnClickListener(this);
        helpButton.setOnClickListener(this);
        highButton.setOnClickListener(this);

    }

    private String[] levelNames = {"Lätt", "Medium", "Svår"};

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.play_btn){
            //Val av nivå
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Välj nivå")
                    .setSingleChoiceItems(levelNames, 0, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startPlay(which);
                        }
                    });
            AlertDialog ad = builder.create();
            ad.show();
        }
        else if(view.getId()==R.id.help_btn){
            //Hjälp
            Intent helpIntent = new Intent(this, howTo.class);
            this.startActivity(helpIntent);
        }
        else if(view.getId()==R.id.high_btn){
            //Topplista
            Intent highIntent = new Intent(this, Score.class);
            this.startActivity(highIntent);

        }

    }

    private void startPlay(int chosenLevel)
    {
        //Spela
        Intent playIntent = new Intent(this, Play.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }

}
