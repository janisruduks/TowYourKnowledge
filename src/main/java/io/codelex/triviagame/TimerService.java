package io.codelex.triviagame;

class TimerService {

    public float calculateTimeElapsedInSeconds(long start, long end) {
        float msec = end - start;
        return msec / 1000f;
    }
}
