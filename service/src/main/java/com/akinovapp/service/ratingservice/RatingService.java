package com.akinovapp.service.ratingservice;

import com.akinovapp.domain.dao.RatingDto;
import com.akinovapp.domain.entity.Customer;
import com.akinovapp.domain.entity.Product;
import com.akinovapp.domain.entity.Rating;
import com.akinovapp.service.exception.ApiRequestException;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.repository.CustomerReppo;
import com.akinovapp.repository.ProductReppo;
import com.akinovapp.repository.RatingReppo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class RatingService {

    @Autowired
    private RatingReppo ratingReppo;

    @Autowired
    private ProductReppo productReppo;

    @Autowired
    private CustomerReppo customerReppo;

    //(1) Method to input rating of Product
    public ResponsePojo<Rating> getRating(RatingDto ratingDto){


        Optional<Product> getProduct1 = productReppo.findByProductName(ratingDto.getProductName());
        getProduct1.orElseThrow(()-> new ApiRequestException("No Product has this Product-Name"));

        Optional<Product> getProduct2 = productReppo.findProductByProductNumber(ratingDto.getProductNumber());
        getProduct2.orElseThrow(()-> new ApiRequestException("No Product has this Product-Number"));

        Optional<Customer> getCustomer = customerReppo.findByEmail(ratingDto.getCustomerEmail());
        getCustomer.orElseThrow(()->new ApiRequestException("No Customer has this email address."));

        Product p1 = getProduct1.get();
        Product p2 = getProduct2.get();
        Customer c1 = getCustomer.get();

        if(p1 != p2)
            throw new ApiRequestException("Product-Name and Product-Number entered are for different Products");

        //Initializing Rating variables
        Rating rate = new Rating();

        rate.setProductName(p1.getProductName());
        rate.setProductNumber(p1.getProductNumber());
        rate.setCustomerEmail(c1.getEmail());

        rate.setReviews(rate.getReviews()+1);

        Long num= function.apply(ratingDto);

        rate.setRating(rate.getRating() + num);

        ratingReppo.save(rate);

        ResponsePojo<Rating> responsePojo = new ResponsePojo<>();
        responsePojo.setData(rate);
        responsePojo.setMessage("Rating accepted.");

        return responsePojo;

    }


    //Class function to sort out rating of pupils
    Function<RatingDto, Long> function = p ->{

        Long rate = p.getRating();

        if(rate==5)
            return rate;

        if(rate ==4)
            return (rate * 80/100);

        if(rate == 3)
            return (rate * 60/100);

        if(rate == 2)
            return (rate * 40/100);

        if(rate == 1)
            return (rate * 20/100);

        else return rate;

    };
}
