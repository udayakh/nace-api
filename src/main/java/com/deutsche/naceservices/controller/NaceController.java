package com.deutsche.naceservices.controller;

import java.util.List;
import java.lang.Object;
import com.deutsche.naceservices.service.impl.NaceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.deutsche.naceservices.exception.NaceOrderNotFoundException;
import com.deutsche.naceservices.model.NaceOrder;
import com.deutsche.naceservices.response.ResponseMessage;
import com.deutsche.naceservices.service.NaceService;
import com.deutsche.naceservices.utility.NaceCSVHelper;

@RestController
@RequestMapping("/nace/api/")
public class NaceController {

	@Autowired
	NaceService naceService;

	/*@Autowired
	NaceService naceService2;


	int a;
	int b;
	// ==

	private boolean checkNaceService(){
		if(naceService.equals(naceService2)){
			return true;
		} else{
			return false;
		}
	}*/

	@PostMapping("/persistNaceDetails")
	public ResponseEntity<ResponseMessage> persistNaceDetails(@RequestParam("file") MultipartFile file) {
		String message = "";
		if (NaceCSVHelper.isCSVFile(file)) {
			try {
				naceService.persistNaceDetails(file);
				message = "Uploaded the file successfully: " + file.getOriginalFilename();
				return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
			} catch (Exception e) {
				e.printStackTrace();
				message = "Could not upload the file: " + file.getOriginalFilename() + "! " + e.getMessage() ;
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage(message));
			}
		}
		message = "Please upload a csv file!";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/getNaceDetails/{orderId}")
	public ResponseEntity<?> getNaceDetails(@PathVariable Integer orderId) {

		NaceOrder naceOrder = naceService.getNaceDetails(orderId)
				.orElseThrow(() -> new NaceOrderNotFoundException("Nace order " + orderId + " does not exist "));
		
		return ResponseEntity.status(HttpStatus.OK).body(naceOrder);
	}

	@GetMapping("/getAllNaceDetails")
	public ResponseEntity<?> getAllNaceDetails() {
		try {
			List<NaceOrder> naceOrders = naceService.getAllNaceDetails();
			if (naceOrders.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseMessage("No orders available"));
			}
			return ResponseEntity.status(HttpStatus.OK).body(naceOrders);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseMessage("Unable to get the orders"));
		}
	}	
}