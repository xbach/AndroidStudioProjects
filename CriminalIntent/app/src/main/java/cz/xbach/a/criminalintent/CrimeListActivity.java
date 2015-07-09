package cz.xbach.a.criminalintent;

import android.app.Fragment;

/**
 * Created by xbach on 7/9/15.
 */
public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
