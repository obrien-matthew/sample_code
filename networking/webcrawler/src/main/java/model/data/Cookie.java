package model.data;

import java.lang.StringBuilder;

/**
 * A Java class representing the set of HTTP csrf and sessionID cookies.
 */
public class Cookie {
        // The CSRF token for the session, given by login page.
        private String csrftoken;
        // The sessionID for the session, given by login page.
        private String sessionid;

        /**
         * A simple constructor that builds a Cookie object with two tokens.
         * 
         * @param csrfTok : the csrf token provided by the HTTP server, as a string
         * @param sessionTok : a sessionID token provided by the HTTP server, as a string
         */
        public Cookie(String csrfTok, String sessionTok) { 
                this.sessionid = sessionTok;
                this.csrftoken = csrfTok;    
        }

        /**
         * Retrieve the csrf cookie for an HTTP session.
         * 
         * @return the csrf cookie for an HTTP session
         */
        public String getCSRF() {
                return this.csrftoken;
        }

        /**
         * Retrieve the sessionID cookie of the current HTTP session.
         * 
         * @return the sessionID cookie of the current HTTP session
         */
        public String getSessionID() {
                return this.sessionid;
        }

        /**
         * Overwrite the sessionID cookie. 
         * Use this when a new login session is initiated.
         * 
         * @param newID : the new sessionID cookie provided by the HTTP server, as a string
         */
        public void setSessionID(String newID) {
                this.sessionid = newID;
        }

        /**
         * Format the current cookie information as an HTTP header. 
         * 
         * @return the current cookie information in an HTTP header line, as a string
         */
        @Override
        public String toString() {
                StringBuilder s = new StringBuilder("\r\nCookie: csrftoken=");
                s.append(csrftoken);
                s.append("; sessionid=");
                s.append(sessionid);
                return s.toString();
        }



}