package com.adanibo.twittersample;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends Activity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "16Z9BZlaWcVcW4mmc7fU6tCc4";
    private static final String TWITTER_SECRET = "c6td1ZNCoUW7AHdJBr1Qr0coGQtkrtpuKzArdxMtmYPdQWKmmE";

    private TwitterAuthClient mTwitterAuthClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        setContentView(R.layout.activity_main);

        mTwitterAuthClient = new TwitterAuthClient();

        LinearLayout twtLogScn = (LinearLayout) findViewById(R.id.twitter_login);
        twtLogScn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mTwitterAuthClient.authorize(MainActivity.this, new com.twitter.sdk.android.core.Callback<TwitterSession>() {

                    @Override
                    public void success(Result<TwitterSession> twitterSessionResult) {
                        Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(TwitterException e) {
                        e.printStackTrace();
                    }
                });

            }
        });

        LinearLayout twtScn = (LinearLayout) findViewById(R.id.twitter_tweet);
        twtScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri myImageUri = Uri.parse("android.resource://com.adanibo.twittersample/drawable/fabric");
                TweetComposer.Builder builder = new TweetComposer.Builder(MainActivity.this)
                        .text("just setting up my Fabric.")
                        .image(myImageUri);
                builder.show();
            }
        });

        LinearLayout twtTmlScn = (LinearLayout) findViewById(R.id.twitter_timeline);
        twtTmlScn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimelineActivity.class);
                startActivity(intent);
            }
        });

    } /* onCreate end */

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        mTwitterAuthClient.onActivityResult(requestCode, responseCode, intent);
    }

}
