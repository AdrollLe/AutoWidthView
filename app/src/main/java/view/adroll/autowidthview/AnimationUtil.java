package view.adroll.autowidthview;

import android.view.View;
import android.view.animation.ScaleAnimation;

/**
 * <p> File description: <p>
 * <p> Creator: Adroll   <p>
 * <p> Created date: 2020/3/26 <p>
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class AnimationUtil {
    private static final int ANIMATION_DURATION = 300;

    public static void enlarge(View view, float scale){
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, scale, 1.0f, scale, width / 2, height / 2);
        scaleAnimation.setDuration(ANIMATION_DURATION);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }

    public static void shrink(View view, float scale){
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        ScaleAnimation scaleAnimation = new ScaleAnimation(scale, 1.0f, scale, 1.0f, width / 2, height / 2);
        scaleAnimation.setDuration(ANIMATION_DURATION);
        scaleAnimation.setFillAfter(true);
        view.startAnimation(scaleAnimation);
    }
}
