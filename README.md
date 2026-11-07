# Batalha Naval da Antonela (app para Android TV)

Jogo em grupos para o chá de bebê: tabuleiro 12x12 com barcos escondidos.
Achou um pedaço de barco, abre uma pergunta (4 alternativas ou mito ou verdade).
Acertou, ganha o pedaço e joga de novo; errou, o pedaço volta a se esconder e passa a vez.

## Como funciona o app

- `app/src/main/assets/index.html`: o jogo inteiro (HTML, CSS e JavaScript num arquivo só).
- `MainActivity.java`: abre o jogo numa WebView em tela cheia, com a tela sempre ligada.
- Controle remoto: setas movem o cursor, OK confirma.
- O placar fica salvo no aparelho; se o app fechar, a partida continua de onde parou.
- Internet só é usada para baixar as fontes; sem internet o jogo usa as fontes do sistema.

## Compilação

Cada envio para `main` roda o GitHub Actions (`.github/workflows/build.yml`), que gera
`batalha-naval.apk` (versão de depuração, assinada com a chave de debug) e publica numa Release.

Link fixo para a última versão:
`https://github.com/hugonndev/batalha-naval-tv/releases/latest/download/batalha-naval.apk`

## Instalar na TV Android

1. Na TV, instale o app **Downloader** (da AFTVnews) pela Play Store.
2. Em Configurações da TV, permita que o Downloader instale apps de fontes desconhecidas
   (o caminho varia por marca; costuma ficar em Apps > Segurança e restrições > Fontes desconhecidas).
3. Abra o Downloader, digite o link acima e confirme a instalação.
4. O app aparece na tela inicial da TV como **Batalha Naval**.

## Limitações conhecidas

- O .apk é de depuração (não vai para a Play Store); serve para instalar manualmente.
- Testado no navegador com teclado (setas e Enter); ainda não testado numa TV real.
