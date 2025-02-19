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
    private int dbRecords;

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApp.class, args);
    }


    public void run(String[] args) {
        String enter = "Press Enter to return to main menu";
        MenuOptions option;
        do {
            dbRecords = productRepository.findAll().size() + 100;
            option = console.displayMainMenu();
            switch (option) {
                case ADD_PRODUCT:
                    addProduct();
                    console.pressEnter(enter);
                    break;
                case VIEW_PRODUCTS:
                    viewProducts();
                    console.displayHeader("\n");
                    console.pressEnter(enter);
                    break;
                case SEARCH_PRODUCTS:
                    console.displayHeader(searchProduct() + "\n");
                    console.pressEnter(enter);
                    break;
                case UPDATE_PRODUCT:
                    updateProduct();
                    console.pressEnter(enter);
                    break;
                case DELETE_PRODUCT:
                    deleteProduct();
                    console.pressEnter(enter);
                    break;
            }
        } while (option != MenuOptions.EXIT);

        console.displayHeader("Goodbye");
    }

    public void addProduct() {
        console.displayHeader("===== Add Product =====\n");
        int productId = console.readInt("Enter Product ID: ", 100, dbRecords + 1);

        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            console.displayHeader("Product exists.");
        } else {
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
        for (Product product : products) {
            System.out.println(product.getProductId() + "   | " + product.getName() + "       | " + product.getQuantity() + "       | $" + product.getPrice());
        }
        console.displayHeader("----------------------------------------");
    }

    public String searchProduct() {
        Optional<Product> product;
        console.displayHeader("===== Search Product =====");
        String productSearch = console.readRequiredString("Enter Product ID or Name");
        boolean checkIfNumerical = productSearch.matches("\\d+");
        if (checkIfNumerical) {
            int productId = Integer.parseInt(productSearch);
            product = productRepository.findById(productId);
            if (product.isPresent()) {
                Product productUnwrap = product.get();
                return "Product Found: \n" + productUnwrap;
            } else {
                return "No product found";
            }
        } else {
            product = productRepository.findByName(productSearch);
            if (product.isPresent()) {
                Product productUnwrap = product.get();
                return "Product Found:\n" + productUnwrap;
            } else {
                return "No product found";
            }
        }
    }

    public void updateProduct() {
        console.displayHeader("===== Update Product =====\n\n\n");
        int productId = console.readInt("Enter Product ID: ", 100, dbRecords);

        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            console.displayHeader("Current details");
            Product productUnwrap = product.get();
            console.displayHeader(productUnwrap.toStringNoId());

            int validQuantity = console.checkQuantity("Enter quantity (or press Enter to skip)");
            double validPrice = console.checkPrice("Enter price (or press Enter to skip)");

            if (validQuantity != -1) {
                product.get().setQuantity(validQuantity);
            }
            if (validPrice != -1.00) {
                product.get().setPrice(BigDecimal.valueOf(validPrice));
            }
            console.displayHeader("Product updated successfully");
        } else {
            console.displayHeader("Product not found");
        }
    }

    public void deleteProduct() {
        console.displayHeader("===== Delete Product =====");
        int productId = console.readInt("Enter Product ID:", 100, dbRecords);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            String deletion = console.readRequiredString("\nAre you sure you want to delete? (Y/N):\n");
            if (deletion.equals("y")) {
                productRepository.delete(product.get());
                console.displayHeader("Product successfully deleted.");
            }
        } else {
            console.displayHeader("Product not found!");
        }
    }

}
