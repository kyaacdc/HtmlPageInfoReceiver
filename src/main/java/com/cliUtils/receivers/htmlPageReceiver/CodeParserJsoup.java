package com.cliUtils.receivers.htmlPageReceiver;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CodeParserJsoup extends CodeParserNoLibraries{

    public CodeParserJsoup() {
    }

    public CodeParserJsoup(String page) {
        super(page);
    }

    //This method is override for demonstrate getting page content with help library Jsoup
    @Override
    public List<String> getListOfBodyWords (String page){
        setPage(page);
        return Arrays.asList(Jsoup.parse(getPage()).body().text().split(" "));
    }

    @Override
    public int wordCountAllWordsNoTagWithNoRepeat(String page){
        return getWordsCountMap(getListOfBodyWords(page)).size();
    }

    @Override
    public List<String> getWordsFromPage(String page){

        List<String> list = getListOfBodyWords(page);

        List<String> resultList = new ArrayList<>();

        for(String s: list){
            boolean isWord = true;
            String result = s;

            if(s.length() == 1 && !Character.isLetter(s.charAt(0)))
                continue;

            for(int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if(!Character.isLetter(c) && (i != s.length() - 1) && (s.charAt(0) != '(') || Character.isDigit(c)) {
                    isWord = false;
                    break;
                }
                else if (i == s.length() - 1 && s.charAt(0) == '(')
                    result = s.substring(1, s.length());
                else if ((i == s.length() - 1)
                        && !Character.isLetter(c))  {
                    result = s.substring(0, s.length() - 1);
                    break;
                }
            }
            if(isWord)
                resultList.add(result);
        }

        return resultList.stream().filter(a -> !a.equals("")).collect(Collectors.toList());
    }
}

