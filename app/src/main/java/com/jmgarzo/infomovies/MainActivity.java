package com.jmgarzo.infomovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.jmgarzo.infomovies.data.MoviesContract;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private boolean mTwoPane;
    private String mSortBy;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private Context mContext;

    private TabLayout mTabLayout;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSortBy = Utility.getPreferredSortBy(this);
        setContentView(R.layout.activity_main);
        mContext = this;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        if (findViewById(R.id.movie_detail_container) != null) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailMovieFragment(), DETAILFRAGMENT_TAG)
                        .commit();


            }
        } else {
            mTwoPane = false;
        }


        //TABS
        //setContentView(R.layout.activity_tabs);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                                                @Override
                                                public void onTabSelected(TabLayout.Tab tab) {
                                                    SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mContext);

                                                    switch (tab.getPosition() + 1) {
                                                        case 1: {
                                                            pref.edit().putString(mContext.getString(R.string.pref_sort_by_key), mContext.getString(R.string.pref_sort_by_most_popular)).apply();
//                        pref = PreferenceManager.getDefaultSharedPreferences(mContext);
                                                            break;
                                                        }
                                                        case 2: {
                                                            pref.edit().putString(mContext.getString(R.string.pref_sort_by_key), mContext.getString(R.string.pref_sort_by_top_rate)).apply();
//                        pref = PreferenceManager.getDefaultSharedPreferences(mContext);

                                                            break;
                                                        }
                                                        case 3: {
                                                            pref.edit().putString(mContext.getString(R.string.pref_sort_by_key), mContext.getString(R.string.pref_sort_by_favorite)).apply();
//                        pref = PreferenceManager.getDefaultSharedPreferences(mContext);

                                                            break;
                                                        }
                                                    }
                                                    String sortBy = Utility.getPreferredSortBy(mContext);
                                                    // update the moviedetail in our second pane using the fragment manager
                                                    if (sortBy != null) {
                                                        MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
                                                        if (null != mainActivityFragment) {
                                                            mainActivityFragment.onSortChanged();
                                                        } else {
                                                            mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                                                            if (null != mainActivityFragment) {
                                                                mainActivityFragment.onSortChanged();
                                                            }
                                                        }
                                                        DetailMovieFragment detailMovieFragment = (DetailMovieFragment) getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
                                                        if (null != detailMovieFragment) {
                                                            detailMovieFragment.onSortChanged();
                                                        }
                                                        mSortBy = sortBy;
                                                    }
                                                }

                                                @Override
                                                public void onTabUnselected(TabLayout.Tab tab) {

                                                }

                                                @Override
                                                public void onTabReselected(TabLayout.Tab tab) {

                                                }
                                            }

        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemSelected(Uri contentUri, String idMovie) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailMovieFragment.DETAIL_URI, contentUri);
            args.putBoolean(DetailMovieFragment.TWO_PANELS, mTwoPane);
            if (Utility.isPreferenceSortByFavorite(this)) {
                args.putString(MoviesContract.FavoriteMovieEntry._ID, idMovie);
            } else {
                args.putString(MoviesContract.MoviesEntry._ID, idMovie);
            }


            DetailMovieFragment fragment = new DetailMovieFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailMovie.class)
                    .setData(contentUri);
            if (Utility.isPreferenceSortByFavorite(this)) {
                intent.putExtra(MoviesContract.FavoriteMovieEntry._ID, idMovie);
            } else {
                intent.putExtra(MoviesContract.MoviesEntry._ID, idMovie);
            }
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String sortBy = Utility.getPreferredSortBy(this);
        // update the moviedetail in our second pane using the fragment manager
        if (sortBy != null) {
            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
            if (null != mainActivityFragment) {
                mainActivityFragment.onSortChanged();
            }else{
                mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                if(mainActivityFragment!=null){
                    mainActivityFragment.onSortChanged();
                }
            }
            DetailMovieFragment detailMovieFragment = (DetailMovieFragment) getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if (null != detailMovieFragment) {
                detailMovieFragment.onSortChanged();
            }
            mSortBy = sortBy;
        }
    }

//    /**
//     * A placeholder fragment containing a simple view.
//     */
//    public static class PlaceholderFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private static final String ARG_SECTION_NUMBER = "section_number";
//
//        public PlaceholderFragment() {
//        }
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        public static PlaceholderFragment newInstance(int sectionNumber) {
//            PlaceholderFragment fragment = new PlaceholderFragment();
//            Bundle args = new Bundle();
//            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                                 Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


           return MainActivityFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.tab_title_most_popular);
                case 1:
                    return getString(R.string.tab_title_top_rate);
                case 2:
                    return getString(R.string.tab_title_favorites);
            }
            return null;
        }
    }
}
