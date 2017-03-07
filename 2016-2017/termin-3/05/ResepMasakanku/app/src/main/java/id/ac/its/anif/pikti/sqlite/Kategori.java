package id.ac.its.anif.pikti.sqlite;

import java.io.Serializable;

/**
 * Created by Abdul Munif on 3/5/2017.
 */

public class Kategori implements Serializable {
    private int kategoriId;
    private String kategoriNama;
    private String kategoriKeterangan;

    public Kategori() {
    }

    public Kategori(int kategoriId, String kategoriNama, String kategoriKeterangan) {
        this.kategoriId = kategoriId;
        this.kategoriNama = kategoriNama;
        this.kategoriKeterangan = kategoriKeterangan;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }

    public String getKategoriNama() {
        return kategoriNama;
    }

    public void setKategoriNama(String kategoriNama) {
        this.kategoriNama = kategoriNama;
    }

    public String getKategoriKeterangan() {
        return kategoriKeterangan;
    }

    public void setKategoriKeterangan(String kategoriKeterangan) {
        this.kategoriKeterangan = kategoriKeterangan;
    }
}
