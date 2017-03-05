package com.cliUtils.receivers.htmlPageReceiver;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import static com.cliUtils.receivers.htmlPageReceiver.HtmlGetter.getContentByUrl;

public class Main{

    private static String url = "";
    private static String page;

    //Constructor is private for deny create any instance from this class
    private Main() {}

    public static void main(String[] args) throws IOException {

        //Checking and/or input URL and content on html-page
        intro(args);

        //Get page content for requested Url
        page = getContentByUrl(url);
    }

    private static void intro(String[] args){

        Scanner sc;

        System.out.println("Welcome to Html Page Words Receiver!");

        if(args.length != 0)
            url = args[0];
        else {
            System.out.println("Please, input Url for receive info");
            sc = new Scanner(System.in);
            url = sc.nextLine();
        }

        while (!HttpUrlValidator.isHttpUrlValid(url)) {
            sc = new Scanner(System.in);
            url = sc.nextLine();
        }
    }
}
