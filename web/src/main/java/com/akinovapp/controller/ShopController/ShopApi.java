package com.akinovapp.controller.ShopController;

import com.akinovapp.domain.dao.ShopDto;
import com.akinovapp.domain.entity.Shop;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.service.shopservice.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopApi {

    @Autowired
    private ShopService shopService;

    //(1) Method to create shop
    @PostMapping("/createShop")
    public ResponsePojo<Shop> createShop(ShopDto shopDto) {

        return shopService.createShop(shopDto);
    }

    //(2) Method to find shop by ID
    @GetMapping("/getShop")
    public ResponsePojo<Shop> getShopById(Long Id){

        return shopService.getShopById(Id);
    }

    //(3) Method to get a list of all the shops
    @GetMapping("/getAllShops")
    public ResponsePojo<List<Shop>> getAllShops(){

        return shopService.getAllShops();
    }

    //(4) Method to search for a company based on certain products they sell...just trying it out
    @GetMapping("/searchShop")
    public ResponsePojo<List<Shop>> searchShop (String companyName, String prodName, String country){

        return shopService.searchShop(companyName, prodName, country);
    }

    //(5) Method to update Shop
    @PutMapping("/update")
    public ResponsePojo<Shop> update(ShopDto shopDto){

        return shopService.update(shopDto);
    }

    //(6) Method to delete shop
    @DeleteMapping("/deleteShop")
    public ResponsePojo<String> deleteShop(ShopDto shopDto){

        return shopService.deleteShop(shopDto);
    }


    //(7) Method to return the list of deleted Shops
    @GetMapping("/deletedShops")
    public ResponsePojo<List<Shop>> deletedShops(){

        return shopService.deletedShops();
    }

}