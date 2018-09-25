package com.example.hi.reminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim2.reset();

        LinearLayout l = (LinearLayout) findViewById(R.id.llayout);
        l.clearAnimation();


        anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        anim2 = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim2.reset();

        ImageView iv = (ImageView) findViewById(R.id.simg);
        TextView tv= (TextView) findViewById(R.id.stv);

        iv.clearAnimation();
        iv.startAnimation(anim);
        tv.clearAnimation();
        tv.startAnimation(anim);

        Thread th=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3500);
                    Intent i=new Intent(Splash.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(i);
                    Splash.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }
}