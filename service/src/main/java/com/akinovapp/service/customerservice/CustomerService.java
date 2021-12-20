package com.akinovapp.service.customerservice;

import com.akinovapp.domain.dao.CustomerDto;
import com.akinovapp.domain.entity.Customer;
import com.akinovapp.domain.entity.QCustomer;
import com.akinovapp.service.exception.ApiRequestException;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.repository.CustomerReppo;
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
public class CustomerService {

    @Autowired
    private CustomerReppo customerReppo;

    @Autowired
    private EntityManager entityManager;

    //(1) Method to Create Customer
    public ResponsePojo<Customer> createCustomer(CustomerDto customerDto){

        if(!StringUtils.hasText(customerDto.getFirstName()))
            throw new ApiRequestException("First Name is needed to profile customer.");

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setCustomerNumber(new Date().getTime());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAccountBalance(customerDto.getAccountBalance());
        customer.setCountry(customerDto.getCountry());
        customer.setDeletedStatus(false);
        customer.setDateCreated(new Date());

        customerReppo.save(customer);

        ResponsePojo<Customer> responsePojo = new ResponsePojo<>();
        responsePojo.setData(customer);
        responsePojo.setMessage("Customer Profiled successfully");

        return responsePojo;
    }

    //(2) Method to get a customer by Id
    public ResponsePojo<Customer> getCustomer(Long Id){


        Optional<Customer> customerOptional= customerReppo.findById(Id);
        customerOptional.orElseThrow(()-> new ApiRequestException("There is no Customer with this Id"));

        Customer cust = customerOptional.get();

        if(cust.getDeletedStatus()!=false)
            throw new ApiRequestException("The item requested has been deleted.");

        ResponsePojo<Customer> responsePojo = new ResponsePojo<>();
        responsePojo.setData(cust);
        responsePojo.setMessage("Customer found!!");

        return responsePojo;
    }

    //(3) Method to get a List of Customers
    public ResponsePojo<List<Customer>> getAllCustomers(){

        List<Customer> allCustomers = customerReppo.findAll();
        if(allCustomers.isEmpty())
            throw new ApiRequestException("There is no Customer in the List");

        //To check that only the non-deleted items are entered
        List<Customer> filteredCustomers = allCustomers.stream().filter(x->x.getDeletedStatus()==false).collect(Collectors.toList());
        ResponsePojo<List<Customer>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(filteredCustomers);
        responsePojo.setMessage("List of all Customers");

        return responsePojo;
    }

    //(4) Search with QueryDSL
    public ResponsePojo<List<Customer>> searchCustomers(String firstName, String lastName, Long customerNumber,  String country,Pageable pageable){

        QCustomer qCustomer = QCustomer.customer;
        BooleanBuilder predicate = new BooleanBuilder();

        if(StringUtils.hasText(firstName))
            predicate.and(qCustomer.firstName.likeIgnoreCase("%s" + firstName + "%s"));


        if(StringUtils.hasText(lastName))
            predicate.and(qCustomer.lastName.likeIgnoreCase("%s" + lastName + "%s"));


        if(StringUtils.hasText(country))
            predicate.and(qCustomer.country.likeIgnoreCase("%s" + country + "%s"));

        if(!ObjectUtils.isEmpty(customerNumber))
            predicate.and(qCustomer.customerNumber.eq(customerNumber));

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Customer> jpaQuery = jpaQueryFactory.selectFrom(qCustomer)
                .where(predicate.and(qCustomer.deletedStatus.eq(false)))
                .orderBy(qCustomer.Id.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

       // Page<Customer> customerPage = new PageImpl<>(jpaQuery.fetch(), pageable, jpaQuery.fetchCount());

        List<Customer> customerList = jpaQuery.fetch();

        ResponsePojo<List<Customer>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(customerList);
        responsePojo.setMessage("List found!!");

        return responsePojo;
    }

    //(5) Updating Customers detail
    public ResponsePojo<Customer> updateCustomer(CustomerDto customerDto){

        Optional<Customer> findCustomer1 = customerReppo.findByCustomerNumber(customerDto.getCustomerNumber());
        findCustomer1.orElseThrow(()->new ApiRequestException("There is no customer with this Customer Number."));

        Optional<Customer> findCustomer2 = customerReppo.findByEmail(customerDto.getEmail());
        findCustomer2.orElseThrow(()->new ApiRequestException("There is no Customer with this email."));

        Customer custom1 = findCustomer1.get();
        Customer custom2 = findCustomer2.get();

        //Checking that the correct Customer detail is gotten
        if(custom1 != custom2)
            throw  new ApiRequestException("The details entered are for different Customers.");

        custom1.setFirstName(customerDto.getFirstName());
        custom1.setLastName(customerDto.getLastName());
        //custom1.setEmail(customerDto.getEmail());
        custom1.setPhoneNumber(customerDto.getPhoneNumber());
        custom1.setCountry(customerDto.getCountry());

        customerReppo.save(custom1);//Saving the detail into the database

        ResponsePojo<Customer> responsePojo = new ResponsePojo<>();
        responsePojo.setData(custom1);
        responsePojo.setMessage("Customer detail successfully updated.");

        return responsePojo;
    }

    //(6) Method to delete a customer's detail...its not really going to delete though.
    public String deleteCustomer(CustomerDto customerDto){


        //Finding Customer by last name
        Optional<Customer> findCustomer3 = customerReppo.findByCustomerNumber(customerDto.getCustomerNumber());
        findCustomer3.orElseThrow(()->new ApiRequestException(" There is no Customer with this Customer Number."));

        //Finding Customer by country
        Optional<Customer> findCustomer4 = customerReppo.findByEmail(customerDto.getEmail());
        findCustomer4.orElseThrow(()->new ApiRequestException(" There is no Customer with this email."));

        Customer c3 = findCustomer3.get();
        Customer c4 = findCustomer4.get();

        if(c3 != c4)
            throw new ApiRequestException("The details entered are not for the same Customer.");

        c3.setDeletedStatus(true);
        customerReppo.save(c3);

        return "Customer deleted";
    }

    //(7) Method to return a list of deleted Customers details
    public ResponsePojo<List<Customer>> deletedCustomers(){

        QCustomer qCustomer = QCustomer.customer;
        //BooleanBuilder predicate = new BooleanBuilder();...a predicate is not really needed here

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<Customer> jpaQuery = jpaQueryFactory.selectFrom(qCustomer)
                .where(qCustomer.deletedStatus.eq(true));

        List<Customer> deletedCustomers = jpaQuery.fetch();

        ResponsePojo<List<Customer>> responsePojo = new ResponsePojo<>();
        responsePojo.setData(deletedCustomers);
        responsePojo.setMessage("Here is the List of deleted Customers");

        return responsePojo;
    }
}
