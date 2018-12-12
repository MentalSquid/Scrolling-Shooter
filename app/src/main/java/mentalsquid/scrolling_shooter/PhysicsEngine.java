package mentalsquid.scrolling_shooter;

public class PhysicsEngine {

    boolean update(long fps, ParticleSystem particleSystem) {
        if (particleSystem.mIsRunning) {
            particleSystem.update(fps);
        }
        return false;
    }
}
