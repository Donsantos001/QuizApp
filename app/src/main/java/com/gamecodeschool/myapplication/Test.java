package com.gamecodeschool.myapplication;

import android.content.Context;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Test {
    private ArrayList<Question> allQuestions;
    private Question[] questions;
    private int score = 0;

    public Test(Context c, int no, String subject){
        String filename = subject.toLowerCase()+".txt";
        allQuestions = new ArrayList<>();
        questions = new Question[no];

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getAssets().open(filename)));
            //Unnecessary code
            String osta = "";
            String ostb = "";
            String ostc = "";
            String ostd = "";

            String qst = "";
            String ast = "";

            int track = 0;
            String x;

            //read values and store in temporary arrays
            while((x = br.readLine()) != null){
                if(track == 0) {
                    qst = x;
                }
                else if(track > 0 && track < 5){
                    switch (track){
                        case 1:
                            osta = x;
                            break;
                        case 2:
                            ostb = x;
                            break;
                        case 3:
                            ostc = x;
                            break;
                        case 4:
                            ostd = x;
                            break;
                        default:
                            break;
                    }
                }
                else if(track == 5){
                    String anss = "";
                    switch (getOptIndex(x)){
                        case 0:
                            anss = osta;
                            break;
                        case 1:
                            anss = ostb;
                            break;
                        case 2:
                            anss = ostc;
                            break;
                        case 3:
                            anss = ostd;
                            break;
                        default:
                             break;
                    }
                    ast = anss;
                    String[] optss = {osta, ostb, ostc, ostd};
                    allQuestions.add(new Question(qst, ast, optss));
                }

                track++;
                if(track > 5) {
                    track = 0;
                }
            }
            shuffleQuestions();
            createQuestion();
        }
        catch(Exception e){
            Log.i("reader", e.getMessage());
        }
    }

    public int getOptIndex(String o){
        switch (o){
            case "a":
                return 0;
            case "b":
                return 1;
            case "c":
                return 2;
            case "d":
                return 3;
            default:
                return -1;
        }
    }

    public void startTest(){
        this.score = 0;
    }

    public void calculateScore() {
        for(int i = 0; i < questions.length; i++){
            if(questions[i].isCorrect()) {
                score += 1;
            }
        }
    }
    
    public ArrayList<Question> getWrongQuestions(){
        ArrayList<Question> wrongs = new ArrayList<>();
        for(Question q : questions){
            if(!q.isCorrect()){
                wrongs.add(q);
            }
        }
        return wrongs;
    }

    public int getScore() {
        return score;
    }

    public Question getQuestion(int p){
        return questions[p];
    }

    public int getTestLength(){
        return this.questions.length;
    }

    public void shuffleQuestions(){

//        Shuffle Options
        for(int i = 0; i < allQuestions.size(); i++){
            Random rn = new Random();
            String[] tempO;
            tempO = allQuestions.get(i).getOpt();

            for(int j = 0; j < tempO.length; j++){
                int rnd = rn.nextInt(tempO.length);
                String temp = tempO[rnd];
                tempO[rnd] = tempO[j];
                tempO[j] = temp;
            }

            Question nq = new Question(allQuestions.get(i).getQues(),allQuestions.get(i).getAns(), tempO);
            allQuestions.set(i, nq);
        }
        Collections.shuffle(allQuestions);
    }

    public void createQuestion(){
        for(int i = 0; i < questions.length; i++){
            questions[i] = allQuestions.get(i);
        }
    }
}
