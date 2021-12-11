package com.akinovapp.controller.RatingController;

import com.akinovapp.domain.dao.RatingDto;
import com.akinovapp.domain.entity.Rating;
import com.akinovapp.service.responsepojo.ResponsePojo;
import com.akinovapp.service.ratingservice.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rating")
public class RatingApi {

    @Autowired
    private RatingService ratingService;


    //(1) Method to input rating of Product
    @PostMapping("/point")
    public ResponsePojo<Rating> getRating(@RequestBody RatingDto ratingDto){

        return ratingService.getRating(ratingDto);
    }
}
