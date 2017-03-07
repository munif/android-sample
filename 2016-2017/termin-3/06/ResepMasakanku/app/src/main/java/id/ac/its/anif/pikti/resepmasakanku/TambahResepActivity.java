package id.ac.its.anif.pikti.resepmasakanku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import id.ac.its.anif.pikti.sqlite.DatabaseHelper;
import id.ac.its.anif.pikti.sqlite.Kategori;
import id.ac.its.anif.pikti.sqlite.Resep;

public class TambahResepActivity extends AppCompatActivity {

    Spinner spinnerKategori;
    DatabaseHelper db;
    List<Kategori> kategoriList;
    HashMap<Integer,Integer> spinnerMap;
    Integer kategoriId;

    EditText etResepNama, etResepIntro, etResepBahan, etResepInstruksi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_resep);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tambah Resep Baru");

        etResepNama = (EditText) findViewById(R.id.etResepNama);
        etResepIntro = (EditText) findViewById(R.id.etResepIntro);
        etResepBahan = (EditText) findViewById(R.id.etResepBahan);
        etResepInstruksi = (EditText) findViewById(R.id.etResepInstruksi);

        // Menghubungkan dengan basis data
        db = new DatabaseHelper(getApplicationContext());
        db.openDatabase();

        // Load data kategori dari basis data ke dalam spinner
        kategoriList = db.getAllKategori();
        String[] spinnerArray = new String[kategoriList.size()];
        spinnerMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < kategoriList.size(); i++)
        {
            spinnerMap.put(i, kategoriList.get(i).getKategoriId());
            spinnerArray[i] = kategoriList.get(i).getKategoriNama();
        }

        spinnerKategori = (Spinner) findViewById(R.id.spinnerKategori);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_style, spinnerArray);
        adapter.setDropDownViewResource(R.layout.spinner_style);
        spinnerKategori.setAdapter(adapter);

        // Mengeset event onItemSelectedListener di spinner
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = spinnerKategori.getSelectedItem().toString();
                kategoriId = spinnerMap.get(spinnerKategori.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void addResepBaru(View view) {
        Resep resep = new Resep();
        resep.setResepNama(String.valueOf(etResepNama.getText()));
        resep.setResepIntro(String.valueOf(etResepIntro.getText()));
        resep.setResepBahan(String.valueOf(etResepBahan.getText()));
        resep.setResepInstruksi(String.valueOf(etResepInstruksi.getText()));
        resep.setResepGambar("default_gambar_menu");
        resep.setKategoriId(kategoriId);

        long result = db.addResep(resep);
        if (result > 0) {
            Toast.makeText(this, "Resep baru berhasil ditambahkan", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Ada kesalahan dalam penyimpanan resep baru", Toast.LENGTH_SHORT).show();
        }

        // Kembali ke MainActivity
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


}
