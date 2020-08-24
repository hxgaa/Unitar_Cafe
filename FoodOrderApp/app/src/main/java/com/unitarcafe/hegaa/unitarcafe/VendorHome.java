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


public class VendorHome extends AppCompatActivity  {

    private TabItem food, order, home;
    TextView lblWelcome;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendorhome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.vendorToolbar);
        setSupportActionBar(toolbar);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);

//        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);



        home = (TabItem) this.findViewById(R.id.tabHome);
        food = (TabItem)this.findViewById(R.id.tabFoods);
        order = (TabItem) this.findViewById(R.id.tabOrders);
        lblWelcome = findViewById(R.id.lblWelcome);

        lblWelcome.setText("Welcome, "+ Common.currentUser.getName());

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(VendorHome.this, "Vendor Home", Toast.LENGTH_SHORT).show();
                if (tab.equals(food)) {
                    food.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(VendorHome.this, "Key: Manage Food!", Toast.LENGTH_SHORT).show();
                            Intent foods = new Intent(VendorHome.this, VendorFoods.class);
                            startActivity(foods);
                        }
                    });
                } else if (tab.equals(order)) {


                    order.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(VendorHome.this, "Key: Manage Orders!", Toast.LENGTH_SHORT).show();
                            Intent foods = new Intent(VendorHome.this, VendorOrders.class);
                            startActivity(foods);
                        }
                    });
                }
//                } else {
//
//                    home.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Toast.makeText(VendorHome.this, "Key: Home!", Toast.LENGTH_SHORT).show();
////                VendorHome.this.recreate();
//                        }
//                    });
//                }
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
