class FindConsecPortsParams {
    int consecPorts = 3
    int endPort = 65535
    int startPort = 49152
    int timeout = 250
    String host = ''
}

def call(Map args) {
    FindConsecPortsParams params = args ?: [:]

    int currPort = params.startPort
    int testRes = 0

    while (testRes < params.consecPorts && currPort <= params.endPort - params.consecPorts + 1) {
        testRes = 0

        for (y = currPort; y < currPort + params.consecPorts; y++) {
			try {
                Socket socket = new Socket()
			    socket.connect(new InetSocketAddress(params.host, y), params.timeout)
                socket.close()
                currPort = y + 1
                break
            }
            catch (IOException e) {
                testRes++
            }
        }

        if (testRes == params.consecPorts) return [exitcode: 0, message: currPort]
	}

    return [exitcode: 1, message: "Consecutive ports set not found in range of ${params.startPort}-${params.endPort}."]
}