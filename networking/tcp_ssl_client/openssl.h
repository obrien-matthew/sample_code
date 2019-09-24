#include <openssl/bio.h>
#include <openssl/ssl.h>
#include <openssl/err.h>

#ifndef MAX
#define MAX 256
#endif //MAX

#ifndef OPEN_SSL_H_
#define OPEN_SSL_H_

char* get_ssl_errmsg(int sslcode);

void ssl_errorhandler(int code, int sslcode, SSL* ssl);

SSL* ssl_initialize(int client_sock);

void ssl_sendhello(SSL* ssl, char* nuid);

void ssl_sendans(int ans, SSL* ssl);

void ssl_interact(SSL* ssl, char** key);

int solve(char* resp[]);

void errorhandler(int code, const char* supplement);

#endif //OPEN_SSL_H_
