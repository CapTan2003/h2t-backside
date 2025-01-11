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

    public static List<String> parseStringToStringList(String str) {
        str = str.substring(1, str.length() - 1); // Loại bỏ dấu "[" và "]"

        // Tách chuỗi theo dấu phẩy và loại bỏ khoảng trắng dư thừa
        String[] items = str.split(",\\s*");

        List<String> result = new LinkedList<>();
        for (String item : items) {
            // Loại bỏ dấu ngoặc kép ở mỗi phần tử
            result.add(item.substring(1, item.length() - 1));
        }

        return result;
    }
}
