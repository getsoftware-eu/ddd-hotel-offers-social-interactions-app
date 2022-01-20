package de.hotelico.controller;

import de.hotelico.dto.CustomerDTO;
import de.hotelico.repository.CustomerRepository;
import de.hotelico.repository.ActivityRepository;
import de.hotelico.repository.HotelRepository;
import de.hotelico.service.CustomerService;
import de.hotelico.service.FileUploadService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import java.util.Iterator;

/**
 * Created by Eugen on 22.08.2015.
 */
//@CrossOrigin
@RestController
@RequestMapping("/files")
public class FileController  extends BasicController{

	@Autowired
	private FileUploadService fileUploadService;	
	
	@Autowired
	private CustomerService customerService;	
	@Autowired
	private CustomerRepository customerRepository;	
	
	@Autowired
	private ActivityRepository activityRepository;	
	
	@Autowired
	private HotelRepository hotelRepository;	
	
//	// Download a file
//	@RequestMapping(
//			value = "/download",
//			method = RequestMethod.GET
//	)
//	public ResponseEntity downloadFile(@RequestParam("filename") String filename) {
//
//		FileUpload fileUpload = fileUploadService.findByFilename(filename);
//
//		// No file found based on the supplied filename
//		if (fileUpload == null) {
//			return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
//		}
//
//		// Generate the http headers with the file properties
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("content-disposition", "attachment; filename=" + fileUpload.getFilename());
//
//		// Split the mimeType into primary and sub types
//		String primaryType, subType;
//		try {
//			primaryType = fileUpload.getMimeType().split("/")[0];
//			subType = fileUpload.getMimeType().split("/")[1];
//		}
//		catch (IndexOutOfBoundsException | NullPointerException ex) {
//			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		headers.setContentType( new MediaType(primaryType, subType) );
//
//		return new ResponseEntity<>(fileUpload.getFile(), headers, HttpStatus.OK);
//	}
	
	
	@RequestMapping(
			value = "/upload/avatar",
			method = RequestMethod.POST, consumes = "multipart/form-data", produces="application/json"
	)
	public ResponseEntity uploadFile(DefaultMultipartHttpServletRequest request, @RequestParam(value = "file") MultipartFile file, HttpServletResponse response) {

		try {

			if(file!=null || file.getOriginalFilename().contains("avatar"))
			{
				ServletContext servletContext = request.getSession().getServletContext();
				String relativeWebPath = "/img";
				String absolutePath = servletContext.getRealPath(relativeWebPath);
				
				int customerId = Integer.parseInt(file.getOriginalFilename().replace("avatar", ""));

				fileUploadService.saveFileToOwner(file, customerId, "avatar", 0, absolutePath);
			}
			
						 
		}
		catch (Exception e) {
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/logo/requester/{requesterId}/entityType/{entityType}/entityId/{entityId}", method = RequestMethod.GET
	)
	public @ResponseBody CustomerDTO getEntityImage(@PathVariable("requesterId") long requesterId, @PathVariable("entityType") String entityType, @PathVariable("entityId") long entityId)
	{
		return fileUploadService.getEntityImage(requesterId, entityType, entityId);
	}		
		
	@RequestMapping(
			value = "/upload/customer/{customerId}/model/{modelStr}/{modelId}",
			method = RequestMethod.POST, consumes = "multipart/form-data", produces="application/json"
	)
	public ResponseEntity uploadFormFile(@PathVariable("customerId") long customerId, @PathVariable("modelStr") String modelStr, @PathVariable("modelId") long modelId, MultipartHttpServletRequest request) {

		try {
			Iterator<String> itr = request.getFileNames();

			while (itr.hasNext()) {
				String uploadedFile = itr.next();
				MultipartFile file = request.getFile(uploadedFile);
				String mimeType = file.getContentType();
				String filename = file.getOriginalFilename();
				byte[] bytes = file.getBytes();

				ServletContext servletContext = request.getSession().getServletContext();
				String relativeWebPath = "/img";
				String absoluteDiskPath = servletContext.getRealPath(relativeWebPath);

//				FileUpload newFile = new FileUpload(filename, bytes, mimeType);
				fileUploadService.saveFileToOwner(file, customerId, modelStr, modelId, absoluteDiskPath);

//				fileUploadService.uploadFile(newFile);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>("{}", HttpStatus.OK);
	}
}
