package br.com.portfolio.comercioeletronico.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_etiquetas")
public class EtiquetaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;

    public EtiquetaEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
