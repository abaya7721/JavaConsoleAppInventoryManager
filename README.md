# Inventory Manager
App which uses console-based interace to manage inventory stored in a SQL database.

## Classes
  1. View 
      * Console
      * Enum MenuOptions
      * MainApp
  2. Model
      * Product
  3. Domain
      * ProductService
  4. Data Access
      * Interface ProductRepository

## Console 
Initialize new Scanner
displayMainMenu()

The method will display the following as the main menu. Console will display other messages and prompts. 

===== Inventory Manager =====
1. Add Product
2. View Products
3. Search Product
4. Update Product
5. Delete Product
6. Save Inventory to File
7. Load Inventory from File
8. Exit

### Console Methods
readInt(String prompt) 
* Takes user input after prompt. Method makes sure that values are within a certain range. If not, it continues to prompt the user for proper values. 
If value less than or value greater than specific values or if value not an integer, display the message that value is incorrect and continue to ask for value.

readString(String prompt)
* Takes user input after prompt. Also, the method makes sure that the value in the Scanner is of type String otherwise displays a message that the value is incorrect and continues to ask for the correct value.

displayMessage(String message) / displayHeader(String header)
* Methods that display a menu header or a message to the user (to inform users of app status or provide feedback after value inputs).
Makes use of System.out.print() or System.out.println()

## enum MenuOptions
EXIT
ADD_PRODUCT
VIEW_PRODUCTS
SEARCH_PRODUCT
UPDATE_PRODUCT
DELETE_PRODUCT

The Menu Options enum is used in a while loop and switch to make the app code more human readable and simplifies options available for the switch during interaction. 

## MainApp
Runs the SpringBoot main method which itself runs the method handling app interaction through the console. 
run()
Method handles the app flow based on inputs using a while loop.
The enum MenuOptions determines which option is selected through Console input and action is determined by a switch and case going to specified method and subsequent actions.

## Model - Product
Used with SpringBoot framework annotations to enable relational database connectivity, specifically with a mySQL database. To help manage data.
Contains the properties, constructors, setter/getters.
Property Names and Data Types:
productId: int
productName: String
price: BigDecimal
quanity: int

Annotations - 
@Entity for the Product class to make accessible and manageable by the repository. Specifies the domain type.
@Id for the int productId property which determines the entity identity type.

## Data Access - ProductRepository
The interface ProductRepository will use the inherited JpaRepository java class to interact with the sql database using the <Product, Integer> as parameters.

## Domain - ProductService
Contains the methods interacting with the Product class and utilizing ProductRepository data access methods through the JpaRepository java class.


### Class and Method implementation

/*	
Import classes
Product, ProductRepository
	
Declare objects
Product product, ProductRepository productRepository

Class signature is - public class ProductService()

	addProduct ()
Create a new Product instance.
Set new product values as the Console inputs through readInt() and readString().
Scanner handles value validations.
productRepository.add(new Product)

	viewProducts()
All products are displayed using the findAll method in productRepository. The result is saved into a list of products. A for loop will go through each product to display each record on a new line. This will be formatted with a header.
	
	searchProduct()
readString to enter ProductId or ProductName
Check to see what type of value was saved from the user.
If String, use a method that checks the database for the existence of a record with the product name with a Jpa query method. If an int, check for the existence using findById.

	updateProduct()
This method prompts the user for productId. Checks to make sure the product exists. Once the product check shows an existing record in the database, the console displays the current product information. The product is saved to a local variable instance Product.  The follow-up console prompts the user for new values for product quantity and price. The user has the option to press enter if they want to skip the prompt. 
New values are saved to local variables. The local variables are set to the new Product by using the set methods. The Product is saved to the database using the save() method. The same productId is saved from the old values so the record is updated.

	deleteProduct()
The method prompts the user for productId.  Checks to make sure the product exists. Once the product check shows an existing record in the database, the console displays the message and prompt for the user to confirm deletion. If the user selects yes, the product is deleted from the database, if the user selects no for confirmation the console returns to the main menu display.

*/

