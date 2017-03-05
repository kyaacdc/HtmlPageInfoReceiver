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

    private Main() {}

    public static void main(String[] args) throws IOException {

        Main main = new Main();

        //Checking and/or input URL and content on html-page
        main.intro(args);

        //Get page content for requested Url
        page = getContentByUrl(url);

        //Process with getting words content without any external libraries
        main.runProcessWithNoLibrariesHelp();

        //Process with getting words content with help Jsoup external library
        main.runProcessWithJsoup();

        System.out.println("\nResults between custom and Jsoup parser can be little differ," +
                " because jsoup parser work at another algorithm that different of task.");
    }


    private void intro(String[] args){

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

    private void runProcessWithNoLibrariesHelp(){

        //Get parser instance for processing html-code (without help any external libraries)
        CodeParserNoLibraries codeParserNoLibraries = new CodeParserNoLibraries();

        //Get list words from body of html-page for codeParserNoLibraries instance
        List<String> listWordsNoLibraries = codeParserNoLibraries.getWordsFromPage(page);

        //Get map and then set of words for show sorted list words with amount (from listWordsNoLibraries)
        Map<String, Long> mapNoLib = codeParserNoLibraries.getWordsCountMap(listWordsNoLibraries);
        Set<Map.Entry<String, Long>> entriesNoLib = mapNoLib.entrySet();

        System.out.println("\nDemonstration of program work, with not use some external libraries for parse");
        System.out.println("All count of words on page (with Tags) - "
                + codeParserNoLibraries.wordCountWithTags(page));
        System.out.println("All count of words on page (with No Tags) with repeat - "
                + codeParserNoLibraries.wordCountAllWordsNoTagWithRepeat(page));
        System.out.println("All count of words on page (with No Tags) with No repeat - "
                + codeParserNoLibraries.wordCountAllWordsNoTagWithNoRepeat(page));
        System.out.println("\nBelow is sorted list of words (WORD - COUNT)");

        entriesNoLib.forEach(a -> System.out.println(a.getKey() + " - " + a.getValue()));
    }

    private void runProcessWithJsoup() {
        //Get parser instance for processing html-code (with help JSoup parser, for compare custom parser)
        CodeParserJsoup codeParserJsoup = new CodeParserJsoup();

        //Get list words from body of html-page for codeParserJsoup instance (for compare results)
        List<String> listWordsJsoup = codeParserJsoup.getWordsFromPage(page);

        //Get map and then set of words for show sorted list words with amount (from listWordsJsoup)
        Map<String, Long> mapJsoup = codeParserJsoup.getWordsCountMap(listWordsJsoup);
        Set<Map.Entry<String, Long>> entriesJsoup = mapJsoup.entrySet();

        System.out.println("\nDemonstration of program work, with use Jsoup library for parse\n");
        System.out.println("All count of words on page (with Tags) - "
                + codeParserJsoup.wordCountWithTags(page));
        System.out.println("All count of words on page (with No Tags) with repeat - "
                + codeParserJsoup.wordCountAllWordsNoTagWithRepeat(page));
        System.out.println("All count of words on page (with No Tags) with No repeat - "
                + codeParserJsoup.wordCountAllWordsNoTagWithNoRepeat(page));
        System.out.println("\nBelow is sorted list of WORD - COUNT");

        entriesJsoup.forEach(a -> System.out.println(a.getKey() + " - " + a.getValue()));
    }
}
