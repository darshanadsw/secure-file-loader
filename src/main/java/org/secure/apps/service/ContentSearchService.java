package org.secure.apps.service;

import java.util.ArrayList;
import java.util.List;

public class ContentSearchService {

    public List<int[]> search(String text, String searchQuery) {
        final List<int[]> result = new ArrayList<>();
        int index = text.indexOf(searchQuery);
        while (index >= 0) {
            int endIndex = index + searchQuery.length();
            result.add(new int[]{index, endIndex});
            index = text.indexOf(searchQuery, endIndex);
        }
        return result;
    }


}
