class VerifyChangesParams {
    boolean debug = false
    int days = 0
    int hours = 0
    int min = 0
    String author = ''
    String branch = ''
    String dir = ''
    String repo = ''
}

def call(Map args) {
    def commitAuthor = ''
    def commitDate = ''
    def commitHash = ''

    VerifyChangesParams params = args ?: [:]

    def minDate = calcDate.min(days: params.days, hours: params.hours, minutes: params.min)

    def scriptCommit = "@echo off | git log -1 --date=format:\"%%Y-%%m-%%d %%H:%%M:%%S\" --format=\"%%cd %%H %%ae\" origin/${params.branch} -- ${params.dir}"

    bat "@echo off | git init > nul"
    bat "@echo off | git remote add origin ${params.repo}"
    bat "@echo off | git fetch --depth 10 --quiet origin ${params.branch} | exit 0"

    def retStat = bat returnStatus: true, script: "${scriptCommit} > nul"

    if (retStat == 0) {
        def retOut = bat(returnStdout: true, script: scriptCommit).trim()

        def outSplit = retOut.split()
        commitDate = Date.parse('yyyy-MM-dd HH:mm:ss', "${outSplit[0]} ${outSplit[1]}")
        commitHash = outSplit[2]
        commitAuthor = outSplit.size() == 4 ? outSplit[3] : params.author

        if (params.debug) {
            echo "[DEBUG] [verifyChanges] commitDate: ${commitDate}"
            echo "[DEBUG] [verifyChanges] commitHash: ${commitHash}"
            echo "[DEBUG] [verifyChanges] commitAuthor: ${commitAuthor}"
        }

        if (commitDate >= minDate) {
            retOut = bat(returnStdout: true, script: "@echo off | git show --name-only --pretty=\"\" origin/${params.branch} ${commitHash} -- ${params.dir}").trim()

            if (retOut.size() > 0) {
                return [ecode: 2, hash: commitHash, author: commitAuthor, changes: retOut]
            }
            else {
                return [ecode: 0, etext: 'Changes not present.']
            }
        }
        else {
            return [ecode: 0, etext: 'Changes not present.']
        }
    }
    else {
        return [ecode: 1, etext: 'Git log returned unknown status.']
    }

    return [ecode: 1, etext: 'Fatal error.']
}