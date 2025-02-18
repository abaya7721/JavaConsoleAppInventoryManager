package com.app.manager;

import com.app.manager.domain.ProductService;
import com.app.manager.view.Console;
import com.app.manager.view.MenuOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class InventoryManagementApp implements CommandLineRunner {

	Console console = new Console();
	ProductService productService = new ProductService();

	public static void main(String[] args) {
		SpringApplication.run(InventoryManagementApp.class, args);
	}


	public void run(String[] args) {

		viewProducts();
		MenuOptions option;
		do {
			option = console.displayMainMenu();
			switch (option) {
				case ADD_PRODUCT:
					//displayAllItems();
					break;
				case VIEW_PRODUCTS:
					viewProducts();
					break;
				case SEARCH_PRODUCTS:
					//updateItem();
					break;
				case UPDATE_PRODUCT:
					//searchCustomer();
					break;
				case DELETE_PRODUCT:
					//updateCustomer();
					break;
			}
		} while (option != MenuOptions.EXIT);

		console.displayHeader("Goodbye");
	}

	public void addItem(){

	}

	public void viewProducts(){
		productService.showProductsInInventory();
	}
}
