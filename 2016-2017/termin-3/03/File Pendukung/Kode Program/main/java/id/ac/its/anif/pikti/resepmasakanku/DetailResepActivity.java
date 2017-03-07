package id.ac.its.anif.pikti.resepmasakanku;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
                    Snackbar.make(view, "Resep berhasil dihapus dari Favorit", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
    }
}
