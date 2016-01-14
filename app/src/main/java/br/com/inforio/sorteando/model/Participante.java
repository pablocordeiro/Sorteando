package br.com.inforio.sorteando.model;

import java.io.Serializable;

public class Participante  implements Serializable {
    private int id;
    private String nome;
    private Boolean marcado;

    public Participante (){
        this.id = 0;
        this.nome = "";
        this.marcado = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getMarcado() {
        return marcado;
    }

    public void setMarcado(Boolean marcado) {
        this.marcado = marcado;
    }

    @Override
    public String toString() {
        return nome;
    }
}
