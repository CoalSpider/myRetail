package com.ben.myretail;

import com.ben.myretail.exceptions.ProductNotFoundException;
import com.ben.myretail.model.CurrentPrice;
import com.ben.myretail.model.Product;
import com.ben.myretail.repository.ProductRepository;
import com.ben.myretail.repository.RedskyRepository;
import java.math.BigDecimal;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyretailApplicationTests {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private RedskyRepository redskyRepository;

    @After
    public void cleanup() {
        // clear products after each test (probably a faster way to do this)
        for (Product p : repository.findAll()) {
            repository.delete(p);
        }
    }

    private Product getTestProduct() {
        Product p = new Product();
        p.setId(13860428);
        p.setName("The Big Lebowski (Blu-ray)");
        p.setCurrentPrice(new CurrentPrice(new BigDecimal("14.99"), "USA"));
        return p;
    }

    @Test
    public void contextLoads() {

    }

    @Test
    public void testMongoDBSave() {
        Product p = getTestProduct();
        // confirm save result is what we expect
        Product saveResult = repository.save(p);
        assertEquals(p, saveResult);
        // confirm we get something back
        Product findResult = repository.findById(13860428).orElse(null);
        assertNotNull(findResult);
        // confirm name was not saved
        assertEquals(null, findResult.getName());
        // confirm we get back what we put in (dont care about name)
        findResult.setName(p.getName());
        assertEquals(p, findResult);
    }

    @Test
    public void testIdNotFoundMongoDB() {
        repository.save(getTestProduct());
        
        Product result = repository.findById(0).orElse(null);
        assertEquals(null, result);
        result = repository.findById(-1).orElse(null);
        assertEquals(null, result);
        result = repository.findById(1386042).orElse(null);
        assertEquals(null, result);
    }

    @Test
    public void testRedskyGetTitle() throws ProductNotFoundException {
        // confirm redsky works
        String title = redskyRepository.getProductTitleById(13860428);
        // confirm it returned what we expected
        assertEquals("The Big Lebowski (Blu-ray)", title);
    }

    @Test
    public void testRedskyProductNotFound() {
        try {
            String title = redskyRepository.getProductTitleById(0);
            fail("should have found error for id " + 0);
        } catch (ProductNotFoundException e) {
            // pass
        }
    }

}
