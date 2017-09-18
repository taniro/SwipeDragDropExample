package br.ufrn.eaj.tads.recycleviewexample;

/**
 * Created by Taniro on 17/09/2017.
 */


public class Fruta {

    String nome;
    int img;
    Boolean bitten;

    public Fruta (String nome, int img){
        this.nome = nome;
        this.img = img;
        this.bitten=false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Boolean getBitten() {
        return bitten;
    }

    public void setBitten(Boolean bitten) {
        this.bitten = bitten;
    }
}