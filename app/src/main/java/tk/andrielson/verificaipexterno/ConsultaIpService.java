package tk.andrielson.verificaipexterno;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

final class ConsultaIpService {
    static private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addNetworkInterceptor(new StethoInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .followRedirects(false)
            .build();

    @NotNull
    static String pesquisaIpv4() {
        String url = "https://ipv4.icanhazip.com/";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            } else {
                return "Nenhuma resposta foi retornada pelo servidor!";
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                return e.getMessage();
            }
        }
        return "Erro desconhecido!";
    }

    @NotNull
    static String pesquisaIpv6() {
        String url = "https://ipv6.icanhazip.com/";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return responseBody.string();
            } else {
                return "Nenhuma resposta foi retornada pelo servidor!";
            }
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
            String erro = e.getMessage();
            if (erro != null) {
                if (erro.contains("failed to connect to ipv6")) {
                    return "IPv6 não suportado!";
                }
                return e.getMessage();
            }
        }
        return "Erro desconhecido";
    }

    static boolean testaIpv4(String ip) {
        String[] tokens = ip.trim().split("\\.");
        if (tokens.length != 4) {
            return false;
        }
        for (String str : tokens) {
            int i = Integer.parseInt(str);
            if ((i < 0) || (i > 255)) {
                return false;
            }
        }
        return true;
    }
}
