package com.example.binhn.englishverstion1.Note;

/**
 * Created by ST on 27/03/2017.
 */

public class Note {
    public int id;
    String Tile,Note;

    public Note() {
    }

    public Note(int id, String tile, String note) {
        this.id = id;
        Tile = tile;
        Note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTile() {
        return Tile;
    }

    public void setTile(String tile) {
        Tile = tile;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
