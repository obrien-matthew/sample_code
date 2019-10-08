# Web Crawler README

**Team**: Johanna Gunawan (gunawan.jo@husky.neu.edu), Matthew O'Brien (obrien.matt@husky.neu.edu)

**Language**: Java

**Submitted**: September 2019

**Class**: CS5700 - Foundations of Networking, [Project #2](https://course.ccs.neu.edu/cs5700f19/project2.html)

**Resources**:

- [Java Sockets Documentation](https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html)
- [Mozilla Developer HTTP Overview](https://developer.mozilla.org/en-US/docs/Web/HTTP/Overview)
- [HTTP Made Really Easy](https://www.jmarshall.com/easy/http/)

## Running the Program

To compile, in the project directory:

```bash
make
```

To run the program:

```bash
./webcrawler [username] [password]
```

Where 'username' and 'password' are login credentials for '[Fakebook](http://www.cs5700f16.ccs.neu.edu/accounts/login)'.

## Project Summary

We were tasked with building a webcrawler to search for 5 hidden keys (unique to each student ID/login username) located in the host server's domain. The webcrawler should not visit any other domains, and should never visit the same page twice. Additionally, we were responsible for building and parsing HTTP requests and responses, with the use of built-in libraries to do such tasks being prohibited.

Our webcrawler runs until it either visits every page in the domain or finds the 5 hidden keys. It handles 300, 400, and 500 level HTTP responses in different ways as to most appropriately address the issue, for example refreshing the connection and trying again with 500 level errors, marking the page as visited with 400 level errors and moving on, and visiting the redirect URL with 300 level errors. It will additionally automatically refresh the connection with the server if the server indicates it would like to close the socket.

### Additional Info

- **Approximate runtime on CCIS servers**: 2 mins 45 seconds
- **Approximate number of pages visited**: 4,500 unique pages in the domain

#### Matthew

- username: 001782036
- password: (*REMOVED*)
- secret keys:

```bash
58dc36a2b6f5ffdc1627db927b7733e3b904ec73bf862ebd6c67fd89f2c0fc27
05ef53a2ad51fc5a793820c32bc6935e71e2a66b6ad96578981dc2337f40bda1
73a3898113da3b006427362aa60517213ac6b8201f73908eb0d06d7c93ab393d
57e209bd793bf0bd38111cd3fd54f507833ecda619eafc7edd9d1e3bd1d8d2a9
ebf6252bc5727bf306aee0f802cc96a4566c1b6ed400bf064d48909689024c6b
```

#### Johanna

- username: (*REMOVED*)
- password: (*REMOVED*)
- secret keys:

```bash
f9a1cec808970cec4f4218c474f6cf95fa6c4334e43b6dd6fb0dd3bd781c5261
51261e2d3209a6691139cb1b888f1179217aef6793281ac56ce5e0a8f912aba8
9dfee7e52db8bdf9648a68dc5336ca4ec305f9fd387130d762f403d0384fd68b
f9444236808a999dbdd2f0c6c0e55f8d566650b4ff1df1dc245a274cc3902488
7635ecbf4416937cf25d8fb35c6585db1598eadd4447e3c9b01367a69685cfd8
```

### Copyright

Copyright 2019 Matthew O'Brien (obrien.matt@husky.neu.edu). All rights reserved.
