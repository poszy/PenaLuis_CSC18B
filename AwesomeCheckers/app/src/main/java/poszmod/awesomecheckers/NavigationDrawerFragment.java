package poszmod.awesomecheckers;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * A simple {@link Fragment} subclass.
 *
 *
 */
public class NavigationDrawerFragment extends Fragment {

    // Preference value that needs to be passed to the reference method

    public static final String PREF_FILE_NAME = "testpref";
    //create object of android drawer toggle
    // standard mDrawerToggle

    public static final String KEY_USER_LEARNED_DRAWER ="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;

    // create object of android drawer layout
    // standard mDrawerLayout

    private DrawerLayout mDrawerLayout;

    //


    // Variable that will determine if the user is aware of the NavigationDrawer
    private boolean mUserLearnedDrawer;

    // Variable that will determine if the fragment is being created for the first time or its coming
    // from a cycle or simply being called again
    private boolean mFromSavedInstanceState;

    private View containerView;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    //read if the mUserLearnedDrawer variable had a default Value
    // was the app started by the user before this


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mUserLearnedDrawer = Boolean.valueOf(readToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, "false"));

        // if not null, drawer is created for the first time
        // if its not null, drawer is coming back from a cycle

        if(savedInstanceState!= null){

            mFromSavedInstanceState=true;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    // set up the navigation bar


    public void setUp(int fragmentId, DrawerLayout drawerlayout, Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);


        mDrawerLayout=drawerlayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerlayout,toolbar,
                R.string.drawer_open, R.string.drawer_close){

            //override Drawer open, drawer closed

            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);
               // if the user has never opened the drawer, when the app starts
               // set it to true and saved that true value to the shared perferences

               if(!mUserLearnedDrawer){

                  mUserLearnedDrawer = true;
                  saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
               }
                //Activity Draw activity multiple times
                getActivity().invalidateOptionsMenu();

            }

            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                //Activity Draw activity multiple times
                getActivity().invalidateOptionsMenu();

            }

        };

        // if user has not learned the drawer and its the very first time the app is opened
        // open drawer by a animation

        if (!mUserLearnedDrawer && !mFromSavedInstanceState){

            mDrawerLayout.openDrawer(containerView);

        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable(){

            public void run(){

                // create menu icon for home
                mDrawerToggle.syncState();
            }

        }); // end pos new Runnable
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){

            //create object of sharedPreferences
            // MODE_PRIVATE is set to ensure only my app is allowed to change the preferences


        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);

        editor.apply();

    } // end saveToPreferences

    public static String readToPreferences(Context context, String preferenceName, String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);


    } // end readToPreferences
}
