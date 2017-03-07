package id.ac.its.anif.pikti.resepmasakanku;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.Channels;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import id.ac.its.anif.pikti.adapter.RecyclerItemClickListener;
import id.ac.its.anif.pikti.adapter.ResepAdapter;
import id.ac.its.anif.pikti.sqlite.DatabaseHelper;
import id.ac.its.anif.pikti.sqlite.Resep;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    // Inisialisasi variabel
    DatabaseHelper db;
    List<Resep> resepList = new ArrayList<Resep>();
    RecyclerView rvResep;
    ResepAdapter resepAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        // Inisialisasi basis data
        db = new DatabaseHelper(getApplicationContext());
        try {
            db.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.openDatabase();

        // Membaca daftar resep dari database
        resepList = db.getAllResep();

        // Mengeset adapter
        resepAdapter = new ResepAdapter(resepList, getApplicationContext());

        // Mengeset recycler view
        rvResep = (RecyclerView) findViewById(R.id.rvResep);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvResep.setLayoutManager(mLayoutManager);
        rvResep.setAdapter(resepAdapter);

        // Menambahkan fungsi klik pada item RecyclerView
        rvResep.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getBaseContext(), DetailResepActivity.class);
                        intent.putExtra("resep", (Serializable) resepList.get(position));
                        startActivity(intent);
                    }
                })
        );
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_semua_resep) {
            resepList = db.getAllResep();
            resepAdapter.swap(resepList);
        } else if (id == R.id.nav_ayam) {
            resepList = db.getResepByKategori(1);
            resepAdapter.swap(resepList);
        } else if (id == R.id.nav_kambing) {
            resepList = db.getResepByKategori(11);
            resepAdapter.swap(resepList);
        } else if (id == R.id.nav_favorit) {
            resepList = db.getAllResepFavorit();
            resepAdapter.swap(resepList);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
