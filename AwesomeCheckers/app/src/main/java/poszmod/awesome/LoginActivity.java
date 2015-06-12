package poszmod.awesome;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import manage.SessionManager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;



public class LoginActivity extends ActionBarActivity {

    // Cast Two buttons that will launch two activities
    // registration, and if successful, lauch user home page

    // Launch the Registration Activity via Link in Login Activity

    private static Button buttonLaunchReg; // id = launchRegistrationBtn
    private static final String TAG = RegistrationActivity.class.getSimpleName();
    // Create Private buttons and variables that will be able to
    // Control the Login Activity
    // The IDs are devined in activity_login.xml

    private Button login; // id = buttonLogin
    private EditText inputEmail; // id = email
    private EditText inputPassword ; // id = password

    private ProgressDialog progressDialog;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Assign buttons GUI Buttons
        inputEmail     = (EditText) findViewById(R.id.email);
        inputPassword  = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.buttonLogin);
        buttonLaunchReg = (Button) findViewById(R.id.launchRegistrationBtn);

        // Add Progress Dialog incase the request hang

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //Set Session Manager object
        session = new SessionManager(getApplicationContext());

        // Check if a user is logged into the app

        if(session.isLoggedIn()){

            // if the user is logged in, take the user
            // to the MainActivity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

        } // end session check

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                if (email.trim().length() > 0 && password.trim().length() > 0){
                    loginCheck(email, password);


                }else{
                    Toast.makeText(getApplicationContext(),
                    "Enter Email and Password",Toast.LENGTH_LONG) .show();
                }


            }
        }); //end LoginOnClickListener

       //Launch Registration if button clicked
        onClickButtonListener();

    }

    public void loginCheck(final String email, final String password){
        String tag_string_req = "req_login";
        progressDialog.setMessage("Attempting to Login");
        showDialog();

        StringRequest stringRequest = new StringRequest(Method.POST,
                NetworkUrl.REGISTRATION_URL, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        session.setLogin(true);

                        // Launch main activity
                        Intent intent = new Intent(LoginActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e(TAG, "Login Error: " + volleyError.getMessage());
                Toast.makeText(getApplicationContext(),
                        volleyError.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "login");
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);
    }

    private void showDialog() {
        if (progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
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
