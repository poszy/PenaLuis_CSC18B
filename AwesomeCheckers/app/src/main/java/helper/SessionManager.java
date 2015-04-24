package helper;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by lu on 4/21/15.
 */
public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();


    SharedPreferences preferences;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;


    private static final String PREF_NAME = "AwesomeCheckersLogin";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this._context = context;
        preferences = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}


