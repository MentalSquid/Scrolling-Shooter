package mentalsquid.scrolling_shooter;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

class GameEngine extends SurfaceView implements Runnable, GameStarter {
    private Thread mGameThread = null;
    private long mFPS;
    private GameState mGameState;
    private SoundEngine mSoundEngine;

    public GameEngine(Context context, Point size) {
        super(context);
        mGameThread = new Thread();
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);

    }

    void startThread() {
        mGameState.startThread();
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    void stopThread() {
        mGameState.stopEverything();
        try {
            mGameThread.join();
        } catch (InterruptedException e) {
            Log.e("InterruptedException: ", e.getMessage());
        }
    }

    @Override
    public void run() {
        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();

            if (!mGameState.getPaused()) {
                //TODO: update game objects
            }

            long timeThisFrame = System.currentTimeMillis() - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        //Handle player input.
        return true;
    }

    @Override
    public void deSpawnReSpawn() {

    }
}
