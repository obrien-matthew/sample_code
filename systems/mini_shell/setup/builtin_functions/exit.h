typedef void (*exit_fp)(int code);


// exits the shell and displays appropriate error messages (if applicable)
void exit_shell(int code) {
    if (code == 1) {
        char * error_one = "\n\n\e[00;91mUser sent abort signal.\n"
        "Terminating shell...\e[m\n";
        write(2, error_one, strlen(error_one));
        
    } else if (code == 2) {
        char *error_two = "\n\n\e[00;91mError creating child process.\n";
        write(2, error_two, strlen(error_two));
    }
    
    printf("\n\e[00;91mmini-shell terminated.\e[m\n\n"
           "========================================="
           "======================================\n\n");
    sleep(1);
    exit(code);
}
