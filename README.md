# Commercial Nuclear Shelter Database

This project was made for a university course about databases. It's, in summary, an abstract representation of several vague databases consolidated into one system
for managing an underground bunker housing people in the 10s and even 100s. This projects goal was mainly to create a coherent and normalised database using any SQL
implementation.

# IMPORTANT NOTICE

This system was created purely for educationial purposes. It does not follow nor incorporate any good security practices and has many shortcomings in those matters.
It is not advised to take any examples in terms of database security from this project. That being said, you are welcome to publish and share with me any critcisms
and tips you may have regarding ANY field this project touches. Thank you in advance! :)

## Creating the database

With any Oracle RDBMS installed (this project was developed on XE 21c ver 21.3.0.0.0) simply run the provided scripts within the *sql/* directory with
the one marked as *DDL* first.
  - You could change the path of the .dbf files created for the tablespace to your liking before runing the scripts
  
## Using the app

### Running the program
Running the app is as simple as building the project into a runnable .jar and running it via terminal or cmd.

### Login screen
You will be greeted with a prompt welcoming you to the database and asking you for your credentials in a certain format, being *NAME SURNAME DWELLER_ID*.
You are always, by default, logged in as the *DWELLER* user type.

#### Here your options are :
- Typing the correct credentials,
- Typing **quit** to closes the connection and the app alltogether (somewhat **IMPORTANT** - _quit_, aswell as other commands that do not refer to specific data like ids or 
credentials **ARE NOT** case sensitive)

### Main menu
Here, depending on the job of the user from the database you logged in as, you will be reconnected into the DB as the correct user type (OVERSEER, MANAGER,
ENGINEER, MEDIC, GUARD or if none of the above - DWELLER). Each user type has his own range of functionalities, however all of them have some in common.

#### Common options :
- File a complaint/check complaints via the complaint menu,
- Display available views,
- Insert into / update available tables.


In most menus, after entering, the user will be greeted with a short usage info message which can be always printed using the command *help*.

### Complaints system
Every dweller can file complaints to the supervisors. The complaints system exists to serve that purpose. Every user type has acces to the *Complaints menu* 
where they can :
- check their/all complaints
  - check all of their/all complaints
  - check only the approved ones
  - check only the pending ones
  - check all except the approved ones
- add a new complaint

Adding a complaint is as easy as providing a *subject*, then *description* to be the complaint's content and finish entering by typing *:q*. After that the whole
complaint will be one more time shown to the user, and a promt to confirm will show asking to type [y/n]. After typing *y* the complaint will be added to the database
with the *pending* status.

### Verification system
The database uses triggers and other mechanisms like plsql procedures to keep track of changes in records. Some tables have a special column, named usually *VER_STATUS*
which determines if the record has ben checked and approved by a DB manager or the overseer himself. 
These columns can have 3 states, which are *Pending, Approved* and *Denied*. The **MANAGER** and **V_OVERSEER** user types are the only ones whit acces to the
*Verification menu* functionality. Whithin that menu, they are able to :
- display any un-approved records within any selected table with the appropriate column,
- verify or deny a specific record of the given id, or all pending records in a named table.

As always the *help* command serves to remind of the required syntax.

## Links

- Project documentation (PL): 
https://drive.google.com/file/d/1kugPrW2JO1LzZD6kO86PjBJFiFMZjAOC/view?usp=sharing

- Project made in cooperation with : 
https://github.com/mateuszbebnowicz

- Table printing lib, courtesy of user htorun : 
https://github.com/htorun/dbtableprinter
