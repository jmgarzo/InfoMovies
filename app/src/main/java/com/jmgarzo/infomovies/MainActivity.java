//package com.jmgarzo.infomovies;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.Menu;
//import android.view.MenuItem;
//
//import com.jmgarzo.infomovies.data.MoviesContract;
//
//public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {
//
//    private boolean mTwoPane;
//    private String mSortBy;
//    private static final String DETAILFRAGMENT_TAG = "DFTAG";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mSortBy = Utility.getPreferredSortBy(this);
//        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//
////        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
////        fab.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
////            }
////        });
//
//        if (findViewById(R.id.movie_detail_container) != null) {
//            // The detail container view will be present only in the large-screen layouts
//            // (res/layout-sw600dp). If this view is present, then the activity should be
//            // in two-pane mode.
//            mTwoPane = true;
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            if (savedInstanceState == null) {
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.movie_detail_container, new DetailMovieFragment(), DETAILFRAGMENT_TAG)
//                        .commit();
//
//
//            }
//        } else {
//            mTwoPane = false;
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent intent = new Intent(this, SettingsActivity.class);
//            startActivity(intent);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//
//    @Override
//    public void onItemSelected(Uri contentUri,String idMovie) {
//        if (mTwoPane) {
//            // In two-pane mode, show the detail view in this activity by
//            // adding or replacing the detail fragment using a
//            // fragment transaction.
//            Bundle args = new Bundle();
//            args.putParcelable(DetailMovieFragment.DETAIL_URI, contentUri);
//            args.putBoolean(DetailMovieFragment.TWO_PANELS, mTwoPane);
//            if(Utility.isPreferenceSortByFavorite(this)){
//                args.putString(MoviesContract.FavoriteMovieEntry._ID, idMovie);
//            }else {
//                args.putString(MoviesContract.MoviesEntry._ID, idMovie);
//            }
//
//
//            DetailMovieFragment fragment = new DetailMovieFragment();
//            fragment.setArguments(args);
//
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.movie_detail_container, fragment, DETAILFRAGMENT_TAG)
//                    .commit();
//        } else {
//            Intent intent = new Intent(this, DetailMovie.class)
//                    .setData(contentUri);
//            if(Utility.isPreferenceSortByFavorite(this)){
//                intent.putExtra(MoviesContract.FavoriteMovieEntry._ID, idMovie);
//            }else {
//                intent.putExtra(MoviesContract.MoviesEntry._ID, idMovie);
//            }
//            startActivity(intent);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        String sortBy = Utility.getPreferredSortBy(this);
//        // update the moviedetail in our second pane using the fragment manager
//        if (sortBy != null ) {
//            MainActivityFragment mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_main);
//            if (null != mainActivityFragment) {
//                mainActivityFragment.onSortChanged();
//            }
//            DetailMovieFragment detailMovieFragment = (DetailMovieFragment) getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
//            if (null != detailMovieFragment) {
//                detailMovieFragment.onSortChanged();
//            }
//            mSortBy = sortBy;
//        }
//    }
//}
//
