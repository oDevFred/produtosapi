-- Criação da tabela 'produto' para armazenar informações dos produtos do sistema
create table
  produto (
    -- ID: identificador único do produto (chave primária)
    -- varchar(255) permite IDs alfanuméricos ou UUIDs
    id varchar(255) not null primary key,
    -- Nome do produto (obrigatório)
    -- Limitado a 50 caracteres para nomes curtos e objetivos
    nome varchar(50) not null,
    -- Descrição detalhada do produto (opcional)
    -- Permite até 300 caracteres para informações complementares
    descricao varchar(300),
    -- Preço do produto em formato decimal
    -- numeric(18, 2) = até 18 dígitos totais, sendo 2 casas decimais
    -- Exemplo: 9999999999999999.99 (valor máximo)
    -- Permite armazenar preços com precisão (R$ 1.234.567,89)
    preco numeric(18, 2)
  );