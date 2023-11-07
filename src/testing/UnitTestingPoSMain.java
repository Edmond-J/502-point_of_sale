/**
 * 
 */
package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import application.*;

/**
 * 
 */
class UnitTestingPoSMain {
	PoS_Main mainController;
	Database database;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		mainController = new PoS_Main();
		database = new Database("testing/DataBaseTesting/");
	}

	public int isKeyExistInFile(String key, String path) throws IOException {
		String[] command = { "cmd.exe", "/c", "find /c \""+key+"\" testing/DataBaseTesting/db_products.csv" };
//		String command = "find /c \""+key+"\" "+path;
		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectErrorStream(true);
		Process process = processBuilder.start();
//		Process process = Runtime.getRuntime().exec(command);
		InputStream stream = process.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		String line;
		String result="";
		while ((line = reader.readLine()) != null) {
			result+=line;
			System.out.println(line);
		}
		// Wait for the process to finish
		try {
			int exitCode = process.waitFor();
//			return Integer.parseInt(result.trim());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Integer.parseInt(result.substring(result.length()-1)) ;
	}

	@Test
	void testLoadProductData() {
		ArrayList<Product> productsList = new ArrayList<Product>();
		productsList = mainController.loadProductData("testing/read only/mockProducts.csv");
		assertEquals("apple", productsList.get(0).getName());
		assertEquals("null", productsList.get(0).getBrand());
		assertEquals(3, productsList.size());
	}

	@Test
	void testLoadProductDataNonExist() {
		ArrayList<Product> productsList = new ArrayList<Product>();
		productsList = mainController.loadProductData("testing/read only/noExist/mockProducts.csv");
		assertEquals(0, productsList.size());
	}

	@Test
	void databaseDelete() {
		Product product = new Product();
		product.setName("apple");
		System.out.println(database.path);
		database.delete(product);
		try {
			assertEquals(0, isKeyExistInFile("apple", null));
		} catch (IOException e) {
			e.printStackTrace();
		}
		;
	}
}
