package model.data;

import java.util.ArrayList;
import java.util.List;

/**
 * A Java class to manage any keys discovered while crawling Fakebook.
 */
public class SecretKeys {
        private List<String> keys;

        /**
         * A simple constructor to hold up to five secret keys.
         */
        public SecretKeys() {
                this.keys = new ArrayList<String>(5);
        }

        /**
         * Add a discovered key to the list of keys. 
         * 
         * @param k : a 64-byte key, as a string
         * @throws IllegalStateException if the addition of 6th key (invalid) is attempted
         */
        public void addKey(String k) throws IllegalStateException {
                if (this.keys.size() < 5) {
                        this.keys.add(k);
                        return;
                }

                throw new IllegalStateException("Trying to add more than 5 keys to SecretKeys object.");
        }

        /**
         * Get the current number of keys in the list.
         * 
         * @return the current number of keys in the list
         */
        public int getSize() {
                return this.keys.size();
        }

        /**
         * Retrieve all found keys as a string.
         * 
         * @return all keys found in one string
         */
        @Override
        public String toString() {
                StringBuilder s = new StringBuilder();
                for (String key : keys) {
                        s.append(key);
                        s.append("\n");
                }
                return s.toString();
        }
}
