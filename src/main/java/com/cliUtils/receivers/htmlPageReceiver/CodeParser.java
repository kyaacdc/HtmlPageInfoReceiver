package com.cliUtils.receivers.htmlPageReceiver;

import java.util.*;
import java.util.stream.Collectors;

public class CodeParser {

    private String page;

    public CodeParser() {
    }

    public CodeParser(String page) {
        this.page = page;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, Long> getWordsCountMap(List<String> words) {

        Map<String, Long> map = new TreeMap<>(String::compareToIgnoreCase);

        for (String s : words)
            map.put(s, words.stream().filter(a -> a.equals(s)).count());

        return map;
    }

    public List<String> getWordsFromPage(String page){
        return  removeEmptyElements(getListNoSingleTags(getNoScriptList(getNoFontTagList(getListBodyRaw(getListOfBodyWords(page))))));
    }

    public int wordCountAllWordsNoTagWithRepeat(String page) {
        return getWordsFromPage(page).size();
    }

    public int wordCountAllWordsNoTagWithNoRepeat(String page){
        return getWordsCountMap(getWordsFromPage(page)).size();
    }

    public int wordCountWithTags(String s) {
        int c = 0;
        char ch[]= new char[s.length()];
        for(int i = 0; i < s.length(); i++)
        {
            ch[i] = s.charAt(i);
            if( ((i > 0) && (ch[i] != ' ') && (ch[i-1] == ' ')) || ((ch[0]!=' ') && (i == 0)) )
                c++;
        }
        return c;
    }

    //This method should for get raw page content for parse without help any libraries
    public List<String> getListOfBodyWords (String text){
        return Arrays.asList(text.split(" "));
    }

    public List<String> getListBodyRaw(List<String> list){

        list = list.stream().filter(a -> !a.equals("")).collect(Collectors.toList());

        int startBodyIndex = 0;
        int endBodyIndex = list.size();

        label:
        for(String s: list){
            for(int i = 0; i < s.length(); i++){
                if ((i + 4 < s.length()) && s.length() > 5 && s.substring(i, i + 5).equals("<body"))
                    startBodyIndex = list.indexOf(s) + 1;
                if ((i + 6 < s.length()) && s.length() > 7 && s.substring(i, i + 7).equals("</body>")) {
                    endBodyIndex = list.indexOf(s);
                    break label;
                }
            }
        }

        return list.subList(startBodyIndex, endBodyIndex);
    }

    public List<String> getNoFontTagList(List<String> list) {

        String temp = "";

        for (String s : list) {
            for (int i = 0; i < s.length(); i++) {

                if ((i + 3 < s.length()) && s.length() > 4 && s.substring(i, i + 2).equals("<h")) {
                    temp = s.substring(4);
                    list.set(list.indexOf(s), temp);
                }
                if(i + 5 < s.length() && s.substring(i, i + 4).equals("</h3")){
                    temp = s.substring(0, i);
                    list.set(list.indexOf(s), temp);
                    break;

                }



                if ((i + 4 < s.length()) && s.length() > 5 && s.substring(i, i + 3).equals("</h")) {
                    //temp = s.substring(0, s.length() - 5);
                    temp = s.substring(0, i);
                    try {
                        list.set(list.indexOf(s), temp);
                    } catch (IndexOutOfBoundsException e){
                        break;
                    }
                }
            }
        }

        return list;
    }

    public List<String> getNoScriptList(List<String> list) {

        int startBodyIndex = 0;
        int endBodyIndex = list.size();
        int counter = 0;

        List<String> listForDelete = new ArrayList<>();

        for (String s : list) {
            for (int i = 0; i < s.length(); i++) {
                if ((i + 6 < s.length()) && s.length() > 7 && s.substring(i, i + 7).equals("<script"))
                    counter++;
            }
        }

        for (int j = 0; j < counter; j++) {
            label:
            for (String s : list) {
                for (int i = 0; i < s.length(); i++) {
                    if ((i + 6 < s.length()) && s.length() > 7 && s.substring(i, i + 7).equals("<script"))
                        startBodyIndex = list.indexOf(s);
                    if ((i + 6 < s.length()) && s.length() > 7 && s.substring(i, i + 7).equals("</scrip")) {
                        endBodyIndex = list.indexOf(s) + 1;
                        break label;
                    }
                }
            }

            listForDelete.addAll(list.subList(startBodyIndex, endBodyIndex));
        }

        List<String> resultList = new ArrayList<>();

        label:
        for(String s: list){
            for(String d: listForDelete) {
                if (s.equals(d))
                    continue label;
            }
            resultList.add(s);
        }

        return resultList;
    }

    public List<String> getListNoSingleTags(List<String> list) {

        Set<String> exscludeList = new LinkedHashSet<>();

        Collections.addAll(exscludeList, "[ ] < > = / \" | - * . ( ) ! @".split(" "));

        for(String exclude: exscludeList) {

            for (String s : list) {

                if (s.length() == 1 && !Character.isLetter(s.charAt(0))) {
                    list.set(list.indexOf(s), "");
                    continue;
                }

                for (int i = 0; i < s.length(); i++) {

                    if (s.charAt(0) == '(') {
                        String result = s.substring(1, s.length());
                        list.set(list.indexOf(s), result);
                        break;
                    }

                    if (s.charAt(i) == ')') {
                        String result = s.substring(0, i);
                        list.set(list.indexOf(s), result);
                        break;
                    }

                    if ((i + exclude.length() - 1 < s.length()) && s.length() > exclude.length()
                            && s.substring(i, i + exclude.length()).equals(exclude))
                    {
                        String temp = s.substring(s.length());
                        list.set(list.indexOf(s), temp);
                        break;
                    }
                }
            }
        }
        return list;
    }

    public List<String> removeEmptyElements(List<String> list){
        return list.stream().filter(a -> !a.equals("")).collect(Collectors.toList());
    }
}
