package cz.xbach.a.criminalintent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;

import java.util.UUID;


public class CrimeActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment() {
        UUID crimeId = (UUID)getIntent().getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);

        return CrimeFragment.newInstance(crimeId);
    }




}
