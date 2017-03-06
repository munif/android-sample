package id.ac.its.anif.pikti.sqlite;

import java.io.Serializable;

/**
 * Created by Abdul Munif on 3/5/2017.
 */

public class Bookmark implements Serializable {
    private int bookmarkId;
    private int resepId;

    public Bookmark() {
    }

    public Bookmark(int bookmarkId, int resepId) {
        this.bookmarkId = bookmarkId;
        this.resepId = resepId;
    }

    public int getBookmarkId() {
        return bookmarkId;
    }

    public void setBookmarkId(int bookmarkId) {
        this.bookmarkId = bookmarkId;
    }

    public int getResepId() {
        return resepId;
    }

    public void setResepId(int resepId) {
        this.resepId = resepId;
    }
}
