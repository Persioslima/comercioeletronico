CREATE UNIQUE INDEX idx_categorias_nome_normalizado
ON tb_categorias (LOWER(TRIM(nome)));