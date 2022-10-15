package com.deutsche.naceservices.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.deutsche.naceservices.model.NaceOrder;
import com.deutsche.naceservices.repository.NaceRepository;
import com.deutsche.naceservices.service.NaceService;
import com.deutsche.naceservices.utility.NaceCSVHelper;

@Service
public class NaceServiceImpl implements NaceService{

	@Autowired
	NaceRepository naceRepository;

	@Override
	public List<NaceOrder> persistNaceDetails(MultipartFile file) {
		List<NaceOrder> naceOrders = NaceCSVHelper.convertCsvToNaceOrders(file);
		return naceRepository.saveAll(naceOrders);
	}

	@Override
	public Optional<NaceOrder> getNaceDetails(Integer order) {
		return naceRepository.findById(order);
	}

	@Override
	public List<NaceOrder> getAllNaceDetails() {
		return naceRepository.findAll();
	}	
}
