package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

public class Database {
	private String path;
//	FileWriter prodWriter;
//	CSVPrinter prodPrinter;

	public Database(String filePath) throws IOException {
		path = filePath;
	}

	public void delete(Product product) {
		try {
			String filePath = path+"db_ products.csv";
			CSVParser csvParser = CSVParser.parse(new File(filePath), StandardCharsets.UTF_8,
					CSVFormat.DEFAULT.withHeader());
			List<CSVRecord> records = new ArrayList<>();
			records = csvParser.getRecords();
			csvParser.close(); // Close the parser
			for (CSVRecord record : records) {
				if (record.get("NAME").equals(product.getName())) {
					records.remove(record);
					break; // Assuming "Name" is unique; if not, remove the break statement
				}
			}
			FileWriter fileWriter = new FileWriter(filePath);
			CSVPrinter csvPrinter = new CSVPrinter(fileWriter,
					CSVFormat.DEFAULT.withHeader("NAME", "ITEM CODE", "PRICE", "UNIT", "BRAND", "CATEGORY"));
			for (CSVRecord record : records) {
				csvPrinter.printRecord(record);
			}
			csvPrinter.close();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			
		}
	}
}
