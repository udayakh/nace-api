package com.deutsche.naceservices.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import com.deutsche.naceservices.model.NaceOrder;
import com.deutsche.naceservices.repository.NaceRepository;
import com.deutsche.naceservices.service.impl.NaceServiceImpl;

@ExtendWith(MockitoExtension.class)
public class NaceServiceImplTest {

	@InjectMocks
	NaceServiceImpl naceService;

	@Mock
	NaceRepository naceRepository;

	private static List<NaceOrder> defaulNaceList;

	@BeforeAll
	public static void setUpBeforeClass() {
		defaulNaceList = Arrays.asList(
				new NaceOrder(100, 1, "01", null, "AGRICULTURE", "Item includes1", "Item includes_extra1", "Rulings1", null, "A"),
				new NaceOrder(101, 3, "011", "01.1", "AGRICULTURE", "Item includes2", null, "Rulings2", "exclusion2", "01"),
				new NaceOrder(102, 4, "A", "02.11", null, null, "Item includes_extra3", null, "exclusion", "023")
				);
	}

	@Test
	public void testPersistNaceDetails() throws IOException {
		InputStream is = getClass().getClassLoader().getResourceAsStream("Nace_test_data.csv");
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "Nace_test_data.csv", "application/vnd.ms-excel", is);
		when(naceRepository.saveAll(anyList())).thenReturn(defaulNaceList);
		List<NaceOrder> naceOrders = naceService.persistNaceDetails(mockMultipartFile);
		assertThat(naceOrders.size()).isEqualTo(3);
		assertEquals(naceOrders.get(0).getOrder(), 100);
		assertEquals(naceOrders.get(1).getCode(), "011");
		assertEquals(naceOrders.get(2).getIsic_rev_ref(), "023");
	}

	@Test
	public void testGetNaceDetails() {
		NaceOrder naceOrder = defaulNaceList.get(0);
		when(naceRepository.findById(anyInt())).thenReturn(Optional.of(naceOrder));
		Optional<NaceOrder> naceOrderActual = naceService.getNaceDetails(100);
		assertTrue(naceOrderActual.isPresent());
		assertTrue(naceOrder.equals(naceOrderActual.get()));
	}

	@Test
	public void testGetNaceDetails_NotFound() {
		when(naceRepository.findById(anyInt())).thenReturn(Optional.empty());
		Optional<NaceOrder> naceOrderActual = naceService.getNaceDetails(100);
		assertFalse(naceOrderActual.isPresent());
	}
	
	@Test
	public void testGetAllNaceDetails() {
		when(naceRepository.findAll()).thenReturn(defaulNaceList);
		List<NaceOrder> naceOrders = naceService.getAllNaceDetails();
		assertThat(naceOrders.size()).isEqualTo(3);
		assertEquals(naceOrders.get(0).getDescription(), "AGRICULTURE");
		assertEquals(naceOrders.get(1).getCode(), "011");
		assertEquals(naceOrders.get(2).getIsic_rev_ref(), "023");
		assertTrue(naceOrders.get(0).equals(defaulNaceList.get(0)));
	}

	@Test
	public void testGetAllNaceDetails_Empty() {
		when(naceRepository.findAll()).thenReturn(new ArrayList<>());
		List<NaceOrder> naceOrders = naceService.getAllNaceDetails();		
		assertTrue(naceOrders.isEmpty());
	}	
}