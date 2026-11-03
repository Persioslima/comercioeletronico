CREATE UNIQUE INDEX idx_marcas_nome_normalizado
ON tb_marcas (LOWER(TRIM(nome)));