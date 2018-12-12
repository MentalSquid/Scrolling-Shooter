package mentalsquid.scrolling_shooter;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;

class GameEngine extends SurfaceView implements Runnable, GameStarter, GameEngineBroadcaster {
    private Thread mGameThread = null;
    private long mFPS;
    private GameState mGameState;
    private SoundEngine mSoundEngine;
    HUD mHUD;
    Renderer mRenderer;
    ParticleSystem mParticleSystem;
    UIController mUIController;
    PhysicsEngine mPhysicsEngine;
    private ArrayList<InputObserver> inputObservers = new ArrayList<InputObserver>();

    public GameEngine(Context context, Point size) {
        super(context);
        mUIController = new UIController(this);
        mGameThread = new Thread();
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
        mParticleSystem = new ParticleSystem();
        mParticleSystem.init(100);
        mPhysicsEngine = new PhysicsEngine();

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
                if (mPhysicsEngine.update(mFPS, mParticleSystem)) {
                    deSpawnReSpawn();
                }
            }
            //draw game objects
            mRenderer.draw(mGameState, mHUD, mParticleSystem);
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
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState, mHUD.getControls());
        }
        //DEBUG CODE
        //mParticleSystem.emitParticles(new PointF(motionEvent.getX(), motionEvent.getY()));
        //
        return true;
    }

    @Override
    public void deSpawnReSpawn() {

    }

    @Override
    public void addObserver(InputObserver o) {
        inputObservers.add(o);
    }
}
