package com.cliUtils.receivers.htmlPageReceiver;

import java.util.Arrays;
import java.util.List;

public class CodeParser {


    private String page;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<String> getWordsFromPage(String page) {
        return getListOfBodyWords(page);
    }

    public List<String> getListOfBodyWords (String text){
        return Arrays.asList(text.split(" "));
    }


}
