/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.myretail.controller;

import com.ben.myretail.exceptions.ProductNotFoundException;
import com.ben.myretail.exceptions.UpdateIntegrityException;
import com.ben.myretail.model.Product;
import com.ben.myretail.repository.ProductRepository;
import com.ben.myretail.repository.RedskyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ben Norman
 */
@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RedskyRepository redskyRepository;

    @GetMapping(value = "/products/{id}")
    public Product getProduct(@PathVariable Integer id) throws ProductNotFoundException {
        String title = redskyRepository.getProductTitleById(id);

        Product dbData = productRepository.findById(id).orElse(null);
        if (dbData == null) {
            throw new ProductNotFoundException("could not find product");
        }

        return new Product(id, title, dbData.getCurrentPrice());
    }

    @PutMapping(value = "/products/{id}")
    public Product updateProduct(@PathVariable Integer id, @RequestBody Product product) throws UpdateIntegrityException {
        if (id != product.getId()) {
            throw new UpdateIntegrityException("IDs are mismatched");
        }
        if (productRepository.existsById(id) == false) {
            throw new UpdateIntegrityException("unknown ID");
        }
        return productRepository.save(product);
    }
}
