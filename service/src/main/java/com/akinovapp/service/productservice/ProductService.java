package com.akinovapp.service.productservice;

import com.akinovapp.domain.dao.ProductDto;
import com.akinovapp.domain.entity.Product;
import com.akinovapp.domain.entity.QProduct;
import com.akinovapp.service.exception.ApiRequestException;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.repository.ProductReppo;
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
public class ProductService {

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private EntityManager entityManager;//For QueryDSL

    /*
       **** PRODUCT HAS BEEN INITIATED IN THE SHOP CLASS ****
     */

    //(1) Method to create a Product
    public ResponsePojo<Product> createProduct(ProductDto productDto){

        if(!StringUtils.hasText(productDto.getProductName()))
            throw new ApiRequestException("Product requires a name to be created....");

        Product product = new Product();
        product.setProductName(productDto.getProductName());//*
        product.setProductNumber(new Date().getTime());
        product.setPrice(productDto.getPrice());//*
        product.setQuantity(productDto.getQuantity());//*
        product.setCompanyName(productDto.getCompanyName());//*
        product.setDateListed(new Date());
        product.setDeleteStatus(false);

        productReppo.save(product);

        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(product);
        responsePojo.setMessage("Product successfully created");

        return responsePojo;
    }


    //(2) Method to get a specific Product by Id
    public ResponsePojo<Product> getProduct(Long Id){

        Optional<Product> productOptional = productReppo.findById(Id);
        productOptional.orElseThrow(()-> new ApiRequestException(String.format("No Product with this ID: %s ", Id)));

        Product prod = productOptional.get();

        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(prod);
        responsePojo.setMessage(String.format("Product with this ID: %s found", Id));

        return responsePojo;
    }

    //(3) Method to get a List of all Products
    public ResponsePojo<List<Product>> getAllProducts(){

        List<Product> allProducts = productReppo.findAll();

        if(allProducts.isEmpty())
            throw new ApiRequestException("The list of Products is empty");

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(allProducts);
        responsePojo.setMessage("This is the list of all products");

        return responsePojo;
    }

    //(4) Method to search for Products based on given criteria
    public ResponsePojo<List<Product>> searchProduct(String item, Long num){
        QProduct qProduct = QProduct.product;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(item))
            predicate.and(qProduct.productName.likeIgnoreCase("%s" + item + "%s"));

        if(StringUtils.hasText(item))
            predicate.and(qProduct.companyName.likeIgnoreCase("%s" + item + "%s"));

        if(ObjectUtils.isEmpty(num))
            predicate.and(qProduct.productNumber.eq(num));


        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(predicate.and(qProduct.deleteStatus.eq(false)))
                .orderBy(qProduct.Id.asc());

        List<Product> result = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(result);
        responsePojo.setMessage("Items Search successful!!");

        return  responsePojo;
    }


    //(5) Method to update Product
    public ResponsePojo<Product> updateProduct(ProductDto productDto){

        Optional<Product> findProduct1 = productReppo.findById(productDto.getId());
        findProduct1.orElseThrow(()->new ApiRequestException(String.format("Product with this ID: %s, not found!", productDto.getId())));

        Optional<Product> findProduct2 = productReppo.findProductByProductNumber(productDto.getProductNumber());
        findProduct2.orElseThrow(()-> new ApiRequestException(String.format("Product with this Product-Number: %s not found", productDto.getProductNumber())));

        Product p1 = findProduct1.get();
        Product p2 = findProduct2.get();

        //Checking that the correct Product is gotten
        if(p1 != p2)
            throw new ApiRequestException("The Product Id and Product-Number are for different products.");


        Product prod = findProduct1.get();
        prod.setProductName(productDto.getProductName());
        prod.setPrice(productDto.getPrice());
        prod.setQuantity(productDto.getQuantity());
        prod.setCompanyName(productDto.getCompanyName());
        productReppo.save(prod);

        ResponsePojo<Product> responsePojo = new ResponsePojo<>();
        responsePojo.setData(prod);
        responsePojo.setMessage("Product successfully updated!!");

        return responsePojo;
    }

    //(6) Method to delete Product
    public ResponsePojo<String> deleteProduct(ProductDto productDto){

        Optional<Product> find1 = productReppo.findById(productDto.getId());
        find1.orElseThrow(()->new ApiRequestException(String.format("Product with this ID: %s, not found!", productDto.getId())));

        Optional<Product> find2 = productReppo.findProductByProductNumber(productDto.getProductNumber());
        find2.orElseThrow(()-> new ApiRequestException(String.format("Product with this Product-Number: %s not found", productDto.getProductNumber())));

        Product p1 = find1.get();
        Product p2 = find2.get();

        //Checking that the correct Product is gotten
        if(p1 != p2)
            throw new ApiRequestException("The Product Id and Product-Number are for different products.");

        Product prod = find1.get();
        prod.setDeleteStatus(true);

        productReppo.save(prod);

        ResponsePojo<String> responsePojo = new ResponsePojo<>();
        responsePojo.setMessage("Successfully Deleted.");

        return responsePojo;

    }

    //(7) Method to return a list of deleted items
    public ResponsePojo<List<Product>> deletedProducts(){

        QProduct qProduct = QProduct.product;
        //BooleanBuilder predicate = new BooleanBuilder();

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Product> jpaQuery = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.deleteStatus.eq(true));

        List<Product> deletedItems = jpaQuery.fetch();

        ResponsePojo<List<Product>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(deletedItems);
        responsePojo.setMessage("These are the deleted Products.");

        return responsePojo;

    }
}
