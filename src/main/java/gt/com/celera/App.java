package gt.com.celera;

import java.io.InputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;

public class App implements RequestHandler<S3Event, String> {
    LambdaLogger logger;

    public String handleRequest(S3Event s3Event, Context context) {
        logger = context.getLogger();
        S3EventNotificationRecord record = s3Event.getRecords().get(0);
        String srcBucket = record.getS3().getBucket().getName();
        logger.log(String.format("* Source bucket is %s", srcBucket));

        String srcKey = record.getS3().getObject().getUrlDecodedKey();
        logger.log(String.format("* Source object is %s", srcKey));

        AmazonS3 s3Client = AmazonS3ClientBuilder.defaultClient();
        S3Object s3Object = s3Client.getObject(new GetObjectRequest(srcBucket, srcKey));
        
        InputStream objectData = s3Object.getObjectContent();
        logger.log("* uploading to dest bucket");
        ObjectMetadata meta = new ObjectMetadata();
        meta.addUserMetadata("test", "value");
        String destBucket = #dest-bucket-name;
        s3Client.putObject(destBucket, srcKey, objectData,meta);
        
        return "wenas";
    }

}
