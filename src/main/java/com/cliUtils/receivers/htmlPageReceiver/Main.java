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

        runProcessWithNoLibrariesHelp();

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

    private static void runProcessWithNoLibrariesHelp(){

        //Get parser instance for processing html-code (without help any external libraries)
        CodeParser codeParser = new CodeParser();

        //Get list words from body of html-page for codeParserNoLibraries instance
        List<String> listWordsNoLibraries = codeParser.getWordsFromPage(page);

        //Get map and then set of words for show sorted list words with amount (from listWordsNoLibraries)
        Map<String, Long> mapNoLib = codeParser.getWordsCountMap(listWordsNoLibraries);
        Set<Map.Entry<String, Long>> entriesNoLib = mapNoLib.entrySet();

        System.out.println("All count of words on page (with Tags) - "
                + codeParser.wordCountWithTags(page));
        System.out.println("All count of words on page (with No Tags) with repeat - "
                + codeParser.wordCountAllWordsNoTagWithRepeat(page));
        System.out.println("All count of words on page (with No Tags) with No repeat - "
                + codeParser.wordCountAllWordsNoTagWithNoRepeat(page));
        System.out.println("\nBelow is sorted list of WORD - COUNT");

        entriesNoLib.forEach(a -> System.out.println(a.getKey() + " - " + a.getValue()));
    }
}
