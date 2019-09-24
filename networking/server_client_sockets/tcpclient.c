#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <unistd.h>
#include <netinet/in.h>
#include <errno.h>

#define MAX 128

extern int errno;

int main(void) {
        // build client socket
        int client_socket;
        if ((client_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
                printf("\n\e[00;91mSocket creation error\e[m\n");
        }

        // build server socket to be targeted
        struct sockaddr_in server_address;
        server_address.sin_family = AF_INET;
        server_address.sin_port = htons(34568);
        server_address.sin_addr.s_addr = htons(INADDR_ANY);


        // attempt to connect to server
        int connection_status = connect(client_socket, 
                                (struct sockaddr*) &server_address, 
                                sizeof(server_address));
        if (connection_status != 0) {
                printf("\n\e[00;91mError %d: Connection Failed\e[m\n", 
                                connection_status);
                printf("%s\n", strerror(errno));
                close(client_socket);
                exit(0);
        }
        // get username and send to server before confirming connection
        char username[MAX-1];
        printf("Username: ");
        fgets(username, MAX-1, stdin);
        send(client_socket, username, strlen(username)+1, 0);
        printf("\n\e[00;92mConnection Successful\e[m\n");

        // get response from server
        char server_response[MAX];
        recv(client_socket, &server_response, sizeof(server_response), 0);
        printf("\e[04;01;96mThe server sent the data:\e[m\n\n%s\n", 
                        server_response);

        // Enter interaction loop
        int active = 1;
        while (active) {
                // get user command
                char cmd[MAX-1];
                printf("\e[00;96mclient > \e[m");
                fgets(cmd, MAX-1, stdin);

                // package and send to server
                cmd[strlen(cmd)] = '\0';
                if (strlen(cmd) > 0) {
                        send(client_socket, cmd, strlen(cmd), 0);
                        if (strlen(cmd) == 5 && (strncmp(cmd, "exit", 4) == 0 || strncmp(cmd, "kill", 4) == 0)) {
                                active = 0;
                        }
                }
        }
        printf("Disconnecting from server...\n");
        close(client_socket);
        printf("Connection terminated.\n");
}

