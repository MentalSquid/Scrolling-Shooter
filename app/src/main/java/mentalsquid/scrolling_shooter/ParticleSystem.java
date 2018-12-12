package mentalsquid.scrolling_shooter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.Random;

class ParticleSystem {
    float mDuration;
    ArrayList<Particle> mParticles;
    Random random = new Random();
    boolean mIsRunning = false;

    void init(int numParticles) {
        mParticles = new ArrayList<Particle>();

        for (int i = 0; i < numParticles; i++) {
            float angle = (random.nextInt(360));
            angle = angle * 3.14F / 180.0F;
            float speed = random.nextInt(20) + 1;

            PointF direction;

            direction = new PointF((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
            mParticles.add(new Particle(direction));
        }
    }

    void update(long fps) {
        mDuration -= (1F / fps);

        for (Particle p : mParticles) {
            p.update();
        }
        if (mDuration < 0) {
            mIsRunning = false;
        }

    }

    void emitParticles(PointF startPosition) {
        mIsRunning = true;
        mDuration = 5F;

        for (Particle p : mParticles) {
            p.setPosition(startPosition);
        }

    }

    void draw(Canvas canvas, Paint paint) {

        for (Particle p : mParticles) {
            //random colours
            paint.setARGB(255,random.nextInt(256),random.nextInt(256),random.nextInt(256));
            //paint.setColor(Color.argb(255, 255, 255, 255));

            canvas.drawRect(p.getRectF(), paint);
        }
    }

}
