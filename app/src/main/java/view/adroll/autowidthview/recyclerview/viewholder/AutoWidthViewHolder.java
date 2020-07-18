package view.adroll.autowidthview.recyclerview.viewholder;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import view.adroll.autowidthview.AnimationUtil;
import view.adroll.autowidthview.R;
import view.adroll.autowidthview.recyclerview.AutoWidthView;

/**
 * Created by Adroll
 * on 2020/3/25
 * Description:
 * * * * * * * * * * * * * * * * * * * * * *
 * Thinking is more important than coding. *
 * * * * * * * * * * * * * * * * * * * * * *
 */
public class AutoWidthViewHolder extends RecyclerView.ViewHolder implements View.OnFocusChangeListener {

    public TextView tv, tvDesc;
    private ImageView iv;
    private RelativeLayout rl;

    private ValueAnimator visibleAnimator, goneAnimator;
    private AnimationHandler animationHandler;

    private boolean isVip;
    private int descWidth;
    // 是否可以执行开始动画
    private volatile boolean isExecuteAnimation;
    // 动画是否结束
    private volatile boolean isAnimationEnd;

    public AutoWidthViewHolder(@NonNull View itemView) {
        super(itemView);

        initView(itemView);
        initListener();
        initHandler();
    }

    private void initView(View itemView){
        tv = itemView.findViewById(R.id.item_auto_width_tv);
        tvDesc = itemView.findViewById(R.id.item_auto_width_tv_desc);
        iv = itemView.findViewById(R.id.item_auto_width_iv);
        rl = itemView.findViewById(R.id.item_auto_width_rl_rec);
    }

    private void initListener(){
        rl.setOnFocusChangeListener(this);
    }

    private void initHandler(){
        animationHandler = new AnimationHandler(this);
    }

    private void initAnimator(){
        Log.d("Adroll", "tv=" + tv.getMeasuredWidth() + " desc=" + descWidth);
        visibleAnimator = ValueAnimator.ofInt(tv.getMeasuredWidth(), descWidth + tv.getMeasuredWidth());
        visibleAnimator.setDuration(AutoWidthView.AUTO_WIDTH_VIEW_ANIMATION_DURATION);
        visibleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
                rlParams.width = (int) valueAnimator.getAnimatedValue();
                rl.setLayoutParams(rlParams);

                if (isVip){
                    LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    tvParams.setMarginStart(iv.getMeasuredWidth() - tv.getPaddingStart());
                    tv.setLayoutParams(tvParams);
                }

                rl.requestLayout();
            }
        });
        visibleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                tvDesc.setVisibility(View.VISIBLE);

                if (isVip){
                    iv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (rl.isFocused()){
                    isAnimationEnd = true;
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                tvDesc.setVisibility(View.GONE);

                if (isVip){
                    iv.setVisibility(View.GONE);

                    LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    tvParams.setMarginStart(0);
                    tv.setLayoutParams(tvParams);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        goneAnimator = ValueAnimator.ofInt(descWidth + tv.getMeasuredWidth(), tv.getMeasuredWidth());
        goneAnimator.setDuration(AutoWidthView.AUTO_WIDTH_VIEW_ANIMATION_DURATION);
        goneAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                RelativeLayout.LayoutParams rlParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
                rlParams.width = (int) valueAnimator.getAnimatedValue();
                rl.setLayoutParams(rlParams);

                if (isVip){
                    LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    tvParams.setMarginStart(0);
                    tv.setLayoutParams(tvParams);
                }

                rl.requestLayout();
            }
        });
        goneAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (isVip && iv.getVisibility() == View.VISIBLE){
                    iv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimationEnd = false;
                tvDesc.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isAnimationEnd = false;

                if (isVip){
                    iv.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams tvParams = (LinearLayout.LayoutParams) tv.getLayoutParams();
                    tvParams.setMarginStart(iv.getMeasuredWidth() - tv.getPaddingStart());
                    tv.setLayoutParams(tvParams);
                }
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void setDescWidth(int descWidth) {
        if (isVip){
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv.getLayoutParams();
            this.descWidth += params.width + descWidth;
        }else {
            this.descWidth = descWidth;
        }

        // 在textview绘制完成后再进行测量动画长度
        tv.post(new Runnable() {
            @Override
            public void run() {
                initAnimator();
            }
        });
    }

    public void setIsVip(boolean isVip) {
        this.isVip = isVip;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus){
            isExecuteAnimation = true;

            if (descWidth > 0){
                animationHandler.sendEmptyMessageDelayed(0, 300);
            }

            tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv.setTextColor(itemView.getContext().getResources().getColor(R.color.white));
            AnimationUtil.enlarge(v, 1.2F);
        }else {
            isExecuteAnimation = false;
            tv.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            if (visibleAnimator.isRunning()){
                visibleAnimator.cancel();
            } else if (isAnimationEnd){
                if (descWidth > 0){
                    animationHandler.sendEmptyMessageDelayed(1, 300);
                }
            }

            tv.setTextColor(itemView.getContext().getResources().getColor(R.color.color_alpha_60_white));
            tvDesc.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

            AnimationUtil.shrink(v, 1.2F);
        }
    }

    private static class AnimationHandler extends Handler {

        private WeakReference<AutoWidthViewHolder> weakReference;

        AnimationHandler(AutoWidthViewHolder context){
            weakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 0){
                if (!weakReference.get().isExecuteAnimation){
                    return;
                }

                weakReference.get().visibleAnimator.start();
            }else {
                if (!weakReference.get().isAnimationEnd){
                    return;
                }

                weakReference.get().goneAnimator.start();
            }
        }
    }
}
