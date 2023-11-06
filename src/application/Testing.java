package application;

import java.io.File;
import java.io.IOException;

public class Testing {
	Product product;
	public Testing() throws IOException {
		File file = new File("data/db_ products.csv");
		System.out.println(file.exists());
//		testProductSave();
	}
	
	public void  testProductSave() throws IOException{
		product=new Product("name",1,1.0,"KG","null","F");
		product.saveToFile();
	}
	
	public static void main(String[] args) throws IOException {
		new Testing();
	}
}
