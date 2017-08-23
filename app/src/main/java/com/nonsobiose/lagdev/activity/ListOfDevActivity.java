package com.nonsobiose.lagdev.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.nonsobiose.lagdev.R;
import com.nonsobiose.lagdev.adapter.DeveloperAdapter;
import com.nonsobiose.lagdev.model.Developer;
import com.nonsobiose.lagdev.model.DeveloperProfile;
import com.nonsobiose.lagdev.model.JSONResponse;
import com.nonsobiose.lagdev.rest.ApiClient;
import com.nonsobiose.lagdev.rest.GithubService;
import com.nonsobiose.lagdev.support.ItemClickSupport;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOfDevActivity extends AppCompatActivity {

    List<Developer> developers;
    RecyclerView developerRecyclerView;
    String developersUsername = "";
    static GithubService githubService;
    CardView listProgressBar;
    CardView profileProgressBar;
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;
    boolean isConnectedOrConnecting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_dev);

        // Creates an object of the recyclerView in the activity_developers_list.xml file
        developerRecyclerView = (RecyclerView) findViewById(R.id.developers_list);

        // Creates an object of the list of developers progress bar in the activity_developers_list.xml file
        listProgressBar = (CardView) findViewById(R.id.list_progress);

        // Creates an object of the developers profile progress bar in the activity_developers_list.xml file
        profileProgressBar = (CardView) findViewById(R.id.profile_progress);

        // Creates an object of the empty state view in the activity_developers_list.xml file
        final LinearLayout emptyStateView = (LinearLayout) findViewById(R.id.empty_state_view);

        // Obtains a reference to the GithubService interface used to makes the Api request call
        githubService = ApiClient.getClient().create(GithubService.class);

        /** Registers the app to receive a Broadcast intent when theres change in the network connection
         *  Here, when theres an active network connection(either the app is connected or connecting) the Api call is made
         *  else, no call to the Api is made and the empty view state is displayed
         */
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                isConnectedOrConnecting = networkInfo != null && networkInfo.isConnectedOrConnecting();
                if (isConnectedOrConnecting) {
                    emptyStateView.setVisibility(View.GONE);
                    listProgressBar.setVisibility(View.VISIBLE);
                    getListOfDevelopers();
                } else {
                    if(developers != null) {
                        emptyStateView.setVisibility(View. GONE);
                    } else {
                        emptyStateView.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
        intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(broadcastReceiver, intentFilter);

        // This is a click listener for each item in the Recyclerview.
        // It gets the position of the clicked view in the RecyclerView and then gets the username of that developer object
        // at that location, which is then appended as a path to the Github base url for an API call for the developer
        // Github profile
        ItemClickSupport.addTo(developerRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (isConnectedOrConnecting) {
                    profileProgressBar.setVisibility(View.VISIBLE);
                    Developer developer = developers.get(position);
                    developersUsername = developer.getLogin();
                    getDeveloperProfile(developersUsername);
                }
            }
        });

    }

    /**
     * This is a helper method that makes an API call to the Github list of Java Developers endpoint
     * and returns a list of the Developers.
     * on response, the progress bar is disabled and the result gotten is passed into an Adapter to display the list
     * of developers to the UI
     */
    private void getListOfDevelopers() {
        Call<JSONResponse> callDevelopersList = githubService.getJSONResponse();
        callDevelopersList.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                listProgressBar.setVisibility(View.GONE);
                developers = response.body().getItems();
                developerRecyclerView.setAdapter(new DeveloperAdapter(developers, getApplicationContext()));
                developerRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }
            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

    /**
     * This is a helper method that makes an API call to the Github Java Developers profile endpoint
     * and returns the profile details of the Developers.
     * on response, the progress bar is disabled and the result gotten is passed through an intent to the
     * DeveloperProfileActivity to display the details to the UI
     *
     * @Param developerUsername this is the Github username of the developer whose profile is the endpoint for the API call
     */
    private void getDeveloperProfile(String developerUsername) {
        Call<DeveloperProfile> callDeveloperProfile = githubService.getDeveloperProfile(developerUsername);
        callDeveloperProfile.enqueue(new Callback<DeveloperProfile>() {
            @Override
            public void onResponse(Call<DeveloperProfile> call, Response<DeveloperProfile> response) {
                profileProgressBar.setVisibility(View.GONE);
                DeveloperProfile developerProfile = response.body();
                Intent intent = new Intent(ListOfDevActivity.this, DevProfileActivity.class);
                intent.putExtra("DeveloperProfile", Parcels.wrap(developerProfile));//Wraps the developer object with Parcel for easy passing through intents
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<DeveloperProfile> call, Throwable t) {
                //Do nothing
            }
        });
    }

    //Unregister the broadcast receiver when the activity is destroyed so as to prevent leakage
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null) {
            this.unregisterReceiver(broadcastReceiver);
        }
    }
}
