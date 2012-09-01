package test.aliyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.AbortMultipartUploadRequest;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.openservices.oss.model.CompleteMultipartUploadResult;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.openservices.oss.model.InitiateMultipartUploadResult;
import com.aliyun.openservices.oss.model.ListMultipartUploadsRequest;
import com.aliyun.openservices.oss.model.MultipartUpload;
import com.aliyun.openservices.oss.model.MultipartUploadListing;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.PartETag;
import com.aliyun.openservices.oss.model.UploadPartRequest;
import com.aliyun.openservices.oss.model.UploadPartResult;

/**
 * 运行multipart示例需要做如下几件事：
 * 1、获取accessId\accessKey，修改示例中对应accessId\accessKey
 * 2、修改需要上传的文件uploadBigFilePath，文件必须存在, 修改下载文件的路径downloadBigFilePath
 * 3、指定文件的显示名称\指定bucketName
 * **/
public class OSSMultipartSample {

    static OSSClient client ;
    
  //TODO 输入你的accessId和accessKey
    static String accessId = "";
    static String accessKey = "";
    static String ossEndpoint = "http://storage.aliyun.com/";
    
    //TODO 输入你的bucket名称 
    static String bucketName = accessId;
    
    //TODO 输入你的上传、下载文件路径
    static String bigFilename = "测试上传大文件.rar";
    static String uploadBigFilePath = "d:/temp/oss/测试上传大文件.rar";
    static String downloadBigFilePath = "d:/temp/oss/测试下载大文件.rar";
    

    
    /**
     * @param args
     */
    public static void main(String[] args)throws Exception {
        

        client = new OSSClient(ossEndpoint, accessId, accessKey);
        
        /**
         * bucket操作
         * 
         * **/
        doBucketOperation();
        
        /**
         *  使用multipart,上传文件
         *  把文件分块上传
         * */
        handlerBigFile();
        
    }
    
    public static void initDeleteBucket(String bucketName)throws Exception{
        
        //删除bucket之前必须保证bucket为空，所以先必须先删除object和multipart
        // 判断bucket是否存在
        if (client.isBucketExist(bucketName)) {
            // 如果存在，查看bucket是否为空
            ObjectListing ObjectListing = client.listObjects(bucketName);
            List<OSSObjectSummary> listDeletes = ObjectListing
                    .getObjectSummaries();
            for (int i = 0; i < listDeletes.size(); i++) {
                String objectName = listDeletes.get(i).getKey();
                // 如果不为空，先删除bucket下的文件
                client.deleteObject(bucketName, objectName);
            }
            
            //查找有多少个multipart
            ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(bucketName);
            MultipartUploadListing uploadListing = client.listMultipartUploads(listMultipartUploadsRequest);
            
            //删除所有的multi part
            for (Iterator<MultipartUpload> it =  uploadListing.getMultipartUploads().iterator(); it.hasNext();){
                
                MultipartUpload part = it.next();
                String key = part.getKey();
                
                AbortMultipartUploadRequest abortMultipartUploadRequest = new AbortMultipartUploadRequest(bucketName, key, part.getUploadId());
                client.abortMultipartUpload(abortMultipartUploadRequest);
            }
            
        }
        
    }
    
    public static void doBucketOperation()throws Exception{
        
      //删除前的准备工作
        initDeleteBucket(bucketName);
        //删除bucket
        if (client.isBucketExist(bucketName)){
            client.deleteBucket(bucketName);
        }
        
//        Assert.assertTrue(!client.isBucketExist(bucketName));
        
        //创建bucket
        client.createBucket(bucketName);
        
        //设置bucket的访问权限，public-read-write权限
        client.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
        
//        AccessControlList accessControlList = client.getBucketAcl(bucketName);
        // 获取bucket的访问权限
//        Assert.assertEquals(Permission.FullControl, ((Grant)(accessControlList.getGrants().toArray()[0])).getPermission());
       
    }
    
    public static void handlerBigFile()throws Exception{
        
        //文件分块数
        int partNum = 3;
        //上传线程数
        int threadNum = 2;
       
        //把大文件分块，并且上传大文件
        createMultipartFromLargeFile(bucketName, bigFilename, uploadBigFilePath, partNum, threadNum, client);
      
        File downBigFile = new File(downloadBigFilePath);
        GetObjectRequest getBigObjectRequest = new GetObjectRequest(bucketName, bigFilename);
        client.getObject(getBigObjectRequest, downBigFile);
        
    }
    
    /**
     *上传大文件
     * */
    public static CompleteMultipartUploadResult createMultipartFromLargeFile(String bucketName,
            String key, String fileName, int partNum, int threadNum, OSSClient client)throws Exception{
        if (partNum > 1000 || partNum < 1) {
            throw new IllegalArgumentException(
                    "multipart 数量必须在1-1000之间:" + bucketName);
        }
        if (threadNum > 100 || threadNum < 1) {
            throw new IllegalArgumentException(
                    "上传文件的线程数要在1-100之间:" + threadNum);
        }
        
        InitiateMultipartUploadRequest initUploadRequest = new InitiateMultipartUploadRequest(bucketName, key);
         InitiateMultipartUploadResult initResult = client.initiateMultipartUpload(initUploadRequest);
         String uploadId = initResult.getUploadId();
        
        ExecutorService service = Executors.newFixedThreadPool(threadNum);
        
        List<PartETag> eTags = Collections.synchronizedList(new ArrayList<PartETag>());
        
        File file = new File(fileName);
        long fileSize = file.length();
        long partSize = (fileSize + partNum) / partNum;
        
        if (partSize < 5 * 1024 * 1024 || partSize > 5 * 1024 * 1024 * 1024){
            throw new IllegalArgumentException("每个multipart的分块大小必须在5M-5G之间，请检查文件大小和上传线程数");
        }
        
        for (int i = 0; i < partNum; i++) {
            long size = partSize < fileSize - partSize * i ? partSize
                    : fileSize - partSize * i;
            service.execute(new UploadObjectThread(bucketName, key, fileName,
                    partSize * i, size, eTags, i + 1, uploadId,client));
        }
        service.shutdown();
        while (!service.isTerminated()) {
            service.awaitTermination(1 * 1000 * 1000, TimeUnit.NANOSECONDS);
        }
        
        //为part按partnumber排序
        ComparatoPartETag comparator = new ComparatoPartETag();
        Collections.sort(eTags, comparator);
        
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, key, uploadId, eTags);
        
        return client.completeMultipartUpload(completeMultipartUploadRequest);
        
    }
    
    private static class UploadObjectThread implements Runnable {
        private String fileName;
        private String bucket;
        private String object;
        private long start;
        private long size;
        private List<PartETag> eTags;
        private int partId;
        private OSSClient client;
        private String uploadId;

        UploadObjectThread(String bucket, String object, String fileName,
                long start, long size, List<PartETag> eTags,
                int partId, String uploadId, OSSClient client) {
            this.fileName = fileName;
            this.bucket = bucket;
            this.object = object;
            this.start = start;
            this.size = size;
            this.eTags = eTags;
            this.partId = partId;
            this.client = client;
            this.uploadId = uploadId;
        }

        @Override
        public void run() {
            File file = new File(fileName);
            if (size <= 0 || start + size > file.length())
                return;
            InputStream in = null;
            try {
                in = new FileInputStream(fileName);
                in.skip(start);
                //String objectName = object+ ".multipart" + partId;
    
                 UploadPartRequest uploadPartRequest = new UploadPartRequest();
                 uploadPartRequest.setBucketName(bucket);
                 uploadPartRequest.setKey(object);
                 uploadPartRequest.setUploadId(uploadId);
                 
                 uploadPartRequest.setInputStream(in);
                 uploadPartRequest.setPartSize(size);
                 uploadPartRequest.setPartNumber(partId);
                 
                 UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
                
                 eTags.add(uploadPartResult.getPartETag());
                
                
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) try { in.close(); } catch (Exception e) {}
            }
        }
    }

}

/**
 * 比较PartNumber的大小
 * */
class ComparatoPartETag implements Comparator<PartETag>{

     public int compare(PartETag arg0, PartETag arg1) {
         PartETag part1= arg0;
         PartETag part2= arg1;

         return part1.getPartNumber() - part2.getPartNumber();

      }  
}


