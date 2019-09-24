#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <errno.h>

#define MAX 128

extern int errno;

int main(void) {
        // create server/socket
        int server_socket;
        server_socket = socket(AF_INET, SOCK_STREAM, 0);
        struct sockaddr_in server_address;
        server_address.sin_family = AF_INET;
        server_address.sin_port = htons(34568);
        server_address.sin_addr.s_addr = htons(INADDR_ANY);
        // bind server to socket
        bind(server_socket, (struct sockaddr*) &server_address, 
                        sizeof(server_address));
        printf("Server bound to socket.\n");

        // enter listening loop
        int listening = 1;
        while (listening) {
                // listen for connections
                printf("Listening for connections...\n");
                listen(server_socket, 1);
                // accept incoming connection
                int client_socket;
                client_socket = accept(server_socket, NULL, NULL);
                // get client username
                char client_username[MAX+1];
                int bytes = recv(client_socket, client_username, MAX, 0);
                client_username[bytes-2] = '\0';
                printf("User connected: %s\n", client_username);
                // send welcome message
                char server_message[52];
                sprintf(server_message, "You have reached the server.\n"
                                        "Socket connected: %d\n",
                                        client_socket);
                send(client_socket, server_message, sizeof(server_message), 0);
                
                // enter interaction loop
                int connected = 1;
                while (connected) {
                        char client_cmd[MAX+1];
                        int bytes = recv(client_socket, client_cmd, MAX, 0);
                        if (bytes == -1) {
                                printf("Transmission Error: %s\n", strerror(errno));
                        } else {
                                client_cmd[bytes] = '\0';
                                printf("Client command: %s\n", client_cmd);
                                if (strlen(client_cmd) == 5 && strncmp(client_cmd, "exit", 4) == 0) {
                                        connected = 0;
                                } else if (strlen(client_cmd) == 5 && strncmp(client_cmd, "kill", 4) == 0) {
                                        connected = 0;
                                        listening = 0;
                                } else {
                                        system(client_cmd);
                                }
                        }
                }
                printf("Client (%s) disconnected.\n", client_username);
        }

        printf("Closing server socket...\n");
        close(server_socket);
        printf("Socket closed. Terminating.\n");
}

