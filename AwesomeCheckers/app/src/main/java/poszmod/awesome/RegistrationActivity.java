package poszmod.awesome;


import manage.RegistrationValidation;
import manage.SQLiteHandler;
import manage.SessionManager;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RegistrationActivity extends ActionBarActivity {


    private static final String TAG = RegistrationActivity.class.getSimpleName();

    // Create Private buttons and variables that will be able to
    // Control the Login Activity
    // The IDs are defined in activity_registration.xml

    private Button register;                 // id = btnRegister
    private Button buttonLaunchLog;         //  id = launchLoginBtn
    private EditText inputName;            //   id = name
    private EditText inputEmail;          //    id = email
    private EditText inputPassword ;     //     id = password


    private ProgressDialog progressDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        valFields();



        // Add Progress Dialog in case the request hang

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //Set Session Manager object
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        //check to see if the User is Logged in
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegistrationActivity.this,
                    MainActivity.class);
            startActivity(intent);

            finish();

        }

        // Register Registration Button clicked event

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                String password = inputPassword.getText().toString();
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();



                if (checkValidation() && checkSelectionExists() &&!name.isEmpty() && !email.isEmpty()&& !password.isEmpty()){


                    addUser(name, email, password);

                }


                else{
                    Toast.makeText(getApplicationContext(),
                            "Please Correct All Errors Before Proceeding", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

       // Launch Login Screen

        buttonLaunchLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        //Make sure Checkbox is clicked




    } // end on create

    private boolean checkSelectionExists() {
        boolean selectionExists;
        if ((boolean) ((CheckBox) findViewById(R.id.Terms)).isChecked()) selectionExists = true;
        else selectionExists = false;

        if (selectionExists == false){ Toast.makeText(getApplicationContext(),
                "Please Agree To Terms", Toast.LENGTH_LONG)
                .show();}



        return selectionExists;
    }

    private void valFields(){

        // Assign GUI Edtis



        buttonLaunchLog = (Button) findViewById(R.id.launchLoginBtn);

        inputName = (EditText) findViewById(R.id.name);
        // TextWatcher would let us check validation error on the fly
        inputName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                RegistrationValidation.hasText(inputName);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        inputEmail = (EditText) findViewById(R.id.email);
        inputEmail.addTextChangedListener(new TextWatcher() {
            // after every change has been made to this editText, we would like to check validity
            public void afterTextChanged(Editable s) {
                RegistrationValidation.isEmailAddress(inputEmail, true);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        inputPassword = (EditText) findViewById(R.id.password);
        inputPassword.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                RegistrationValidation.isPassword(inputPassword, false);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        register = (Button) findViewById(R.id.btnRegister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Validation class will check the error and display the error on respective fields
                but it won't resist the form submission, so we need to check again before submit
                 */
                if ( checkValidation () )
                    submitForm();
                else
                    Toast.makeText(RegistrationActivity.this, "Form contains error", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void submitForm() {
        // Submit your form here. your form is valid
        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!RegistrationValidation.hasText(inputName)) ret = false;
        if (!RegistrationValidation.isEmailAddress(inputEmail, true)) ret = false;
        if (!RegistrationValidation.isPassword(inputPassword, false)) ret = false;

        return ret;
    }

    private void addUser(final String name, final String email, final String password){
        String tag_request_string = "req_register";

        progressDialog.setMessage("Attempting to Register");
        showDialog();

        StringRequest stringRequest = new StringRequest(Method.POST,
                NetworkUrl.REGISTRATION_URL, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, uid, created_at);

                        // Launch login activity
                        Intent intent = new Intent(
                                RegistrationActivity.this,
                                LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }
                ){
            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("tag", "register");
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_request_string);

    } //end register user


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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


    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }



}
