package com.days.momentb.personalboard.controller;

import com.days.momentb.personalboard.dto.PersonalBoardPictureDTO;
import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UploadController {

	private final CloudVisionTemplate cloudVisionTemplate;

	@Value("${com.days.upload.path}")
	private String uploadPath;

	@PostMapping("/uploadAjax")
	public ResponseEntity<List<PersonalBoardPictureDTO>> uploadFile(@RequestParam(value="file", required=true)MultipartFile[] uploadFiles) throws Exception {

		List<PersonalBoardPictureDTO> resultDTOList = new ArrayList<>();

		for (MultipartFile uploadFile: uploadFiles) {

			if(uploadFile.getContentType().startsWith("image") == false) {
			 	log.warn("this file is not image type");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			//실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

			log.info("fileName: " + fileName);
			//날짜 폴더 생성
			String folderPath = makeFolder();

			//UUID
			String uuid = UUID.randomUUID().toString();

			//저장할 파일 이름 중간에 "_"를 이용해서 구분
			String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +"_" + fileName;
			Path savePath = Paths.get(saveName);

			try {
				//원본 파일 저장
				uploadFile.transferTo(savePath);

				//섬네일 생성
				String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator
						+"s_" + uuid +"_" + fileName;
				//섬네일 파일 이름은 중간에 s_로 시작하도록
				File thumbnailFile = new File(thumbnailSaveName);
				//섬네일 생성
				Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,405,335 );
				String imageLabel = getImageLabel(saveName);
				log.info("###################################");
				log.info("###################################");
				log.info("imageLabel : " + imageLabel);
				resultDTOList.add(new PersonalBoardPictureDTO(uuid, fileName, folderPath, imageLabel, false));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}//end for
		return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
	}


	private String makeFolder() {

		String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

		String folderPath =  str.replace("//", File.separator);

		// make folder --------
		File uploadPathFolder = new File(uploadPath, folderPath);

		if (uploadPathFolder.exists() == false) {
			uploadPathFolder.mkdirs();
		}
		return folderPath;
	}


	@PostMapping("/removeFile")
	public ResponseEntity<Boolean> removeFile(String fileName){

		String srcFileName = null;
		try {
			srcFileName = URLDecoder.decode(fileName,"UTF-8");
			File file = new File(uploadPath +File.separator+ srcFileName);
			boolean result = file.delete();

			File thumbnail = new File(file.getParent(), "s_" + file.getName());

			result = thumbnail.delete();

			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/display")
	public ResponseEntity<byte[]> getFile(String fileName, String size) {

		ResponseEntity<byte[]> result = null;

		try {
			String srcFileName =  URLDecoder.decode(fileName,"UTF-8");

			log.info("fileName: " + srcFileName);

			File file = new File(uploadPath +File.separator+ srcFileName);

			log.info("111111file: " + file);

			if(size != null && size.equals("1")){
				file  = new File(file.getParent(), file.getName().substring(2));
			}

			log.info("222222file: " + file);

			HttpHeaders header = new HttpHeaders();

			//MIME타입 처리
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			//파일 데이터 처리
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
			log.info(result);
		return result;
	}

	private String getImageLabel(String fileName) throws Exception {

		String srcFileName =  URLDecoder.decode(fileName,"UTF-8");


		File file = new File(File.separator+ srcFileName);

		log.info("Before file: " + file);

		log.info("uploadPath : " + uploadPath);
		log.info("srcFileName : " + srcFileName);

		InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));

		AnnotateImageResponse response =
				this.cloudVisionTemplate.analyzeImage(inputStreamResource, Feature.Type.LABEL_DETECTION);

//			log.info(response.getLabelAnnotationsList().stream().collect(Collectors.toList()).get(0).getDescription());

		String label = response.getLabelAnnotationsList().stream().collect(Collectors.toList()).get(0).getDescription();

		return label;
	}

	@PostMapping("/uploadCanvas")
	public ResponseEntity<List<PersonalBoardPictureDTO>> uploadCanvasFile(@RequestParam(value="file", required=true) MultipartFile [] uploadFiles){

		log.info("uploadControllerFile");
		log.info(uploadFiles);

		List<PersonalBoardPictureDTO> resultDTOList = new ArrayList<>();

		for (MultipartFile uploadFile: uploadFiles) {

			if(uploadFile.getContentType().startsWith("image") == false) {
				log.warn("this file is not image type");
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}

			//실제 파일 이름 IE나 Edge는 전체 경로가 들어오므로
			String originalName = uploadFile.getOriginalFilename();
			String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

			log.info("fileName: " + fileName);
			//날짜 폴더 생성
			String folderPath = makeFolder();

			//UUID
			String uuid = UUID.randomUUID().toString();

			//저장할 파일 이름 중간에 "_"를 이용해서 구분
			String saveName = uploadPath + File.separator + folderPath + File.separator + uuid +"_" + fileName;
			Path savePath = Paths.get(saveName);

			try {
				//원본 파일 저장
				uploadFile.transferTo(savePath);

				//캔버스 이미지 생성
				String thumbnailSaveName = uploadPath + File.separator + folderPath + File.separator
						+"h_" + uuid +"_" + fileName;
				//캔버스 이미지는 h_로 시작하도록
				File thumbnailFile = new File(thumbnailSaveName);
				String imageLabel = "handwriting";
				//캔버스 이미지 생성
				Thumbnailator.createThumbnail(savePath.toFile(), thumbnailFile,405,335 );
				resultDTOList.add(new PersonalBoardPictureDTO(uuid, fileName, folderPath, imageLabel,  false));

			} catch (IOException e) {
				e.printStackTrace();
			}

		}//end for
		return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
	}



}