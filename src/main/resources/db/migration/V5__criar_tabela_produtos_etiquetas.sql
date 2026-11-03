CREATE TABLE tb_produtos_etiquetas (
    produto_id BIGINT NOT NULL,
    etiqueta_id BIGINT NOT NULL,

    CONSTRAINT pk_produtos_etiquetas
        PRIMARY KEY (produto_id, etiqueta_id),

    CONSTRAINT fk_produtos_etiquetas_produto
        FOREIGN KEY (produto_id)
        REFERENCES tb_produtos(id),

    CONSTRAINT fk_produtos_etiquetas_etiqueta
        FOREIGN KEY (etiqueta_id)
        REFERENCES tb_etiquetas(id)
);