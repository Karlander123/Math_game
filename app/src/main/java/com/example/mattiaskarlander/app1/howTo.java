package com.example.mattiaskarlander.app1;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by MattKarl on 11/8/2016.
 */

public class howTo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how);

        TextView version = (TextView)findViewById(R.id.version);

        version.setText("Version Release: " + Build.VERSION.RELEASE
                        + "\n API level: " + Build.VERSION.SDK_INT);


    }

}
