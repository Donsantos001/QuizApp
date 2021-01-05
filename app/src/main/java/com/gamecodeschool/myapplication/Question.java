package com.gamecodeschool.myapplication;

import java.util.Arrays;

public class Question {
    String ques;
    String[] opt;
    int ans;

    public Question(String q, int a, String[] o){
        this.ques = q;
        this.ans = a;
        this.opt = o;
    }

    public void setQues(String ques) {
        this.ques = ques;
    }

    public String getQues() {
        return ques;
    }

    public void setAns(int ans) {
        this.ans = ans;
    }

    public int getAns() {
        return ans;
    }

    public void setOpt(String[] opt) {
        this.opt = opt;
    }

    public String[] getOpt() {
        return opt;
    }

    @Override
    public String toString() {
        return "Question{" +
                "ques='" + ques + '\'' +
                ", opt=" + Arrays.toString(opt) +
                ", ans=" + ans +
                '}';
    }
}
