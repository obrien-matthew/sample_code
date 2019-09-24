CC=clang

all: tcpserver tcpclient

%: %.c
	$(CC) $< -o $@

clean:
	rm tcpserver tcpclient
