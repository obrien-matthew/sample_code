typedef void (*cd_fp)(char **inputs, int argnum);


// function to change directories
void cd(char** inputs, int argnum) {
    // check there are only 2 arguments total (including
    // "cd" command)
    if (argnum > 2) {
        char *cd_arg_error =  "cd: too many arguments.\n";
        write(2, cd_arg_error, strlen(cd_arg_error));
        
        // run cd
    } else if (strlen(inputs[1]) > 0) {
        int changedir = chdir(inputs[1]);
        
        // if failed, print error message
        if (changedir != 0) {
            char *cd_error = "Could not change directory. "
            "Ensure directory exists and "
            "is accessible.\n";
            write(2, cd_error, strlen(cd_error));
        }
    }
}