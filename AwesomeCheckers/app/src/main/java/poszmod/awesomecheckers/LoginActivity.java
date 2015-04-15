package poszmod.awesomecheckers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class LoginActivity extends ActionBarActivity {

    // Cast Two buttons that will launch two activities
    // registration, and if successful, lauch user home page

    private static Button buttonLaunchReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


       //Launch Registration if button clicked
        onClickButtonListener();
    }


    // Create Method that will launch Registration
    // activity

    public void onClickButtonListener(){

    // Assign buttonLaunchReg a value by Id
    // ID is defined in activty_login.xml

   buttonLaunchReg = (Button)findViewById(R.id.launchRegistrationBtn);
   buttonLaunchReg.setOnClickListener(
           new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                   startActivity(intent);
               }
           }




   );




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    public void launchRegistration(RegistrationActivity view){

     Intent intent = new Intent(this, RegistrationActivity.class);


    }

}
