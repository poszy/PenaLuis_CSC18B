package poszmod.awesomecheckers;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends ActionBarActivity {

    //create toolbar object

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initilize my toolbar used on main activity
        // call it. execute it.

        toolbar= (Toolbar) findViewById(R.id.app_bar);

        //Use my own custom actionbar and not googles
        setSupportActionBar(toolbar);

        // click on navigation drawer to click on multiple activities
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        // create Navigation bar object
        // this must be imported from android.suport.v4.app.Fragment and not
        // android.app.Fragment

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        // set up the drawerfragment class

        drawerFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout), toolbar);



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


        // Load the Settings Activity on touch

        if (id ==R.id.navigate){


            startActivity(new Intent(this, SettingsActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }
}
