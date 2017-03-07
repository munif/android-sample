package id.ac.its.anif.pikti.resepmasakanku;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.ac.its.anif.pikti.sqlite.DatabaseHelper;
import id.ac.its.anif.pikti.sqlite.Resep;

public class DetailResepActivity extends AppCompatActivity {

    TextView tvResepIntro, tvResepBahan, tvResepInstruksi;
    ImageView ivResepCover;
    Resep resep;
    DatabaseHelper db;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_resep);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (db.isFavorit(resep.getResepId())) {
                    db.deleteFavorit(resep.getResepId());
                    Snackbar.make(view, "Resep berhasil dihapus dari Favorit", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(android.R.drawable.btn_star_big_off);
                } else {
                    db.addFavorit(resep.getResepId());
                    Snackbar.make(view, "Resep berhasil ditambahkan ke dalam Favorit", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    fab.setImageResource(android.R.drawable.btn_star_big_on);
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Mendapatkan objek resep dari MainActivity
        resep = (Resep) getIntent().getSerializableExtra("resep");
        getSupportActionBar().setTitle(resep.getResepNama());

        // Mendapatkan elemen view
        tvResepIntro = (TextView)findViewById(R.id.tvResepIntro);
        tvResepBahan = (TextView)findViewById(R.id.tvResepBahan);
        tvResepInstruksi = (TextView)findViewById(R.id.tvResepInstruksi);
        ivResepCover = (ImageView)findViewById(R.id.ivResepCover);

        // Mengeset elemen view
        tvResepIntro.setText(resep.getResepIntro());
        tvResepBahan.setText(resep.getResepBahan());
        tvResepInstruksi.setText(resep.getResepInstruksi());

        Resources res = getBaseContext().getResources();
        String mDrawableName = resep.getResepGambar();
        int resId = res.getIdentifier(mDrawableName, "drawable", getBaseContext().getPackageName());
        ivResepCover.setImageResource(resId);

        // Menghubungkan dengan basis data
        db = new DatabaseHelper(getApplicationContext());
        db.openDatabase();

        // Mengeset ikon Favorit
        if (db.isFavorit(resep.getResepId())) {
            fab.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            fab.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }

    public void deleteResep(View view) {
        long result = db.deleteResep(resep.getResepId());
        if (result > 0) {
            Toast.makeText(this, "Data resep berhasil dihapus.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void editResep(View view) {
        Intent intent = new Intent(getApplicationContext(), EditResepActivity.class);
        intent.putExtra("resep", resep);
        startActivity(intent);
    }
}
