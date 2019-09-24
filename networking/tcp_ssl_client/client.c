/*

*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <netdb.h>
#include <errno.h>
#include <limits.h>
#include "openssl.h"

// Define max message length
#define MAX 256

extern int errno;

/* 
 * An error handler for the program. Supplies the user with an error 
 * message then exits with a failure code.
 */
void errorhandler(int code, const char* supplement) {
        switch (code) {
                case 99 :
                        fprintf(stderr, "Unable to extract key. 'key' pointer "
                                "was still NULL after interaction loop.\n");
                                break;
                case 100 :
                        fprintf(stderr, "Usage: ./client <-p portnum> <-s> "
                                "[hostname] [nuid]\nIf portnum is not "
                                "specified default is 27995\nIf <-s> "
                                "flag is entered, default port is 27996\n");
                        break;
                case 101 : {
                        char msg[512];
                        snprintf(msg, 512, "\nError: unable to retrieve server "
                                "address info: %s", supplement);
                        perror(msg);
                        break; }
                case 102 :
                        fprintf(stderr, "Error: all connection attempts "
                                "to host failed\n");
                        break;
                case 103 :
                        fprintf(stderr, "Error: server closed connection\n");
                        break;
                case 104 :
                        fprintf(stderr, "Error: incomplete data transmission "
                                "from server\n");
                        break;
                case 105 :
                        fprintf(stderr, "Error: arithmetic expression "
                                "invalid\n");
                        break;
                case 106 : {
                        char msg[512];
                        snprintf(msg, 512, "Error: calculated answer is null. "
                                "Message recieved: %s", supplement);
                        perror(msg);
                        break; }
                case 107 : {
                        char msg[512];
                        snprintf(msg, 512, "Error: server message invalid or not "
                                "recieved. Message recieved: %s", supplement);
                        perror(msg);
                        break; }
                default :
                        perror("Error: unknown error");
                        break;
        }
        exit(EXIT_FAILURE);
}



                                
                                
/*
 * When supplied with a host (either decimal or alphabetic) and port,
 * looks up host information using getaddrinfo() and returns a pointer to 
 * the resulting address list.
 */
struct addrinfo* getserverinfo(char* host, char* port) {
        struct addrinfo addr_filters, *addr_list;
        memset(&addr_filters, 0, sizeof(struct addrinfo));
        addr_filters.ai_socktype = SOCK_STREAM;
        addr_filters.ai_family = AF_UNSPEC;
        int addr_status = getaddrinfo(host, port, &addr_filters, &addr_list);
        if (addr_status != 0) {
                errorhandler(101, gai_strerror(addr_status));
        }
        return addr_list;
}

/* 
 * Builds the client socket, connects to the server, and returns the socket. 
 * Takes a list of addrinfo objects to which it will attempt to connect.
 */ 
int connecttohost(struct addrinfo* list) {
        int client_sock = -1;
        struct addrinfo* p;
        for (p = list; p != NULL; p = p->ai_next) {
                if ((client_sock 
                        = socket(p->ai_family, p->ai_socktype, 0)) != -1 
                        && connect(client_sock, p->ai_addr, p->ai_addrlen) 
                        != -1) {
                        break;
                }
        }

        if (p == NULL) {
                errorhandler(102, NULL);
        }

        return client_sock;
}

/*
 * Takes an NUID as a string and the client socket. Formats the HELLO 
 * message and sends it to the server.
 */
void sendhello(char* nuid, int client_sock) {
        char hello_msg[MAX];
        snprintf(hello_msg, MAX, "cs5700fall2019 HELLO %s\n", nuid);
        send(client_sock, hello_msg, strlen(hello_msg), 0);
        return;
}

/*
 * Takes an integer, the answer to a math problem, and the client 
 * socket, and builds the properly formatted string before sending 
 * it to the server.
 */
void sendanswer(int ans, int client_sock) {
        char client_resp[MAX];
        snprintf(client_resp, MAX, "cs5700fall2019 %d\n", ans);
        send(client_sock, client_resp, strlen(client_resp), 0);
        return;
}

/*
 * Called by the interact() function below. Passed a list 
 * of terms sent from server, selecting the relevent terms for 
 * the mathematical problem and returning the answer as an int.
 */
int solve(char* resp[]) {
        int ans = INT_MAX;
        int n1 = atoi(resp[2]);
        int n2 = atoi(resp[4]);
        int op = (int)resp[3][0];
        switch (op) {
                case ((int)'+') :
                        ans = n1 + n2;
                        break;
                case ((int)'-') :
                        ans = n1 - n2;
                        break;
                case ((int)'/') :
                        ans = (int) (n1 / n2);
                        break;
                case ((int)'*') :
                        ans = n1 * n2;
                        break;
                default :
                        errorhandler(105, NULL);
        }
        return ans;
}

/* 
 * The main interaction loop between client and server. Takes the client 
 * socket and a malloc'd region of memory to store the 64-bit key. Upon 
 * successful completion, closes the socket and returns. If it encounters 
 * an error, it closes the socket before passing the error code to the 
 * error handler funciton.
 */
void interact(int client_sock, char** key) {
        int active = 1;
        while (active) {
                char server_resp[MAX];
                memset(server_resp, 0, MAX);
                char err_copy[MAX];
                int ans = INT_MAX;
                int rec_status = recv(client_sock, &server_resp, MAX, 0);
                snprintf(err_copy, MAX, "%s", server_resp);
                if (rec_status == 0) {
                        close(client_sock);
                        errorhandler(103, NULL);
                } else if (rec_status == -1) {
                        close(client_sock);
                        errorhandler(104, NULL);
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
                                sendanswer(ans, client_sock);
                        } else {
                                close(client_sock);
                                errorhandler(106, err_copy);
                        }
                } else {
                        close(client_sock);
                        errorhandler(107, err_copy);
                }
        }
        close(client_sock);
        return;
}

/*
 * The main program loop. Calls multiple functions in order to execute.
 * When run from the command line, takes a hostname and NUID as arguments,
 * and optionaly a portnumber/SSL flag. Before closing after a successful run, 
 * prints extracted key to stdout.
 */
int main(int argc, char* argv[]) {
        // handle arguments, assign variables
        errno = 0;
        char* port = "27995";
        int is_ssl = 0;
        char* host = NULL;
        char* nuid = NULL;
        int err = 0;
        if (argc == 3) {
                host = argv[1];
                nuid = argv[2];
        } else if (argc == 4 && strcmp(argv[1], "-s") == 0) {
                port = "27996";
                is_ssl = 1;
                host = argv[2];
                nuid = argv[3];
        } else if (argc == 5 && strcmp(argv[1], "-p") == 0) {
                port = argv[2];
                host = argv[3];
                nuid = argv[4];
        } else if (argc == 6 && strcmp(argv[1], "-p") == 0
                    && strcmp(argv[3], "-s") == 0) {
                port = argv[2];
                is_ssl = 1;
                host = argv[4];
                nuid = argv[5];
        } else {
                errorhandler(100, NULL);
        }

        // get address info from hostname
        struct addrinfo *addr_list = getserverinfo(host, port);

        // try connecting to result set of addresses, free allocated memory
        int client_sock = connecttohost(addr_list);
        freeaddrinfo(addr_list);

        char *key = NULL;
        if (is_ssl) {
                // initialize and get SSL object
                SSL* ssl = ssl_initialize(client_sock);

                int s = 1;
                if ((s = SSL_do_handshake(ssl)) < 1) {
                        ssl_errorhandler(203, SSL_get_error(ssl, s), ssl);
                }
                // send hello message to SSL server
                ssl_sendhello(ssl, nuid);

                // enter SSL interaction loop and extract key
                ssl_interact(ssl, &key);

                // Shut down SSL connection and free allocated memory
                // Note: In this program, SSL_free() will:
                //      - close the socket stored in the BIO
                //      - free the alloc'd BIO object memory
                //      - free the alloc'd CTX object memory
                //      - free the alloc'd SSL object memory
                SSL_shutdown(ssl);
                SSL_free(ssl);
        } else {
                // send hello message to server
                sendhello(nuid, client_sock);
                
                // enter interaction loop and extract key
                interact(client_sock, &key);
        }


        // print key and exit succesfully
        if (key != NULL) {
                printf("%s\n", key);
                free(key);
                exit(EXIT_SUCCESS);
        }
        errorhandler(99, NULL);
}

