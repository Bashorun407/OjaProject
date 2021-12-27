package com.akinovapp.service.ratingservice;

import com.akinovapp.domain.entity.Rating;
import com.akinovapp.service.exception.ApiRequestException;
import com.akinovapp.service.responsepojo.ResponsePojo;

import com.akinovapp.repository.RatingReppo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class RatingService {

    @Autowired
    private RatingReppo ratingReppo;

    //(1) Method to input rating of Product
    public ResponsePojo<Rating> getRating(Long productNumber, Long rateNumber){

        Optional<Rating> getRating = ratingReppo.findRatingByProductNumber(productNumber);
        getRating.orElseThrow(()-> new ApiRequestException("No Product has this Product-Number"));

        //To check that the rateNumber provided is from 1 - 5
        if(rateNumber<1 && rateNumber>5)
            throw new ApiRequestException("Rate Number is out of bounds...select from 1 - 5");

        //Initializing Rating variables
        Rating rateObj = getRating.get();

        //Setting variables
        long count = 1;
        Long num;
        Long review = rateObj.getReviews();

        //For New Rating of a particular product
        if(review==null) {
            rateObj.setReviews(count);
            //To implement the first Rating of the Product
            num= function.apply(rateNumber);
            rateObj.setRating(num);

        }

        //For Continuous rating of a particular product
        if(review!=null){
            rateObj.setReviews(rateObj.getReviews() + count);

            //To implement the first Rating of the Product
            num= function.apply(rateNumber);
            rateObj.setRating(rateObj.getRating() + num);
        }

            //To save the change to the repository
            ratingReppo.save(rateObj);//Saves all the implementations

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
