package com.akinovapp.service.transactionservice;

import com.akinovapp.domain.entity.Customer;
import com.akinovapp.domain.entity.Product;
import com.akinovapp.domain.entity.Shop;
import com.akinovapp.domain.entity.Transaction;
import com.akinovapp.service.exception.ApiRequestException;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.repository.CustomerReppo;
import com.akinovapp.repository.ProductReppo;
import com.akinovapp.repository.ShopReppo;
import com.akinovapp.repository.TransactionReppo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private CustomerReppo customerReppo;

    @Autowired
    private ShopReppo shopReppo;

    @Autowired
    private TransactionReppo transactionReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) Method to select and purchase Item(Products)
    public ResponsePojo<Transaction> buyItem(String productName, String companyName, Long quantity, String lastName, String email){

        //Getting Product Detail from Product Repository
        Optional<Product> findProduct = productReppo.findProductByProductNameAndCompanyName(productName, companyName);
        findProduct.orElseThrow(()-> new ApiRequestException("There is no product with the  Product-name and Company-name entered...check again"));

        //Getting the Shop where the product is bought
        Optional<Shop> findShop = shopReppo.findShopByCompanyName(companyName);
        findShop.orElseThrow(()-> new ApiRequestException("There is no Shop with this name."));


        //Getting Customer detail from Customer Repository through customer's Last name.
        Optional<Customer> findCustomer3 = customerReppo.findByLastName(lastName);
        findCustomer3.orElseThrow(()-> new ApiRequestException("There is no Customer with this last name."));
        //Getting Customer detail from Customer Repository through customerId
        Optional<Customer> findCustomer2 = customerReppo.findByEmail(email);
        findCustomer2.orElseThrow(()-> new ApiRequestException("There is no Customer with this Email."));


        //Creating objects from the Optionals and assigning values to new variables
        Product product = findProduct.get();
        Customer customer3 = findCustomer3.get();
        Customer customer2 = findCustomer2.get();

        //Checking that the details entered for Customer is correct and is for the same Object
        if(customer3 != customer2)
            throw new ApiRequestException("The Id and Email entered are for different Customers.");

        Shop shop = findShop.get();
        Long balance = customer3.getAccountBalance();
        Long totalCost = quantity * product.getPrice();

        Transaction transact = new Transaction();
        transact.setProductName(product.getProductName());
        transact.setProductNumber(product.getProductNumber());
        transact.setCompanyName(shop.getCompanyName());
        transact.setCustomerId(customer3.getId());
        transact.setAmount(totalCost);

        if(balance >=totalCost){

            transact.setPaymentStatus(true);
            transact.setTransactionNumber(new Date().getTime());
            transact.setTransactionDate(new Date());

            customer3.setAccountBalance(balance - totalCost);
            customerReppo.save(customer3);

            //Effecting changes in Quantity of Product available in Shop
//            shop.setQuantity(shop.getQuantity() - quantity);
            shopReppo.save(shop);

            //Effecting the change in the Quantity of Product available
            product.setQuantity(product.getQuantity() - quantity);
            productReppo.save(product);

            transactionReppo.save(transact);
        }

        ResponsePojo<Transaction> responsePojo = new ResponsePojo<>();
        responsePojo.setData(transact);
        responsePojo.setMessage("Transaction details: ");

        return responsePojo;
    }

    //(2) Method to list all successful transactions
    public ResponsePojo<List<Transaction>> getAllTransaction(){

        List<Transaction> allTransaction = transactionReppo.findAll();
        if(allTransaction.isEmpty())
            throw new ApiRequestException("There is no record of any transaction yet.");

        ResponsePojo<List<Transaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(allTransaction);
        responsePojo.setMessage("List of all Transaction");

        return responsePojo;
    }

    //(3) Method to get a specific Transaction
    public ResponsePojo<Transaction> getTransactionByTransactionNumber(Long transactionNumber){

        Optional<Transaction> transactionOptional = transactionReppo.findTransactionByTransactionNumber(transactionNumber);
        transactionOptional.orElseThrow(()->new ApiRequestException("There is no record of a transaction with this number"));

        Transaction transact = transactionOptional.get();

        ResponsePojo<Transaction> responsePojo = new ResponsePojo<>();
        responsePojo.setData(transact);
        responsePojo.setMessage(String.format("Transaction with transaction number %s found!!", transactionNumber));

        return responsePojo;
    }

    //(4) Method to get a list of transaction on a specific date
    public ResponsePojo<List<Transaction>> getTransactionOnADate(Date date){

        List<Transaction> transactionList = transactionReppo.findTransactionByTransactionDate(date);

        if(transactionList.isEmpty())
            throw new ApiRequestException("There was no transaction on this date.");

        ResponsePojo<List<Transaction>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(transactionList);
        responsePojo.setMessage(String.format("Transaction List on this date: %s found.", date));

        return responsePojo;
    }
}


