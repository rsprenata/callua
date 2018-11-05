/**********************
* CRIAÇÃO DAS TABELAS *
***********************/

CREATE TABLE Estado (
        id SERIAL,
        uf CHAR(2),
        
        PRIMARY KEY (id)
);
                                                                                                   
CREATE TABLE Cidade (
        id SERIAL,
        nome VARCHAR(100),
        idEstado INTEGER,
        
        PRIMARY KEY (id),
        FOREIGN KEY (idEstado) REFERENCES Estado (id)
);

CREATE TABLE Cliente (
	id SERIAL,
	nome VARCHAR(128),
	cpfCnpj VARCHAR(14),
	telefoneCelular VARCHAR(11),
	email VARCHAR(128),
	senha VARCHAR(32),
	endereco VARCHAR(128),
	cep VARCHAR(8),
	idCidade INT,
	
	PRIMARY KEY (id),
	UNIQUE (cpfCnpj), 
	FOREIGN KEY (idCidade) REFERENCES Cidade (id)
);

CREATE TABLE Usuario (
	id SERIAL,
	nome VARCHAR(128),
	cpfCnpj VARCHAR(14),
	telefoneCelular VARCHAR(11),
	email VARCHAR(128),
	senha VARCHAR(32),
	administrador BOOLEAN,

	PRIMARY KEY (id),
	UNIQUE (cpfCnpj)
);

CREATE TABLE Chamado (
	id SERIAL,
	titulo VARCHAR(128),
	descricao VARCHAR(1024),
	endereco VARCHAR(128),
	cep VARCHAR(8),
	idCidade INT,

	PRIMARY KEY (id)
);

ALTER TABLE Chamado ADD COLUMN files_path VARCHAR(128);

CREATE TYPE StatusChamado AS ENUM ('ABERTO', 'RESOLVIDO');
ALTER TABLE Chamado ADD COLUMN status StatusChamado;
UPDATE Chamado SET status = 'ABERTO';

ALTER TABLE Chamado ADD COLUMN idCliente INT NOT NULL;

ALTER TABLE Chamado ADD FOREIGN KEY (idCliente) REFERENCES Cliente(id);

ALTER TABLE Chamado ADD COLUMN idTecnico INT;


ALTER TABLE Chamado ADD FOREIGN KEY (idTecnico) REFERENCES Usuario(id);

CREATE TABLE TecnicoCriarSenhaToken (
	idTecnico INT,
	token VARCHAR(128),

	FOREIGN KEY (idTecnico) REFERENCES Usuario (id)
);

/*********************
* INSERÇÃO DOS DADOS *
**********************/

INSERT INTO Estado (uf) VALUES ('AC');  
INSERT INTO Estado (uf) VALUES ('AL');  
INSERT INTO Estado (uf) VALUES ('AM');
INSERT INTO Estado (uf) VALUES ('AP');
INSERT INTO Estado (uf) VALUES ('BA');
INSERT INTO Estado (uf) VALUES ('CE');
INSERT INTO Estado (uf) VALUES ('DF');
INSERT INTO Estado (uf) VALUES ('ES');
INSERT INTO Estado (uf) VALUES ('GO');
INSERT INTO Estado (uf) VALUES ('MA');
INSERT INTO Estado (uf) VALUES ('MG');
INSERT INTO Estado (uf) VALUES ('MS');
INSERT INTO Estado (uf) VALUES ('MT');
INSERT INTO Estado (uf) VALUES ('PA');
INSERT INTO Estado (uf) VALUES ('PB');
INSERT INTO Estado (uf) VALUES ('PE');
INSERT INTO Estado (uf) VALUES ('PI');
INSERT INTO Estado (uf) VALUES ('PR');
INSERT INTO Estado (uf) VALUES ('RJ');
INSERT INTO Estado (uf) VALUES ('RN');
INSERT INTO Estado (uf) VALUES ('RO');
INSERT INTO Estado (uf) VALUES ('RR');
INSERT INTO Estado (uf) VALUES ('RS');
INSERT INTO Estado (uf) VALUES ('SC');
INSERT INTO Estado (uf) VALUES ('SE');
INSERT INTO Estado (uf) VALUES ('SP');
INSERT INTO Estado (uf) VALUES ('TO');

INSERT INTO Cidade (nome, idEstado) values ('Acrelândia', 1);
INSERT INTO Cidade (nome, idEstado) values ('Assis Brasil', 1);
INSERT INTO Cidade (nome, idEstado) values ('Brasiléia', 1);
INSERT INTO Cidade (nome, idEstado) values ('Bujari', 1);
INSERT INTO Cidade (nome, idEstado) values ('Capixaba', 1);
INSERT INTO Cidade (nome, idEstado) values ('Cruzeiro do Sul', 1);
INSERT INTO Cidade (nome, idEstado) values ('Epitaciolândia', 1);
INSERT INTO Cidade (nome, idEstado) values ('Feijó', 1);
INSERT INTO Cidade (nome, idEstado) values ('Jordão', 1);
INSERT INTO Cidade (nome, idEstado) values ('Mâncio Lima', 1);

INSERT INTO Cidade (nome, idEstado) values ('Água Branca', 2);
INSERT INTO Cidade (nome, idEstado) values ('Anadia', 2);
INSERT INTO Cidade (nome, idEstado) values ('Arapiraca', 2);
INSERT INTO Cidade (nome, idEstado) values ('Atalaia', 2);
INSERT INTO Cidade (nome, idEstado) values ('Barra de Santo Antônio', 2);
INSERT INTO Cidade (nome, idEstado) values ('Barra de São Miguel', 2);
INSERT INTO Cidade (nome, idEstado) values ('Batalha', 2);
INSERT INTO Cidade (nome, idEstado) values ('Belém', 2);
INSERT INTO Cidade (nome, idEstado) values ('Belo Monte', 2);
INSERT INTO Cidade (nome, idEstado) values ('Boca da Mata', 2);

INSERT INTO Cidade (nome, idEstado) values ('Alvarães', 3);
INSERT INTO Cidade (nome, idEstado) values ('Amaturá', 3);
INSERT INTO Cidade (nome, idEstado) values ('Anamã', 3);
INSERT INTO Cidade (nome, idEstado) values ('Anori', 3);
INSERT INTO Cidade (nome, idEstado) values ('Apuí', 3);
INSERT INTO Cidade (nome, idEstado) values ('Atalaia do Norte', 3);
INSERT INTO Cidade (nome, idEstado) values ('Autazes', 3);
INSERT INTO Cidade (nome, idEstado) values ('Barcelos', 3);
INSERT INTO Cidade (nome, idEstado) values ('Barreirinha', 3);
INSERT INTO Cidade (nome, idEstado) values ('Benjamin Constant', 3);

INSERT INTO Cidade (nome, idEstado) values ('Serra do Navio', 4);
INSERT INTO Cidade (nome, idEstado) values ('Amapá', 4);
INSERT INTO Cidade (nome, idEstado) values ('Pedra Branca do Amapari', 4);
INSERT INTO Cidade (nome, idEstado) values ('Calçoene', 4);
INSERT INTO Cidade (nome, idEstado) values ('Cutias', 4);
INSERT INTO Cidade (nome, idEstado) values ('Ferreira Gomes', 4);
INSERT INTO Cidade (nome, idEstado) values ('Itaubal', 4);
INSERT INTO Cidade (nome, idEstado) values ('Laranjal do Jari', 4);
INSERT INTO Cidade (nome, idEstado) values ('Macapá', 4);
INSERT INTO Cidade (nome, idEstado) values ('Mazagão', 4);

INSERT INTO Cidade (nome, idEstado) values ('Abaíra', 5);
INSERT INTO Cidade (nome, idEstado) values ('Abaré', 5);
INSERT INTO Cidade (nome, idEstado) values ('Acajutiba', 5);
INSERT INTO Cidade (nome, idEstado) values ('Adustina', 5);
INSERT INTO Cidade (nome, idEstado) values ('Água Fria', 5);
INSERT INTO Cidade (nome, idEstado) values ('Érico Cardoso', 5);
INSERT INTO Cidade (nome, idEstado) values ('Aiquara', 5);
INSERT INTO Cidade (nome, idEstado) values ('Alagoinhas', 5);
INSERT INTO Cidade (nome, idEstado) values ('Alcobaça', 5);
INSERT INTO Cidade (nome, idEstado) values ('Almadina', 5);

INSERT INTO Cidade (nome, idEstado) values ('Abaiara', 6);
INSERT INTO Cidade (nome, idEstado) values ('Acarape', 6);
INSERT INTO Cidade (nome, idEstado) values ('Acaraú', 6);
INSERT INTO Cidade (nome, idEstado) values ('Acopiara', 6);
INSERT INTO Cidade (nome, idEstado) values ('Aiuaba', 6);
INSERT INTO Cidade (nome, idEstado) values ('Alcântaras', 6);
INSERT INTO Cidade (nome, idEstado) values ('Altaneira', 6);
INSERT INTO Cidade (nome, idEstado) values ('Alto Santo', 6);
INSERT INTO Cidade (nome, idEstado) values ('Amontada', 6);
INSERT INTO Cidade (nome, idEstado) values ('Antonina do Norte', 6);

INSERT INTO Cidade (nome, idEstado) values ('Brasília', 7);

INSERT INTO Cidade (nome, idEstado) values ('Afonso Cláudio', 8);
INSERT INTO Cidade (nome, idEstado) values ('Águia Branca', 8);
INSERT INTO Cidade (nome, idEstado) values ('Água Doce do Norte', 8);
INSERT INTO Cidade (nome, idEstado) values ('Alegre', 8);
INSERT INTO Cidade (nome, idEstado) values ('Alfredo Chaves', 8);
INSERT INTO Cidade (nome, idEstado) values ('Alto Rio Novo', 8);
INSERT INTO Cidade (nome, idEstado) values ('Anchieta', 8);
INSERT INTO Cidade (nome, idEstado) values ('Apiacá', 8);
INSERT INTO Cidade (nome, idEstado) values ('Aracruz', 8);
INSERT INTO Cidade (nome, idEstado) values ('Atilio Vivacqua', 8);

INSERT INTO Cidade (nome, idEstado) values ('Abadia de Goiás', 9);
INSERT INTO Cidade (nome, idEstado) values ('Abadiânia', 9);
INSERT INTO Cidade (nome, idEstado) values ('Acreúna', 9);
INSERT INTO Cidade (nome, idEstado) values ('Adelândia', 9);
INSERT INTO Cidade (nome, idEstado) values ('Água Fria de Goiás', 9);
INSERT INTO Cidade (nome, idEstado) values ('Água Limpa', 9);
INSERT INTO Cidade (nome, idEstado) values ('Águas Lindas de Goiás', 9);
INSERT INTO Cidade (nome, idEstado) values ('Alexânia', 9);
INSERT INTO Cidade (nome, idEstado) values ('Aloândia', 9);
INSERT INTO Cidade (nome, idEstado) values ('Alto Horizonte', 9);

INSERT INTO Cidade (nome, idEstado) values ('Açailândia', 10);
INSERT INTO Cidade (nome, idEstado) values ('Afonso Cunha', 10);
INSERT INTO Cidade (nome, idEstado) values ('Água Doce do Maranhão', 10);
INSERT INTO Cidade (nome, idEstado) values ('Alcântara', 10);
INSERT INTO Cidade (nome, idEstado) values ('Aldeias Altas', 10);
INSERT INTO Cidade (nome, idEstado) values ('Altamira do Maranhão', 10);
INSERT INTO Cidade (nome, idEstado) values ('Alto Alegre do Maranhão', 10);
INSERT INTO Cidade (nome, idEstado) values ('Alto Alegre do Pindaré', 10);
INSERT INTO Cidade (nome, idEstado) values ('Alto Parnaíba', 10);
INSERT INTO Cidade (nome, idEstado) values ('Amapá do Maranhão', 10);

INSERT INTO Cidade (nome, idEstado) values ('Abadia dos Dourados', 11);
INSERT INTO Cidade (nome, idEstado) values ('Abaeté', 11);
INSERT INTO Cidade (nome, idEstado) values ('Abre Campo', 11);
INSERT INTO Cidade (nome, idEstado) values ('Acaiaca', 11);
INSERT INTO Cidade (nome, idEstado) values ('Açucena', 11);
INSERT INTO Cidade (nome, idEstado) values ('Água Boa', 11);
INSERT INTO Cidade (nome, idEstado) values ('Água Comprida', 11);
INSERT INTO Cidade (nome, idEstado) values ('Aguanil', 11);
INSERT INTO Cidade (nome, idEstado) values ('Águas Formosas', 11);
INSERT INTO Cidade (nome, idEstado) values ('Águas Vermelhas', 11);

INSERT INTO Cidade (nome, idEstado) values ('Água Clara', 12);
INSERT INTO Cidade (nome, idEstado) values ('Alcinópolis', 12);
INSERT INTO Cidade (nome, idEstado) values ('Amambai', 12);
INSERT INTO Cidade (nome, idEstado) values ('Anastácio', 12);
INSERT INTO Cidade (nome, idEstado) values ('Anaurilândia', 12);
INSERT INTO Cidade (nome, idEstado) values ('Angélica', 12);
INSERT INTO Cidade (nome, idEstado) values ('Antônio João', 12);
INSERT INTO Cidade (nome, idEstado) values ('Aparecida do Taboado', 12);
INSERT INTO Cidade (nome, idEstado) values ('Aquidauana', 12);
INSERT INTO Cidade (nome, idEstado) values ('Aral Moreira', 12);

INSERT INTO Cidade (nome, idEstado) values ('Acorizal', 13);
INSERT INTO Cidade (nome, idEstado) values ('Água Boa', 13);
INSERT INTO Cidade (nome, idEstado) values ('Alta Floresta', 13);
INSERT INTO Cidade (nome, idEstado) values ('Alto Araguaia', 13);
INSERT INTO Cidade (nome, idEstado) values ('Alto Boa Vista', 13);
INSERT INTO Cidade (nome, idEstado) values ('Alto Garças', 13);
INSERT INTO Cidade (nome, idEstado) values ('Alto Paraguai', 13);
INSERT INTO Cidade (nome, idEstado) values ('Alto Taquari', 13);
INSERT INTO Cidade (nome, idEstado) values ('Apiacás', 13);
INSERT INTO Cidade (nome, idEstado) values ('Araguaiana', 13);

INSERT INTO Cidade (nome, idEstado) values ('Abaetetuba', 14);
INSERT INTO Cidade (nome, idEstado) values ('Abel Figueiredo', 14);
INSERT INTO Cidade (nome, idEstado) values ('Acará', 14);
INSERT INTO Cidade (nome, idEstado) values ('Afuá', 14);
INSERT INTO Cidade (nome, idEstado) values ('Água Azul do Norte', 14);
INSERT INTO Cidade (nome, idEstado) values ('Alenquer', 14);
INSERT INTO Cidade (nome, idEstado) values ('Almeirim', 14);
INSERT INTO Cidade (nome, idEstado) values ('Altamira', 14);
INSERT INTO Cidade (nome, idEstado) values ('Anajás', 14);
INSERT INTO Cidade (nome, idEstado) values ('Ananindeua', 14);

INSERT INTO Cidade (nome, idEstado) values ('Água Branca', 15);
INSERT INTO Cidade (nome, idEstado) values ('Aguiar', 15);
INSERT INTO Cidade (nome, idEstado) values ('Alagoa Grande', 15);
INSERT INTO Cidade (nome, idEstado) values ('Alagoa Nova', 15);
INSERT INTO Cidade (nome, idEstado) values ('Alagoinha', 15);
INSERT INTO Cidade (nome, idEstado) values ('Alcantil', 15);
INSERT INTO Cidade (nome, idEstado) values ('Algodão de Jandaíra', 15);
INSERT INTO Cidade (nome, idEstado) values ('Alhandra', 15);
INSERT INTO Cidade (nome, idEstado) values ('São João do Rio do Peixe', 15);
INSERT INTO Cidade (nome, idEstado) values ('Amparo', 15);

INSERT INTO Cidade (nome, idEstado) values ('Abreu e Lima', 16);
INSERT INTO Cidade (nome, idEstado) values ('Afogados da Ingazeira', 16);
INSERT INTO Cidade (nome, idEstado) values ('Afrânio', 16);
INSERT INTO Cidade (nome, idEstado) values ('Agrestina', 16);
INSERT INTO Cidade (nome, idEstado) values ('Água Preta', 16);
INSERT INTO Cidade (nome, idEstado) values ('Águas Belas', 16);
INSERT INTO Cidade (nome, idEstado) values ('Alagoinha', 16);
INSERT INTO Cidade (nome, idEstado) values ('Aliança', 16);
INSERT INTO Cidade (nome, idEstado) values ('Altinho', 16);
INSERT INTO Cidade (nome, idEstado) values ('Amaraji', 16);

INSERT INTO Cidade (nome, idEstado) values ('Acauã', 17);
INSERT INTO Cidade (nome, idEstado) values ('Agricolândia', 17);
INSERT INTO Cidade (nome, idEstado) values ('Água Branca', 17);
INSERT INTO Cidade (nome, idEstado) values ('Alagoinha do Piauí', 17);
INSERT INTO Cidade (nome, idEstado) values ('Alegrete do Piauí', 17);
INSERT INTO Cidade (nome, idEstado) values ('Alto Longá', 17);
INSERT INTO Cidade (nome, idEstado) values ('Altos', 17);
INSERT INTO Cidade (nome, idEstado) values ('Alvorada do Gurguéia', 17);
INSERT INTO Cidade (nome, idEstado) values ('Amarante', 17);
INSERT INTO Cidade (nome, idEstado) values ('Angical do Piauí', 17);

INSERT INTO Cidade (nome, idEstado) values ('Abatiá', 18);
INSERT INTO Cidade (nome, idEstado) values ('Adrianópolis', 18);
INSERT INTO Cidade (nome, idEstado) values ('Agudos do Sul', 18);
INSERT INTO Cidade (nome, idEstado) values ('Almirante Tamandaré', 18);
INSERT INTO Cidade (nome, idEstado) values ('Altamira do Paraná', 18);
INSERT INTO Cidade (nome, idEstado) values ('Altônia', 18);
INSERT INTO Cidade (nome, idEstado) values ('Alto Paraná', 18);
INSERT INTO Cidade (nome, idEstado) values ('Alto Piquiri', 18);
INSERT INTO Cidade (nome, idEstado) values ('Alvorada do Sul', 18);
INSERT INTO Cidade (nome, idEstado) values ('Amaporã', 18);

INSERT INTO Cidade (nome, idEstado) values ('Angra dos Reis', 19);
INSERT INTO Cidade (nome, idEstado) values ('Aperibé', 19);
INSERT INTO Cidade (nome, idEstado) values ('Araruama', 19);
INSERT INTO Cidade (nome, idEstado) values ('Areal', 19);
INSERT INTO Cidade (nome, idEstado) values ('Armação dos Búzios', 19);
INSERT INTO Cidade (nome, idEstado) values ('Arraial do Cabo', 19);
INSERT INTO Cidade (nome, idEstado) values ('Barra do Piraí', 19);
INSERT INTO Cidade (nome, idEstado) values ('Barra Mansa', 19);
INSERT INTO Cidade (nome, idEstado) values ('Belford Roxo', 19);
INSERT INTO Cidade (nome, idEstado) values ('Bom Jardim', 19);

INSERT INTO Cidade (nome, idEstado) values ('Acari', 20);
INSERT INTO Cidade (nome, idEstado) values ('Açu', 20);
INSERT INTO Cidade (nome, idEstado) values ('Afonso Bezerra', 20);
INSERT INTO Cidade (nome, idEstado) values ('Água Nova', 20);
INSERT INTO Cidade (nome, idEstado) values ('Alexandria', 20);
INSERT INTO Cidade (nome, idEstado) values ('Almino Afonso', 20);
INSERT INTO Cidade (nome, idEstado) values ('Alto do Rodrigues', 20);
INSERT INTO Cidade (nome, idEstado) values ('Angicos', 20);
INSERT INTO Cidade (nome, idEstado) values ('Antônio Martins', 20);
INSERT INTO Cidade (nome, idEstado) values ('Apodi', 20);

INSERT INTO Cidade (nome, idEstado) values ('Alta Floresta D''Oeste', 21);
INSERT INTO Cidade (nome, idEstado) values ('Ariquemes', 21);
INSERT INTO Cidade (nome, idEstado) values ('Cabixi', 21);
INSERT INTO Cidade (nome, idEstado) values ('Cacoal', 21);
INSERT INTO Cidade (nome, idEstado) values ('Cerejeiras', 21);
INSERT INTO Cidade (nome, idEstado) values ('Colorado do Oeste', 21);
INSERT INTO Cidade (nome, idEstado) values ('Corumbiara', 21);
INSERT INTO Cidade (nome, idEstado) values ('Costa Marques', 21);
INSERT INTO Cidade (nome, idEstado) values ('Espigão D''Oeste', 21);
INSERT INTO Cidade (nome, idEstado) values ('Guajará-Mirim', 21);

INSERT INTO Cidade (nome, idEstado) values ('Amajari', 22);
INSERT INTO Cidade (nome, idEstado) values ('Alto Alegre', 22);
INSERT INTO Cidade (nome, idEstado) values ('Boa Vista', 22);
INSERT INTO Cidade (nome, idEstado) values ('Bonfim', 22);
INSERT INTO Cidade (nome, idEstado) values ('Cantá', 22);
INSERT INTO Cidade (nome, idEstado) values ('Caracaraí', 22);
INSERT INTO Cidade (nome, idEstado) values ('Caroebe', 22);
INSERT INTO Cidade (nome, idEstado) values ('Iracema', 22);
INSERT INTO Cidade (nome, idEstado) values ('Mucajaí', 22);
INSERT INTO Cidade (nome, idEstado) values ('Normandia', 22);

INSERT INTO Cidade (nome, idEstado) values ('Aceguá', 23);
INSERT INTO Cidade (nome, idEstado) values ('Água Santa', 23);
INSERT INTO Cidade (nome, idEstado) values ('Agudo', 23);
INSERT INTO Cidade (nome, idEstado) values ('Ajuricaba', 23);
INSERT INTO Cidade (nome, idEstado) values ('Alecrim', 23);
INSERT INTO Cidade (nome, idEstado) values ('Alegrete', 23);
INSERT INTO Cidade (nome, idEstado) values ('Alegria', 23);
INSERT INTO Cidade (nome, idEstado) values ('Almirante Tamandaré do Sul', 23);
INSERT INTO Cidade (nome, idEstado) values ('Alpestre', 23);
INSERT INTO Cidade (nome, idEstado) values ('Alto Alegre', 23);

INSERT INTO Cidade (nome, idEstado) values ('Abdon Batista', 24);
INSERT INTO Cidade (nome, idEstado) values ('Abelardo Luz', 24);
INSERT INTO Cidade (nome, idEstado) values ('Agrolândia', 24);
INSERT INTO Cidade (nome, idEstado) values ('Agronômica', 24);
INSERT INTO Cidade (nome, idEstado) values ('Água Doce', 24);
INSERT INTO Cidade (nome, idEstado) values ('Águas de Chapecó', 24);
INSERT INTO Cidade (nome, idEstado) values ('Águas Frias', 24);
INSERT INTO Cidade (nome, idEstado) values ('Águas Mornas', 24);
INSERT INTO Cidade (nome, idEstado) values ('Alfredo Wagner', 24);
INSERT INTO Cidade (nome, idEstado) values ('Alto Bela Vista', 24);

INSERT INTO Cidade (nome, idEstado) values ('Amparo de São Francisco', 25);
INSERT INTO Cidade (nome, idEstado) values ('Aquidabã', 25);
INSERT INTO Cidade (nome, idEstado) values ('Aracaju', 25);
INSERT INTO Cidade (nome, idEstado) values ('Arauá', 25);
INSERT INTO Cidade (nome, idEstado) values ('Areia Branca', 25);
INSERT INTO Cidade (nome, idEstado) values ('Barra dos Coqueiros', 25);
INSERT INTO Cidade (nome, idEstado) values ('Boquim', 25);
INSERT INTO Cidade (nome, idEstado) values ('Brejo Grande', 25);
INSERT INTO Cidade (nome, idEstado) values ('Campo do Brito', 25);
INSERT INTO Cidade (nome, idEstado) values ('Canhoba', 25);

INSERT INTO Cidade (nome, idEstado) values ('Adamantina', 26);
INSERT INTO Cidade (nome, idEstado) values ('Adolfo', 26);
INSERT INTO Cidade (nome, idEstado) values ('Aguaí', 26);
INSERT INTO Cidade (nome, idEstado) values ('Águas da Prata', 26);
INSERT INTO Cidade (nome, idEstado) values ('Águas de Lindóia', 26);
INSERT INTO Cidade (nome, idEstado) values ('Águas de Santa Bárbara', 26);
INSERT INTO Cidade (nome, idEstado) values ('Águas de São Pedro', 26);
INSERT INTO Cidade (nome, idEstado) values ('Agudos', 26);
INSERT INTO Cidade (nome, idEstado) values ('Alambari', 26);
INSERT INTO Cidade (nome, idEstado) values ('Alfredo Marcondes', 26);

INSERT INTO Cidade (nome, idEstado) values ('Abreulândia', 27);
INSERT INTO Cidade (nome, idEstado) values ('Aguiarnópolis', 27);
INSERT INTO Cidade (nome, idEstado) values ('Aliança do Tocantins', 27);
INSERT INTO Cidade (nome, idEstado) values ('Almas', 27);
INSERT INTO Cidade (nome, idEstado) values ('Alvorada', 27);
INSERT INTO Cidade (nome, idEstado) values ('Ananás', 27);
INSERT INTO Cidade (nome, idEstado) values ('Angico', 27);
INSERT INTO Cidade (nome, idEstado) values ('Aparecida do Rio Negro', 27);
INSERT INTO Cidade (nome, idEstado) values ('Aragominas', 27);
INSERT INTO Cidade (nome, idEstado) values ('Araguacema', 27);