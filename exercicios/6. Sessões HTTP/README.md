# Exercício 6 — Gerenciamento de Contatos com Sessões HTTP

Projeto Spring Boot que amplia o gerenciamento de contatos do exercício anterior com login, logout e favoritos mantidos na sessão HTTP. A interface usa Thymeleaf e Bootstrap; usuários e contatos ficam no MySQL.

## Requisitos

- JDK 17 ou superior
- Maven 3.6.3 ou superior
- MySQL Server e MySQL CLI

## 1. Criar e popular o banco

Na raiz deste projeto, execute:

```bash
mysql -u root -p < dados.sql
```

O script cria o banco `contatos_sessao`, as tabelas `usuarios` e `contatos`, um usuário de teste e três contatos. Para conferir:

```bash
mysql -u root -p
```

```sql
USE contatos_sessao;
SHOW TABLES;
SELECT * FROM usuarios;
SELECT * FROM contatos;
```

## 2. Configurar a conexão MySQL

A configuração está em `src/main/resources/application.properties`. Por padrão, a aplicação tenta conectar com:

- Banco: `contatos_sessao`
- Usuário: `root`
- Senha: vazia (altere se o seu MySQL exigir senha)

Ajuste os valores no arquivo para sua instalação ou configure as variáveis de ambiente:

```bash
export DB_USERNAME=root
export DB_PASSWORD='sua_senha_mysql'
```

Opcionalmente, também pode configurar `DB_URL`.

Antes de iniciar a aplicação, valide as mesmas credenciais no MySQL:

```bash
mysql -u root -p
```

Se o comando solicitar senha, a aplicação também precisa recebê-la. A forma recomendada é:

```bash
DB_USERNAME=root DB_PASSWORD='sua_senha_mysql' mvn spring-boot:run
```

Não use `sudo mvn` e execute o comando dentro de `ContatosSessao_KauaMartins`, que é a pasta onde está o `pom.xml`.

## 3. Iniciar a aplicação

Na pasta que contém o `pom.xml`:

```bash
mvn spring-boot:run
```

Acesse `http://localhost:8080/login`.

Credenciais iniciais:

- E-mail: `usuario@exemplo.com`
- Senha: `123456`

Para encerrar, pressione `Ctrl+C` no terminal.

## Funcionalidades e rotas

| Método | Rota | Comportamento |
|---|---|---|
| GET | `/login` | Exibe o formulário de login. |
| POST | `/login` | Autentica e guarda `usuarioLogado` e `favoritos` na sessão. |
| GET | `/logout` | Invalida a sessão e volta ao login. |
| GET | `/contatos` | Lista todos os contatos e indica os favoritos. Exige login. |
| GET | `/contatos/novo` | Exibe o formulário de criação. Exige login. |
| POST | `/contatos` | Cria um contato persistido no MySQL. Exige login. |
| GET | `/contatos/{id}/editar` | Exibe o formulário de edição. Exige login. |
| POST | `/contatos/{id}` | Atualiza um contato persistido no MySQL. Exige login. |
| POST | `/contatos/{id}/excluir` | Remove um contato e seu favorito da sessão. Exige login. |
| POST | `/contatos/{id}/favoritar` | Alterna o contato entre favorito e não favorito. Exige login. |
| GET | `/favoritos` | Lista somente os contatos favoritados na sessão. Exige login. |

## Organização do código

```text
src/main/java/br/com/kauamartins/contatossessao/
├── ContatosSessaoApplication.java
├── model/
│   ├── Contact.java
│   └── Usuario.java
├── repository/
│   ├── ContactRepository.java
│   └── UsuarioRepository.java
├── service/
│   ├── ContactService.java
│   └── UsuarioService.java
└── controller/
    ├── ContactController.java
    └── LoginController.java

src/main/resources/
├── application.properties
└── templates/
    ├── login.html
    ├── contatos.html
    ├── contato-form.html
    └── favoritos.html

dados.sql
```

## Como as sessões funcionam

Depois do login válido, o controller cria uma `HttpSession` e grava dois atributos:

- `usuarioLogado`: objeto `Usuario` autenticado.
- `favoritos`: `List<Long>` com os IDs dos contatos favoritos.

Toda rota do `ContactController` verifica se existe `usuarioLogado`. Sem autenticação, redireciona para `/login`. A lista de favoritos é criada se ainda não existir. Ao favoritar/desfavoritar, o ID é incluído/removido da lista na sessão; não há tabela de favoritos no banco.

O `ContactService.listarFavoritos(List<Long> ids)` busca no banco somente os contatos informados e preserva a ordem dos IDs da sessão.

Ao sair, `invalidate()` invalida a sessão. Ao fechar a sessão ou fazer logout, os favoritos deixam de existir. Os contatos e usuários permanecem no MySQL.

## Relação com o enunciado

- `Usuario`: entidade JPA com `id`, `nome`, `email` e `senha`.
- `UsuarioRepository`: estende `JpaRepository` e define `findByEmailAndSenha(String email, String senha)`.
- `UsuarioService.autenticar(email, senha)`: retorna o usuário ou `null`.
- `ContactController`: usa `@Controller` e as rotas de páginas solicitadas.
- Templates: `login.html`, `contatos.html`, `contato-form.html` e `favoritos.html`, com Bootstrap.

## Observação de segurança

O enunciado determina senha em texto puro somente para fins didáticos. Essa implementação segue essa orientação para reproduzir a atividade. Em aplicações reais, nunca armazene senhas em texto puro; use hash apropriado (por exemplo, BCrypt) e proteções adicionais como Spring Security e CSRF.
