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

    // Console instance for inputs and inventory interaction
    // dbRecords for maximum integer values
    private Console console = new Console();;
    private int dbRecords;

    // Main method to start the SpringBoot application
    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementApp.class, args);
    }

    // Uses a while loop to display a menu and user chooses from options to interact with product inventory
    // Continues until the option to exit is selected
    public void run(String[] args) {
        String enter = "Press Enter to return to main menu";
        MenuOptions option;
        do {
            dbRecords = productRepository.findAll().size() + 500;
            option = console.displayMainMenu();
            switch (option) {
                case ADD_PRODUCT:
                    addProduct();
                    console.pressEnter(enter);
                    break;
                case VIEW_PRODUCTS:
                    viewProducts();
                    console.pressEnter("\n" + enter);
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

    // Requests a product ID that is then verified against the database
    // If product exists in the database the operation stops
    // If product doesn't exist, the user must enter a product name, quantity and price
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

    // A display of all the products in the database is presented with ID, Name, Quantity, Price
    // Uses the ProductRepository method from JpaRepository findAll()
    public void viewProducts() {
        List<Product> products = productRepository.findAll();
        console.displayHeader("===== Inventory List =====");
        String name = "Name";
        String ID = "ID";
        String quantity = "Quantity";
        String price = "Price";
        console.displayHeader(String.format("%-3s | %-13s | %-12s | %-1s", ID, name, quantity, price));
        console.displayHeader("----------------------------------------");
        for (Product product : products) {
            System.out.println(product.productLineString());
        }
        console.displayHeader("----------------------------------------");
    }

    // User must enter a product ID or name to search the inventory database for existing product
    // Checks whether user entered a numerical value for ID or a String-based name
    // After the input value type is determine, the value is checked against the database
    // Any matches will return the product information - ProductID, Name, Quantity, Price
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

    // User must enter a numerical product ID within the acceptable range of values
    // If not product found the operation stops
    // If product is found, application displays current product details
    // Application requests a new quantity, user can skip and keep original
    //      - The quantity is validated for negatives, non-numerical values
    // Application requests a new price, user can skip and keep original
    //      - The price is validated for zero, negative, and non-numerical values
    // Valid new entries are set to product properties updating the product details or product is unmodified if no new values
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

    // User must enter a numerical product ID within the acceptable range of values
    // If not product found the operation stops
    // If product is found, application displays a confirmation prompt
    // User must enter a specified value of Y or N to confirm deletion or continue without deletion
    // After confirming deletion the product with given Product ID will be deleted
    public void deleteProduct() {
        console.displayHeader("===== Delete Product =====");
        int productId = console.readInt("Enter Product ID:", 100, dbRecords);
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            while (true) {
                String deletion = console.readRequiredString("\nAre you sure you want to delete? (Y/N):\n");
                if (deletion.equals("y")) {
                    productRepository.delete(product.get());
                    console.displayHeader("Product successfully deleted.");
                    break;
                }
                if (deletion.equals("n")) {
                    break;
                } else {
                    console.displayHeader("Enter (Y/N)");
                }
            }
        } else {
            console.displayHeader("Product not found!");
        }
    }

}
