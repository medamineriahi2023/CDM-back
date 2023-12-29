package oga.microservice.athentification.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FilterUtils {
    public static Map<String, Set<String>> filterFromField(String filterParam) {
        if (filterParam == null || filterParam.isEmpty()) {
            return new HashMap<>();
        }

        String[] fields = filterParam.split(",");

        Set<String> fieldsToInclude = new HashSet<>();
        Set<String> fieldsToExclude = new HashSet<>();

        for (String field : fields) {
            String propertyName = field.replaceAll("[+-]", "");
            String[] multiProps = propertyName.split("\\.");
            boolean isExclude = field.startsWith("-");
            if (multiProps.length > 1) {
                propertyName = multiProps[0];
                isExclude = false;
            }

            if (isExclude) {
                fieldsToExclude.add(propertyName);
            } else {
                fieldsToInclude.add(propertyName);
            }
        }

        HashMap<String, Set<String>> res = new HashMap<>();

        if (!fieldsToExclude.isEmpty()) {
            res.put("exclude", fieldsToExclude);
        } else if (!fieldsToInclude.isEmpty()) {
            res.put("include", fieldsToInclude);
        }

        return res;
    }

    public static String fieldsFromChildFields(String child, String filterParam) {
        if (filterParam == null || filterParam.isEmpty()) {
            return null;
        }

        String[] fields = filterParam.split(",");

        Pattern p = Pattern.compile("^[+-]?" + child + "\\.");

        List<String> childFields = Arrays.stream(fields).filter(field -> {
            Matcher matcher = p.matcher(field);
            return matcher.lookingAt();
        }).map(field -> field.replace(child + ".", "")).collect(Collectors.toList());

        return childFields.stream().collect(Collectors.joining(","));
    }

    public static boolean isFetched(String property, Map<String, Set<String>> filters) {
        return (filters.containsKey("include") && filters.get("include").contains(property)) ||
                (filters.containsKey("exclude") && !filters.get("exclude").contains(property)) || filters.isEmpty();
    }
}
