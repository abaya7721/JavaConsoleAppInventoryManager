package com.app.manager;

import com.app.manager.domain.ProductService;
import com.app.manager.model.Product;
import com.app.manager.repository.ProductRepository;
import com.app.manager.view.Console;
import com.app.manager.view.MenuOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class InventoryManagementApp implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    private Console console = new Console();
    private ProductService productService = new ProductService();


    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApp.class, args);
    }


    public void run(String[] args) {

        MenuOptions option;
        do {
            option = console.displayMainMenu();
            switch (option) {
                case ADD_PRODUCT:
                    addProduct();
                    console.pressEnter("Press Enter to return to main menu");
                    break;
                case VIEW_PRODUCTS:
                    viewProducts();
                    console.pressEnter("Press Enter to return to main menu");
                    break;
                case SEARCH_PRODUCTS:
                    console.displayHeader(searchProduct()+"\n");
                    console.pressEnter("Press Enter to return to main menu");
                    break;
                case UPDATE_PRODUCT:
                    //updateProduct);
                    break;
                case DELETE_PRODUCT:
                    //updateCustomer();
                    break;
            }
        } while (option != MenuOptions.EXIT);

        console.displayHeader("Goodbye");
    }

    public void addProduct() {
        console.displayHeader("===== Add Product =====\n");
        int productId = console.readInt("Enter Product ID: ", 100, 200);

        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            console.displayHeader("Product exists.");
        }

        else{
            String productName = console.readRequiredString("Enter Product Name:");
            int quantity = console.readInt("Enter Quantity: ", 0, 1000);
            double price = console.readDouble("Enter Price: ");
            BigDecimal decimalPrice = new BigDecimal(price);
            Product newProduct = new Product(productName, decimalPrice, quantity);
            newProduct.setProductId(productId);
            productRepository.save(newProduct);
            console.displayHeader("Product Added Successfully!");
        }
    }

    public void viewProducts() {
        List<Product> products = productRepository.findAll();
        console.displayHeader("===== Inventory List =====");
        console.displayHeader("ID    | Name          | Quantity         | Price  ");
        console.displayHeader("----------------------------------------");
        for(Product product : products){
            System.out.println(product.getProductId()+"   | "+product.getName()+"       | "+product.getQuantity()+"       | $"+product.getPrice());
        }
        console.displayHeader("----------------------------------------");
    }

    public String searchProduct(){
        Optional<Product> product;
        console.displayHeader("===== Search Product =====");
        String productSearch = console.readRequiredString("Enter Product ID or Name");
        boolean check = productSearch.matches("\\d+");
        if(check){
            int productId = Integer.parseInt(productSearch);
            product = productRepository.findById(productId);
            if(product.isPresent()){
                return "Product Found: \n"+ product;
            }
            else {
                return "No product found";
            }
        }
        else {
            product = productRepository.findByName(productSearch);
            if(product.isPresent()){
                return "Product Found:\n" + product;
            }
            else{
                return "No product found";
            }
        }
    }

    public void updateProduct(){

    }



}
