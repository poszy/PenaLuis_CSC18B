package poszmod.awesomecheckers;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class SettingsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar=(Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        // make the text in the left part of the action bar
        // go back ONE activty

        getSupportActionBar().setHomeButtonEnabled(true);

        // display icon to go back, next to text in actionbar / logo

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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

        if(id== R.id.home){

            // Going to navigate to the parent activity
            // needs to be defined in the android manifest file
            // FULL PACKAGE NAME OF ACTIVITY
            // " meta-data android:name="android.support.PARENT_ACTIVITY "
            // activities also must be running on the same task

            NavUtils.navigateUpFromSameTask(this);


        }
        return super.onOptionsItemSelected(item);
    }
}
