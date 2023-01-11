package com.cyecize.redirector;

import com.cyecize.javache.embedded.JavacheEmbedded;
import com.cyecize.solet.BaseHttpSolet;
import com.cyecize.solet.HttpSoletRequest;
import com.cyecize.solet.HttpSoletResponse;
import com.cyecize.solet.WebSolet;

import java.util.stream.Collectors;

@WebSolet("/*")
public class MainSolet extends BaseHttpSolet {


    public static void main(String[] args) {
        JavacheEmbedded.startServer(MainSolet.class);
    }

    @Override
    protected void doGet(HttpSoletRequest request, HttpSoletResponse response) throws Exception {
        final String fullPath = String.format("https://%s", request.getRequestURI());
        final String queryParams = request.getQueryParameters().entrySet().stream()
                .map(kvp -> String.format("%s=%s", kvp.getKey(), kvp.getValue()))
                .collect(Collectors.joining("&"));

        final String finalPath = String.format("%s%s",
                fullPath,
                queryParams.trim().isEmpty() ? "" : "?" + queryParams
        );

        response.sendRedirect(finalPath);
    }
}
