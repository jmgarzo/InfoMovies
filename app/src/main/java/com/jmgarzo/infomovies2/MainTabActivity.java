package com.jmgarzo.infomovies2;

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

import com.jmgarzo.infomovies2.data.MoviesContract;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MostPopularFragment(), getString(R.string.most_popular_tab_label));
        adapter.addFragment(new TopRateFragment(), getString(R.string.top_rate_tab_label));
        adapter.addFragment(new FavoriteFragment(), getString(R.string.favorite_tab_label));
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(Uri contentUri, String idMovie) {

        Intent intent = new Intent(this, DetailMovie.class)
                .setData(contentUri);

        if (Utility.isPreferenceSortByFavorite(this)) {
            intent.putExtra(MoviesContract.FavoriteMovieEntry._ID, idMovie);
        } else {
            intent.putExtra(MoviesContract.MoviesEntry._ID, idMovie);
        }

        startActivity(intent);
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
