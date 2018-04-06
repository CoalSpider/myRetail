/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.myretail.repository;

import com.ben.myretail.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Ben Norman
 *
 * Class so we can pretend data coming from redsky is a internal source
 */
@Repository
public class RedskyRepository {
    
    private static final String REDSKY_URL_PRE_PRODUCT_ID = "https://redsky.target.com/v2/pdp/tcin/";
    // what to exclude from JSON response
    private static final String REDSKY_URL_PROST_PRODUCT_ID = "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

    /**
     * @param id the products id
     * @return the title of the product
     * @throws ProductNotFoundException
     */
    public String getProductTitleById(Integer id) throws ProductNotFoundException {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("id", id + ""); // cast id to string

        ObjectMapper mapper = new ObjectMapper();

        String productUrl = REDSKY_URL_PRE_PRODUCT_ID + id + REDSKY_URL_PROST_PRODUCT_ID;

        try {
            // http get from redsky
            ResponseEntity<String> response = restTemplate.getForEntity(productUrl, String.class, uriVariables);
            Map<String, Map> info = mapper.readValue(response.getBody(), Map.class);

            // drill down through JSON object returned from redsky
            Map<String, Map> productMap = info.get("product");
            Map<String, Map> itemMap = productMap.get("item");
            Map<String, String> prodDescrMap = itemMap.get("product_description");
            String title = prodDescrMap.get("title");

            return title;
        } catch (Exception e) { // catch any exception as product not found
            throw new ProductNotFoundException("product not found");
        }

    }
}
