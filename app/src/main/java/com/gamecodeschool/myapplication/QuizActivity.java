package com.gamecodeschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {
    private Test myTest = null;
    private RadioGroup rg;
    private TextView ques;
    private TextView stat;
    private TextView timer;
    private Button confirm;
    private Button previous;
    private int position = 0;
    private int minutes = 0;
    private int seconds = 0;
    private int numberOfQ;
    private String subject;

    Handler handler;
    Thread thread;
    myRunnable runnable = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if(getSupportActionBar() != null){
            this.getSupportActionBar().hide();
        }

        stat = (TextView)findViewById(R.id.quesStat);
        ques = (TextView)findViewById(R.id.questionView);
        rg = (RadioGroup)findViewById(R.id.myRadios);
        confirm = (Button)findViewById(R.id.check);
        previous = (Button)findViewById(R.id.prev);
        timer = (TextView)findViewById(R.id.timerTV);
        previous.setEnabled(false);

        Intent in = getIntent();
        numberOfQ = in.getIntExtra("QNUMBER", -1);
        minutes = in.getIntExtra("MINUTES", 0);
        seconds = in.getIntExtra("SECONDS", 0);
        subject = in.getStringExtra("SUBJECT");

        if(numberOfQ != -1){
            myTest = new Test(getApplicationContext(), numberOfQ, subject);

            numberOfQ = myTest.getTestLength();
            myTest.startTest();
            nextQuestion();
        }

        handler = new Handler();
        runnable = new myRunnable();
        thread = new Thread(runnable);
        thread.start();

        //Radio group checked change
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId != -1){
                    String ans = ((RadioButton)findViewById(checkedId)).getText().toString();
                    myTest.getQuestion(position).setSelected(ans);
                }
            }
        });

        //Process options when button is clicked
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position >= numberOfQ - 1){
                    runnable.terminate();
                    submitTest(false);
                }
                else{
                    rg.clearCheck();
                    position++;
                    nextQuestion();
                }
            }
        });



        //Previous
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position <= 0){
                    Toast.makeText(getApplicationContext(), "cant go back", Toast.LENGTH_SHORT).show();
                }
                else {
                    rg.clearCheck();
                    position--;
                    prevQuestion();
                }
            }
        });
    }



    //Move to the next question
    public void nextQuestion(){
        stat.setText("Question : " + (position+1) + "/" + numberOfQ);
        Question presentQ = myTest.getQuestion(position);
        ques.setText(presentQ.getQues());
        String[] options = presentQ.getOpt();

        if(position != 0){
            previous.setEnabled(true);
        }

        for(int i = 0; i < rg.getChildCount(); i++){
            ((RadioButton)rg.getChildAt(i)).setText(options[i] +"");
        }

        for(int i = 0; i < rg.getChildCount(); i++){
            if(presentQ.getSelected() != null  && !presentQ.getSelected().equals("")){
                if(options[i].equals(presentQ.getSelected())){
                    ((RadioButton)rg.getChildAt(i)).setChecked(true);
                }
            }
        }


        if(position == numberOfQ-1)
            confirm.setText("SUBMIT");
        else
            confirm.setText("NEXT");
    }



    //Move to previous question
    public void prevQuestion(){
        stat.setText("Question : " + (position+1) + "/" + numberOfQ);
        Question presentQ = myTest.getQuestion(position);
        ques.setText(presentQ.getQues());
        String[] options = presentQ.getOpt();

        for(int i = 0; i < rg.getChildCount(); i++){
            ((RadioButton)rg.getChildAt(i)).setText(options[i] +"");
        }

        for(int i = 0; i < rg.getChildCount(); i++){
            if(presentQ.getSelected() != null  && !presentQ.getSelected().equals("")){
                if(options[i].equalsIgnoreCase(presentQ.getSelected())){
                    ((RadioButton)rg.getChildAt(i)).setChecked(true);
                }
            }
        }

        if(position == numberOfQ-1) {
            confirm.setText("SUBMIT");
        }
        else if(position == 0) {
            previous.setEnabled(false);
        }
        else {
            previous.setEnabled(true);
            confirm.setText("NEXT");
        }
    }

    public void submitTest(boolean timeup){
        myTest.calculateScore();
        ArrayList<Question> wrong = myTest.getWrongQuestions();
        Intent result = new Intent(getApplicationContext(), ResultActivity.class);

        result.putExtra("com.gamecodeschool.myapplication.RESULT", myTest.getScore());
        result.putExtra("com.gamecodeschool.myapplication.TOTAL", numberOfQ);
        result.putExtra("WRONGS", wrong);
        if(timeup){
            result.putExtra("SUBTIME", "TIME UP!!!");
        }
        else {
            result.putExtra("SUBTIME", "SUBMITTED");
        }

        startActivity(result);
        finish();
    }

    class myRunnable implements Runnable{
        private volatile boolean running = true;

        public void terminate(){
            running = false;
        }

            @Override
            public void run() {
            for(int counter = (60*minutes + seconds); counter >= 0 && running; counter--){
                int count = counter;
                int minute = counter/60;
                int second = counter%60;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String m = "";
                        String s = "";

                        if(Integer.toString(minute).length() < 2){
                            m = "0" + minute;
                        }
                        else {
                            m = minute+"";
                        }
                        if(Integer.toString(second).length() < 2) {
                            s = "0" + second;
                        }
                        else {
                            s = second+"";
                        }
                        String time = m + " : " + s;

                        timer.setText(time);
                        if(count < 31){
                            timer.setTextColor(Color.RED);
                        }
                        if(count < 1){
                            submitTest(true);
                        }
                    }
                });

                try{
                    Thread.sleep(1000);
                }
                catch (Exception e){
                    Log.i("threading", "Unable ooo");
                }
            }

        }
    }
}