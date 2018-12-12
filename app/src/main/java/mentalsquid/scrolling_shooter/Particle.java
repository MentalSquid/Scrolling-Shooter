package mentalsquid.scrolling_shooter;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class Particle {
    PointF mVelocity;
    PointF mPosition;

    Particle(PointF direction) {
        mVelocity = new PointF();
        mPosition = new PointF();
        mVelocity.x = direction.x;
        mVelocity.y = direction.y;
    }

    void update() {
        mPosition.x += mVelocity.x;
        mPosition.y += mVelocity.y;
    }

    void setPosition(PointF position) {
        mPosition.x = position.x;
        mPosition.y = position.y;
    }

    PointF getPosition() {
        return mPosition;
    }

    RectF getRectF() {
        return new RectF(mPosition.x, mPosition.y, mPosition.x + 25, mPosition.y + 25);
    }
}
