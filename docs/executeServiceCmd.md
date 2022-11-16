`executeServiceCmd(String service, String command)`

Executes command against Windows service.

Method takes two arguments:
 - **service**

    Service name.
 - **command**

    Currently supported commands are: `query`, `start`, `stop`, `restart`.

Method returns a list:
 - **exit code**

    0 - success, 1 - error.
 - **exit text**

    Message appropriate to executed action.