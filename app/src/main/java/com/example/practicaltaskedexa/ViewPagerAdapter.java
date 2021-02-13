package com.example.practicaltaskedexa;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs;

    public ViewPagerAdapter(FragmentManager fm, CharSequence[] mTitles, int mNumbOfTabsumb) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AllFragment();
            case 1:
                return new ChicagoFragment();
            case 2:
                return  new NewYorkFragment();
            case 3:
                return   new LosAngelesFragment();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
