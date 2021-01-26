package com.gamecodeschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView welcome;
    private TextView instruction;
    private Button starter;

    private Spinner minSP;
    private Spinner secSP;
    private Spinner subjectSP;
    private Spinner qnoSP;
    private TextView minTV;
    private TextView secTV;
    private TextView subjectTV;
    private TextView qnoTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar() != null){
            this.getSupportActionBar().hide();
        }


        welcome = (TextView)findViewById(R.id.welcomeTV);
        instruction = (TextView)findViewById(R.id.instructionTV);
        starter = (Button)findViewById(R.id.starterBtn);
        minSP = (Spinner)findViewById(R.id.minSP);
        secSP = (Spinner)findViewById(R.id.secSP);
        subjectSP = (Spinner)findViewById(R.id.subjectSP);
        qnoSP = (Spinner)findViewById(R.id.qnoSP);
        minTV = (TextView)findViewById(R.id.minTV);
        secTV = (TextView)findViewById(R.id.secTV);
        subjectTV = (TextView)findViewById(R.id.subjectTV);
        qnoTV = (TextView)findViewById(R.id.qnoTV);


        List<String> subjects = Arrays.asList(getResources().getStringArray( R.array.subjects));
        List<String> minutes = Arrays.asList(getResources().getStringArray( R.array.minutes));
        List<String> seconds = Arrays.asList(getResources().getStringArray( R.array.seconds));
        List<String> qnumbers = Arrays.asList(getResources().getStringArray( R.array.qnumbers));

        ArrayAdapter<String> adapt1;
        adapt1 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, minutes);
        adapt1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minSP.setAdapter(adapt1);

        ArrayAdapter<String> adapt2;
        adapt2 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, seconds);
        adapt2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        secSP.setAdapter(adapt2);

        ArrayAdapter<String> adapt3;
        adapt3 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, qnumbers);
        adapt3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qnoSP.setAdapter(adapt3);

        ArrayAdapter<String> adapt4;
        adapt4 = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, subjects);
        adapt4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectSP.setAdapter(adapt4);

        minTV.setText("mins");
        secTV.setText("secs");
        subjectTV.setText("Subject : ");
        qnoTV.setText("Number of Questions : ");

        welcome.setText("Welcome to Quiz App");
        instruction.setText("Specify your Test Details");
        starter.setText("START");


        //Process options when button is clicked
        starter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subs = subjectSP.getSelectedItem().toString();
                if(subs.equalsIgnoreCase("Random")){
                    subs = subjects.get(1 + (int)Math.random()*(subjects.size()-1));
                }
                int det[] = getTestDetails();

                if(!(det[0] == 0 && det[1] == 0)){
                    Intent in = new Intent(getApplicationContext(), QuizActivity.class);
                    in.putExtra("MINUTES", det[0]);
                    in.putExtra("SECONDS", det[1]);
                    in.putExtra("QNUMBER", det[2]);
                    in.putExtra("SUBJECT", subs);
                    startActivity(in);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Time is too short", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Conver String to int
    public int[] getTestDetails(){
        int[] detail = new int[3];
        String mins = minSP.getSelectedItem().toString();
        String secs = secSP.getSelectedItem().toString();
        String qno = qnoSP.getSelectedItem().toString();

        if(mins.equalsIgnoreCase("Custom")){
            detail[0] = 0;
        }
        else{
            try{
                detail[0] = Integer.parseInt(mins);
            }
            catch (Exception e){

            }
        }

        if(secs.equalsIgnoreCase("Custom")){
            detail[1] = 30;
        }
        else{
            try{
                detail[1] = Integer.parseInt(secs);
            }
            catch (Exception e){

            }
        }

        if(qno.equalsIgnoreCase("Custom")){
            detail[2] = 5;
        }
        else{
            try{
                detail[2] = Integer.parseInt(qno);
            }
            catch (Exception e){

            }
        }
        return detail;
    }
}