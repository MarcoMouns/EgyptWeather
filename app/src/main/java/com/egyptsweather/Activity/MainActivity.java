package com.egyptsweather.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.egyptsweather.Adapter.PagerAdapter;
import com.egyptsweather.Adapter.RecyclerViewAdapter;
import com.egyptsweather.Fragment.ListFragment;
import com.egyptsweather.Fragment.MapFragment;
import com.egyptsweather.Model.City_Details;
import com.egyptsweather.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager vp;
    long time;



    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press Back Again", Toast.LENGTH_SHORT).show();
        }
        time = System.currentTimeMillis();
    }

    private int[] tabIcons = {R.drawable.cities, R.drawable.maps};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //set locale to English
        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        vp = findViewById(R.id.viewpager);
        addPages(vp);

        tab = findViewById(R.id.tabs);

        tab.setTabGravity(TabLayout.GRAVITY_FILL);
        tab.setupWithViewPager(vp);
        tab.setOnTabSelectedListener(tabSelectedListener(vp));
        setupTabIcons();

    }

    private void setupTabIcons() {
        tab.getTabAt(0).setIcon(tabIcons[0]);
        tab.getTabAt(1).setIcon(tabIcons[1]);

    }

    private void addPages(ViewPager viewPager) {
        PagerAdapter myPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        myPagerAdapter.addPage(new ListFragment());
        myPagerAdapter.addPage(new MapFragment());


        vp.setAdapter(myPagerAdapter);
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener(final ViewPager pager) {
        return new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };
    }


}
