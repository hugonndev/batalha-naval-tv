package com.antonela.batalhanaval;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Abre o jogo em tela cheia, com a tela sempre ligada.
 *
 * Atualização automática: ao abrir, mostra a última versão salva do jogo (ou a que veio no app)
 * e, em segundo plano, baixa a versão mais nova do GitHub. Se mudou, guarda e recarrega na hora.
 * Sem internet, segue com a versão salva.
 */
public class MainActivity extends Activity {

    private static final String UPDATE_URL =
            "https://raw.githubusercontent.com/hugonndev/batalha-naval-tv/main/app/src/main/assets/index.html";
    // Mesmo endereço base da versão embutida: o placar salvo (localStorage) continua o mesmo.
    private static final String BASE_URL = "file:///android_asset/index.html";
    private static final int MAX_BYTES = 2 * 1024 * 1024;

    private WebView web;
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        web = new WebView(this);
        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);              // guarda o placar se o app for fechado
        s.setMediaPlaybackRequiresUserGesture(false);
        web.setWebViewClient(new WebViewClient());  // links ficam dentro do app
        web.setBackgroundColor(0xFFFFF7F7);
        web.setFocusable(true);
        web.setFocusableInTouchMode(true);
        setContentView(web);

        current = loadSavedGame();
        show(current);
        web.requestFocus();
        hideBars();
        checkForUpdate();
    }

    private void show(String html) {
        web.loadDataWithBaseURL(BASE_URL, html, "text/html", "UTF-8", null);
    }

    private File cacheFile() {
        return new File(getFilesDir(), "jogo.html");
    }

    /** Última versão baixada; se não houver ou estiver estragada, a que veio dentro do app. */
    private String loadSavedGame() {
        try {
            File f = cacheFile();
            if (f.exists()) {
                String html = read(new java.io.FileInputStream(f));
                if (looksValid(html)) return html;
            }
        } catch (Exception ignored) {
        }
        try {
            return read(getAssets().open("index.html"));
        } catch (Exception e) {
            return "<p>Não foi possível abrir o jogo.</p>";
        }
    }

    private void checkForUpdate() {
        new Thread(() -> {
            try {
                HttpURLConnection c = (HttpURLConnection) new URL(UPDATE_URL).openConnection();
                c.setConnectTimeout(6000);
                c.setReadTimeout(10000);
                c.setUseCaches(false);
                if (c.getResponseCode() != 200) return;
                String html = read(c.getInputStream());
                c.disconnect();
                if (!looksValid(html) || html.equals(current)) return;

                try (FileOutputStream out = new FileOutputStream(cacheFile())) {
                    out.write(html.getBytes(StandardCharsets.UTF_8));
                }
                runOnUiThread(() -> {
                    current = html;
                    show(html);
                    Toast.makeText(this, "Jogo atualizado!", Toast.LENGTH_LONG).show();
                });
            } catch (Exception ignored) {
                // Sem internet ou GitHub fora do ar: segue com a versão que já está aberta.
            }
        }).start();
    }

    /** Confere se o arquivo baixado é mesmo o jogo, e não uma página de erro. */
    private static boolean looksValid(String html) {
        return html != null && html.length() > 10000
                && html.contains("Batalha Naval") && html.contains("const QS = [") && html.contains("</html>");
    }

    private static String read(InputStream in) throws java.io.IOException {
        try (InputStream is = in) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            byte[] chunk = new byte[16384];
            int n;
            while ((n = is.read(chunk)) != -1) {
                buf.write(chunk, 0, n);
                if (buf.size() > MAX_BYTES) throw new java.io.IOException("arquivo grande demais");
            }
            return new String(buf.toByteArray(), StandardCharsets.UTF_8);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) hideBars();
    }

    @SuppressWarnings("deprecation")
    private void hideBars() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
    }
}
