class RobocopyParams {
    String sdir = ''
    String ddir = ''
    String files = ''
    String options = ''
}

def call(Map args) {
    RobocopyParams params = args ?: [:]

    def retstat = bat returnStatus: true, script: "robocopy ${params.sdir} ${params.ddir} ${params.files} ${params.options}"

    if (retstat < 8) println "Robocopy succeeded with exit code ${retstat}. Visit https://ss64.com/nt/robocopy-exit.html for more details."
    else error "Robocopy failed with exit code ${retstat}. Visit https://ss64.com/nt/robocopy-exit.html for more details."
}