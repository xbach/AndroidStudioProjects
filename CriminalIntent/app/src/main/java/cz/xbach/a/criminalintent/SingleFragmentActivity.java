package cz.xbach.a.criminalintent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

/**
 * Created by xbach on 7/9/15.
 */
public abstract class SingleFragmentActivity extends Activity {
    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);


        FragmentManager fm = getFragmentManager();
        // getFragmentManager if used without Support Lib

        Fragment fragment =  fm.findFragmentById(R.id.fragmentContainer);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }
}
