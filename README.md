#Documentação do Sistema

Visão Geral
O sistema desenvolvido é uma aplicação de gerenciamento de livros para uma livraria ou biblioteca. Ele permite que os usuários cadastrem livros físicos e eBooks, listem livros cadastrados, atualizem informações sobre os livros, excluam livros, e realizem o empréstimo de livros. A interface gráfica (UI) é construída utilizando JOptionPane para a interação do usuário, enquanto a lógica de persistência é gerenciada pela camada de acesso a dados (DAO).
Funcionalidades do Sistema
Cadastro de Livro Físico:


Permite o cadastro de livros físicos, onde o usuário informa o título, autor, preço e peso do livro. O livro é marcado como disponível no momento do cadastro.
Cadastro de eBook:


Permite o cadastro de eBooks, onde o usuário informa o título, autor, preço e tamanho do arquivo. O eBook também é marcado como disponível no momento do cadastro.
Listagem de Livros:


O sistema oferece a opção de listar todos os livros cadastrados, ou apenas os livros disponíveis ou indisponíveis (aqueles emprestados).
Empréstimo de Livros:


Os usuários podem pegar um livro emprestado, o que altera o status de "disponível" para "indisponível", sinalizando que o livro não pode ser emprestado novamente até ser devolvido.
Atualização de Dados de Livros:


Permite atualizar os dados de um livro existente, como título, autor e preço.
Exclusão de Livros:


Permite excluir um livro do banco de dados, removendo-o completamente.
Interações e Fluxo
Interface do Usuário (UI): A interface gráfica é gerida pela classe View, que apresenta os dados para o usuário e coleta as entradas necessárias (como o título e autor de um livro, o ID de um livro para deleção, etc.).
Controlador: A classe Controller serve como intermediária entre a View e a camada de acesso a dados (DAO). O controlador recebe as entradas do usuário a partir da View, executa a lógica apropriada e chama os métodos correspondentes do OperationsDAO.
Acesso ao Banco de Dados (DAO): A camada OperationsDAO é responsável pelas operações de banco de dados, como inserir, atualizar, listar e deletar registros de livros. Ela usa JDBC para interagir com o banco de dados.
Tecnologias Utilizadas
Java: A linguagem principal para o desenvolvimento do sistema.
JDBC: Para conexão e manipulação de dados no banco de dados.
JOptionPane: Para interação gráfica com o usuário (inputs e mensagens).

Documentação do Banco de Dados
O banco de dados utilizado para armazenar as informações dos livros e operações relacionadas tem a seguinte estrutura:
Tabelas do Banco de Dados
Tabela livros


Armazena informações gerais sobre os livros, incluindo seu título, autor, preço, tipo (físico ou eBook), e disponibilidade (se está disponível ou emprestado).
Estrutura:

 CREATE TABLE livros (
    id SERIAL PRIMARY KEY,  -- Identificador único do livro
    titulo VARCHAR(255) NOT NULL,  -- Título do livro
    autor VARCHAR(255) NOT NULL,  -- Autor do livro
    preco DECIMAL(10, 2) NOT NULL,  -- Preço do livro
    tipo VARCHAR(50) NOT NULL,  -- Tipo do livro (LivroFisico ou Ebook)
    disponivel BOOLEAN NOT NULL  -- Indica se o livro está disponível (true) ou emprestado (false)
);


Tabela livros_fisicos


Armazena detalhes específicos dos livros físicos, como o peso.
Estrutura:

 CREATE TABLE livros_fisicos (
    livro_id INT REFERENCES livros(id) ON DELETE CASCADE,  -- Referência ao livro na tabela livros
    peso DECIMAL(10, 2) NOT NULL  -- Peso do livro físico
);


Tabela ebooks


Armazena detalhes específicos dos eBooks, como o tamanho do arquivo.
Estrutura:

 CREATE TABLE ebooks (
    livro_id INT REFERENCES livros(id) ON DELETE CASCADE,  -- Referência ao livro na tabela livros
    tamanho_arquivo DECIMAL(10, 2) NOT NULL  -- Tamanho do arquivo do eBook em MB
);


Fluxo de Operações no Banco de Dados
Inserção de Livro:


Quando um novo livro (físico ou eBook) é cadastrado, os dados são armazenados na tabela livros. Para livros físicos, os detalhes adicionais, como peso, são armazenados na tabela livros_fisicos. Para eBooks, os detalhes, como tamanho do arquivo, são armazenados na tabela ebooks.
Atualização de Livro:


A atualização de um livro altera os campos na tabela livros, como título, autor e preço, sem modificar a tabela livros_fisicos ou ebooks, a menos que esses campos também sejam alterados.
Exclusão de Livro:


Ao deletar um livro, ele é removido da tabela livros. Dependendo do tipo (físico ou eBook), a entrada correspondente na tabela livros_fisicos ou ebooks também é excluída. A exclusão na tabela livros é configurada com a opção ON DELETE CASCADE, o que garante que as referências associadas também sejam removidas automaticamente.
Empréstimo de Livro:


O status de um livro é alterado de "disponível" para "indisponível" quando ele é emprestado. O campo disponivel na tabela livros é atualizado para false para indicar que o livro não pode ser emprestado novamente até ser devolvido.
Listagem de Livros:


Os livros podem ser listados de acordo com seu status de disponibilidade. A consulta pode ser filtrada para retornar apenas livros disponíveis ou apenas livros emprestados.

Exemplo de Operações SQL
Cadastrar Livro Físico:


Inserir um livro físico no banco de dados:
INSERT INTO livros (titulo, autor, preco, tipo, disponivel) 
VALUES ('Livro Exemplo', 'Autor Exemplo', 25.50, 'LivroFisico', TRUE) 
RETURNING id;

INSERT INTO livros_fisicos (livro_id, peso) 
VALUES (1, 1.5);  -- Supondo que o ID retornado do primeiro INSERT seja 1


Cadastrar eBook:


Inserir um eBook no banco de dados:
INSERT INTO livros (titulo, autor, preco, tipo, disponivel) 
VALUES ('eBook Exemplo', 'Autor eBook', 15.99, 'Ebook', TRUE) 
RETURNING id;

INSERT INTO ebooks (livro_id, tamanho_arquivo) 
VALUES (2, 5.0);  -- Supondo que o ID retornado do primeiro INSERT seja 2


Listar Livros Disponíveis:


Selecionar livros disponíveis:
SELECT * FROM livros WHERE disponivel = TRUE;


Empréstimo de Livro:


Atualizar a disponibilidade de um livro:
UPDATE livros SET disponivel = FALSE WHERE id = 1;


Exclusão de Livro:


Deletar um livro e suas referências:
DELETE FROM livros WHERE id = 1;



Conclusão
Este sistema é uma aplicação de gerenciamento de livros simples que inclui funcionalidades de cadastro, listagem, atualização, exclusão e empréstimo. O banco de dados é estruturado de forma relacional com tabelas que armazenam informações de livros físicos e eBooks, além de permitir a manipulação do status de disponibilidade dos livros. A interação do usuário ocorre através de uma interface gráfica simples e os dados são manipulados diretamente no banco de dados utilizando SQL.


