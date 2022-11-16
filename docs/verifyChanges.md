`verifyChanges(Map args)`

Checks the git repository for changes made to a specific branch, in the specified location (folder), within the specified time period.

Method takes eight arguments:
 - **repo**

    Repo url.
 - **branch**

    Repo branch.
 - **dir**

    Repo folder(s) that should contain changed files.
 - **days**

    Days for verified commit history time frame.
    Defaults to 0.
 - **hours**

    Hours for verified commit history time frame.
    Defaults to 0.
 - **minutes**

    Minutes for verified commit history time frame.
    Defaults to 0.
 - **author**

    Default commit author email, in case commit doesn't have any.
 - **debug**

    Display debug messages.
    Defaults to no debug.

Method returns a map with two or four keys:
 - **ecode**

    0 - success (without changes present), 1 - error, 2 - success (with changes present)
 - **etext**

    When ecode in (0, 1) returns appropriate message.
 - **hash**

    When ecode in (2) returns commit hash.
 - **author**

    When ecode in (2) returns commit author.
 - **changes**

    When ecode in (2) returns changes list.