package poszmod.awesome;

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
public class NavigationFragmentGeneral extends Fragment {

    public static final String PREF_FILE_NAME = "testpref";
    public static final String KEY_USER_LEARNED_DRAWER ="user_learned_drawer";
    private ActionBarDrawerToggle mDrawerToggle;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    public NavigationFragmentGeneral() {}

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readToPreferences(getActivity(),KEY_USER_LEARNED_DRAWER, "false"));
        if(savedInstanceState!= null)
            mFromSavedInstanceState = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navigation_general, container, false);
    }

    public void setUp(int fragmentId, DrawerLayout drawerlayout, Toolbar toolbar) {

        View containerView = getActivity().findViewById(fragmentId);

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),drawerlayout,toolbar,
                R.string.open, R.string.close){

            public void onDrawerOpened(View drawerView){
                super.onDrawerOpened(drawerView);

                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true");
                }

                getActivity().invalidateOptionsMenu();
            }

            public void onDrawerClosed(View drawerView){
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

        };

        // if user has not learned the drawer and its the very first time the app is opened
        // open drawer by a animation

        if (!mUserLearnedDrawer && !mFromSavedInstanceState)
            drawerlayout.openDrawer(containerView);

        drawerlayout.setDrawerListener(mDrawerToggle);

        drawerlayout.post(new Runnable() {

            public void run() {
                mDrawerToggle.syncState();
            }

        });
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue){

        //create object of sharedPreferences
        // MODE_PRIVATE is set to ensure only my app is allowed to change the preferences

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);

        editor.apply();

    }

    public static String readToPreferences(Context context, String preferenceName, String defaultValue){

        SharedPreferences sharedPreferences=context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName,defaultValue);
    }

}
