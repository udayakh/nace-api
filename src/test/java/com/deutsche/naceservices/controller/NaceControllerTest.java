package com.deutsche.naceservices.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.deutsche.naceservices.model.NaceOrder;
import com.deutsche.naceservices.repository.NaceRepository;
import com.deutsche.naceservices.utility.NaceCSVHelper;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class NaceControllerTest {

	@Autowired
	NaceRepository naceRepository;

	@Autowired
	private MockMvc mockMvc;

	private MockMultipartFile mockMultipartFile;

	private List<NaceOrder> naceOrders;

	@BeforeEach
	public void setUp() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream("Nace_test_data.csv");
		mockMultipartFile = new MockMultipartFile("file", "Nace_test_data.csv", "application/vnd.ms-excel", is);
		naceOrders = NaceCSVHelper.convertCsvToNaceOrders(mockMultipartFile);
		naceRepository.deleteAll();
	}

	@Test
	public void testPersistNaceDetails() throws Exception{
		MvcResult result = mockMvc.perform(multipart("/nace/api/persistNaceDetails").file(mockMultipartFile))
				.andExpect(status().isOk()).andReturn();
		assertNotNull(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Uploaded the file successfully: " + mockMultipartFile.getOriginalFilename()));
	}

	@Test
	public void testPersistNaceDetails_NegativeScenario() throws Exception {
		MockMultipartFile invalidfile  = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
		MvcResult result = mockMvc.perform(multipart("/nace/api/persistNaceDetails").file(invalidfile))
				.andExpect(status().isBadRequest()).andReturn();
		assertNotNull(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Please upload a csv file!"));
	}

	@Test
	public void testGetNaceDetails() throws Exception {
		NaceOrder naceOrder = naceOrders.get(0);
		naceRepository.saveAll(naceOrders);
		mockMvc.perform(get("/nace/api/getNaceDetails/{orderId}", naceOrder.getOrder()))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.order", is(naceOrder.getOrder())))
		.andExpect(jsonPath("$.level", is(naceOrder.getLevel())))
		.andExpect(jsonPath("$.code", is(naceOrder.getCode())))
		.andExpect(jsonPath("$.isic_rev_ref", is(naceOrder.getIsic_rev_ref())))
		.andExpect(jsonPath("$.description", is(naceOrder.getDescription())));
	}

	@Test
	public void testGetNaceDetails_NegativeScenario() throws Exception {
		NaceOrder naceOrder = naceOrders.get(0);
		mockMvc.perform(get("/nace/api/getNaceDetails/{orderId}", naceOrder.getOrder()))
		.andExpect(status().isNotFound())
		.andDo(print());
	}

	@Test
	public void testGetAllNaceDetails() throws Exception {
		naceRepository.saveAll(naceOrders);
		mockMvc.perform(get("/nace/api/getAllNaceDetails"))
		.andExpect(status().isOk())
		.andDo(print())
		.andExpect(jsonPath("$.size()", is(naceOrders.size())));
	}

	@Test
	public void testGetAllNaceDetails_NegativeScenario() throws Exception {
		mockMvc.perform(get("/nace/api/getAllNaceDetails"))
		.andExpect(status().isNotFound())
		.andDo(print());
	}
}
