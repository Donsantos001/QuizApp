package com.gamecodeschool.myapplication;

import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable {
    private String question;
    private String[] options;
    private String answer;
    private String selected = null;

    public Question(String q, String a, String[] o){
        this.question = q;
        this.answer = a;
        this.options = o;
    }

    public void setQues(String ques) {
        this.question = ques;
    }

    public String getQues() {
        return question;
    }

    public void setAns(String ans) {
        this.answer = ans;
    }

    public String getAns() {
        return answer;
    }

    public void setOpt(String[] opt) {
        this.options = opt;
    }

    public String[] getOpt() {
        return options;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getSelected() {
        return selected;
    }

    public boolean isCorrect(){
        return this.answer.equals(selected);
    }

    @Override
    public String toString() {
        return "Question{" +
                "ques='" + question + '\'' +
                ", opt=" + Arrays.toString(options) +
                ", ans=" + answer +
                '}';
    }
}
