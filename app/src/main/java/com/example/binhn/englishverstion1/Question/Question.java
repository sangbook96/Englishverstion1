package com.example.binhn.englishverstion1.Question;

/**
 * Created by binhn on 3/28/2017.
 */

public class Question  {
    private int id;
    private String cauhoi;
    private String A;
    private String B;
    private String C;
    private String D;
    private String Dung;
    boolean selectedA = false;
    boolean selectedB = false;
    boolean selectedC = false;
    boolean selectedD = false;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCauhoi() {
        return cauhoi;
    }

    public void setCauhoi(String cauhoi) {
        this.cauhoi = cauhoi;
    }

    public String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getDung() {
        return Dung;
    }

    public void setDung(String dung) {
        Dung = dung;
    }
    public boolean isSelectedA() {
        return selectedA;
    }
    public void setSelectedA(boolean selected) {
        this.selectedA = selected;
    }

    public boolean isSelectedB() {
        return selectedB;
    }

    public void setSelectedB(boolean selectedB) {
        this.selectedB = selectedB;
    }

    public boolean isSelectedC() {
        return selectedC;
    }

    public void setSelectedC(boolean selectedC) {
        this.selectedC = selectedC;
    }

    public boolean isSelectedD() {
        return selectedD;
    }

    public void setSelectedD(boolean selectedD) {
        this.selectedD = selectedD;
    }
}

