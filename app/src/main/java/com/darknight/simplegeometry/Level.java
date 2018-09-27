package com.darknight.simplegeometry;

import android.content.res.Resources;

public class Level {

    private int id;
    private int answer;
    private int status;

    public Level(){

    }

    public Level(int answer, int status) {
        this.answer = answer;
        this.status = status;
    }

    public Level(int id, int answer, int status) {
        this.id = id;
        this.answer = answer;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Level # " + id + ": answer - " + answer + " ; status - " + status;
    }
}
