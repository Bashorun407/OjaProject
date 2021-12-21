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
import org.springframework.util.ObjectUtils;

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
    public ResponsePojo<Rating> getRating(Long productNumber, Long rateNumber){

        Optional<Product> getProduct2 = productReppo.findProductByProductNumber(productNumber);
        getProduct2.orElseThrow(()-> new ApiRequestException("No Product has this Product-Number"));

        Optional<Rating> getRatingObj = ratingReppo.findRatingByProductNumber(productNumber);

        //Initializing Rating variables
        Product p2 = getProduct2.get();

        Rating rateObj = getRatingObj.get();
        long count = 1;
        Long num;
        //Conditional to check  if a product has not been given a rating or review before
        if(ObjectUtils.isEmpty(getRatingObj.get())){

            rateObj.setProductName(p2.getProductName());
            rateObj.setProductNumber(p2.getProductNumber());
            //To set number of Review to 1...since this is the first review of the Product
            rateObj.setReviews(count);

            //To implement the first Rating of the Product
            num= function.apply(rateNumber);
            rateObj.setRating(num);

            //To save the change to the repository
           ratingReppo.save(rateObj);

        }

        //Conditional to check  if a product has been given a rating or review before
        if(!ObjectUtils.isEmpty(getRatingObj.get())){
            //To increment review of Product
            rateObj.setReviews(rateObj.getReviews() + count);

            //To implement rating of Product
            num = function.apply(rateNumber);
            rateObj.setRating(rateObj.getRating() + num);

            //To save the change to the repository
            ratingReppo.save(rateObj);
        }

        ResponsePojo<Rating> responsePojo = new ResponsePojo<>();
        responsePojo.setData(rateObj);
        responsePojo.setMessage("Rating accepted.");

        return responsePojo;

    }


    //Class function to sort out rating of Product
    Function<Long, Long> function = p ->{

        Long rate = p;

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
