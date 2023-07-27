package br.com.hellodev.controledeprodutos.model;

import android.telephony.mbms.StreamingServiceInfo;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

import br.com.hellodev.controledeprodutos.helper.FirebaseHelper;

public class Produto implements Serializable {


    private String id;
    private String nome;
    private int estoque;
    private double valor;
    private String urlImagem;

    public Produto() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference();
        this.setId(reference.push().getKey());
    }

    public void salvarProduto() {
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("produtos")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.id);
        reference.setValue(this);
    }

    public void deletaProduto(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("produtos")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.id);
        reference.removeValue();

        StorageReference storageReference = FirebaseHelper.getStorageReference()
                .child("Imagens")
                .child("produtos")
                .child(FirebaseHelper.getIdFirebase())
                .child(this.id + ".jpeg");
        storageReference.delete();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }
}
