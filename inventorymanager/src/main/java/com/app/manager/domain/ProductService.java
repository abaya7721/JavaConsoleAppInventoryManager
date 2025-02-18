package com.app.manager.domain;

import com.app.manager.model.Product;
import com.app.manager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {


    @Autowired
    private ProductRepository productRepository;


    public void addProductToInventory(){

    }

    public void showProductsInInventory(){
        List<Product> products = productRepository.findAll();
        System.out.println("==== Inventory List ====");
        System.out.println("----------------------------------------");
        System.out.println("ID  | Name        | Quantity | Price  ");
        for(Product product : products){
            System.out.println(product.getProductId()+"   | "+product.getName()+"     | "+product.getQuantity()+"       | $"+product.getPrice());
        }
        System.out.println("----------------------------------------");
    }
}
