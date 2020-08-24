package com.unitarcafe.hegaa.unitarcafe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.unitarcafe.hegaa.unitarcafe.Common.Common;
import com.unitarcafe.hegaa.unitarcafe.Model.ChartModel;

import org.achartengine.chart.PointStyle;
//import org.achartengine.ChartFactory;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class VendorHome extends AppCompatActivity {

    TextView lblWelcome;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.vendorToolbar);
        toolbar.setTitle("Vendor Home");
        setSupportActionBar(toolbar);
        final TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        TabLayout.Tab tab = tabs.getTabAt(0);
        tab.select();

        lblWelcome = findViewById(R.id.lblWelcome);

        lblWelcome.setText("Welcome, " + Common.currentUser.getName());

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorHome.this, "Vendor Home", Toast.LENGTH_SHORT).show();
                int selectedTabPosition = tabs.getSelectedTabPosition();
                if (selectedTabPosition == 2) {

                    Intent foods = new Intent(VendorHome.this, VendorFoods.class);
                    startActivity(foods);
                    finish();

                } else if (selectedTabPosition == 1) {

                    Intent foods = new Intent(VendorHome.this, VendorOrders.class);
                    startActivity(foods);
                    finish();

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }


}
