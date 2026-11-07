# Batalha Naval da Antonela

Jogo em grupos para o chá de bebê: tabuleiro 12x12 com barcos escondidos.
Achou um pedaço de barco, abre uma pergunta (4 alternativas ou mito ou verdade).
Acertou, ganha o pedaço e joga de novo; errou, o pedaço volta a se esconder e passa a vez.

Roda em **TV Android, celular Android e Chromebook** (app `.apk`) e em **qualquer notebook**
(arquivo `.html`, abre no navegador).

## Como funciona

- `app/src/main/assets/index.html`: o jogo inteiro (HTML, CSS e JavaScript num arquivo só).
  É o mesmo arquivo usado no app e na versão para notebook.
- `MainActivity.java`: abre o jogo numa WebView em tela cheia, com a tela sempre ligada.
- TV: setas movem o cursor, OK confirma. Celular: toque na tela, em pé ou deitado.
  Notebook: mouse, ou setas e Enter.
- O placar fica salvo no aparelho; se o app fechar, a partida continua de onde parou.
- Internet só é usada para baixar as fontes; sem internet o jogo usa as fontes do sistema.

## Atualização automática do jogo (a partir da versão 1.2 do app)

Ao abrir, o app mostra a última versão salva do jogo e, em segundo plano, baixa o
`app/src/main/assets/index.html` mais novo deste repositório (branch `main`).
Se mudou, guarda no aparelho, recarrega e mostra "Jogo atualizado!". Sem internet, segue com a versão salva.
Perguntas, regras e visual atualizam sozinhos; só mudanças na parte Android (Java/manifest) exigem instalar um .apk novo.

## Compilação

Cada envio para `main` roda o GitHub Actions (`.github/workflows/build.yml`), que gera
`batalha-naval.apk` (versão de depuração) e `batalha-naval.html` e publica os dois numa Release.

Links fixos para a última versão:
- App: `https://github.com/hugonndev/batalha-naval-tv/releases/latest/download/batalha-naval.apk`
- Notebook: `https://github.com/hugonndev/batalha-naval-tv/releases/latest/download/batalha-naval.html`

## Instalar

**TV Android**
1. Instale o app **Downloader** (da AFTVnews) pela Play Store da TV.
2. Permita que o Downloader instale apps de fontes desconhecidas
   (costuma ficar em Configurações > Apps > Segurança e restrições > Fontes desconhecidas).
3. Abra o Downloader, digite o link do app e confirme a instalação.

**Celular Android**
1. Abra o link do app no navegador do celular e baixe o arquivo.
2. Toque no arquivo baixado e permita a instalação quando o celular pedir.

**Notebook (Windows, Mac ou Linux)**
1. Baixe o arquivo `batalha-naval.html` pelo link acima.
2. Dê dois cliques: ele abre no navegador. Use F11 ou o botão "Tela cheia".

## Limitações conhecidas

- O .apk é de depuração (não vai para a Play Store); serve para instalar manualmente.
- Cada compilação usa uma chave de assinatura nova. Para atualizar, desinstale a versão anterior
  antes de instalar a nova (o placar salvo é apagado junto).
- iPhone não instala .apk; nele use a versão do navegador.
- Testado em navegador simulando TV, celular e notebook; ainda não testado em aparelhos reais.
