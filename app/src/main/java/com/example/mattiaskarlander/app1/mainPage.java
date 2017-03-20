package com.example.mattiaskarlander.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;




public class mainPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        Button vidare = (Button)findViewById(R.id.vidare);

        vidare.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    }
                }
        );

    }


}
