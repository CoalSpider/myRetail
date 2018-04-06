/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.myretail.repository;

import com.ben.myretail.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ben Norman
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, Integer> {
    // no additional methods needed remove this comment if you add any
}
