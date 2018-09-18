# iFood Backend Test - Score

Our goal is to be the best food marketplace in the world.

In order to build trust from our million users (restaurants and consumers), we need to fully understand 
how relevant the menu item/categories sold by our partners are. 
So, we can give insights to our restaurants and better offers to our consumers. 

## Setup
* To populate orders and scores run the following command, this will create MongoDB and ActiveMQ, and starts the application in "dev" profile:
```
    ./buildAndRun.sh
```

## Rest APIs and Task developed:
* Provide Score for a given Menu Item
```
    GET /menus/{menuId}/score
```

* Provide Score for a given Category
```
    GET /categories/{category}/score
```

* Retrieve Menu Item with Score above/below a parameter
```
    GET /menus/score/above/{relevance}
    GET /menus/score/above/{relevance}?page={}&size={}
    GET /menus/score/bellow/{relevance}
    GET /menus/score/bellow/{relevance}?page={}&size={}
```
* Retrieve Categories with Score above/below a parameter
```
    GET /categories/score/above/{relevance}
    GET /categories/score/above/{relevance}?page={}&size={}
    GET /categories/score/bellow/{relevance}
    GET /categories/score/bellow/{relevance}?page={}&size={}
```
* Update score of Menu Item and Categories according to our business rules
```
    JMS checkout-order
    JMS cancel-order
```

# JMS Queues
* There is a JMS Handler to threat if a queue has problems, if some error happens it will save the queue information in OrderJmsError @Document

# MongoDataBase
Choose mongodb because all of its "pros" and the model designed: document oriented, no joins, heterogeneous Data, some data representation as JSON, High availability, High scalability.
Ofcourse i cound choose Postgrees since it could provide all the "pros" of mongo but "document oriented".

#Postman
You can import some request examples in Postman:
```
    env_setup/postman-collections.json
```

#What could be better?
* I think spring-cloud-contract is awesome for microservices architecture, i think it could be implemented in this case if used in this architecture
* i would provide different modules for REST APIs and Background tasks.
