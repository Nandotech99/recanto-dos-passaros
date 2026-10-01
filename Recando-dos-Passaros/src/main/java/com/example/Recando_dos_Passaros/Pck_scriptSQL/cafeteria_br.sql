---
-- 1. ESTRUTURA DE TABELAS
---

CREATE TABLE venda (
                       cod_venda   INT PRIMARY KEY AUTO_INCREMENT,
                       valortotal  DECIMAL(10,2) NOT NULL DEFAULT 0
);

CREATE TABLE produto (
                         cod             INT PRIMARY KEY,
                         nome            VARCHAR(50) NOT NULL,
                         quantidade      INT NOT NULL,
                         quantidade_min  INT NOT NULL,
                         preco           DECIMAL(10,2) NOT NULL,
                         data            DATE NOT NULL,
                         registro        VARCHAR(100)
);

CREATE TABLE item (
                      cod_item        INT PRIMARY KEY AUTO_INCREMENT,
                      cod_venda       INT NOT NULL,
                      cod_produto     INT NOT NULL,
                      nome            VARCHAR(50) NOT NULL,
                      preco           DECIMAL(10,2) NOT NULL,
                      quantidade      INT NOT NULL,
                      FOREIGN KEY (cod_venda) REFERENCES venda (cod_venda),
                      FOREIGN KEY (cod_produto) REFERENCES produto (cod)
);

---
-- 2. FUNÇÕES E PROCEDIMENTOS DE LÓGICA
---

DELIMITER //
CREATE FUNCTION soma_subtotal(p_cod_produto INT, p_quantidade INT)
    RETURNS DECIMAL(10,2)
    DETERMINISTIC
BEGIN
    DECLARE f_preco DECIMAL(5,2);
SELECT preco INTO f_preco FROM produto WHERE cod = p_cod_produto;
RETURN f_preco * p_quantidade;
END //

DELIMITER //
CREATE PROCEDURE abrir_venda(OUT p_cod_venda INT)
BEGIN
INSERT INTO venda(valortotal) VALUES (0);
SET p_cod_venda = LAST_INSERT_ID();
END //

DELIMITER //
CREATE PROCEDURE add_item_venda(
    IN p_cod_venda 		INT,
    IN p_cod_produto 	INT,
    IN p_quantidade 	INT
)
BEGIN
    DECLARE d_preco     DECIMAL(5,2);
    DECLARE d_nome      VARCHAR(50);
    DECLARE d_subtotal  DECIMAL(10,2);

SELECT nome, preco INTO d_nome, d_preco FROM produto WHERE cod = p_cod_produto;

SET d_subtotal = soma_subtotal(p_cod_produto, p_quantidade);

INSERT INTO item(cod_venda, cod_produto, nome, preco, quantidade)
VALUES (p_cod_venda, p_cod_produto, d_nome, d_preco, p_quantidade);

UPDATE venda SET valortotal = valortotal + d_subtotal WHERE cod_venda = p_cod_venda;
END //

---
-- 3. AUTOMATIZAÇÃO (TRIGGERS)
---

DELIMITER //
CREATE TRIGGER baixa_estoque
    AFTER INSERT ON item
    FOR EACH ROW
BEGIN
    UPDATE produto
    SET quantidade = quantidade - NEW.quantidade
    WHERE cod = NEW.cod_produto;
END //
DELIMITER ;

---
-- 4. PROCEDIMENTOS DE CONSULTA (CRUD)
---

DELIMITER //
CREATE PROCEDURE exibir_venda(IN p_cod_venda INT)
BEGIN
SELECT cod_venda AS CODIGO, valortotal AS VALOR_TOTAL
FROM venda
WHERE cod_venda = p_cod_venda OR p_cod_venda IS NULL;
END //

DELIMITER //
CREATE PROCEDURE exibir_item(IN p_cod_venda INT)
BEGIN
SELECT i.cod_item 		AS ITEM,
       i.cod_produto 	AS COD_PROD,
       p.nome 			AS PRODUTO,
       p.preco 			AS PRECO,
       i.quantidade 	AS QUANTIDADE
FROM item i
         INNER JOIN produto p ON i.cod_produto = p.cod
WHERE i.cod_venda = p_cod_venda;
END //

DELIMITER //
CREATE PROCEDURE exibir_produto()
BEGIN
SELECT cod AS CODIGO,
       nome AS PRODUTO,
       quantidade AS QUANTIDADE,
       quantidade_min AS QTD_MINIMA,
       preco AS PRECO,
       DATE_FORMAT(data, '%d/%m/%Y') AS DATA,
       registro AS DESCRICAO
FROM produto;
END //

DELIMITER //
CREATE PROCEDURE ins_produto(
    IN p_cod INT,
    IN p_nome VARCHAR(50),
    IN p_quantidade INT,
    IN p_quantidade_min INT,
    IN p_preco DECIMAL(5,2),
    IN p_data VARCHAR(10),
    IN p_registro VARCHAR(100)
)
BEGIN
INSERT INTO produto(cod, nome, quantidade, quantidade_min, preco, data, registro)
VALUES(p_cod, p_nome, p_quantidade, p_quantidade_min, p_preco, STR_TO_DATE(p_data, '%d/%m/%Y'), p_registro);
END //

DELIMITER //
CREATE PROCEDURE upt_quantidade(
    IN p_cod INT, IN p_quantidade INT,
    IN p_data VARCHAR(20), IN p_registro VARCHAR(100)
)
BEGIN
UPDATE produto
SET quantidade = p_quantidade,
    data = STR_TO_DATE(p_data, '%d/%m/%Y'),
    registro = p_registro
WHERE cod = p_cod;
END //

DELIMITER //
CREATE PROCEDURE upt_preco(
    IN p_cod 		INT,
    IN p_preco		DECIMAL(10,2),
    IN p_data		VARCHAR(20),
    IN p_registro	VARCHAR(100)
)
BEGIN
UPDATE produto
set preco = p_preco,
    data = str_to_date(p_data,'%d/%m/%Y'),
    registro = p_registro
WHERE cod = p_cod;
END //

DELIMITER //
CREATE PROCEDURE del_produto(IN p_cod INT)
BEGIN
DELETE FROM item WHERE cod_produto = p_cod;
DELETE FROM produto WHERE cod = p_cod;
END //

-- 5. SEGURANÇA E TESTES

DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS venda;

SELECT *
FROM produto;

SELECT *
FROM item;

SELECT *
FROM venda;