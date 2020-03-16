package gt.com.celera;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Event;

public class App implements RequestHandler<S3Event, String> {
    LambdaLogger logger;

    public String handleRequest(S3Event s3Event, Context context)
    logger = context.getLogger();
    S3EventNotificationRecord record = s3Event.getRecords().get(0);
    String srcBucket = record.getS3().getBucket().getName();
    logger.log(String.format("* Source bucket is %s", srcBucket));
    
    String srcKey = record.getS3().getObject().getUrlDecodedKey();
    logger.log(String.format("* Source object is %s", srcBucket));

}
