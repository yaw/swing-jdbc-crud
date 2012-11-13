swing-jdbc-crud
===============

Aplicativo desktop, desenvolvido utilizando tecnologias que compõe o core da linguagem Java: o framework Swing e a API JDBC.

A aplicação utiliza o HSQLDB (HyperSQL DataBase), um banco de dados relacional escrito em Java, adequado para projetos com propósitos de estudos.

O objetivo dessa aplicação é servir como conteúdo complementar para o estudo da linguagem Java e suas APIS, além de colocar em prática a modelagem de componentes através da Orientação de Objetos.


Detalhes da implementação
-------
Tecnologias utilizadas na implementação:
* Swing: utilizamos o framework Swing para construção das interfaces e componentes gráficos da aplicação (camada cliente);
* JDBC: a API JDBC (Java Database Connectivity) para integração e execução de comandos no banco de dados (camada servidor);
* Collection: reunimos uma relação de objeto em memória via coleções do Java;
* Thread: as ações (eventos) dos componentes da tela com o banco de dados são tratados em outra thread (SwingUtilities), de forma que o usuário tenha uma melhor experiencia em usabilidade.

Para facilitar o uso de bibliotecas externas e a construção, o projeto utiliza o Maven.

Pré-requisitos
-------
* JDK - última versão do Kit de desenvolvimento Java;
* Maven;
* IDE de sua preferencia (recomendamos Eclipse ou NetBeans);

Saiba mais
-------
Visite a página do projeto:
http://www.yaw.com.br/open/projetos/swing-jdbc-crud/
