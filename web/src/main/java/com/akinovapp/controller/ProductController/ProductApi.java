package com.akinovapp.controller.ProductController;

import com.akinovapp.domain.dao.ProductDto;
import com.akinovapp.domain.entity.Product;
import com.akinovapp.service.productservice.ProductService;
import com.akinovapp.service.responsepojo.ResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")

public class ProductApi {

    @Autowired
    private ProductService productService;

    //***** METHOD 1 IS NOT NEEDED BECAUSE SERVICE HAS BEEN INITIATED IN THE SHOPAPI CLASS
//
//    //(1) Method to create a Product
//    @PostMapping("/createProduct")
//    public ResponsePojo<Product> createProduct(@RequestBody ProductDto productDto){
//
//        return productService.createProduct(productDto);
//    }


    //(2) Method to get a specific Product by Id
    @GetMapping("/product/{Id}")
    public ResponsePojo<Product> getProduct(@PathVariable  Long Id){

        return productService.getProduct(Id);
    }

    //(3) Method to get a List of all Products
    @GetMapping("/allProducts")
    public ResponsePojo<List<Product>> getAllProducts(){

        return productService.getAllProducts();
    }

    //(4) Method to search for Products based on given criteria
    @GetMapping("/search/item")
    public ResponsePojo<List<Product>> searchProduct( @RequestParam String item, @RequestParam Long num){

        return productService.searchProduct(item, num);
    }

    //(5) Method to update Product
    @PutMapping("/update")
    public ResponsePojo<Product> updateProduct(@RequestBody ProductDto productDto){

        return productService.updateProduct(productDto);
    }

    //(6) Method to delete Product
    @DeleteMapping("/delete")
    public ResponsePojo<String> deleteProduct(@RequestBody ProductDto productDto){

        return productService.deleteProduct(productDto);
    }

    //(7) Method to return a list of deleted items
    @GetMapping("/deletedProducts")
    public ResponsePojo<List<Product>> deletedProducts(){

        return productService.deletedProducts();
    }

}
