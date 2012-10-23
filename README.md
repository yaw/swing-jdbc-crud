swing-jdbc-crud
===============

Aplicativo desktop demonstração, útil para estudantes e profissionais que estam começando a estudar/utilizar a linguagem Java. Esse é um sistema simples, que implementa um cadastro de produtos, desenvolvido na arquitetura cliente servidor. O sistema representa a camada cliente enquanto o banco de dados o servidor.

O foco desse projeto é o estudo da linguagem e tecnologias que compõe o core do Java. Além disso utilizamos o HSQLDB (HyperSQL DataBase), um banco de dados relacional escrito em Java, adequado para projetos com propósitos de estudos.

Detalhes da implementação
-------
Tecnologias utilizadas na implementação:
* Swing: utilizamos o framework Swing para construção das interfaces e componentes gráficos da aplicação;
* JDBC: a API JDBC (Java Database Connectivity) para integração e execução de comandos no banco de dados;
* Collection: reunimos uma relação de objeto em memória via coleções do Java;
* Thread: as ações (eventos) dos componentes da tela com o banco de dados são tratados em outra thread (SwingUtilities), de forma que o usuário tenha uma melhor experiencia em usabilidade.

Para facilitar o uso de bibliotecas externas e a construção, o projeto utiliza o Maven.

Pré-requisitos
-------
* JDK -  Kit de desenvolvimento Java;
* Maven;
* IDE de sua preferencia, recomendamos Eclipse ou NetBeans;

