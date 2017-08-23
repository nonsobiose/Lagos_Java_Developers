package com.nonsobiose.lagdev.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nonsobiose.lagdev.R;
import com.nonsobiose.lagdev.model.DeveloperProfile;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DevProfileActivity extends AppCompatActivity {
    DeveloperProfile developerProfile;
    TextView developerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_profile);

        // Gets the developers profile that was passed through an intent and unwraps it with Parcel
        developerProfile = Parcels.unwrap(getIntent().getParcelableExtra("DeveloperProfile"));

        // Gets a reference to an image view and Loads developers profile picture unto imageview
        ImageView developerImage = (ImageView) findViewById(R.id.developer_image);
        Picasso.with(this)
                .load(developerProfile.getAvatarUrl())
                .into(developerImage);


        // Creates an object of the textview used to display the developers name
        developerName = (TextView) findViewById(R.id.developer_name);
        developerName.setText(getDevelopersFullName());

        // Creates an object of the textview used to display the developers location
        TextView developerLocation = (TextView) findViewById(R.id.developer_location);
        developerLocation.setText(developerProfile.getLocation());

        // Creates an object of the textview used to display the developers number of repositories
        TextView developerRepos = (TextView) findViewById(R.id.developer_repos);
        developerRepos.setText(String.valueOf(developerProfile.getPublicRepos()));

        // Creates an object of the button used to view the developers profile on the web
        Button developerWebLink = (Button) findViewById(R.id.developer_web_link);
        developerWebLink.setText("VIEW " + getDevelopersFullName() + " ON THE WEB");

        // Sets an ACTION_VIEW intent as the onclick action, to view the developers web profile in a browser
        developerWebLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri developerWebPage = Uri.parse(developerProfile.getHtmlUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, developerWebPage);
                Intent intentChooser = Intent.createChooser(intent, getString(R.string.view_developer_profile));
                if (intentChooser.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentChooser);
                }
            }
        });

        // Creates an object of the floating action button in the activity_developers_profile.xml file
        FloatingActionButton developerShareLink = (FloatingActionButton) findViewById(R.id.share_button);

        // This is a share intent that shares the profile of the Github Java Developer,
        // when the floating action button is clicked
        developerShareLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_developer_profile) + developerProfile.getLogin() + "\n " + developerProfile.getHtmlUrl());
                Intent intentChooser = Intent.createChooser(intent, getString(R.string.share_intent_chooser_title));
                if (intentChooser.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentChooser);
                }
            }
        });
    }
    // Truncates the length of the developers fullname if its length is greater than 20
    private String getDevelopersFullName() {
        String developersName = developerProfile.getName();
        if (developersName.length() > 20) {
            return developersName.substring(0, 20);
        }
        return developersName;
    }
}
