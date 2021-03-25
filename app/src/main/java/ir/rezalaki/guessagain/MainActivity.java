package ir.rezalaki.guessagain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final String START = "start";
    private static final String TEST = "test";

    EditText etInput;
    TextView tvStart, tvNumber;
    ImageView ivArrow, ivAgain, ivShowAnswer;
    FrameLayout frameTop;
    LottieAnimationView animationView;

    int guessNumber = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();

        tvStart.setOnClickListener(v -> {
            // start stat, need to create random number
            if (v.getTag().equals(START)) {

                startGame();
            }
            // random number is already created, check if input = guessNumber
            else {

                if (!etInput.getText().toString().isEmpty()) {

                    if (Integer.parseInt(etInput.getText().toString()) == guessNumber) {
                        // show user success

                        success();

                    } else if (Integer.parseInt(etInput.getText().toString()) > guessNumber) {
                        // input > guess
                        arrowDown();
                    } else {
                        // input < guess
                        arrowUp();
                    }

                }

            }

        });
        ivAgain.setOnClickListener(v -> startGame());
        ivShowAnswer.setOnClickListener(v -> {
            if (guessNumber != 0)
                showAnswer();
        });
    }

    private void showAnswer() {
        tvNumber.setText(String.valueOf(guessNumber));
        etInput.setText("");

        tvStart.setTag(START);
        tvStart.setText(START);
    }


    private void success() {
        frameTop.setVisibility(View.VISIBLE);
        animationView.playAnimation();

        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                frameTop.setVisibility(View.GONE);
                tvNumber.setText("");
                etInput.setText("");
                etInput.setHint(R.string.start_game);
                tvStart.setText("Start");
                tvStart.setTag(START);
                guessNumber = 0;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



        Toast.makeText(this, "good job man :)", Toast.LENGTH_SHORT).show();
    }

    // answer was higher
    private void arrowUp() {
        ivArrow.setColorFilter(ContextCompat.getColor(this, R.color.red));
        ivArrow.setRotation(-90f);
        ivArrow.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(1000L);
        ivArrow.startAnimation(scaleAnimation);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.GONE);
            }
        }, 1700);
    }

    // answer was lower
    private void arrowDown() {
        ivArrow.setColorFilter(ContextCompat.getColor(this, R.color.orange));
        ivArrow.setRotation(90f);
        ivArrow.setVisibility(View.VISIBLE);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setDuration(1000L);
        ivArrow.startAnimation(scaleAnimation);

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.GONE);
            }
        }, 1700);
    }


    private void startGame() {
        guessNumber = new Random().nextInt(100 - 2) + 2;
        tvNumber.setText("?");
        etInput.setHint(R.string.what_guess);
        tvStart.setTag(TEST);
        tvStart.setText("Check it");
    }

    private void setViews() {
        etInput = findViewById(R.id.etInput);
        tvStart = findViewById(R.id.tvStart);
        tvNumber = findViewById(R.id.tvNumber);
        ivAgain = findViewById(R.id.ivAgain);
        ivShowAnswer = findViewById(R.id.ivShowAnswer);
        ivArrow = findViewById(R.id.ivArrow);
        frameTop = findViewById(R.id.frBody);
        animationView = findViewById(R.id.animView);
    }
}