# mcpanalyzer
In order to run it...

`mvn spring-boot:run`

The base context (/) with a YYYYMMDD parameter will process the selected file, given that it is found on the server.
I've taken the liberty of adding an optional 'replace' POST parameter that, when set to true, allows to reprocess a file just in case someone feels bad about having uploaded json objects with formatting errors and they wanted to fix them ;)

For the (/metrics) endpoint, if you call it with no parameter, it will show the metrics for the last uploaded file. However, I've also allowed an optional YYYYMMDD path variable, much like when processing, that will allow you to recover that specific day's mertrics if it has been processed.

I could've done A LOT more test-wise but I've been out of town all weekend and time was of the essence.  Not an excuse, but just putting it out there.
Sorry about that.
