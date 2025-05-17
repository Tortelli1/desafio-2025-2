# Documentação Locadora Unoesc

Començando com a inicialização do projeto, realize a clonagem do repositório. Na IDE, será automaticamente instalado as dependências. Para a tela de login, acesse com:
* Usuário ('admin')
* Senha ('unoesc@2025')

## Navegando

Na parte superior das telas, há o menu superior, contendo os menus que possibilitam:
* Navegar através das telas de Cadastro e Consulta;
* Botão da tela inicial
* Botão de Logout do sistema

## Cadastro

### Filmes

1. No campo **Buscar filme**, insira um filme de sua escolha. O sistema fará a busca em uma base de dados, retornando o filme desejado;
2. Selecione se ele está ativo **Sim** ou **Não** e depois, salve o registro;
3. Caso o registro seja salvo com sucesso, será retornado uma mensagem de indicação;

### Exemplares

1. Selecione o filme a qual deseja vincular um exemplar. 
2. Selecione se ele está ativo **Sim** ou **Não** e depois, salve o registro;
3. Caso o registro seja salvo com sucesso, será retornado uma mensagem de indicação;

### Locações

1. Preencha os respectivos campos: *Nome, CPF, E-mail, Telefone, Data a ser devolvido e Quantidade de filmes (Sendo a seleção, limitada a 3 por locação)*;
2. Salve o registro.
3. A geração do QRCode será registrada com os dados, mas será exibida apenas na consulta pública;

## Consulta

Nas telas de Consulta, é possível encontrar os registros em tabela, com suas respectivas informações. Em especial, há a tela de **Ver locações**. Aqui, poderá ser feita a busca pelo CPF da pessoa, que será retornado:
* Nome
* E-mail
* Telefone
* Data de Locação:
* Data de devolução prevista:
* Filmes alocados
* QR Code da locação
