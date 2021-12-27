package com.akinovapp.controller.RatingController;

import com.akinovapp.domain.dao.RatingDto;
import com.akinovapp.domain.entity.Rating;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.service.ratingservice.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingApi {

    @Autowired
    private RatingService ratingService;


    //(1) Method to input rating of Product
    @PostMapping("/point/{productNumber}")
    public ResponsePojo<Rating> getRating(@PathVariable Long productNumber, Long rateNumber){

        return ratingService.getRating(productNumber, rateNumber);
    }
}
