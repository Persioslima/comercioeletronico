CREATE UNIQUE INDEX idx_etiquetas_nome_normalizado
ON tb_etiquetas (LOWER(TRIM(nome)));