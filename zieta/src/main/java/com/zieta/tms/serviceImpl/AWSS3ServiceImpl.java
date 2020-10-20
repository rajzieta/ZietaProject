package com.zieta.tms.serviceImpl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.zieta.tms.service.AWSS3Service;

import lombok.extern.slf4j.Slf4j;

	@Service
	@Slf4j
	public class AWSS3ServiceImpl implements AWSS3Service {

		@Autowired
		AmazonS3Client amazonS3Client;
		@Value("${aws.s3.bucket}")
		private String bucketName;
		

		@Override
		// @Async annotation ensures that the method is executed in a different background thread 
		// but not consume the main thread.
		@Async
		public String uploadFile(final MultipartFile multipartFile, String objectId) {
			log.info("File upload in progress.");
			File file = null;
			String attachmentPath = StringUtils.EMPTY;
			try {
				file = convertMultiPartFileToFile(multipartFile);
				attachmentPath = uploadFileToS3Bucket(bucketName, file, objectId);
				log.info("File upload is completed.");
			}finally {
				file.delete();	// To remove the file locally created in the project folder.
			}
			
			return attachmentPath;
		}

		private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
			final File file = new File(multipartFile.getOriginalFilename());
			try (final FileOutputStream outputStream = new FileOutputStream(file)) {
				outputStream.write(multipartFile.getBytes());
			} catch (final IOException ex) {

				log.error("Error converting the multi-part file to file= ", ex.getMessage());
			}
			return file;
		}

		private String uploadFileToS3Bucket(final String bucketName, final File file,String objectKey) {

			objectKey = objectKey + file.getName();
			log.info("Uploading file with name= " + objectKey);
			final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectKey, file);
			PutObjectResult result = amazonS3Client.putObject(putObjectRequest);
			return objectKey;
		}
		

		@Override
		// @Async annotation ensures that the method is executed in a different background thread 
		// but not consume the main thread.
		@Async
		public byte[] downloadFile(final String keyName) {
			byte[] content = null;

			log.info("Downloading an object with key= " + keyName);
			final S3Object s3Object = amazonS3Client.getObject(bucketName, keyName);
			final S3ObjectInputStream stream = s3Object.getObjectContent();
			try {
				content = IOUtils.toByteArray(stream);

				log.info("File downloaded successfully.");
				s3Object.close();
			} catch(final IOException ex) {
				log.info("IO Error Message= " + ex.getMessage());
			}
			return content;
		}
	}
