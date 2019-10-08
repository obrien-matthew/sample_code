package model.utils;

/**
 * A Java class dedicated to building and formatting HTTP requests.
 */
public class HTTPBuilder {
        private static final String host = "cs5700f19.ccs.neu.edu";
        private static final String loginURL = "/accounts/login/";

        /**
         * Build a HTTP GET message for the Fakebook login page. 
         * This is used for the initial GET request for login page content.
         * 
         * @param url : the URL of the login page
         * @return the full HTTP GET request content, as a string
         */
        public static String loginGET() {
                // Build GET request.
                StringBuilder g = new StringBuilder("GET ");
                g.append(loginURL);
                g.append(" HTTP/1.1\r\nHost: ");
                g.append(host);
                g.append("\r\n\r\n");

                // Return the GET request as a String.
                return g.toString();
        }

        /**
         * Build a HTTP POST message for the Fakebook login page. 
         * 
         * @param host : the Fakebook host
         * @param url : the URL of the login page
         * @param cookie: the initial session cookie to send to the HTTP server
         * @param user: the username used to log in
         * @param pass: the password used to log in
         * @param csrf: the crsf cookie to pass to the HTTP server
         * 
         * @return the full HTTP POST request header + content, as a string
         */
        public static String loginPOST(String cookie, String user, String pass, String csrf) {
                // Build content first, to get length for header.
                StringBuilder c = new StringBuilder("username=");
                c.append(user);
                c.append("&password=");
                c.append(pass);
                c.append("&csrfmiddlewaretoken=");
                c.append(csrf);
                c.append("&next=%2Ffakebook%2F\r\n");
                String content = c.toString();
                int contentLen = content.length();

                // Build header and append content.
                StringBuilder h = new StringBuilder("POST ");
                h.append(loginURL);
                h.append(" HTTP/1.1\r\nHost: ");
                h.append(host);
                h.append("\r\nContent-Type: application/x-www-form-urlencoded\r\nContent-Length: ");
                h.append(contentLen);
                h.append(cookie);
                h.append("\r\n\r\n");
                h.append(content);

                // Return POST message as a String.
                return h.toString();
        }

        /**
         * Build a HTTP GET request for a non-login Fakebook page.
         * 
         * @param url : the URL of the login page
         * @param cookieHeader : the formatted cookie header as a string, using the updated current sessionID token
         * @return the full HTTP GET request header + content, as a string
         */
        public static String pageGET(String url, String cookieHeader) {
                StringBuilder g = new StringBuilder("GET ");
                g.append(url);
                g.append(" HTTP/1.1\r\nHost: ");
                g.append(host);
                g.append("\r\nAccept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\nAccept-Encoding: identity\r\nAccept-Language: en-US\r\nConnection: keep-alive");
                g.append(cookieHeader);
                g.append("\r\n\r\n");

                // Return the GET request as a String.
                return g.toString();
        }


}
