`httpDownload(Map args)`

Downloads file from url.

Method takes eight arguments:
 - **ignoressl**

    If set to true a request with https will ignore invalid or expired certificate.
    Defaults to true.
 - **interval**

    Interval between probes (in seconds).
    Defaults to 20.
 - **probes**

    How many request attempts should be made.
    Defaults to 5.
 - **timeout**

    Single request timeout (in seconds).
    Defaults to 20.
 - **mime**

    Request MIME type.
    Defaults to APPLICATION_OCTETSTREAM.
 - **outfile**

    Downloaded file name. Target path can be included.
    
 - **url**

    File url to download.

Method returns no value. If download fail, sends error signal and abort job execution.