package com.cliUtils.receivers.htmlPageReceiver;

public class HttpUrlValidator {

    public static boolean isHttpUrlValid(String url){

        boolean isValid = (url.length() > 7) && (url.substring(0, 7).equalsIgnoreCase(("HTTP://")));

        if(!isValid)
            System.out.println("URL does not valid. Please input valid URL, that should begin like http://...... ");

        return isValid;
    }

    private static boolean isHtmlDocType(String page){
        return page.substring(0, 14).equalsIgnoreCase("<!DOCTYPE html");
    }
}

