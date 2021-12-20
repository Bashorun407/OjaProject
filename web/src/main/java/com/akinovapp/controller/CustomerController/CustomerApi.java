package com.akinovapp.controller.CustomerController;

import com.akinovapp.domain.dao.CustomerDto;
import com.akinovapp.domain.entity.Customer;
import com.akinovapp.service.customerservice.CustomerService;
import com.akinovapp.service.responsepojo.ResponsePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerApi {

    @Autowired
    private CustomerService customerService;


    //(1) Method to Create Customer
    @PostMapping("/createCustomer")
    public ResponsePojo<Customer> createCustomer(@RequestBody CustomerDto customerDto){

        return customerService.createCustomer(customerDto);
    }

    //(2) Method to get a customer by Id
    @GetMapping("/getCustomer/{Id}")
    public ResponsePojo<Customer> getCustomer(@PathVariable Long Id){

        return customerService.getCustomer(Id);
    }

    //(3) Method to get a List of Customers
    @GetMapping("/allCustomers")
    public ResponsePojo<List<Customer>> getAllCustomers(){

        return customerService.getAllCustomers();
    }


    //(4) Search with QueryDSL
    @GetMapping("/search")
    public ResponsePojo<List<Customer>> searchCustomers(@RequestParam(name = "firstName", required = false) String firstName,
                                                        @RequestParam(name = "lastName", required = false) String lastName,
                                                        @RequestParam(name = "ustomerNumber", required = false) Long customerNumber,
                                                        @RequestParam(name = "country", required = false) String country,
                                                        Pageable pageable){

        return customerService.searchCustomers(firstName, lastName, customerNumber, country, pageable);
    }


    //(5) Updating Customers detail
    @PutMapping("/update")
    public ResponsePojo<Customer> updateCustomer(@RequestBody CustomerDto customerDto){

        return customerService.updateCustomer(customerDto);
    }

    //(6) Method to delete a customer's detail...its not really going to delete though.
    @DeleteMapping("/delete")
    public String deleteCustomer(@RequestBody CustomerDto customerDto){

        return customerService.deleteCustomer(customerDto);
    }

    //(7) Method to return a list of deleted Customers details
    @GetMapping("/deletedCustomers")
    public ResponsePojo<List<Customer>> deletedCustomers(){

        return customerService.deletedCustomers();
    }
}

