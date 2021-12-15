package com.akinovapp.service.shopservice;

import com.akinovapp.domain.dao.ShopDto;
import com.akinovapp.domain.entity.Product;
import com.akinovapp.domain.entity.QProduct;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        if(!(StringUtils.hasText(shopDto.getCompanyName())) && !(StringUtils.hasText(shopDto.getProductName())))
            throw new ApiRequestException("Company-name and Product-name is required to create a Shop...ensure to input both.");

        Shop shop = new Shop();//Shop Object
        Product product = new Product();//Product Object

        shop.setCompanyName(shopDto.getCompanyName());
        product.setCompanyName(shopDto.getCompanyName());

        shop.setProductName(shopDto.getProductName());
        product.setProductName(shopDto.getProductName());

        product.setProductNumber(new Date().getTime());

        shop.setPrice(shopDto.getPrice());
        product.setPrice(shopDto.getPrice());

        shop.setQuantity(shopDto.getQuantity());
        product.setQuantity(shopDto.getQuantity());

        shop.setPhoneNumber(shopDto.getPhoneNumber());

        shop.setDateListed(new Date());
        product.setDateListed(new Date());

        shop.setCountry(shopDto.getCountry());
        shop.setDeletedStatus(false);
        product.setDeleteStatus(false);

        shopReppo.save(shop); //Saving shop details into its repository
        productReppo.save(product);  //Saving product into its repository

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

        //To filter and collect only those shops whose delete status is false
        List<Shop> filteredShops = allShops.stream().filter(x-> x.getDeletedStatus()==false).collect(Collectors.toList());

        if(allShops.isEmpty())
            throw new ApiRequestException("The shops list is empty");

        ResponsePojo<List<Shop>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(filteredShops);
        responsePojo.setMessage("List of all Shops");

        return responsePojo;
    }

    //(4) Method to search for a company based on certain products they sell...just trying it out
    public ResponsePojo<List<Shop>> searchShop (String companyName, String prodName, String country, Pageable pageable){

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
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize());

        //This Page<Shop> is not working as it should...that is why I commented it out.
       // Page<Shop> shopPage = new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.stream().count());
        List<Shop> shopList = jpaQuery.fetch();

        ResponsePojo<List<Shop>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shopList);
        responsePojo.setMessage("Here is the list of shops");

        return responsePojo;
    }

    //(5) Method to update Shop
    public ResponsePojo<Shop> update(ShopDto shopDto){

        Optional<Shop> findShop1 = shopReppo.findShopByPhoneNumber(shopDto.getPhoneNumber());
        findShop1.orElseThrow(()->new ApiRequestException(String.format("There is no Shop with this phone number: %s.", shopDto.getPhoneNumber())));

        Optional<Shop> findShop2 = shopReppo.findShopByCompanyName(shopDto.getCompanyName());
        findShop2.orElseThrow(()->new ApiRequestException("There is Shop with this company name."));

        Shop shop1 = findShop1.get();
        Shop shop2 = findShop2.get();

        //Checking that the correct Customer detail is gotten
        if(shop1 != shop2)
            throw  new ApiRequestException("The details entered are for different Customers.");

        //Since we are searching shops with these two fields(CompanyName and PhoneNumber), it is not wise to edit them
//        shop1.setCompanyName(shopDto.getCompanyName());
//        shop1.setPhoneNumber(shopDto.getPhoneNumber());
        shop1.setProductName(shopDto.getProductName());
        shop1.setPrice(shopDto.getPrice());
        shop1.setQuantity(shopDto.getQuantity());
        shop1.setCountry(shopDto.getCountry());

        shopReppo.save(shop1);//Saving the detail into the database

        ResponsePojo<Shop> responsePojo = new ResponsePojo<>();
        responsePojo.setData(shop1);
        responsePojo.setMessage("Customer detail successfully updated.");

        return responsePojo;
    }

   //(6) Method to delete shop
    public ResponsePojo<String> deleteShop(ShopDto shopDto){

        Optional<Shop> findShop1 = shopReppo.findShopByPhoneNumber(shopDto.getPhoneNumber());
        findShop1.orElseThrow(()->new ApiRequestException(String.format("There is no Shop with this Phone Number: %s", shopDto.getPhoneNumber())));

        Optional<Shop> findShop2 = shopReppo.findShopByCompanyName(shopDto.getCompanyName());
        findShop2.orElseThrow(()->new ApiRequestException(String.format("There is Shop with this company name : %s", shopDto.getCompanyName())));

        Shop shop1 = findShop1.get();
        Shop shop2 = findShop2.get();

        //Checking that the correct Customer detail is gotten
        if(shop1 != shop2)
            throw  new ApiRequestException("The details entered are for different Customers.");

        shop1.setDeletedStatus(true);//...does not really delete the shop....but only changes the Shop's deletedStatus

        //To set the delete status of all products in the deleted shop as deleted too....using QUERY DSL
        QProduct qProduct = QProduct.product;

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                        .where(qProduct.companyName.eq(shop2.getCompanyName()));

        List<Product> companyProducts = jpaQuery.fetch();
        companyProducts.stream().map(x->{
            x.setDeleteStatus(true);
            productReppo.save(x);//To save the change into the productReppo repository
       return x; });//...I dont know if this will work...I want to be able to set the deletedStatus of each record in this category as false

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
