# TCP / SSL Client

**Author**: Matthew O'Brien (obrien.matt@huksy.neu.edu)

**Written**: September 2019

**Class**: CS5700 - Foundations of Networking

**Resources**:
- https://linux.die.net/man/
- https://www.openssl.org/
- https://github.com/openssl/openssl


## Running the Program

To compile code, while in project directory:
```bash
$ make
```
To run program:
```bash
$ ./client <-p port> <-s> [host] [NUID]
```
Where <-p port> specifies a port number, <-s> specifies an SSL connection, [host] is the server location (see below), and [NUID] is a student's ID number. Specifying -p and -s are optional, but host and NUID are necessary for the program to run.

**NOTE ON COMPATIBILITY** : Designed to run on Northeastern University servers; issues may arise on other machines.

## Project Summary
This project required students to engineer a program which would create a TCP socket, connect to a remote server, solve a variable number of math problems sent by the server, and when eventually provided with a 64-bit secret key, output that key to stdout and close the socket.

This code also supports SSL connections with the -s flag as a command line argument.

- **Usage**: ./client <-p portnum> <-s> [host] [NUID]
- **Output**: A 64-bit secret key unique to each NUID


**Additional Usage information**:
- host: cs5700f19.ccs.neu.edu
- nuid: 001782036
- key: d36cb1ea6ce346e76ff148caa94ab9c4847fa564a21f6bb0615a2debf7a46394
- ssl_key: 78aa960bea94811f8ecf55f2f7cbb21f8ef704c74ce1b078953837c5af582dd9


### Copyright

Copyright 2019 Matthew O'Brien (obrien.matt@husky.neu.edu). All rights reserved.
