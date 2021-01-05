package com.gamecodeschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView info = (TextView)findViewById(R.id.info);
        TextView result = (TextView)findViewById(R.id.result);
        Button restart = (Button) findViewById(R.id.restart);

        info.setText("YOU SCORED");
        restart.setText("RESTART");

        Intent in = getIntent();
        int score = in.getIntExtra("com.gamecodeschool.myapplication.RESULT", -1);

        if(score > -1){
            result.setText(score+"");
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });
    }
}