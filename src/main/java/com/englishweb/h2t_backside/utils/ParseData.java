package com.englishweb.h2t_backside.utils;

import org.springframework.data.domain.Sort;

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

    public static List<Sort.Order> parseStringToSortOrderList(String sortFields) {
        List<Sort.Order> orders = new LinkedList<>();
        if (sortFields != null && !sortFields.isEmpty()) {
            String[] fields = sortFields.split(",");
            for (String field : fields) {
                if (field.startsWith("-")) {
                    orders.add(new Sort.Order(Sort.Direction.DESC, field.substring(1)));
                } else {
                    orders.add(new Sort.Order(Sort.Direction.ASC, field));
                }
            }
        }
        return orders;
    }
}
