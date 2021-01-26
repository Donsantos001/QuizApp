package com.gamecodeschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CorrectionActivity extends AppCompatActivity {
    private TextView stat;
    private TextView ques;
    private Button prev;
    private Button next;
    private Button back;

    private ArrayList<Question> wrongs;
    private int position = 0;
    private int numberOfQ;

    private TextView[] opts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);
        if(getSupportActionBar() != null){
            this.getSupportActionBar().hide();
        }

        stat = (TextView)findViewById(R.id.status);
        ques = (TextView)findViewById(R.id.quesTV);
        prev = (Button)findViewById(R.id.prevB);
        next = (Button)findViewById(R.id.nextB);
        back = (Button)findViewById(R.id.back);
        opts = new TextView[]{
                (TextView) findViewById(R.id.tv1),
                (TextView) findViewById(R.id.tv2),
                (TextView) findViewById(R.id.tv3),
                (TextView) findViewById(R.id.tv4)
        };

        next.setText("NEXT");
        prev.setText("PREV");
        prev.setEnabled(false);
        back.setText("Go back");

        Intent in = getIntent();
        wrongs = (ArrayList<Question>)in.getSerializableExtra("WRONGS");
        numberOfQ = wrongs.size();

        nextQuestion();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position >= numberOfQ - 1){
                    Intent result = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(result);
                    finish();
                }
                else{
                    position++;
                    nextQuestion();
                }
            }
        });


        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position <= 0){
                    Toast.makeText(getApplicationContext(), "cant go back", Toast.LENGTH_SHORT).show();
                }
                else {
                    position--;
                    prevQuestion();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent out = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(out);
                finish();
            }
        });
    }





    public void nextQuestion(){
        stat.setText("Solution : " + (position+1) + "/" + numberOfQ);
        Question presentQ = wrongs.get(position);
        ques.setText(presentQ.getQues());
        String[] options = presentQ.getOpt();

        if(position != 0){
            prev.setEnabled(true);
        }

        for(int i = 0; i < opts.length; i++){
            ((TextView)opts[i]).setText(options[i] +"");
        }

        for(int i = 0; i < opts.length; i++){
            opts[i].setTextColor(Color.BLACK);
            if(options[i].equals(presentQ.getAns())){
                opts[i].setTextColor(Color.GREEN);
            }

            if(presentQ.getSelected() != null  && !presentQ.getSelected().equals("")){
                if(options[i].equals(presentQ.getSelected())){
                    opts[i].setTextColor(Color.RED);
                }
            }
        }


        if(position == numberOfQ-1)
            next.setText("DONE");
        else
            next.setText("NEXT");
    }


    public void prevQuestion(){
        stat.setText("Solutions : " + (position+1) + "/" + numberOfQ);
        Question presentQ = wrongs.get(position);
        ques.setText(presentQ.getQues());
        String[] options = presentQ.getOpt();

        for(int i = 0; i < opts.length; i++){
            ((TextView)opts[i]).setText(options[i] +"");
        }

        for(int i = 0; i < opts.length; i++){
            opts[i].setTextColor(Color.BLACK);
            if(options[i].equals(presentQ.getAns())){
                opts[i].setTextColor(Color.GREEN);
            }

            if(presentQ.getSelected() != null  && !presentQ.getSelected().equals("")){
                if(options[i].equals(presentQ.getSelected())){
                    opts[i].setTextColor(Color.RED);
                }
            }
        }


        if(position == numberOfQ-1) {
            next.setText("DONE");
        }
        else if(position == 0) {
            prev.setEnabled(false);
        }
        else {
            prev.setEnabled(true);
            next.setText("NEXT");
        }
    }
}