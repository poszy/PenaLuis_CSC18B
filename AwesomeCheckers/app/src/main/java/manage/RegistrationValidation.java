package manage;

import android.widget.EditText;
import java.util.regex.Pattern;


public class RegistrationValidation {

    //Reg ex Variables and Patterns
    private static final String valEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    private static final String valName = "^[a-zA-Z0-9]{3,35}$ ";

    private static final String valPassword = "^[a-z]";

    //Error Messages
    private static final String errName = "Invalid name";
    private static final String errEmail = "invalid email , Must Match the following joe@aol.com | joe@wrox.co.uk | joe@domain.info";
    private static final String errPassword = "Invalid Password";

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, valEmail, errEmail, required);
    } //end isemail


    // Check Password
    public static boolean isPassword(EditText editText, boolean required) {
        return isValid(editText, valPassword, errPassword, required);
    } // end is password

    // If the input files is valid, based on the paramater, return
    // True
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();

        editText.setError(null);

        // If the fields are blank, return false, and throw error MSG
        if (required && !hasText(editText)) return false;

        // If the user input does not match Regex patten
        // return false and throw error MSG
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        } // enf if


        return true;
    } // end boolean is valid

    // Make sure use input does contain text
    // if not, throw error MSg
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(errName);
            return false;
        } // end if

        return true;
    }// end boolean has text

} // end Class
