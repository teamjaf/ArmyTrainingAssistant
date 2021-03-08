package com.eb.basictrainingprep.utils;

public class StopWatch {

    private long startTime = 0;
    private long stopTime = 0;
    private long elapsedTime = 0;
    private boolean running = false;


    public void resume() {
        if(!running) {
            this.startTime = System.currentTimeMillis();
            this.running = true;
        }
    }


    public void pause() {
        if(running) {
            this.stopTime = System.currentTimeMillis();
            this.running = false;
            elapsedTime += stopTime - startTime;
        }
    }


    // elaspsed time in milliseconds
    public long getElapsedTime() {
        if (running) {
            return  elapsedTime + System.currentTimeMillis() - startTime;
        }
        return elapsedTime;
    }


    // elaspsed time in seconds
    public long getElapsedTimeSecs() {
        if (running) {
            return ((System.currentTimeMillis() - startTime + elapsedTime)  / 1000);
        }
        return (elapsedTime / 1000);
    }


}