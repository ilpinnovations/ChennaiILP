package info.androidhive.customlistviewvolley;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
/**
 * Created by PUSHPAL on 1/14/2016.
 */
public class IndexActivity extends Activity {

    /** Splash Screen Timer **/
    private static int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /** This method will be executed once the timer is over **/
                Intent i = new Intent(IndexActivity.this, MainActivity.class);
                startActivity(i);

                /** close this activity **/
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}