package com.mpscstarter.backend.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.mpscstarter.exceptions.S3Exception;

@Service
public class S3Service {

	/** The application logger*/
	
	private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	private static final String PROFILE_PICTURE_FILE_NAME= "profilePicture";
	
	@Value("${aws.s3.root.bucket.name}")
	private String bucketName;
	
	@Value("${aws.s3.profile}")
	private String awsProfileName;
	
	@Value("${image.store.tmp.folder}")
	private String tempImageStore;
	
	@Autowired
	private AmazonS3 s3Client;
	
	public String storeProfileImage(MultipartFile uploadFile,String username){
		
		String profileImageUrl = null;
		
		try {
			if (uploadFile!= null && !uploadFile.isEmpty()) {
				byte[] bytes = uploadFile.getBytes();
				
				//The root of our temporary assets. Will create if it does not exist
				File tmpImageStoreFolder= new File(tempImageStore+File.separatorChar+username);
				if(!tmpImageStoreFolder.exists()) {
					LOG.info("Creating temporary root folder for s3 assets");
					tmpImageStoreFolder.mkdirs();
				}
				//Temporary file where the profile image will be stored
				File tmpProfileImagefile = new File(tmpImageStoreFolder.getAbsolutePath()
													+File.separatorChar
													+PROFILE_PICTURE_FILE_NAME
													+"."
													+FilenameUtils.getExtension(uploadFile.getOriginalFilename())
						);
			
				LOG.info("Temporary file will be saved to {}",tmpProfileImagefile.getAbsolutePath());
				
				try (
					 BufferedOutputStream stream = 
							 new BufferedOutputStream(
									 new FileOutputStream(new File(tmpProfileImagefile.getAbsolutePath())))
				){
					stream.write(bytes);
				}
				profileImageUrl = this.storeProfileImageToS3(tmpProfileImagefile,username);
				
				//cleanup Temporary folder
				tmpProfileImagefile.delete();
				
			}
			
			
		
		}catch (IOException e){
			throw new S3Exception(e);
		}
		return profileImageUrl;	
	}		
	
	// Private methods
	
	private String storeProfileImageToS3(File resource, String username) {
		
		String resourceUrl = null;
		
		if(!resource.exists()) {
			LOG.error("The file {} does not exist. Throwing an exception"+resource.getAbsolutePath());
			throw new S3Exception("The file"+resource.getAbsolutePath()+"does not exist");
		}
		String rootBucketUrl = this.ensureBucketExists(bucketName);
		
		if (null == rootBucketUrl) {
			LOG.error("The bucket {}does not exist and Application was unable to create it. "
					+ "The image wont be stored with profile"+bucketName);
		}else {
			AccessControlList acl =new AccessControlList();
			acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
			String key = username +"/"+PROFILE_PICTURE_FILE_NAME+"."+FilenameUtils.getExtension(resource.getName());
			
			try {
				s3Client.putObject(new PutObjectRequest(bucketName, key, resource).withAccessControlList(acl));
				resourceUrl = s3Client.getUrl(bucketName, key).toString();
				
			}catch(AmazonClientException ace) {
				LOG.error("A client exception {} occurred while trying to store the profile image {}"
						+"on s3. The profile image wont be stored",ace,resource.getAbsolutePath());
				throw new S3Exception(ace);
			}
		}
		
		return resourceUrl;
	}

	private String ensureBucketExists(String bucketName) {
		String bucketUrl = null;
		
		try {
			if(!s3Client.doesBucketExistV2(bucketName)) {
				LOG.info("Bucket {} does not exist... creating one");
				s3Client.createBucket(bucketName);
				LOG.info("Created bucket{}",bucketName);
			}
			bucketUrl = s3Client.getUrl(bucketName, null)+bucketName;
		}catch(AmazonClientException ace) {
			LOG.error("An error occurred while connecting to S3. "
					+ "Will not execute action for bucket{} "+bucketName);
			throw new S3Exception(ace);
		}
		
		return bucketUrl;
	}
}
