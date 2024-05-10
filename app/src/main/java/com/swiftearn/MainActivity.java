package com.swiftearn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView logoImage;
    private Handler checkConn;
    private Runnable checkRun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logoImage = findViewById(R.id.logoImage);

        Animation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotate.setDuration(2000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        logoImage.startAnimation(rotate);

        checkConn = new Handler();
        checkRun = new Runnable() {
            @Override
            public void run() {
                NetworkChecker networkChecker = new NetworkChecker();
                if(networkChecker.isNetworkAvailable(MainActivity.this))
                {
                    Toast.makeText(MainActivity.this, "No Network!", Toast.LENGTH_LONG).show();
                    checkConn.postDelayed(checkRun, 10000);
                }
                else
                {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }, 2000);
                }
            }
        };

        checkConn.post(checkRun);

    }
}