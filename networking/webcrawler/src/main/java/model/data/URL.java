package model.data;

/**
 * A Java class to store URLs and the URL character length.
 */
public class URL {
        private String url;

        /**
         * A simple constructor for the URL object.
         * 
         * @param url : a URL, as a string
         */
        public URL(String url) {
                this.url = url;
        }

        /**
         * Compare a general object hash to the URL's hash.
         * 
         * @param object : the object to compare the URL hash to
         * @return the boolean result of comparing the URL hashcode to the object hashcode
         */
        @Override
        public boolean equals(Object object) {
                if (object.getClass() != URL.class) {
                        return false;
                }
                return (this.hashCode() == object.hashCode());
        }

        /**
         * Compute a hash for a single URL. 
         * 
         * @return the hash for the URL
         */
        @Override
        public int hashCode() {
                return this.url.toLowerCase().hashCode();
        }

        /**
         * Override the default toString() method to return the string URL only.
         * 
         * @return the string URL (as opposed to the Object)
         */
        @Override
        public String toString() {
                return this.url;
        }
}