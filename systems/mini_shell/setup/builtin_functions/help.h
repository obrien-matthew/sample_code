typedef void (*help_fp)(void);

// display some guidance
void help() {
    char *str = "\n   Help Section:\n - call 'cd [path]' to change the current "
    "directory.\n - call 'exit' to terminate the current shell\n"
    " - call 'help' to see this window again :)\n - call any "
    "standard bash command to execute\n   (e.g., 'ls', 'pwd',"
    " 'cat [file]', etc.)\n - lastly, call 'game' to play a custom-made"
    " game, just for you :)\n\n\n";
    write(1, str, strlen(str));
    return;
}
