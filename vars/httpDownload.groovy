class HttpDownloadParams {
    boolean ignoressl = true
    int interval = 20
    int probes = 5
    int timeout = 20
    String mime = 'APPLICATION_OCTETSTREAM'
    String outfile = ''
    String url = ''
}

def call(Map args) {
    def response = ''
    int probesCount = 1

    HttpDownloadParams params = args ?: [:]

    while (probesCount <= params.probes) {
        response = httpRequest responseHandle: 'NONE', timeout: params.timeout, url: params.url, validResponseCodes: '100:599'
        
        if (response.status != 200) {
            if (probesCount != params.probes) sleep(params.interval)
            probesCount++
        }
        else {
            httpRequest contentType: params.mime, outputFile: params.outfile, responseHandle: 'NONE', timeout: params.timeout, url: params.url , validResponseCodes: '100:599'
            println "Successfuly downloaded file ${params.outfile}."
            return
        }
    }

    error "Failed to download file ${params.url}."
}