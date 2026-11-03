package br.com.portfolio.comercioeletronico.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_produtos")
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaEntity categoria;

    @ManyToOne
    @JoinColumn(name = "marca_id")
    private MarcaEntity marca;

    @ManyToMany
    @JoinTable(
            name = "tb_produtos_etiquetas",
            joinColumns = @JoinColumn(name = "produto_id"),
            inverseJoinColumns = @JoinColumn(name = "etiqueta_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"produto_id", "etiqueta_id"}
            )
    )
    private List<EtiquetaEntity> etiquetas = new ArrayList<>();

    public ProdutoEntity() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public MarcaEntity getMarca() {
        return marca;
    }

    public List<EtiquetaEntity> getEtiquetas() { return etiquetas;}

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }

    public void setMarca(MarcaEntity marca) {
        this.marca = marca;
    }

    public void setEtiquetas(List<EtiquetaEntity> etiquetas) { this.etiquetas = etiquetas; }
}


