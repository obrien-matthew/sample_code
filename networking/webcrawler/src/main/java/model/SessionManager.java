package model;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import model.utils.HTMLParser;
import model.utils.HTTPBuilder;
import model.data.Cookie;


/**
 * A manager for a full crawling session attempt. 
 * 
 * One "session" refers to one complete attempt handle HTTP request building and HTML/HTTP response parsing.
 * A single SessionManager object may include multiple login/socket sessions in order to help the Crawler 
 * retrieve all required keys. 
 */
public class SessionManager {
        private final String host = "cs5700f16.ccs.neu.edu";
        private final Integer port = 80;
        private final Integer maxLen = 4096*2;
        private final Integer timeout = 1000;

        private Socket connection;
        private OutputStreamWriter out;
        private InputStreamReader in;
        private String username;
        private String password;
        private Cookie cookie;

        /**
         * Constructor for a SessionManager object. 
         * 
         * @param username : the username passed in the initial webcrawler command, as a string
         * @param password : the password passed in the initial webcrawler command, as a string
         */
        public SessionManager(String username, String password) {
                this.username = username;
                this.password = password;

                while (true) {
                        try {
                                // Create a new socket and open a new connection.
                                this.connection = new Socket(this.host, this.port);
                                this.connection.setSoTimeout(this.timeout);
                                this.out = new OutputStreamWriter(this.connection.getOutputStream(), "UTF-8");
                                this.in = new InputStreamReader(this.connection.getInputStream(), "UTF-8");
                                return;
                        } catch (Exception e) {
                                // do nothing, try again
                        }
                }
        }

        /**
         * Log into Fakebook using login details attached to the SessionManager object. 
         * Read the initial response to gather the correct sessionID and csrf cookies for crawling, 
         * then get the response content of the first Fakebook page after successful login. 
         * 
         * @return the server response for the first page after login, as a string
         * 
         * @throws SocketTimeoutException if socket connection times out
         * @throws IllegalStateException if illegal socket behavior is found
         * @throws Exception if recieved from called functions
         */
        public String login() throws SocketTimeoutException, IllegalStateException, Exception {
                // Initial GET request on login page.
                String GETlogin = HTTPBuilder.loginGET();
                this.sendHTTP(GETlogin);
                String serverResp;

                // Read login page to a String.
                serverResp = this.readHTTP();
                // Find and store cookies.
                this.cookie = HTMLParser.getCookies(serverResp);

                // Send login information to server.
                String POSTlogin = HTTPBuilder.loginPOST(this.cookie.toString(), username, password,
                                this.cookie.getCSRF());
                this.sendHTTP(POSTlogin);

                // Read server response and update session ID to a logged-in-status ID
                serverResp = this.readHTTP();
                this.cookie.setSessionID(HTMLParser.getSessionID(serverResp));

                // Send the GET request
                String pageGET = HTTPBuilder.pageGET("/fakebook/", this.cookie.toString());
                this.sendHTTP(pageGET);

                // Read the server response
                serverResp = this.readHTTP();
                return serverResp;
        }

        /**
         * Get the contents of another page of Fakebook, given a page URL.
         * 
         * @param url : the URL to visit next, sent via HTTP GET
         * 
         * @return the server response to the HTTP GET request, as a string
         * 
         * @throws SocketTimeoutException, IllegalStateException, Exception when received from called functions.
         */
        public String visit(String url) throws SocketTimeoutException, IllegalStateException, Exception {
                String pageGET = HTTPBuilder.pageGET(url, this.cookie.toString());
                this.sendHTTP(pageGET);
                String serverResp;
                serverResp = this.readHTTP();
                return serverResp;
        }

        /**
         * Send a HTTP message to the server.
         * 
         * @param message : the HTTP GET or POST message to send to the server
         * 
         * @throws IllegalStateException if the login attempt failed
         */
        public void sendHTTP(String message) throws IllegalStateException {
                try {
                        // Write to the stream
                        this.out.write(message);
                        this.out.flush();

                } catch (Exception e) {
                        throw new IllegalStateException("Error sending HTTP. Error message: " + e.getMessage());
                }
        }

        /**
         * Get the contents sent by the server in response to an HTTP request, as a string.
         * 
         * @return the contents sent by the server in response to a GET or POST request
         * 
         * @throws SocketTimeoutException if socket connection times out and expires
         * @throws IllegalStateException if illegal socket behavior is found
         * @throws Exception generally for other errors, used for debugging and log-referencing purposes
         */
        public String readHTTP() throws SocketTimeoutException, IllegalStateException, Exception {
                char[] buff = new char[this.maxLen];
                StringBuilder sb = new StringBuilder();
                int count;
                int cl = -1;
                try {
                        // Check for stream-end responses
                        count = this.in.read(buff, 0, this.maxLen);
                        if (count == -1) {
                                throw new IllegalStateException("Invalid server response.");
                        }
                        sb.append(new String(buff, 0, count));

                        // Handle chunked HTTP messages
                        if (sb.toString().contains("Transfer-Encoding: chunked")) {
                                while (count != -1) {
                                        buff = new char[this.maxLen];
                                        count = this.in.read(buff, 0, this.maxLen);
                                        if (count > -1) {
                                                sb.append(new String(buff, 0, count));
                                        }
                                }
                        }
                        // Handle expected HTTP messages 
                        else if ((cl = sb.toString().indexOf("Content-Length: ")) > -1) {
                                String msg = sb.toString();
                                int contentLen = Integer.parseInt(msg.substring(cl+16, msg.indexOf("\r\n", cl+16)));
                                int headerLen = msg.substring(0, msg.indexOf("\r\n\r\n")).length();
                                int sum = count - headerLen;
                                while (contentLen > sum) {
                                        buff = new char[this.maxLen];
                                        count = this.in.read(buff, 0, this.maxLen);
                                        sb.append(new String(buff, 0, count));
                                        sum += count;
                                }
                        }
                } catch (SocketTimeoutException f) {
                        throw new SocketTimeoutException("Socket timeout.");
                } catch (IllegalStateException g) {
                        throw new IllegalStateException(g.getMessage());
                } catch (Exception e) {
                        e.printStackTrace();
                        throw new Exception(
                                        "Error reading HTTP response. Error message: " + e.getMessage());
                }
                return sb.toString();
        }

        /**
         * Close a socket connection.
         * 
         * @throws IOException if the socket cannot be closed
         */
        public void close() throws IOException {
                try {
                        this.connection.close();
                } catch (IOException e) {
                        throw new IllegalStateException("Error closing socket. Error message: " + e.toString());
                }
        }


}
