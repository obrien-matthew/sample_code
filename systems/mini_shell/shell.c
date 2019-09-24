// A custom bash shell by Matthew O'Brien
// March 2019

#include <signal.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/utsname.h>
#include <sys/wait.h>
#include <unistd.h>
#include "setup/builtin.h"

#define BUFSIZE	80

// a signal handler for Ctrl-C
void sigint_handler(int sig)
{
	((exit_fp)builtins[2])(1);
}

// prints out a greeting to the user
void greetings()
{
	// fetch user info
	struct utsname* userinfo = malloc(sizeof(struct utsname));
	if (uname(userinfo) != 0)
	{
		char *info_error = "\nError fetching user info.\n";
		write(2, info_error, strlen(info_error));
	}
	else
	{
		// greet user
		char* greetings = "\n\n======================================"
			"=========================================\n"
			"\n\e[04;92mWelcome to mini-shell!\e[m\n"
			"It is currently %s on %s.\n"
			"Current OS:      \e[00;93m%s\e[m\n"
			"Version:         \e[00;93m%s\e[m\n"
			"Machine Arch:    \e[00;93m%s\e[m\n\n";
		printf(greetings, __TIME__, __DATE__,
			userinfo->sysname, userinfo->release, 
			userinfo->machine);
	}
	free(userinfo);
	return;
}

// function to collect and process user CLI input
int get_input(char *inputs[][BUFSIZE/2], int* argnum)
{
	signal(SIGINT, sigint_handler);
	char input[BUFSIZE];
	int pipenum = 0;

	printf("\e[00;96mmini-shell > \e[m");
	fgets(input, BUFSIZE, stdin);

	// split by pipes; generates list of each expression
	// and stores it in argpipes
	char *pipesplit = strtok(input, "|");
	char *argpipes[BUFSIZE/4];
	while (pipesplit != NULL)
	{
		argpipes[pipenum] = calloc(1, strlen(pipesplit) + 1);
		strncpy(argpipes[pipenum], pipesplit, strlen(pipesplit));
		pipesplit = strtok(NULL, "|");
		pipenum++;
	}
	// split each pipe segment by newlines and spaces
	int i = 0;
	for (; i < pipenum; i++)
	{
		argnum[i] = 0;
		char *arg = strtok(argpipes[i], "\n ");
		while (arg != NULL)
		{
			inputs[i][argnum[i]] = calloc(1, strlen(arg) + 1);
			strncpy(inputs[i][argnum[i]], arg, strlen(arg));
			arg = strtok(NULL, "\n ");
			argnum[i]++;
		}
		free(argpipes[i]);
	}
	return pipenum;
}


// checks if a command exists and, if so, executes it.
// if it doesn't exist, displays error message.
void execute(int pipenum, int argnum[], char *inputs[][BUFSIZE/2])
{
	// check if each desired command exists
	// check in /bin/ and custom path a user may have entered
	int access_code = 1;
	int i = 0;
	for (; i < pipenum; i++)
	{
		char filepath_bin[128] = "/bin/";
		char filepath_usr_bin[128] = "/usr/bin/";
		strncat(filepath_bin, inputs[i][0], 122);
		strncat(filepath_usr_bin, inputs[i][0], 118);
		if (access(filepath_bin, F_OK) != 0 
		    && (access(inputs[i][0], F_OK) != 0) 
		    && access(filepath_usr_bin, F_OK) != 0)
		{
			access_code = 0;
		}
	}

	// if it exists, dispatch child and execute
	if (access_code)
	{
		if (fork() == 0)
		{
			int i = 0;
			for (; i < pipenum - 1; i++)
			{
				int pd[2];
				pipe(pd);

				if (fork() == 0)
				{
					dup2(pd[1], 1);
					execvp(inputs[i][0], inputs[i]);
					((exit_fp)builtins[2])(2);
				}
				dup2(pd[0], 0);
				close(pd[1]);
			}
			execvp(inputs[i][0], inputs[i]);
			((exit_fp)builtins[2])(2);
		}
		else
		{
			wait(NULL);
		}
	// otherwise, the command wasn't found.
	}
	else
	{
		char *cmd_error = "Command not "
			"found. Did you mean "
			"something else?\n";
		write(2, cmd_error, strlen(cmd_error));
	}
}

// when called, frees memory allocated for user input
void freemem(int pipenum, int argnum[], char *inputs[][BUFSIZE/2])
{
	// free malloc'd memory
	int i = 0;
	for (; i < pipenum; i++)
	{
		int j = 0;
		for (; j < argnum[i]; j++)
		{
			free(inputs[i][j]);
		}
	}
	return;
}

// the main shell loop
int main()
	{
	// set initial variables, install sigint handler
	signal(SIGINT, sigint_handler);
	char *inputs[BUFSIZE/4][BUFSIZE/2];

	// greet the user
	greetings();

	// initiate loop
	while (1)
	{
		// diplay prompt and await user input
		int argnum[BUFSIZE/2];
		int pipenum = get_input(inputs, argnum);
		int builtin_func = 0;
		
		// before testing inputs, check if they
		// actually entered something
		if (argnum[0] >= 1)
		{
			int arglen = strlen(inputs[0][0]);

			// check if command is builtin; if so execute
			int num_builtin = sizeof(funcs)/sizeof(funcs[0]);
			int func_num = -1;
			int i = 0;
			for (; i < num_builtin; i++)
			{
				if (strncmp(inputs[0][0], funcs[i], arglen) == 0 
					&& arglen >= strlen(funcs[i]))
				{
					func_num = i;
				}
			}

			if (func_num >= 0)
			{
				if (func_num == 2)
				{
					freemem(pipenum, argnum, inputs);
				}
				execute_builtin(func_num, argnum[0], inputs[0]);

			}
			else
			{
				// otherwise try to execute their command
				int i = 0;
				for (; i < pipenum; i++)
				{
					inputs[i][argnum[i]] = (char*) NULL;
				}
				execute(pipenum, argnum, inputs);
			}
		}
		freemem(pipenum, argnum, inputs);
	}
}
