package mentalsquid.scrolling_shooter;

import android.content.Context;
import android.content.SharedPreferences;

final class GameState {

    private static volatile boolean mThreadRunning = false;
    private static volatile boolean mPaused = true;
    private static volatile boolean mGameOver = true;
    private static volatile boolean mDrawing = false;
    private GameStarter gameStarter;
    private int mScore;
    private int mHighScore;
    //player lives
    private int mNumShips;
    SharedPreferences.Editor mEditor;

    public GameState(GameStarter gs, Context context) {
        gameStarter = gs;
        SharedPreferences prefs;
        prefs = context.getSharedPreferences("HiScore", context.MODE_PRIVATE);

        mEditor = prefs.edit();

        mHighScore = prefs.getInt("hi_score", 0);

    }

    private void endGame() {
        mGameOver = true;
        mPaused = true;
        if (mScore > mHighScore) {
            mHighScore = mScore;
            mEditor.putInt("hi_score", mHighScore);
            mEditor.commit();
        }
    }

    void startNewGame() {
        mScore = 0;
        mNumShips = 3;
        stopDrawing();
        gameStarter.deSpawnReSpawn();
        resume();

        startDrawing();
    }

    void loseLife(SoundEngine se) {
        mNumShips--;
        se.playPlayerExplode();
        if (mNumShips <= 0) {
            pause();
            endGame();
        }
    }

    int getNumShips() {
        return mNumShips;
    }

    void increaseScore() {
        mScore++;
    }

    int getScore() {
        return mScore;
    }

    int getHighScore() {
        return mHighScore;
    }

    void pause() {
        mPaused = true;
    }

    void resume() {
        mGameOver = false;
        mPaused = false;
    }

    void stopEverything() {
        mPaused = true;
        mGameOver = true;
        mThreadRunning = false;
    }

    boolean getThreadRunning() {
        return mThreadRunning;
    }

    void startThread() {
        mThreadRunning = true;
    }

    private void stopDrawing() {
        mDrawing = false;
    }

    private void startDrawing() {
        mDrawing = true;
    }

    boolean getDrawing() {
        return mDrawing;
    }

    boolean getPaused() {
        return mPaused;
    }

    boolean getGameOver() {
        return mGameOver;
    }
}
