package ClientSideSystem;

import java.util.*;

public class SearchAPI {
    public List<String> find(String target, Set<String> options){
        List<String> similar = new ArrayList<>();
        target = target.toLowerCase();

        for (String option : options) {
            if (containsSubstring(target, option.toLowerCase()))
                similar.add(option);
        }

        return similar;
    }

    private static boolean containsSubstring(String str1, String str2) {
        if (str1.length() > str2.length()) return false;

        int i = 0;

        for (int j = 0; j < str2.length(); j++){
            if (str1.charAt(i) == str2.charAt(j)){
                if (++i >= str1.length()) return true;
            }
            else i = 0;
        }

        return false;
    }
}
