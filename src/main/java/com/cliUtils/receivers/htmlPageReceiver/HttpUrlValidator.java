package com.cliUtils.receivers.htmlPageReceiver;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import static com.cliUtils.receivers.htmlPageReceiver.HtmlGetter.getContentByUrl;

public class HttpUrlValidator {

    public static boolean isHttpUrlValid(String url){

        boolean isValid = (url.length() > 7) && (url.substring(0, 7).equalsIgnoreCase(("HTTP://")));

        if(!isValid)
            System.out.println("URL does not valid. Please input valid URL, that should begin like http://...... ");

        return isValid && (isHttpUrlExist(url));
    }

    private static boolean isHttpUrlExist(String url) {
        try {
            try {
                String contentByUrl = getContentByUrl(url);

                try {
                    if (!isHtmlDocType(contentByUrl))
                        throw new TypeNotPresentException("This URL " + url + " does not have HTML content. Please input another URL", new Throwable());
                } catch (TypeNotPresentException et){
                    System.out.println(et.typeName());
                    return false;
                }
                return true;
            } catch (IllegalArgumentException | UnknownHostException | SocketException e) {
                System.out.println("This URL " + url + " does not exist. Please input an existing URL. Please input another URL");
                return false;
            }
        } catch (IOException eio) {
            eio.printStackTrace();
            return false;
        }
    }

    private static boolean isHtmlDocType(String page){
        return page.substring(0, 14).equalsIgnoreCase("<!DOCTYPE html") ||
                page.substring(0, 5).equalsIgnoreCase("<html");
    }
}
