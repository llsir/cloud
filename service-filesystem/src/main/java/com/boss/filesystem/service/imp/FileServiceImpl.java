package com.boss.filesystem.service.imp;

import com.boss.filesystem.service.FileService;
import com.boss.framework.annotation.SystemLog;
import com.boss.framework.enums.SystemLogEnum;
import com.boss.framework.model.response.ResponseResult;
import com.obs.services.ObsClient;
import com.obs.services.model.HttpMethodEnum;
import com.obs.services.model.PutObjectResult;
import com.obs.services.model.TemporarySignatureRequest;
import com.obs.services.model.TemporarySignatureResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * @author: lpb
 * @create: 2020-07-21 13:51
 */
@Service
public class FileServiceImpl implements FileService {

    @Value("${cloud.ak}")
    String ak;

    @Value("${cloud.sk}")
    String sk;

    @Value("${cloud.endPoint}")
    String endPoint;

    @Value("${cloud.bucket}")
    String bucket;

    private ObsClient obsClient;

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public void initObsClient(){
        obsClient = new ObsClient(ak, sk, endPoint);
    }


    @Override
    @SystemLog(type = SystemLogEnum.UPLOAD_FILE_LOG)
    public ResponseResult uploadFile(MultipartFile file) {
        initObsClient();
        PutObjectResult result = null;
        try {
            result = obsClient.putObject(bucket, file.getName(), new ByteArrayInputStream(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseResult.FAIL();
        }finally {
            try {
                obsClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(result.getStatusCode() == 200){
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }

    @Override
    @SystemLog(type = SystemLogEnum.DOWNLOAD_FILE_LOG)
    public String getFile(String objectKey) {
        initObsClient();
        long expireSeconds = 600L;
        TemporarySignatureRequest request = new TemporarySignatureRequest(HttpMethodEnum.GET, expireSeconds);
        request.setBucketName(bucket);
        request.setObjectKey(objectKey);
        TemporarySignatureResponse response = obsClient.createTemporarySignature(request);
        return response.getSignedUrl();
    }
}
