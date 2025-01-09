package com.englishweb.h2t_backside.utils;

import java.util.LinkedList;
import java.util.List;

public class ParseData {

    public static List<Long> parseStringToLongList(String str) {
        List<Long> list = new LinkedList<>();
        if (!str.isEmpty()) {
            String[] elements = str.split(",");
            for (String element : elements) {
                if (!element.isEmpty()) {
                    list.add(Long.parseLong(element.trim()));
                }
            }
        }
        return list;
    }
}
