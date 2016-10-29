package com.jmgarzo.infomovies;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jmgarzo.infomovies.data.MoviesContract;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


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
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TopRateFragment(), "Top rate");
        adapter.addFragment(new MostPopularFragment(), "Most popular");
        adapter.addFragment(new FavoriteFragment(), "Favorite");
        viewPager.setAdapter(adapter);
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
            if(Utility.isPreferenceSortByFavorite(this)){
                args.putString(MoviesContract.FavoriteMovieEntry._ID, idMovie);
            }else {
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
            if(Utility.isPreferenceSortByFavorite(this)){
                intent.putExtra(MoviesContract.FavoriteMovieEntry._ID, idMovie);
            }else {
                intent.putExtra(MoviesContract.MoviesEntry._ID, idMovie);
            }
            startActivity(intent);
        }

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
