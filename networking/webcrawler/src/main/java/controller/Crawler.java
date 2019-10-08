package controller;

import model.data.SecretKeys;

import java.util.Set;
import java.util.ArrayDeque;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;

import model.SessionManager;
import model.data.URL;
import model.utils.HTMLParser;

/**
 * The Crawler controller. Makes calls to model functionality for HTTP parsing and page processing.
 */

public class Crawler {
        private SecretKeys keys;
        private SessionManager manager;
        private Set<URL> visited;
        private Deque<URL> toVisit;
        private String username;
        private String password;

        /** 
         * The constructor for a Crawler object. 
         * 
         * It stores all discovered keys, visited and crawled pages, and the index of pages to visit. 
         */
        public Crawler(String username, String password) {
                this.keys = new SecretKeys();
                this.visited = new HashSet<URL>(5000);
                this.toVisit = new ArrayDeque<URL>(4000);
                this.username = username;
                this.password = password;
        }

        /**
         * Start crawling Fakebook. 
         * 
         * @param username : the username used for logging into Fakebook
         * @param password : the password used for logging into Fakebook
         * @return the five keys scraped from Fakebook
         */
        public SecretKeys crawl() {
                String serverResp = this.getNewSessionManager();
                visited.add(new URL("/accounts/login/"));
                List<URL> links = HTMLParser.getLinks(serverResp);
                List<String> keys = HTMLParser.getKeys(serverResp);

                // Add any discovered URLs from the login page to the queue for crawling
                for (URL url : links) {
                        if (!visited.contains(url) && !toVisit.contains(url)) {
                                toVisit.add(url);
                        }
                }

                // Add keys from the initial login page, if any
                for (String key : keys) {
                        this.keys.addKey(key);
                }

                // Continue looking for keys and crawling pages until all keys are found
                while (!toVisit.isEmpty() && this.keys.getSize() < 5) {
                        URL next = toVisit.remove();
                        visited.add(next);
                        while (true) {
                                try {
                                        serverResp = this.manager.visit(next.toString());
                                        this.codeHandler(serverResp, next);
                                        break;
                                } catch (SocketTimeoutException | IllegalStateException e) {
                                        this.getNewSessionManager();
                                } catch (Exception i) {
                                        System.err.println(i.getMessage());
                                        System.exit(1);
                                }
                        }
                }

                return this.keys;
        }

        /**
         * Refreshes the session manager object attached to this Crawler (and closes the previous one).
         * 
         * @return a String, the server's response to the login process.
         */
        public String getNewSessionManager() {
                try {
                        this.manager.close();
                } catch (IOException | NullPointerException e) {
                        // Do nothing and continue
                }
                String s = new String();
                while (true) {
                        try {
                                this.manager = new SessionManager(this.username, this.password);
                                s = this.manager.login();
                                break;
                        } catch (SocketTimeoutException e) {
                                // do nothing; try again
                        } catch (IllegalStateException h) {
                                try {
                                        this.manager.close();
                                } catch (IOException e) {
                                        // Do nothing and continue
                                }
                        } catch (Exception i) {
                                System.err.println(i.getMessage());
                                System.exit(1);
                        }
                }
                return s;
        }

        /**
         * Count how many more requests can be managed on the current connection. 
         * Corresponds to the HTTP Keep-Alive header.
         * 
         * @param serverResp : the response from the HTTP server, as a string
         * @return the number of requests left on the current connection
         */
        public int getRemainingMsgCount(String serverResp) {
                int start = serverResp.indexOf("Keep-Alive:");
                if (start >= 0) {
                        String count = serverResp.substring(start+27);
                        count = count.substring(0, count.indexOf("\r\n"));
                        try {
                                return Integer.parseInt(count);
                        } catch (NumberFormatException e) {
                                // do nothing
                        }
                }
                return 100;
        }

        /** 
         * Determine which responses can be crawled and discard or reprocess special HTTP cases. 
         * Parse any HTML discovered for keys and new URLs.
         * 
         * @param serverResp : the response from the HTTP server, as a string
         * @param page : the URL object containing the link to inspect next
         * 
         * @throws IllegalStateException if a bad response is received from the server
         * @throws SocketTimeoutException if the socket times out
         * @throws Exception if received from a called method
         */
        public void codeHandler(String serverResp, URL page) throws IllegalStateException, SocketTimeoutException, Exception {
                // Handle HTTP 500 case
                if (serverResp.startsWith("HTTP/1.1 500")) {
                        this.toVisit.push(page);
                        throw new IllegalStateException("500 error. Try again.");
                }
                // Handle HTTP 301/302 cases 
                else if (serverResp.startsWith("HTTP/1.1 301") || serverResp.startsWith("HTTP/1.1 302")) { 
                        // Grab redirect link as a substring
                        int startLink = serverResp.indexOf("Location: ");
                        int endLink = serverResp.indexOf("\r\n", startLink);
                        String newLink = serverResp.substring(startLink+10,endLink);
                        this.toVisit.push(new URL(newLink));
                        return;

                } 
                // Handle valid cases or ignore HTTP 403/404 and other unexpected response behavior
                else if (!serverResp.startsWith("HTTP/1.1 403") && !serverResp.startsWith("HTTP/1.1 404")) {
                        if (!serverResp.startsWith("HTTP/1.1 200")) {
                                this.toVisit.push(page);
                                throw new IllegalStateException("HTTP Code Error. Try again.");
                        }

                        List<URL> links = new ArrayList<>();
                        try {
                                links = HTMLParser.getLinks(serverResp);
                        } catch (Exception e) {
                                this.toVisit.push(page);
                                throw new IllegalStateException("Link retrieval error. Try again.");
                        }
                        List<String> keys = HTMLParser.getKeys(serverResp);
        
                        // Add any new URLs found to the queue
                        for (URL url : links) {
                                if (!visited.contains(url) && !toVisit.contains(url)) {
                                        toVisit.add(url);
                                }
                        }
        
                        // Save any keys found to SecretKeys
                        for (String key : keys) {
                                this.keys.addKey(key);
                        }
                }

                // Start a new socket connection if a timeout is upcoming
                if (this.getRemainingMsgCount(serverResp) < 5) {
                        this.getNewSessionManager();
                }
        }
}
