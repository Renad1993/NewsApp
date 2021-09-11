package com.example.android.newsapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.newsapp.Constants.API_KEY_PARAM;
import static com.example.android.newsapp.Constants.FORMAT_PARAM;
import static com.example.android.newsapp.Constants.FORMAT_VALUE;
import static com.example.android.newsapp.Constants.FROM_DATE_PARAM;
import static com.example.android.newsapp.Constants.FROM_DATE_VALUE;
import static com.example.android.newsapp.Constants.ORDER_BY_PARAM;
import static com.example.android.newsapp.Constants.ORDER_BY_VALUE;
import static com.example.android.newsapp.Constants.Q_PARAM;
import static com.example.android.newsapp.Constants.Q_VALUE;
import static com.example.android.newsapp.Constants.SHOW_FIELDS_PARAM;
import static com.example.android.newsapp.Constants.SHOW_FIELDS_VALUE;
import static com.example.android.newsapp.Constants.SHOW_TAGS_PARAM;
import static com.example.android.newsapp.Constants.SHOW_TAGS_VALUE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;
    /**
     * URL for news data from the Guardian dataset
     */
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search";
    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView emptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(emptyStateTextView);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current news that was clicked on
                News currentNews = mAdapter.getItem(position);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getmUrl());
                // Create a new intent to view the news URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                if (websiteIntent.resolveActivity(getPackageManager()) != null) {

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                } else {
    // Notify the user that there is no app installed to open a browser

                }

            }
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            Log.e(getString(R.string.news_activity), getString(R.string.init_Loader));
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            emptyStateTextView.setText(R.string.no_internet_connection);
        }


    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        //secret api key value
        String secretValue = getString(R.string.api_key_value);
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();

        // Append query parameter and its value. For example, the `format=geojson`
        uriBuilder.appendQueryParameter(Q_PARAM, Q_VALUE);
        uriBuilder.appendQueryParameter(FORMAT_PARAM, FORMAT_VALUE);
        uriBuilder.appendQueryParameter(FROM_DATE_PARAM, FROM_DATE_VALUE);
        uriBuilder.appendQueryParameter(SHOW_TAGS_PARAM, SHOW_TAGS_VALUE);
        uriBuilder.appendQueryParameter(SHOW_FIELDS_PARAM, SHOW_FIELDS_VALUE);
        uriBuilder.appendQueryParameter(ORDER_BY_PARAM, ORDER_BY_VALUE);
        uriBuilder.appendQueryParameter(API_KEY_PARAM, secretValue);

        // Return the completed uri `https://content.guardianapis.com/search?q=music&format=json&
        // from-date=2010-01-01&show-tags=contributor&show-fields=short_url&order-by=newest&
        // api-key=15c7b31a-2dbf-40ce-8d72-ef2aa1878025
        return new NewsLoader(this, uriBuilder.toString());


    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        // Set empty state text to display "No news found."
        emptyStateTextView.setText(R.string.no_news);
        // Clear the adapter of previous news data
        Log.e(getString(R.string.new_activity), getString(R.string.on_load_finished));
        mAdapter.clear();

        // If there is a valid list of {@link news}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();

    }
}