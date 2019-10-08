package model.utils;

import java.util.ArrayList;
import java.util.List;

import model.data.Cookie;
import model.data.URL;

/**
 * A Java class dedicated to parsing HTML content received from the HTTP server.
 */
public class HTMLParser {

        //public HTMLParser() {
        //}

        /**
         * Get both the sessionID and csrf cookies from the HTTP server response.
         * 
         * @param serverMessage : the server's response content, as a string
         * @return both HTTP cookies in a single Cookie struct
         */
        public static Cookie getCookies(String serverMessage) {
                int csrfloc = serverMessage.indexOf("csrftoken=");
                String csrfToken = serverMessage.substring(csrfloc+10, csrfloc+42);
                String sessionToken = HTMLParser.getSessionID(serverMessage);
                return new Cookie(csrfToken, sessionToken);
        }

        /**
         * Get the sessionID cookie only from the HTTP server response. 
         * 
         * @param serverMessage : the server's GET response content, as a string
         * @return the new sessionID token as a string, for overwriting the cookie
         */
        public static String getSessionID(String serverMessage) {
                int sessloc = serverMessage.indexOf("sessionid=");
                return serverMessage.substring(sessloc+10, sessloc+42);
        }

        /**
         * Scrape the links from the HTTP server response.
         * 
         * @param serverMsg : the server's GET response content, as a string
         * @return the list of links scraped from the current HTTP server response content
         * @throws IllegalArgumentException if the link substring requires an out-of-bounds char index
         */
        public static List<URL> getLinks(String serverMsg) throws IllegalArgumentException {
                String serverMessage = new String(serverMsg);
                ArrayList<URL> toReturn = new ArrayList<>();
                int begin = serverMessage.indexOf("<a href=\"/fakebook/")+9;
                while (begin > 8) {
                        serverMessage = serverMessage.substring(begin);
                        int end = serverMessage.indexOf("\">");
                        if (end == -1) {
                                throw new IllegalArgumentException("Link could not be extracted: " + serverMessage);
                        }
                        String l = serverMessage.substring(0, end);
                        int llen = l.length();
                        if (llen > 50) {
                                System.err.println("ERROR: URL TOO LONG: " + l + "\nLEN: " + l.length());
                                System.exit(1);
                        }
                        toReturn.add(new URL(l.toLowerCase()));
                        begin = serverMessage.indexOf("<a href=\"/fakebook/")+9;
                }
                return toReturn;
        }

        /**
         * Scrape any hidden keys from the HTTP server response.
         * 
         * @param serverMessage : the server's GET response content, as a string
         * @return any discovered keys in a list of strings
         */
        public static List<String> getKeys(String serverMessage) {
                ArrayList<String> toReturn = new ArrayList<>();
                int begin = serverMessage.indexOf("<h2 class=\'secret_flag\'");
                while (begin != -1) {
                        serverMessage = serverMessage.substring(begin+48);
                        String k = serverMessage.substring(0, 64);
                        toReturn.add(k);
                        begin = serverMessage.indexOf("<h2 class=\'secret_flag\'");
                }
                return toReturn;
        }


}
