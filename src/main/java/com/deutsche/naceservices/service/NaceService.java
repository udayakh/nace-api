package com.deutsche.naceservices.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.deutsche.naceservices.model.NaceOrder;

public interface NaceService {

	public List<NaceOrder> persistNaceDetails(MultipartFile file);
	
	public Optional<NaceOrder> getNaceDetails(Integer order);

	public List<NaceOrder> getAllNaceDetails();
}
