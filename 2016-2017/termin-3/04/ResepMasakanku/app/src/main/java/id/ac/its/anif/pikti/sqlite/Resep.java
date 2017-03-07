package id.ac.its.anif.pikti.sqlite;

import java.io.Serializable;

/**
 * Created by Abdul Munif on 3/5/2017.
 */

public class Resep implements Serializable {
    private int resepId;
    private String resepNama;
    private String resepIntro;
    private String resepBahan;
    private String resepInstruksi;
    private String resepGambar;
    private int kategoriId;

    public Resep() {
    }

    public Resep(int resepId, String resepNama, String resepIntro, String resepBahan, String resepInstruksi, String resepGambar, int kategoriId) {
        this.resepId = resepId;
        this.resepNama = resepNama;
        this.resepIntro = resepIntro;
        this.resepBahan = resepBahan;
        this.resepInstruksi = resepInstruksi;
        this.resepGambar = resepGambar;
        this.kategoriId = kategoriId;
    }

    public int getResepId() {
        return resepId;
    }

    public void setResepId(int resepId) {
        this.resepId = resepId;
    }

    public String getResepNama() {
        return resepNama;
    }

    public void setResepNama(String resepNama) {
        this.resepNama = resepNama;
    }

    public String getResepIntro() {
        return resepIntro;
    }

    public void setResepIntro(String resepIntro) {
        this.resepIntro = resepIntro;
    }

    public String getResepBahan() {
        return resepBahan;
    }

    public void setResepBahan(String resepBahan) {
        this.resepBahan = resepBahan;
    }

    public String getResepInstruksi() {
        return resepInstruksi;
    }

    public void setResepInstruksi(String resepInstruksi) {
        this.resepInstruksi = resepInstruksi;
    }

    public String getResepGambar() {
        return resepGambar;
    }

    public void setResepGambar(String resepGambar) {
        this.resepGambar = resepGambar;
    }

    public int getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(int kategoriId) {
        this.kategoriId = kategoriId;
    }
}
