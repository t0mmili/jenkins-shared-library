class FindConsecPortsParams {
    int consecPorts = 5
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

        if (testRes == params.consecPorts) return [ecode: 0, etext: currPort]
	}

    return [ecode: 1, etext: "Consecutive ports set not found in range ${params.startPort}-${params.endPort}."]
}