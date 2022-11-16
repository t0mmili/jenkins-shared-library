`findConsecPorts(Map args)`

Checks whether consecutive ports are open on the specified destination host.

Method takes five arguments:
 - **host**

   Destination host ip address.
 - **consecPorts**

   Number of consecutive ports to find. Defaults to 5.
 - **startPort**

   Start port for the search range. Defaults to 49152.
 - **endPort**

   End port for the search range. Defaults to 65535.
 - **timeout**

   Timeout value in milliseconds of connecting socket to the server (destination host).  
   A zero timeout is inferred as an infinite timeout. Defaults to 250 ms.

Method returns a map with two keys:
 - **ecode**

   0 - success, 1 - error
 - **etext**

   In case of success returns starting port as integer.  
   In case of error returns error message.