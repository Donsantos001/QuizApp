package com.gamecodeschool.myapplication;

public class Test {
    Question[] questions = new Question[4];;
    String[] varietiesA = {
            "What is the name of the first element?",
            "What is the name of the sixth element?",
            "Which element has the highest electronegativity?",
            "Which element has the highest electropositivity?",
            "Which element is a metal?",
            "Which element is a non-metal?"
    };

    String[] varietiesB = {
            "Hydrogen",
            "Fluorine",
            "Oxygen",
            "Sodium",
            "Carbon",
            "Potassium",
            "Silicon",
            "Nitrogen",
            "Aluminium",
            "Sulphur"
    };

    private int score = 0;

    public Test(){
        for(int i = 0; i < questions.length; i ++){
            String ques = varietiesA[(int)(Math.random()*varietiesA.length)];
            int an =  (int)(Math.random()*4);
            String[] op = new String[4];
            for(int j = 0; j < 4; j++){
                op[i] = varietiesB[(int)(Math.random()*varietiesB.length)];
            }
            questions[i] = new Question(ques, an, op);
        }
    }

    public boolean checkAnswer(int no, int op){
        Question attempted = questions[no];
        return op == attempted.getAns();
    }

    public void correct(boolean status){
        if(status)
            score += 10;
    }

    public void startTest(){
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public Question getQuestion(int p){
        return questions[p];
    }
}
