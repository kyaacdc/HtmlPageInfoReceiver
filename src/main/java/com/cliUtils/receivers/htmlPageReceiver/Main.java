package com.cliUtils.receivers.htmlPageReceiver;

import java.util.Scanner;

public class Main{

    private static String url = "";

    public static void intro(String[] args){

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

    public static void main(String[] args) {

        intro(args);

    }
}
