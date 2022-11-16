def call(String service, String command) {
	def scriptState = """@echo off
        for /f \"tokens=3*\" %%a in ('sc query ${service} ^| findstr STATE') do echo %%a"""
    
    def retStat = bat returnStatus: true, script: "${scriptState} > nul"
	
    if (retStat == 0) {
		def retOut = bat (returnStdout: true, script: scriptState).trim()
        int status = retOut.toInteger()

        if (command == 'query') {
            switch (status) {
                case 1:
                    return [0, 'Service status: STOPPED.']
                    break
                case 2:
                    return [0, 'Service status: START_PENDING.']
                    break
                case 3:
                    return [0, 'Service status: STOP_PENDING.']
                    break
                case 4:
                    return [0, 'Service status: RUNNING.']
                    break
                default:
                    return [1, 'Unsupported status or service doesn\'t exist.']
                    break
            }
        }
        else if (command == 'start') {
            switch (status) {
                case 1:
                    retStat = bat returnStatus: true, script: "@echo off | sc start ${service} > nul"

                    if (retStat == 0) {
                        sleep(10)
                        retOut = bat (returnStdout: true, script: scriptState).trim()
                        status = retOut.toInteger()

                        if (status == 4) {
                            return [0, 'Service started successfully.']
                        }
                    }

                    return [1, 'Service start failed.']
                    break
                case 2:
                    return [0, 'Service status: START_PENDING.']
                    break
                case 3:
                    return [0, 'Service status: STOP_PENDING.']
                    break
                case 4:
                    return [0, 'Service status: RUNNING.']
                    break
                default:
                    return [1, 'Unsupported status or service doesn\'t exist.']
                    break
            }
        }
        else if (command == 'stop') {
            switch (status) {
                case 1:
                    return [0, 'Service status: STOPPED.']
                    break
                case 2:
                    return [0, 'Service status: START_PENDING.']
                    break
                case 3:
                    return [0, 'Service status: STOP_PENDING.']
                    break
                case 4:
                    retStat = bat returnStatus: true, script: "@echo off | sc stop ${service} > nul"

                    if (retStat == 0) {
                        sleep(5)
                        retOut = bat (returnStdout: true, script: scriptState).trim()
                        status = retOut.toInteger()

                        if (status != 1) {
                            bat "@echo off | taskkill /f /fi \"services eq ${service}\" > nul"
                        }

                        return [0, 'Service stopped successfully.']
                    }

                    return [1, 'Service stop failed.']
                    break
                default:
                    return [1, 'Unsupported status or service doesn\'t exist.']
                    break
            }
        }
        else if (command == 'restart') {
            switch (status) {
                case 1:
                    echo 'Service status: STOPPED.'
                    retStat = bat returnStatus: true, script: "@echo off | sc start ${service} > nul"
                    if (retStat == 0) {
                        return [0, 'Service started successfully.']
                    }
                    else {
                        return [1, 'Service start failed.']
                    }
                    break
                case 2:
                    return [0, 'Service status: START_PENDING.']
                    break
                case 3:
                    return [0, 'Service status: STOP_PENDING.']
                    break
                case 4:
                    retStat = bat returnStatus: true, script: "@echo off | sc stop ${service} > nul"
                    if (retStat != 0) {
                        bat "@echo off | taskkill /f /fi \"services eq ${service}\" > nul"
                    }
                    sleep(5)
                    retStat = bat returnStatus: true, script: "@echo off | sc start ${service} > nul"
                    if (retStat == 0) {
                        return [0, 'Service restarted successfully.']
                    }
                    else {
                        return [1, 'Service restart failed.']
                    }
                    break
                default:
                    return [1, 'Unsupported status or service doesn\'t exist.']
            }
        }
        else {
            return [1, 'Unsupported command.']
        }
	}
    else {
        return [1, 'Service query returned unknown status.']
    }

    return [1, 'Fatal error.']
}