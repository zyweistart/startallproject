package test.aliyun;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import com.aliyun.openservices.oss.OSSClient;
import com.aliyun.openservices.oss.model.AbortMultipartUploadRequest;
import com.aliyun.openservices.oss.model.CannedAccessControlList;
import com.aliyun.openservices.oss.model.GetObjectRequest;
import com.aliyun.openservices.oss.model.ListMultipartUploadsRequest;
import com.aliyun.openservices.oss.model.MultipartUpload;
import com.aliyun.openservices.oss.model.MultipartUploadListing;
import com.aliyun.openservices.oss.model.OSSObjectSummary;
import com.aliyun.openservices.oss.model.ObjectListing;
import com.aliyun.openservices.oss.model.ObjectMetadata;

/**
 * 运行object示例需要做如下几件事：
 * 1、获取accessId\accessKey，修改示例中对应accessId\accessKey
 * 2、修改需要上传的文件uploadFilePath，文件必须存在, 修改下载文件的路径downloadFilePath
 * 3、指定文件的显示名称objectName，指定bucketName
 * **/
public class OSSObjectSample {

    static OSSClient client ;
    //TODO 输入你的accessId和accessKey
    static String accessId = "";
    static String accessKey = "";
    static String ossEndpoint = "http://storage.aliyun.com/";
      
    //TODO 输入你的bucket名称 
    static String bucketName = accessId;
    //TODO 输入你的object名称
    static String objectName = "测试上传文件.rar";
    //TODO 输入你的上传、下载文件路径
    static String uploadFilePath = "d:/temp/oss/测试上传文件.rar";
    static String downloadFilePath = "d:/temp/oss/测试下载文件.rar";
    
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
         * 上传、下载文件
         * **/
        doObjectOperation();
        
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
    
    public static void doObjectOperation()throws Exception{
        
//        Assert.assertNotNull(uploadFilePath);
//        Assert.assertNotNull(downloadFilePath);
        
        ObjectMetadata objectMeta = new ObjectMetadata();
        //可以输入你的user meta信息
        //objectMeta.addUserMetadata("Id", "123");
        //objectMeta.addUserMetadata("Name", "test");
        
        File file = new File(uploadFilePath);
        objectMeta.setContentLength(file.length());
        //上传文件
        InputStream input = new FileInputStream(file);
        client.putObject(bucketName, objectName, input, objectMeta);
        
        //下载文件
        client.getObject(new GetObjectRequest(bucketName, objectName), new File(downloadFilePath));
        
    }
}
