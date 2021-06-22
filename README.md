# log-processor


  # Executing the code
```bash
   mvn spring-boot:run -Dspring-boot.run.arguments="<PATH_OF_LOGFILE>"
```
  # Expected logfile

```
{"id":"scsmbstgra", "state":"STARTED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495212}
{"id":"scsmbstgrb", "state":"STARTED", "timestamp":1491377495213}
{"id":"scsmbstgrc", "state":"FINISHED", "timestamp":1491377495218}
{"id":"scsmbstgra", "state":"FINISHED", "type":"APPLICATION_LOG", "host":"12345", "timestamp":1491377495217}
{"id":"scsmbstgrc", "state":"STARTED", "timestamp":1491377495210}
{"id":"scsmbstgrb", "state":"FINISHED", "timestamp":1491377495216}
```

  # Future Improvements
    1. Currently newline is taken as delimeter that can be improved depending on requirement.
    2. Temprory data stored in cache can be persistant to avoid data loss during outage.
    3. For the efficient processing Apache Spark can be used for parallel processing.