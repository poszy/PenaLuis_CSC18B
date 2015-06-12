package poszmod.awesome;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

/**
 * Created by lu on 6/6/15.
 */
public class NavigationGeneralActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_general);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_custom);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        NavigationFragmentGeneral nfg_drawer = (NavigationFragmentGeneral) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        nfg_drawer.setUp(R.id.fragment_navigation_drawer, (DrawerLayout)findViewById(R.id.dl_drawer_layout_general), toolbar);

        ListView lv = (ListView) nfg_drawer.getActivity().findViewById(R.id.lv_fragment_navigation_list);
        lv.setOnItemClickListener(oicl_navigation_list);

    }

    public View setInnerLayout(int layout_id) {
        RelativeLayout r = (RelativeLayout) findViewById(R.id.rl_inclusion_space);
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(layout_id, null, false);
        r.addView(v, 1);

        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        p.addRule(RelativeLayout.BELOW, R.id.toolbar_custom);
        v.setLayoutParams(p);

        return v;
    }

    private ListView.OnItemClickListener oicl_navigation_list = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch(position) {
                case 0:
                    Log.i("Item Clicked", "Home");
                    startActivity(new Intent(NavigationGeneralActivity.this, MainActivity.class));
                    break;
                case 1:
                    Log.i("Item Clicked", "My Profile");
                    startActivity(new Intent(NavigationGeneralActivity.this, SettingsActivity.class));
                    break;
                case 2:
                    Log.i("Item Clicked", "New Game");
                    startActivity(new Intent(NavigationGeneralActivity.this, GameActivity.class));
                    break;
                case 3:
                    Log.i("Item Clicked", "Settings");
                    startActivity(new Intent(NavigationGeneralActivity.this, SettingsActivity.class));
                    break;
                case 4:
                    Log.i("Item Clicked", "About");
                    startActivity(new Intent(NavigationGeneralActivity.this, SettingsActivity.class));
                    break;
                case 5:
                    Log.i("Item Clicked", "More Apps");
                    startActivity(new Intent(NavigationGeneralActivity.this, SettingsActivity.class));
                    break;
                case 6:
                    Log.i("Item Clicked", "Contact");
                    startActivity(new Intent(NavigationGeneralActivity.this, SettingsActivity.class));
                    break;
                case 7:
                    Log.i("Item Clicked", "Logout");
                    startActivity(new Intent(NavigationGeneralActivity.this, SettingsActivity.class));
                    break;
            }
        }
    };

}
