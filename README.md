# myRetail
Case Study myRetail for target job opportunity (tested on windows only)

## Project
>myRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. myRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
>
>The goal for this exercise is to create an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller. 
>
>Your goal is to create a RESTful service that can retrieve product and price details by ID. The URL structure is up to you to define, but try to follow some sort of logical convention.
>
>Build an application that performs the following actions: 
>- Responds to an HTTP GET request at /products/{id} and delivers product data as JSON (where {id} will be a number. 
>- Example product IDs: 15117729, 16483589, 16696652, 16752456, 15643793) 
>- Example response: {"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}
>- Performs an HTTP GET to retrieve the product name from an external API. (For this exercise the data will come from redsky.target.com, but let’s just pretend this is an internal resource hosted by myRetail)  
>- Example: http://redsky.target.com/v2/pdp/tcin/13860428?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics
>- Reads pricing information from a NoSQL data store and combines it with the product id and name from the HTTP request into a single response. 
>
>BONUS: Accepts an HTTP PUT request at the same path (/products/{id}), containing a JSON request body similar to the GET response, and updates the product’s price in the data store. 

## Requirements
Maven
mongodb: https://docs.mongodb.com/tutorials/install-mongodb-on-windows/ (make sure to install mongoDB compass)
windows path variables: https://code.msdn.microsoft.com/Mongo-Database-setup-on-6963f46f

## Build and Run Instructions
Setup Server
- choose a folder to save the database data, it can be anywhere
- In command prompt navigate to MongoDB/Server/3.6/bin
- run mongod.exe --dbpath "path to your database"
- Launch mongoDB compass
- In compass create the first database: productsDB with a collection named products
- Create the following document 
 ```
 1 _id:13860428                                      Int32
 2 currentPrice:Object                               Object
 3     value:"19.98"                                 String
 4     currencyCode:"USA"                            String
 5 _class:"com.ben.myretail.model.Product"           String
```
- create the second database: productsTestDB with a collection named produts

Setup Project
- clone repository into folder of your choice
- open project in IDE of your choice
- build project - in netbeans the tests will run at this point and should be successfull
- run the project
