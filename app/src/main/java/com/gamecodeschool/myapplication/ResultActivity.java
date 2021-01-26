package com.gamecodeschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private Button solutions;
    private ArrayList<Question> wrongs;
    private TextView info;
    private TextView result;
    private Button restart;
    private TextView subtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        if(getSupportActionBar() != null){
            this.getSupportActionBar().hide();
        }

        info = (TextView)findViewById(R.id.info);
        result = (TextView)findViewById(R.id.result);
        restart = (Button) findViewById(R.id.restart);
        solutions = (Button)findViewById(R.id.solution);
        subtime = (TextView)findViewById(R.id.subtime);

        info.setText("RESULT!!!");
        restart.setText("RESTART");
        solutions.setText("View solutions");

        Intent in = getIntent();

        int score = in.getIntExtra("com.gamecodeschool.myapplication.RESULT", -1);
        int total = in.getIntExtra("com.gamecodeschool.myapplication.TOTAL", -1);
        wrongs = (ArrayList<Question>)in.getSerializableExtra("WRONGS");

        String stat = in.getStringExtra("SUBTIME");
        if(stat != null){
            subtime.setText(stat);
            if(stat.equals("SUBMITTED")){
                subtime.setTextColor(Color.BLUE);
            }
            else {
                subtime.setTextColor(Color.RED);
            }
        }

        if(score > -1 && total > -1){
            if(((double)total/2) > score){
                info.setText("FAILED!!!");
                info.setTextColor(Color.RED);
            }
            else {
                info.setText("PASSED!!!");
                info.setTextColor(Color.GREEN);
            }
            result.setText("You scored " + score + " out of " + total);
        }

        if(wrongs.size() == 0){
            solutions.setEnabled(false);
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });

        solutions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent out = new Intent(getApplicationContext(), CorrectionActivity.class);
                out.putExtra("WRONGS", wrongs);

                startActivity(out);
            }
        });
    }
}