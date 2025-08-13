-- 1) criar database
CREATE DATABASE IF NOT EXISTS sistemafarmcia CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE sistemafarmcia;

-- 2) tabelas
CREATE TABLE IF NOT EXISTS clientes (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  cpf VARCHAR(14),
  telefone VARCHAR(20),
  email VARCHAR(120)
);

CREATE TABLE IF NOT EXISTS medicamentos (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(120) NOT NULL,
  quantidade INT NOT NULL,
  validade DATE,
  preco DECIMAL(10,2) NOT NULL,
  INDEX idx_validade (validade)
);

CREATE TABLE IF NOT EXISTS vendas (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  data_hora DATETIME NOT NULL,
  cliente_id BIGINT,
  valor_total DECIMAL(12,2) NOT NULL,
  finalizada BOOLEAN NOT NULL,
  CONSTRAINT fk_venda_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

CREATE TABLE IF NOT EXISTS itens_venda (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  venda_id BIGINT NOT NULL,
  medicamento_id BIGINT NOT NULL,
  quantidade INT NOT NULL,
  preco_unitario DECIMAL(10,2) NOT NULL,
  CONSTRAINT fk_item_venda FOREIGN KEY (venda_id) REFERENCES vendas(id),
  CONSTRAINT fk_item_medicamento FOREIGN KEY (medicamento_id) REFERENCES medicamentos(id)
);

-- 3) dados de exemplo (opcional)
INSERT INTO clientes (nome, cpf, telefone, email)
VALUES ('Cliente Demo', '000.000.000-00', '(11) 90000-0000', 'demo@cliente.com');

INSERT INTO medicamentos (nome, quantidade, validade, preco)
VALUES ('Dipirona 500mg', 100, DATE_ADD(CURDATE(), INTERVAL 90 DAY), 9.90),
       ('Paracetamol 750mg', 50,  DATE_ADD(CURDATE(), INTERVAL 30 DAY), 14.50),
       ('Ibuprofeno 400mg', 30,  DATE_ADD(CURDATE(), INTERVAL 10 DAY), 22.00);
