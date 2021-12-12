package com.akinovapp.service.shopservice;

import com.akinovapp.domain.dao.ShopDto;
import com.akinovapp.domain.entity.Product;
import com.akinovapp.domain.entity.QShop;
import com.akinovapp.domain.entity.Shop;
import com.akinovapp.service.exception.ApiRequestException;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.repository.ProductReppo;
import com.akinovapp.repository.ShopReppo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopService {

    @Autowired
    private ShopReppo shopReppo;

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) Method to create shop
    public ResponsePojo<Shop> createShop(ShopDto shopDto){

        if((shopDto.getCompanyName().isEmpty()) && (shopDto.getProductName().isEmpty()))
            throw new ApiRequestException("Company-name and Product-name is required to create a Shop...ensure to input both.");

        Shop shop = new Shop();//Shop Object
        Product product = new Product();//Product Object

        shop.setCompanyName(shopDto.getCompanyName());
        product.setCompanyName(shop.getCompanyName());

        shop.setProductName(shopDto.getProductName());
        product.setProductName(shop.getProductName());

        product.setProductNumber(new Date().getTime());

        shop.setPrice(shopDto.getPrice());
        product.setPrice(shop.getPrice());

        shop.setQuantity(shopDto.getQuantity());
        product.setQuantity(shop.getQuantity());

        shop.setPhoneNumber(shopDto.getPhoneNumber());

        shop.setDateListed(new Date());
        product.setDateListed(new Date());

        shop.setCountry(shopDto.getCountry());
        shop.setDeletedStatus(false);
        product.setDeleteStatus(false);

        shopReppo.save(shop); //Saving shop details into its repository
        productReppo.save(product);  //Saivng product into its repository

        ResponsePojo<Shop> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shop);
        responsePojo.setMessage("Shop successfully created.");

        return responsePojo;
    }

    //(2) Method to find shop by ID
    public ResponsePojo<Shop> getShopById(Long Id){

        if(ObjectUtils.isEmpty(Id))
            throw new ApiRequestException("Id is required to find shop");

        Optional<Shop> shopOptional = shopReppo.findById(Id);
        shopOptional.orElseThrow(()-> new ApiRequestException(String.format("There is no Shop with this ID: %s.", Id)));

        Shop shop = shopOptional.get();
        ResponsePojo<Shop> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shop);
        responsePojo.setMessage("Shop found.");

        return responsePojo;
    }

    //(3) Method to get a list of all the shops
    public ResponsePojo<List<Shop>> getAllShops(){

        List<Shop> allShops = shopReppo.findAll();

        if(allShops.isEmpty())
            throw new ApiRequestException("The shops list is empty");

        ResponsePojo<List<Shop>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(allShops);
        responsePojo.setMessage("List of all Shops");

        return responsePojo;
    }

    //(4) Method to search for a company based on certain products they sell...just trying it out
    public ResponsePojo<List<Shop>> searchShop (String companyName, String prodName, String country){

        QShop qShop = QShop.shop;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(companyName))
            predicate.and(qShop.companyName.likeIgnoreCase("%s" + companyName + "%s"));

        if(StringUtils.hasText(prodName))
            predicate.and(qShop.productName.likeIgnoreCase("%s" + prodName + "%s"));

        if(StringUtils.hasText(country))
            predicate.and(qShop.country.likeIgnoreCase("%s" + country + "%s"));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Shop> jpaQuery = jpaQueryFactory.selectFrom(qShop)
                .where(predicate.and(qShop.deletedStatus.eq(false)))
                .orderBy(qShop.Id.asc());

        List<Shop> shopList = jpaQuery.fetch();

        ResponsePojo<List<Shop>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shopList);
        responsePojo.setMessage("Here is the list of shops");

        return responsePojo;
    }

    //(5) Method to update Shop
    public ResponsePojo<Shop> update(ShopDto shopDto){

        Optional<Shop> findShop1 = shopReppo.findById(shopDto.getId());
        findShop1.orElseThrow(()->new ApiRequestException("There is no Shop with this ID."));

        Optional<Shop> findShop2 = shopReppo.findShopByCompanyName(shopDto.getCompanyName());
        findShop2.orElseThrow(()->new ApiRequestException("There is Shop with this company name."));

        Shop shop1 = findShop1.get();
        Shop shop2 = findShop2.get();

        //Checking that the correct Customer detail is gotten
        if(shop1 != shop2)
            throw  new ApiRequestException("The details entered are for different Customers.");

        shop1.setCompanyName(shopDto.getCompanyName());
        shop1.setPhoneNumber(shopDto.getPhoneNumber());
        shop1.setCountry(shopDto.getCountry());

        shopReppo.save(shop1);//Saving the detail into the database

        ResponsePojo<Shop> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shop1);
        responsePojo.setMessage("Customer detail successfully updated.");

        return responsePojo;
    }

   //(6) Method to delete shop
    public ResponsePojo<String> deleteShop(ShopDto shopDto){

        Optional<Shop> findShop1 = shopReppo.findById(shopDto.getId());
        findShop1.orElseThrow(()->new ApiRequestException("There is no Shop with this ID."));

        Optional<Shop> findShop2 = shopReppo.findShopByCompanyName(shopDto.getCompanyName());
        findShop2.orElseThrow(()->new ApiRequestException("There is Shop with this company name."));

        Shop shop1 = findShop1.get();
        Shop shop2 = findShop2.get();

        //Checking that the correct Customer detail is gotten
        if(shop1 != shop2)
            throw  new ApiRequestException("The details entered are for different Customers.");

        shop1.setDeletedStatus(true);//...does not really delete the shop....but only changes the Shop's deletedStatus

        shopReppo.save(shop1);//Saving the detail into the database

        ResponsePojo<String> responsePojo = new ResponsePojo<>();
        responsePojo.setMessage("Customer detail successfully deleted.");

        return responsePojo;

    }

    //(7) Method to return the list of deleted Shops
    public ResponsePojo<List<Shop>> deletedShops(){

        QShop qShop = QShop.shop;
        //BooleanBuilder predicate = new BooleanBuilder();//Predicate is not really needed here

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Shop> jpaQuery = jpaQueryFactory.selectFrom(qShop)
                .where(qShop.deletedStatus.eq(true));

        List<Shop> deletedShops = jpaQuery.fetch();

        ResponsePojo<List<Shop>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(deletedShops);
        responsePojo.setMessage("Here is the list of deleted Shops");

        return responsePojo;
    }

}
