#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>
#include "openssl.h"


/*
 * Extracts the appropriate error information from the code returned by SSL_get_error().
 */
char* get_ssl_errmsg(int sslcode) {
        switch (sslcode) {
                case SSL_ERROR_NONE :
                        return "No SSL error occured.\n";
                case SSL_ERROR_ZERO_RETURN :
                        return "SSL Error: connection closed.\n";
                case SSL_ERROR_WANT_READ :
                        return  "SSL Error: want read.\n";
                case SSL_ERROR_WANT_WRITE :
                        return  "SSL Error: want write.\n";
                case SSL_ERROR_WANT_CONNECT :
                        return  "SSL Error: connection not established in time.\n";
                case SSL_ERROR_WANT_X509_LOOKUP :
                        return  "SSL Error: callback error occured.\n";
                case SSL_ERROR_SYSCALL :
                        return  "SSL Error: a syscall error occured. Check errno.\n";
                case SSL_ERROR_SSL :
                        return  "SSL Error: A fatal SSL error occured.\n";
                default :
                        return "An unknown SSL error occured.\n";
        }
}

/*
 * An general errorhandler for SSL-related errors. Prints any error
 * information before closing down the SSL connection, freeing
 * any allocated memory, and terminating with a failure code.
 */
void ssl_errorhandler(int code, int sslcode, SSL* ssl) {
        char* errmsg;
        if (sslcode < 1) {
                errmsg = get_ssl_errmsg(sslcode);
        }
        switch (code) {
                case 200 : 
                        fprintf(stderr, "Error: could not create CTX object.\n");
                        break;
                case 201 :
                        fprintf(stderr, "Error: could not create SSL object.\n");
                        break;
                case 202 :
                        fprintf(stderr, "Error: could not create BIO object.\n");
                        break;
                case 203 :
                        fprintf(stderr, "Error: An SSL connection error occured: %s\n", errmsg);
                        break;
                case 204 :
                        fprintf(stderr, "Error: An SSL write error occured: %s\n", errmsg);
                        break;
                case 205 :
                        fprintf(stderr, "Error: An SSL read error occured: %s\n", errmsg);
                        break;
                default :
                        break;
        }
        perror("OpenSSL Error Queue:\n");
        ERR_print_errors_fp(stderr);
        if (sslcode != SSL_ERROR_SSL) {
                SSL_shutdown(ssl);
        }
        if (ssl != NULL) {
                SSL_free(ssl);
        }
        exit(EXIT_FAILURE);
}


/*
 * Initializes SSL-related functionality. Loads libraries, creates
 * SSL objects, and links BIO/socket/CTX to SSL object before
 * returning the SSL object back to the caller.
 */
SSL* ssl_initialize(int client_sock) {
        // SSL library initializations
        SSL_load_error_strings();
        ERR_load_crypto_strings();
        SSL_library_init();
        ERR_clear_error();
        
        // Initializing SSL objects
        SSL_CTX *ctx = SSL_CTX_new(SSLv23_client_method());
        if (ctx == NULL) {
                ssl_errorhandler(200, 1, NULL);
        }
        SSL *ssl = SSL_new(ctx);
        if (ssl == NULL) {
                ssl_errorhandler(201, 1, NULL);
        }

        BIO *bio = BIO_new_socket(client_sock, BIO_CLOSE);
        if (bio == NULL) {
                ssl_errorhandler(202, 1, ssl);
        }
        SSL_set_bio(ssl, bio, bio);
        SSL_set_connect_state(ssl);

        return ssl;
}

/*
 * Sends the HELLO message to the server over an SSL connection.
 */
void ssl_sendhello(SSL* ssl, char* nuid) {
        char hello[MAX];
        snprintf(hello, MAX, "cs5700fall2019 HELLO %s\n", nuid);
        int s = 1;
        if ((s = SSL_write(ssl, hello, strlen(hello))) <= 0) {
                ssl_errorhandler(204, SSL_get_error(ssl, s), ssl);
        }
}

/*
 * Sends the answer to a math problem back to the server over an SSL connection.
 */
void ssl_sendans(int ans, SSL* ssl) {
        char client_resp[MAX];
        snprintf(client_resp, MAX, "cs5700fall2019 %d\n", ans);
        int s = 1;
        if ((s = SSL_write(ssl, client_resp, strlen(client_resp))) <= 0) {
                ssl_errorhandler(204, SSL_get_error(ssl, s), ssl);
        }
}

/*
 * The main interaction loop for the SSL version of the program.
 * Main functionality is nearly the same; however there are differences
 * in how errors are handled (with ssl_errorhandler() rather than
 * errorhandler()), in functions that are called (ssl_sendans() rather 
 * than sendans()), and in how messages are recieved (SSL_read() rather 
 * than recv()).
 */
void ssl_interact(SSL* ssl, char** key) {
        int active = 1;
        while (active) {
                int ans = INT_MAX;
                char server_resp[MAX];
                memset(server_resp, 0, MAX);
                char err_copy[MAX];
                int rec_status = SSL_read(ssl, server_resp, MAX);
                snprintf(err_copy, MAX, "%s", server_resp);
                if (rec_status <= 0) {
                        ssl_errorhandler(205, SSL_get_error(ssl, rec_status), ssl);
                }
                char* resp[5];
                char* tok = strtok(server_resp, " ");
                int i = 0;
                while (tok != NULL) {
                        resp[i] = tok;
                        i++;
                        tok = strtok(NULL, " ");
                }
                if (strcmp(resp[2], "BYE\n") == 0) {
                        *key = calloc(1, sizeof(char)*65);
                        snprintf(*key, 65, "%s", resp[1]);
                        active = 0;
                } else if (strcmp(resp[1], "STATUS") == 0) {
                        ans = solve(resp);
                        if (ans != INT_MAX) {
                                ssl_sendans(ans, ssl);
                        } else {
                                errorhandler(106, err_copy);
                        }
                } else {
                        errorhandler(107, err_copy);
                }
        }
        return;
}
