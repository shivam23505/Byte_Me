# BYTE ME!!!
#### The following project is my implementation of a basic food delivery service providing application. It provides access to two kinds of users Customers and Admin. The functionalities have been implemented with respect to the basic needs of the 2 users for the required system. The project has helped me a lot to improve my understanding of OOPS concents and learn their practical use cases and application.
## Features Implemented
### Customers
--Browse Menu\
--Cart operations(add, remove, update, checkout)\
--Order tracking\
--Reviews and comments. 

### ADMIN
--Menu Management(add, remove, update) \
--Order management(process pending orders, update status etc.) \
--Sales report generator

### GUI IMPLEMENTATION
The menu page and pending orders will be seen in the GUI pages which have been implemented using swing.

### DATA MANAGEMENT
All the menu and customer data is saved in text file, so that the data regarding customers orders is not lost even on closing the program.

### JUNIT TESTS
Unit tests have been added to check the login validity and validity of correct quantity while adding an item to an order.

## Assumptions

--The gui pages have been started using a thread when the CLI program is started. Closing the gui window will terminate the entire program. \
<br>--When the main is started, the program retrieves previously saved menu items from “menu.txt” and retrieves previously logged in customer’s details using their respective text file, which follow the format “(customer.getEmail()).txt”.\
<br>--Details like special request of a certain order are not saved in the text files. But  they are handled if implemented while not closing the program.\
<br>--When a new customer logs in a separate file “(customer.getEmail()).txt” is created to save the order history of this current customer.\
<br>--Availability of items has been handled by admin in the form of quantity of an item in the menu\
<br>--The customers can only cancel the order if it has not been delivered already.\
<br>--Admin has the choice to handle or not to handle a particular order’s special request.\
<br>--Please enter the correct data type for the input else program will get terminated(All the choices to be entered are int or string (what has to be entered will be obvious))\


## OOPS CONCEPTS APPLIED

### 1.Abstraction
Each user interacts only with the parts of the system they are concerned with, and unnecessary details are hidden. By providing methods (like browsing a menu or placing an order) without exposing the complexities of the operations, the program effectively uses abstraction

### 2.Encapsulation
The system uses getter and setter methods to access or modify private data fields while preventing unauthorized access to sensitive data. For example, the Customer class has several private attributes, such as MyOrders, CustomerID, MyCart, and isVIP. These attributes cannot be accessed directly from outside the class, which restricts the visibility and protects the data from unintended modifications.

### 3.Inheritance
The Customer class and Admin class extends the User class, which is an abstract representation of a user in the system. By doing this, they inherit properties and behaviors from User without revealing how the User class is implemented internally. 

### 4.Polymorphism
Polymorphism allows objects to be treated as instances of their parent class, enabling multiple implementations of the same method. For example, the toString() method is a method of an object class which is the parent class of all class. So through overriding it, I have implemented it differently in food item and order class.


## HOW TO RUN THE CODE
--Open the project inside a text editor that can handle java code (preferably IntelliJ)\
--Run the main.java







