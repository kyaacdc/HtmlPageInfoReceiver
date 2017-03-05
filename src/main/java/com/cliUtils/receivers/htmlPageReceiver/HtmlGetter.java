package com.cliUtils.receivers.htmlPageReceiver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlGetter {

    public static String getContentByUrl(String url) throws IOException {

        URL urlForGet = new URL(url);
        URLConnection urlConnection = urlForGet.openConnection();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                urlConnection.getInputStream(), "UTF-8"));
        String inputLine;
        StringBuilder stringBuilder = new StringBuilder();
        while ((inputLine = bufferedReader.readLine()) != null)
            stringBuilder.append(inputLine);

        return stringBuilder.toString();
    }
}
