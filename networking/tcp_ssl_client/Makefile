CC=clang
EXEC=client
HEAD=openssl.h
LIBS=-lssl -lcrypto
OBJ=client.o openssl.o

all: $(EXEC)

$(EXEC): $(OBJ)
	$(CC) $(OBJ) -o $(EXEC) $(LIBS)

%.o: %.c $(HEAD)
	$(CC) -c $<

clean:
	rm $(OBJ) $(EXEC)
