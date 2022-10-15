package com.deutsche.naceservices.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.deutsche.naceservices.model.NaceOrder;

public class NaceCSVHelper {
	public static String TYPE = "csv";

	public static boolean isCSVFile(MultipartFile file) {
		if (file.getOriginalFilename().endsWith(TYPE)) {
			return true;
		}
		return false;
	}

	public static List<NaceOrder> convertCsvToNaceOrders(MultipartFile file) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,	CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<NaceOrder> naceOrders = new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();
			for (CSVRecord csvRecord : csvRecords) {
				NaceOrder naceOrder = new NaceOrder(
						Integer.parseInt(csvRecord.get("Order")),
						Integer.parseInt(csvRecord.get("Level")),
						csvRecord.get("Code"),
						csvRecord.get("Parent"),
						csvRecord.get("Description"),
						csvRecord.get("This item includes"),
						csvRecord.get("This item also includes"),
						csvRecord.get("Rulings"),
						csvRecord.get("This item excludes"),
						csvRecord.get("Reference to ISIC Rev. 4")
						);
				naceOrders.add(naceOrder);
			}
			return naceOrders;
		} catch (IOException e) {
			throw new RuntimeException("Unable to parse the CSV file: " + e.getMessage());
		} catch (IllegalArgumentException e) {			
			throw new IllegalArgumentException("One of the column name is not correct in the CSV file: ", e);
		} 
	}	
}