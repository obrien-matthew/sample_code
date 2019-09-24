#include "builtin_functions/help.h"
#include "builtin_functions/cd.h"
#include "builtin_functions/exit.h"
#include "builtin_functions/betting_game.h"

#ifndef BUILTIN_H_
#define BUILTIN_H_

typedef void (*fp)(void);

// declaration of builtin function arrays
// to install a new builtin function, simply import it above,
// add it to the two arrays below, and add an appropriate switch
// statement to execute_builtin().
static char *funcs[] = { "help", "cd", "exit", "game" };
static fp builtins[4] = { (fp)help, (fp)cd, (fp)exit_shell, (fp)betting_game };

// executes a given builtin function
void execute_builtin(int func_num, int argnum, char *inputs[]) {
    switch (func_num) {
        case 0 : {
            ((help_fp)builtins[0])();
            break;
        }
        case 1 : {
            ((cd_fp)builtins[1])(inputs, argnum);
            break;
        }
        case 2 : {
            ((exit_fp)builtins[2])(0);
            break;
        }
        case 3 : {
            ((betting_game_fp)builtins[3])();
            break;
        }
        default : {
            char *builtin_err = "\nError processing builtin function.\n"
                                "Ensure function is properly installed "
                                "in setup/builtin.h.\n";
            write(2, builtin_err, strlen(builtin_err));
    
        }
    }
}

#endif //BUILTIN_H_
