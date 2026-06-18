# PostViewer

Aplicativo Android que consome a API pública [JSONPlaceholder](https://jsonplaceholder.typicode.com/)
para listar *posts* e seus comentários, permitindo ainda que o usuário adicione
comentários locais persistidos no dispositivo.

## Identificação

- **Aluno:** Rhuan Andrey de Andrade Boni
- **Prontuário:** sc3043983
- **Disciplina:** Programação para Dispositivos Móveis
- **Instituição:** Instituto Federal de São Paulo (IFSP)
- **Professor:** Pedro Northon Nobile

## Descrição e requisitos

O app cumpre os seguintes requisitos funcionais:

1. **Tela de lista de posts** (tela inicial): carrega os posts de
   `GET /posts` e exibe o título de cada um. Tocar em um item navega para os
   detalhes daquele post.
2. **Tela de detalhes do post**: exibe os comentários do post, carregados de
   `GET /posts/{id}/comments`.
3. **Comentário local**: a tela de detalhes possui um campo de texto e um botão
   para adicionar um comentário. Ele é salvo no banco Room (associado ao `id` do
   post), aparece **imediatamente** na lista (junto aos comentários da API) e
   **persiste** entre sessões do app.

Todas as telas tratam os estados de **carregamento** (indicador de progresso) e
**erro** (mensagem com botão "Tentar novamente").

## Tecnologias e bibliotecas

| Recurso | Tecnologia | Versão |
|---|---|---|
| Linguagem | Kotlin | 2.2.10 |
| Interface | Jetpack Compose (Material 3) | BOM 2026.02.01 |
| Navegação | Navigation Compose | 2.9.8 |
| Consumo de API REST | Retrofit + converter-gson | 2.11.0 |
| Persistência local | Room (processado via KSP) | 2.8.0 |
| Gerenciamento de estado | ViewModel + StateFlow | lifecycle 2.10.0 |
| Assíncrono | Kotlin Coroutines / Flow | — |

- **Min SDK:** 26 (Android 8.0 Oreo) · **Target/Compile SDK:** 36 · **AGP:** 9.2.1

## Como executar localmente

**Pré-requisitos:** Android Studio recente (Hedgehog ou superior), JDK 11+,
Android SDK com a API 36 instalada, e um emulador ou dispositivo com **API 26+**
e **acesso à internet**.

1. Clone o repositório:
   ```bash
   git clone https://github.com/Rhuan-aa/PostViewer.git
   ```
2. Abra a pasta do projeto no Android Studio e aguarde o **Gradle Sync**.
3. Selecione um emulador/dispositivo e clique em **Run** (`Shift+F10`).

Alternativamente, pela linha de comando:
```bash
./gradlew assembleDebug      # gera o APK de debug
./gradlew installDebug       # instala em um dispositivo/emulador conectado
```

## Arquitetura e decisões de design

O projeto segue o padrão **MVVM** com separação clara de responsabilidades:

```
data/
 ├─ remote/      → API Retrofit (JsonPlaceholderApi, RetrofitInstance) e modelos
 ├─ local/       → Room (entidade, DAO e banco)
 └─ remote/repository/ → repositórios que abstraem as fontes de dados
ui/
 ├─ posts/       → tela de lista (ViewModel, UiState, Screen)
 ├─ detail/      → tela de detalhes (ViewModel, UiState, Screen, CommentUi)
 ├─ navigation/  → grafo de navegação (rotas)
 └─ theme/       → tema Material 3
```

- **Estado unidirecional com `StateFlow` + `sealed interface`.** Cada tela expõe
  um `UiState` com os casos `Loading`, `Success` e `Error`. Como a interface é
  *sealed*, o `when` na UI é obrigado a tratar todos os estados — carregamento e
  erro nunca ficam esquecidos.
- **Padrão Repository.** Os repositórios isolam *de onde* os dados vêm (API e/ou
  banco), de modo que os ViewModels não conhecem detalhes de Retrofit ou Room.
- **Retrofit + Gson.** Escolhido pela simplicidade e por ser o padrão de mercado
  para REST em Android. Como os nomes dos campos das *data classes* coincidem com
  as chaves do JSON, não foi necessária nenhuma anotação de mapeamento.
- **Room com KSP (em vez de kapt).** O KSP é o processador de anotações
  recomendado pelo Google: lê o Kotlin diretamente e é significativamente mais
  rápido que o kapt na compilação. É ele quem gera a implementação do DAO.
- **Comentários locais reativos com `Flow`.** O DAO retorna um `Flow`, então ao
  inserir um comentário a tela se atualiza sozinha. Um modelo de UI unificado
  (`CommentUi`) permite exibir, numa única lista, comentários da API e locais,
  marcando estes últimos com um selo "local".
- **`AndroidViewModel` na tela de detalhes.** Usado para obter o banco a partir do
  contexto da aplicação sem precisar de uma *factory* manual.
- **Tema próprio com `dynamicColor` desativado.** Uma paleta Material 3
  (índigo/teal/âmbar) garante uma identidade visual consistente em qualquer
  dispositivo, em vez de depender das cores do papel de parede do sistema.

### Observação de build

O projeto usa o **Kotlin embutido do AGP 9**, que desabilita o DSL
`kotlin.sourceSets` usado pelo KSP para registrar o código gerado. Por isso, o
arquivo `gradle.properties` define `android.disallowKotlinSourceSets=false`,
conforme recomendado pela documentação de ferramentas do Android.

## Capturas de tela

| Lista de posts | Comentários (API) | Comentário local |
|---|---|---|
| ![Lista de posts](docs/01-lista-posts.png) | ![Comentários da API](docs/02-comentarios-api.png) | ![Comentário local](docs/03-comentario-local.png) |
