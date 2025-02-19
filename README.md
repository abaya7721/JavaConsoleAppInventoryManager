# Inventory Manager
App which uses console-based interface to manage inventory stored in a SQL database.

## Project Structure
  1. View 
      * Console ([#console](https://github.com/abaya7721/JavaConsoleAppInventoryManager#console))
      * Enum MenuOptions ([#enum-menu-options](https://github.com/abaya7721/JavaConsoleAppInventoryManager#enum-menuoptions))
      * InventoryManagementApp ([#mainapp](https://github.com/abaya7721/JavaConsoleAppInventoryManager#mainapp))
  2. Model
      * Product ([#product](https://github.com/abaya7721/JavaConsoleAppInventoryManager#product))
      * PerishableProduct
  3. Data Access
      * Interface ProductRepository ([#interface-productrepository](https://github.com/abaya7721/JavaConsoleAppInventoryManager#interface-productrepository))
  4. Domain
      * ProductService ([#productservice](https://github.com/abaya7721/JavaConsoleAppInventoryManager#productservice))

## View
### Console
Initialize new Scanner
displayMainMenu()

The method will display the following as the main menu. Console will display other messages and prompts. 

===== Inventory Manager =====
1. Add Product
2. View Products
3. Search Product
4. Update Product
5. Delete Product
6. Exit

User can interact with product inventory by selecting an options. 
Search Product, Update Product, Delete Product require a valid existing product ID or product name(Search Product).
Add Product needs a valid product ID which doesn't exist in the inventory.
When adding a new product to the inventory a valid name, quantity (can be zero but limited to 1,000) and price cannot be zero with a maximum of 10000.00.
Updating a product allows for updating the both quantity and price, only one of the two, or none if user chooses to skip both.
Deleting a product will remove a product from inventory if it exists. There is a last confirmation before deletion is finalized. User can cancel deletion at this point. 


### Class and Method implementation

### Console Methods
		readInt(String prompt) 
* Takes user input after prompt. Method makes sure that values are within a certain range. If not, it continues to prompt the user for proper values. 
If value less than or value greater than specific values or if value not an integer, display the message that value is incorrect and continue to ask for value.

		readString(String prompt)
* Takes user input after prompt. Also, the method makes sure that the value in the Scanner is of type String otherwise displays a message that the value is incorrect and continues to ask for the correct value.

		displayMessage(String message) / displayHeader(String header)
* Methods that display a menu header or a message to the user (to inform users of app status or provide feedback after value inputs).
Makes use of System.out.print() or System.out.println()

        readDouble(String prompt)
* Checks for valid numerical values of type double

        checkPrice(String message) and checkQuantity(String message)
*   During product update user can enter new price / quantity
    Checks if input is empty  to decide the application flow - continue or branch into else
    If inputs are not empty, the value is passed through a data type validation method for int and double values

        validatePrice(double price) and validateQuantity(int quantity)
* Validates the correct input for the product attributes which become the record fields in the inventory when a new product is being added or updated.



### enum MenuOptions
	EXIT
	ADD_PRODUCT
	VIEW_PRODUCTS
	SEARCH_PRODUCT
	UPDATE_PRODUCT
	DELETE_PRODUCT

The Menu Options enum is used in a while loop and switch to make the app code more human-readable and simplifies options available for the switch during interaction. 

### MainApp
Runs the SpringBoot main method which itself runs the method handling app interaction through the console.  
The run() method handles the app flow based on inputs using a while loop.
The enum MenuOptions determines which option is selected through Console input and action is determined by a switch and case going to specified method and subsequent actions.

	addProduct (String name, BigDecimal price, int quantity)
Create a new Product instance.
Set new product values suing the console inputs through readInt() and readString().
Scanner handles value validations.  
productRepository.save(new Product)

	viewProducts()
All products are displayed using the findAll() method in productRepository. The result is saved into a list of products. A for loop will go through each product to display each record on a new line. This will be formatted with a header.

	searchProduct()
readString to enter ProductId or ProductName
Check to see what type of value was saved from the user.
If String, use a method that checks the database for the existence of a record with the product name with a Jpa query method. If an int, check for the existence using findById.

	updateProduct()
This method prompts the user for productId. Checks to make sure the product exists. Once the product check shows an existing record in the database, the console displays the current product information. The product is saved to a local variable instance Product.  The follow-up console prompts the user for new values for product quantity and price. The user has the option to press enter if they want to skip the prompt.  
New values are saved to local variables. The local variables are set to the new Product by using the set methods. The local Product is saved to the database using the save() method. The same productId is used in order to update the record.

	deleteProduct()
The method prompts the user for productId.  Checks to make sure the product exists. Once the product check shows an existing record in the database, the console displays the message and prompt for the user to confirm deletion. If the user selects yes, the product is deleted from the database, if the user selects no for confirmation the console returns to the main menu display.


## Model 
### Product
Used with SpringBoot framework annotations to enable object relations and data management specifically with a mySQL database.
Contains the properties, constructors, setter/getters.

	 Property Names and Data Types:
	-productId: int
	-productName: String
	-price: BigDecimal
	-quanity: int

##### Annotations
	@Entity 
 	Used for the Product class to make accessible and manageable by the repository. Specifies the table association.
 	@Id 
  	Used for the int productId property which determines the entity identity type.

### PerishableProduct extends Product
     Inherits properties and methods from superclass
	 Property Names and Data Types:
	- expirationDate: Date


## Data Access
### ProductRepository
The interface ProductRepository will use the inherited JpaRepository java class to interact with the sql database using the <Product, Integer> as parameters.

