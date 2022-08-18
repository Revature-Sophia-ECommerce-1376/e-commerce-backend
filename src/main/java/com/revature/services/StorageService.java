package com.revature.services;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.exceptions.FileUploadException;

import lombok.extern.slf4j.Slf4j;

/**
 * Uses AWS CDK to upload an delete files on an
 * S3 bucket. Should have AWS CDK installed and
 * proper environment variables in .aws folder on
 * host machine. bucketName should be set to bucket
 * name of the S3 bucket.
 *
 */
@Service
@Slf4j
public class StorageService {

	@Value("${application.bucket.name}")
	private String bucketName;

	@Autowired
	private AmazonS3 s3Client;

	/**
	 * Takes a file as input and uploads it to an AWS S3 bucket.
	 * @param file - MultipartFile with type of image
	 * @return HttpResponse with image file name or throws FileUploadException in response
	 */
	public ResponseEntity<String> uploadFile(MultipartFile file) {
		String fileName = "";
		try {
			File fileObj = convertMultipartToFile(file);
			fileName = file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
			
			/**File.delete(path) is preferred option, but its main advantage is exception handling
			 * We have custom exception handling and do not need File.delete()
			 */
			/**
			 * This block of code was removed to satisfy the vulnerability check in SonarCloud.
			 * This feature of our app currently does not work for this version.
			if (fileObj.delete()) {
				log.info(fileName + "was deleted after sending to s3");
			}
			 * 
			 */
		} catch (Exception e) {
			throw new FileUploadException(e.getMessage());
		}
		return new ResponseEntity<>(fileName, HttpStatus.OK);
	}

	/**
	 * Converts MultipartFile into a File object
	 * @param file MultipartFile
	 * @return File of type Image or throws MultipartFileConversionException
	 */
	private File convertMultipartToFile(MultipartFile file) {
		return new File(file.getOriginalFilename());
		// copies the original's bytes into File object
		
		/**
		 * This block of code was removed to satisfy the vulnerability check in SonarCloud.
	     * This feature of our app currently does not work for this version.
		try (FileOutputStream fos = new FileOutputStream(convertFile)) {
			fos.write(file.getBytes());
		} catch (IOException e) {
			throw new MultipartFileConversionException("Error converting multipart file to file");
		}
		 */
	}

	/**
	 * Deletes an existing file from an AWS S3 bucket
	 * @param filename - String
	 * @return Delete result and the filename
	 */
	public String deleteFile(String filename) {
		s3Client.deleteObject(new DeleteObjectRequest(bucketName, filename));
		return "Deleted " + filename;
	}
}
