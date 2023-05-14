package com.example.mysensor;

public class Astre {

    private String nom;
    private int width;
    private int height;
    private int image;
    private int positonIntialX;
    private int positonIntialY;
    private int habite;

    public Astre(String nom, int width, int height, int image, int positonIntialX, int positonIntialY, int habite) {
        this.nom = nom;
        this.width = width;
        this.height = height;
        this.image = image;
        this.positonIntialX = positonIntialX;
        this.positonIntialY = positonIntialY;
        this.habite = habite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPositonIntialX() {
        return positonIntialX;
    }

    public void setPositonIntialX(int positonIntialX) {
        this.positonIntialX = positonIntialX;
    }

    public int getPositonIntialY() {
        return positonIntialY;
    }

    public void setPositonIntialY(int positonIntialY) {
        this.positonIntialY = positonIntialY;
    }

    public int getHabite() {
        return habite;
    }

    public void setHabite(int habite) {
        this.habite = habite;
    }
}
