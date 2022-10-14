# clangametesttask

**Java EE, H2 database, Glassfish; Junit and Hamcrest** to test API calls

Test assignment.

The goal: build an application that can interract with up to 100 threads.

In particular, there should be a way to concurrently increase balance of Clan entity. 

## Api
### /clan

Path responsible for all available operations with Clans
 - GET /clan/{id} 
 
 Get Information about clan with number {id} in the database
 
 - GET /clan/{id}/transactions 
 
 Get information about all transactions that altered the balance of the clan. 
 
 - GET /clan
 
 Get information about all clans
 
 - POST /clan
 
 Adds a new clan 
 
 - PUT /clan/{id} 
 
 Updates an existing Clan with number {id} 
 
 - DELETE /clan/{id}
 
 Deletes a Clan if it exists
 
 ### /task
 
 Path responsible for all tasks in the game
 - GET /task/{id} 
 
 Get Information about task with number {id} in the database
 
 - GET /task
 
 Get information about all tasks
 
 - POST /task
 
 Adds a new task to the database 
 
 - PUT /task/{id} 
 
 Updates an existing task with number {id} 
 
  - PUT /task/{id}/complete?clan={clanID}
 
 Attempts to complete a certain task {id} for a clan {clanID}. Returns transaction: successful ({successful: true}) if the task was completed and ({successful: false}) if it was completed. Returns 503 status code if it wasn't possible to process the request.  
 
 - DELETE /task/{id}
 
 Deletes a task if it exists
 
  ### /users
 
 Path responsible for all users in the game
 - GET /users/{id} 
 
 Get Information about user with number {id} in the database
 
 - GET /users
 
 Get information about all users
 
 - POST /users
 
 Adds a new users to the database 
 
 - PUT /users/{id} 
 
 Updates an existing task with number {id} 
 
  - PUT /users//{id}/addmoney?clan={clanId}&gold={amount} 
  
 Adds money to a chosen clan {clanId}. Returns transaction: successful ({successful: true}) if the transaction was completed and ({successful: false}) if user entered incorrect data. Returns 503 status code if it wasn't possible to process the request.

 - DELETE /users/{id}
 
 Deletes a users if it exists

 ## Tests
 - DBTest
 
 checks if the connection with database is alright and it's possible to directly add and retrieve data
 - RestControllerTest
 
 checks if REST is working as expected: sends data in the anticipated format/mediatype, returns the expected status code, etc.
 - ClanGoldTest 
 
 imitates http requests to the api. Runs hundreds of threads at the same time to check if the Connection pools is working and the data in the database isn't getting corrupted
