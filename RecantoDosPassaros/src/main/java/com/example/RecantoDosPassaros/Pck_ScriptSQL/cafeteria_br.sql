---
-- 1. ESTRUTURA DE TABELAS
---

DROP TABLE IF EXISTS item;
DROP TABLE IF EXISTS estoque;
DROP TABLE IF EXISTS produto;
DROP TABLE IF EXISTS venda;

CREATE TABLE venda (
                       cod_venda INT PRIMARY KEY AUTO_INCREMENT,
                       valortotal DECIMAL(10,2) NOT NULL DEFAULT 0,
                       data_venda DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       status VARCHAR(20) NOT NULL DEFAULT 'PAGO'
);

CREATE TABLE produto (
                         id          INT PRIMARY KEY AUTO_INCREMENT,
                         nome        VARCHAR(50) NOT NULL,
                         preco       DECIMAL(10,2) NOT NULL,
                         categoria   VARCHAR(50) NOT NULL
);

CREATE TABLE estoque (
                         cod_estoque     INT PRIMARY KEY AUTO_INCREMENT,
                         cod_produto     INT NOT NULL,
                         quantidade      INT NOT NULL,
                         quantidade_min  INT NOT NULL,
                         FOREIGN KEY (cod_produto) REFERENCES produto (id)
);

CREATE TABLE item (
                      cod_item        INT PRIMARY KEY AUTO_INCREMENT,
                      cod_venda       INT NOT NULL,
                      cod_produto     INT NOT NULL,
                      nome            VARCHAR(50) NOT NULL,
                      preco           DECIMAL(10,2) NOT NULL,
                      quantidade      INT NOT NULL,
                      FOREIGN KEY (cod_venda) REFERENCES venda (cod_venda),
                      FOREIGN KEY (cod_produto) REFERENCES produto (id)
);

---
-- 2. FUNÇÕES E PROCEDIMENTOS DE LÓGICA
---

DELIMITER //
CREATE FUNCTION soma_subtotal(p_cod_produto INT, p_quantidade INT)
    RETURNS DECIMAL(10,2)
    DETERMINISTIC
BEGIN
    DECLARE f_preco DECIMAL(10,2);
    SELECT preco INTO f_preco FROM produto WHERE id = p_cod_produto;
    RETURN f_preco * p_quantidade;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE abrir_venda(OUT p_cod_venda INT)
BEGIN
    INSERT INTO venda(valortotal, data_venda, status) VALUES (0, NOW(), 'PAGO');
    SET p_cod_venda = LAST_INSERT_ID();
END//
DELIMITER ;

DELIMITER //
CREATE PROCEDURE add_item_venda(
  IN p_id_venda      INT,
    IN p_id_produto    INT,
    IN p_quantidade    INT
)
BEGIN
    DECLARE d_preco DECIMAL(10,2);
    DECLARE d_nome VARCHAR(50);
    DECLARE d_subtotal DECIMAL(10,2);

SELECT nome, preco INTO d_nome, d_preco
FROM produto
WHERE id = p_id_produto;

SET d_subtotal = soma_subtotal(p_id_produto, p_quantidade);

INSERT INTO item(cod_venda, cod_produto, nome, preco, quantidade)
VALUES (p_id_venda, p_id_produto, d_nome, d_preco, p_quantidade);

UPDATE venda
SET valortotal = valortotal + d_subtotal
WHERE cod_venda = p_id_venda;
END //
DELIMITER ;

---
-- 3. AUTOMATIZAÇÃO (TRIGGERS)
---

DELIMITER //
CREATE TRIGGER baixa_estoque
    AFTER INSERT ON item
    FOR EACH ROW
BEGIN
    UPDATE estoque
    SET quantidade = quantidade - NEW.quantidade
    WHERE cod_produto = NEW.cod_produto;
END //
DELIMITER ;

---
-- 4. PROCEDIMENTOS DE CONSULTA E CRUD
---

DELIMITER //
CREATE PROCEDURE exibir_venda(IN p_cod_venda INT)
BEGIN
    SELECT cod_venda AS CODIGO,
           valortotal AS VALOR_TOTAL,
           DATE_FORMAT(data_venda, '%d/%m/%Y %H:%i') AS DATA_VENDA,
           status AS STATUS
    FROM venda
    WHERE cod_venda = p_cod_venda OR p_cod_venda IS NULL;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE exibir_produto_por_id(IN p_cod INT)
BEGIN
    SELECT p.id AS CODIGO,
           p.nome AS PRODUTO,
           p.preco AS PRECO,
           p.categoria AS CATEGORIA,
           e.quantidade AS QUANTIDADE,
           e.quantidade_min AS QUANTIDADE_MIN
    FROM produto p
         LEFT JOIN estoque e ON p.id = e.cod_produto
    WHERE p.id = p_cod;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE exibir_produto()
BEGIN
    SELECT p.id AS CODIGO,
           p.nome AS PRODUTO,
           p.preco AS PRECO,
           p.categoria AS CATEGORIA,
           e.quantidade AS QUANTIDADE,
           e.quantidade_min AS QUANTIDADE_MIN
    FROM produto p
         LEFT JOIN estoque e ON p.id = e.cod_produto;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE exibir_estoque()
BEGIN
    SELECT p.id AS CODIGO,
           p.nome AS PRODUTO,
           p.categoria AS CATEGORIA,
           e.quantidade AS QUANTIDADE_ATUAL,
           e.quantidade_min AS QUANTIDADE_MINIMA,
           (e.quantidade * p.preco) AS VALOR_ESTOQUE
    FROM estoque e
         INNER JOIN produto p ON e.cod_produto = p.id;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE ins_produto(
    IN p_nome VARCHAR(50),
    IN p_preco DECIMAL(10,2),
    IN p_categoria VARCHAR(50),
    IN p_quantidade_inicial INT,
    IN p_quantidade_min INT
)
BEGIN
    DECLARE v_id_produto INT;

    INSERT INTO produto(nome, preco, categoria)
    VALUES(p_nome, p_preco, p_categoria);

    SET v_id_produto = LAST_INSERT_ID();

    INSERT INTO estoque(cod_produto, quantidade, quantidade_min)
    VALUES(v_id_produto, p_quantidade_inicial, p_quantidade_min);
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE upt_quantidade(
    IN p_cod INT,
    IN p_quantidade INT
)
BEGIN
    UPDATE estoque
    SET quantidade = p_quantidade
    WHERE cod_produto = p_cod;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE upt_preco(
    IN p_cod INT,
    IN p_preco DECIMAL(10,2)
)
BEGIN
    UPDATE produto
    SET preco = p_preco
    WHERE id = p_cod;
END //
DELIMITER ;

DELIMITER //
CREATE PROCEDURE del_produto(IN p_cod INT)
BEGIN
    DELETE FROM item WHERE cod_produto = p_cod;
    DELETE FROM estoque WHERE cod_produto = p_cod;
    DELETE FROM produto WHERE id = p_cod;
END //
DELIMITER ;