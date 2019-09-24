// A small cup-based guessing/betting game that can be
// run from minishell.

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <time.h>

typedef void (*betting_game_fp)(void);

#ifndef betting_game_h
#define betting_game_h

#define STARTING_CASH   100
#define NUM_CUPS        3

// Opening message explaining the rules
void opening() {
    
    printf("\n\nWelcome to this little betting game!\n"
           "The rules are simple:\n - You start out with"
           " $%d\n - You've got to guess which cup has the "
           " hash\n - Before each round, you specify how much "
           "you'd like to bet\n - If you win, we pay out 2:1\n"
           " - If you lose, we take your money :)\n - If you run"
           "out of money, it's game over!\n\n", STARTING_CASH);
    return;
}

// Draws the cups
void draw_cups() {
    int i = 0;
    for (; i < NUM_CUPS; i++) {
        printf("     ___  ");
    }
    printf("\n");
    i = 0;
    for (; i < NUM_CUPS; i++) {
        printf("    /   \\ ");
    }
    printf("\n");
    i = 0;
    for (; i < NUM_CUPS; i++) {
        printf("   /     \\");
    }
    printf("\n");
    i = 0;
    for (; i < NUM_CUPS; i++) {
        printf("      %d   ", i + 1);
    }
}

// Draws cups and generates the answer. Asks user for their guess
// and returns a 0 if they're wrong, a 1 if they're right.
int guess() {
    srand(time(0));
    int hash = (rand() % NUM_CUPS) + 1;

    draw_cups();
    
    printf("\n\n...Which cup contains the hash?\n");
    
    int player_guess = 0;
    while (1) {
        char input[20];
        fgets(input, 20, stdin);
        int inputnum = atoi(input);
        if (inputnum > NUM_CUPS || inputnum < 1) {
            printf("Please enter a value between 1 and %d!\n", NUM_CUPS);
        } else {
            player_guess = inputnum;
            break;
        }
    }
    
    if (player_guess == hash) {
        return 1;
    }
    
    return 0;
    
}

// Start the game loop. Get user bet. Check if user still has money
// after each round. If user quits or runs out of money, sends back
// to betting_game() to end the game.
void initialize_game() {
    int cash = STARTING_CASH;
    int round = 1;
    
    while (1) {
        printf("\n\n===== ROUND %d =====\n\n", round);
        printf("You've got $%d. How much would you like to bet?\n", cash);
        int pot = 0;
        while (1) {
            char input[20];
            fgets(input, 20, stdin);
            int inputnum = atoi(input);
            if (inputnum > cash || inputnum < 1) {
                printf("Please enter a value greater than 0 and less than "
                       "or equal to %d!\n", cash);
            } else {
                pot = inputnum;
                break;
            }
        }
        
        printf("Generating cup layout...\n");
        sleep(1);
        printf("Slipping hash up my sleeve... (jk)\n");
        sleep(1);
        
        int result = guess();
        
        if (result) {
            printf("Oops! You lost that one!\n");
            cash -= pot;
        } else {
            printf("Nice! Looks like we've got a winner!\n");
            cash += pot;
        }
        
        if (cash < 1) {
            printf("Sorry, you're all out of money!\n");
            break;
        } else {
            char input[20];
            printf("Want to continue? (c/q)\n");
            while (1) {
                fgets(input, 20, stdin);
                if (strlen(input) != 2 || ((strncmp(input, "c", 1) != 0
                                           && strncmp(input, "q", 1) != 0))) {
                    printf("Please enter 'c' (continue) or 'q' (quit).\n");
                } else {
                    break;
                }
            }
            if (strncmp(input, "q", 1) == 0) {
                break;
            } else {
                round++;
            }
        }
    }
    printf("You made it through %d rounds, and are walking away with $%d!\n", 
		round, cash - STARTING_CASH);
    return;
}

// Starts the game. Introduces user to rules and asks them if they
// want to play. Returns game information to user upon exit.
void betting_game() {
    opening();
    
    char input[20];
    
    printf("So what do you say, want to play? (y/n)\n");
    while (1) {
        fgets(input, 20, stdin);
        if (strlen(input) != 2 || (strncmp(input, "y", 1) != 0
                                   && strncmp(input, "n", 1) != 0)) {
            printf("Please enter 'y' or 'n'!\n");
        } else {
            break;
        }
    }
    
    if (strncmp(input, "y", 1) == 0) {
        initialize_game();
    }
    
    printf("Come back another time!\n\n\n");
    return;
}

#endif /* betting_game_h */
