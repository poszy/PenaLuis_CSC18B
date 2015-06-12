package poszmod.awesome;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

// For splash Screen
// timer Task


public class SplashScreen extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        // Splash Screen timeout,
       // int timeout_Splash = 3000;

        // Begin Splash Screen
        //Set Circle loader time
        //Timer SplashLoader = new Timer();

        //TimerTask SplashDuration = new TimerTask()

        Thread Splash = new Thread(){

            @Override
            public void run() {
                try{
                    sleep(5*1000);
                    Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(intent);

                    //close
                    finish();

                } catch (Exception e){

                }

            }
        }; //SplashLoader.schedule(SplashDuration, timeout_Splash);

        Splash.start();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
