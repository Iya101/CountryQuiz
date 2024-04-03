package edu.uga.cs.countryquiz;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CountryQuizPagerAdapter extends FragmentStateAdapter {



    public CountryQuizPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle ) {
        super( fragmentManager, lifecycle );
    }

    @Override
    public Fragment createFragment(int position){
        return CountryQuizFragment.newInstance( position );
    }

    @Override
    public int getItemCount() {
        return CountryQuizFragment.getNumberOfVersions();
    }






}

}
