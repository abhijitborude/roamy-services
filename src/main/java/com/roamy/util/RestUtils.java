package com.roamy.util;

import org.springframework.data.domain.Page;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abhijit on 11/14/2015.
 */
public class RestUtils {

    public static String getStackTraceAsString(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static Map<String, String> getLinks(Page page, int size) {
        Map<String, String> links = new HashMap<String, String>();
        if (page.hasPrevious()) {
            links.put("previous", "/?page=" + page.previousPageable().getOffset() + "&size=" + size);
        }
        if (page.hasNext()) {
            links.put("next", "/?page=" + page.nextPageable().getOffset() + "&size=" + size);
        }
        return links;
    }

    public static Map<String, String> getErrorMessages(Throwable t) {
        Map<String, String> messages = new HashMap<String, String>();
        messages.put("error", t.getMessage());
        messages.put("developerErrorMessage", RestUtils.getStackTraceAsString(t));
        return messages;
    }
}
