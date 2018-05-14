/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ben.myretail;

import com.ben.myretail.controller.ProductController;
import com.ben.myretail.model.CurrentPrice;
import com.ben.myretail.model.Product;
import com.ben.myretail.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.After;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author Ben Norman
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RestEndpointTests {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    private Product getTestProduct() {
        Product p = new Product();
        p.setId(13860428);
        p.setName("The Big Lebowski (Blu-ray)");
        p.setCurrentPrice(new CurrentPrice(new BigDecimal("14.99"), "USA"));
        return p;
    }

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).dispatchOptions(true).build();
        Product product = getTestProduct();
        repository.save(product);
    }

    @After
    public void teardown() {
        // clear database
        for (Product p : repository.findAll()) {
            repository.delete(p);
        }
    }

    @Test
    public void testGetProduct() throws Exception {
        mockMvc
                .perform(get("/products/13860428"))
                .andExpect(status().isOk())
                .andExpect(content().json("{id:13860428,name:'The Big Lebowski (Blu-ray)',currentPrice:{value:14.99,currencyCode:'USA'}}"));
    }

    @Test
    public void testGetProductNotFound() throws Exception {
        mockMvc
                .perform(get("/products/-1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{message:'product not found'}"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        // create updated product
        Product updatedProduct = getTestProduct();
        updatedProduct.getCurrentPrice().setValue(new BigDecimal("19.99"));
        // convert to JSON
        String updatedProductJSON = new ObjectMapper().writeValueAsString(updatedProduct);
        // confirm we return what we were suppose to
        mockMvc
                .perform(put("/products/13860428").contentType(MediaType.APPLICATION_JSON).content(updatedProductJSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{id:13860428,name:'The Big Lebowski (Blu-ray)',currentPrice:{value:19.99,currencyCode:'USA'}}"));
    }

    @Test
    public void testPutIdThrowsIdMismatch() throws Exception{
        // create updated product
        Product updatedProduct = getTestProduct();
        updatedProduct.getCurrentPrice().setValue(new BigDecimal("19.99"));
        // break ID
        updatedProduct.setId(-1);
        // convert to JSON
        String updatedProductJSON = new ObjectMapper().writeValueAsString(updatedProduct);
        mockMvc
                .perform(put("/products/13860428").contentType(MediaType.APPLICATION_JSON).content(updatedProductJSON))
                .andExpect(status().is4xxClientError()) // should be a 422 unprocessable entity
                .andExpect(content().json("{message:'IDs are mismatched'}"));
    }

    @Test
    public void testPutIdThrowsUnknownId() throws Exception{
         // create updated product
        Product updatedProduct = getTestProduct();
        updatedProduct.getCurrentPrice().setValue(new BigDecimal("19.99"));
        // break ID
        updatedProduct.setId(-1);
        // convert to JSON
        String updatedProductJSON = new ObjectMapper().writeValueAsString(updatedProduct);
        mockMvc
                .perform(put("/products/-1").contentType(MediaType.APPLICATION_JSON).content(updatedProductJSON))
                .andExpect(status().is4xxClientError()) // should be a 422 unprocessable entity
                .andExpect(content().json("{message:'unknown ID'}"));
    }
}
