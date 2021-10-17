~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
|      County Garden CSE341 Project      |
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Project outline:

I.   Project Directory
      A. Source files
      B. Data generation

II.  General Overview of Interfaces
      A. Customer Interface
      B. Agent Interface

III. Challenges + Assumptions
      A. Image versus Reality
      B. Notes for the future
      C. Resources



I. Project Directory:

A. Sources Files:

    The source code written for this project can be found in root/mkm322, and should
include three files: "Databaseinterfaces.java," "Database.java," and "ojdbc8.jar."
The first file is where I maintain the majority of my java code for running the interface.
The second file is while I defined my Oracle JDBC methods that interact with the remote database.

B. Data generation:

    As far as data generation code goes, I did not write a script for making insert statements.
Instead I used a resource by the name of dbForge Studio. This tool allows for some smart
generation of data by looking at defined constraints and scheme labels in order to create realistic
data. For the homework assignment in the past, I generated the code using nested forloops and random
number generation, and found that the data was hard to understand. As great as this tool sounds, it
was not perfect, and litered the database with some tough to catch inconsistencies. That being said,
I openly admit that some of the data was not cleaned from the raw generation of 40 rows per table.
When using the interfaces you may be able to spot out these data points.


II. General Overview of Interfaces:

    Although I planned to integrate four interfaces, I only ended up making two. I will save the
details for the next section. Each interface can be selected from the main menu once valid
database credentials are entered.

A. Customer Interface:

    The customer interface both the public face and brain of the operation. For this reason, I tried
outline the relevant options a customer would like to have when trying to obtain insurance. Furthermore,
In the customer interface, I focused on previewing menus and row data so that the customer never gets
confused on what to do next. The first step to the interface is figuring our if the customer is new or
returning. This is done by directly asking the user, and if the user is new, then entering a new customer
in the database. If the customer is returning, we allow them to assume the role of any other customer in
the database. Once an identification is selected, the user has the option view their policies, claims, and
transactions. Each selection has its own submenu where users can decided what course of action is best.
Since the database is well populated, picking any customer id should allow for some interesting 
observations. Use the view options in the submenus to gain further insight of the membership of the
consumer, and explore the add, remove, and modify features. While not all of these features were fully
implemented, there are enough features for a consumer to freely navigate the menu and view there speific
information. Ideally, combinations of actions allow the user to stay in touch of their policies, due
transactions, and claims.

B. Agent Interface:

    The Agent interface is designed to get the agent the information needed to serve the customer. Similar
to the customer, an agent has the option to login as a new member or identify as an exisitng employee.
Although the agent has less options than the customer, the agent is able to obtain data on their subjects.
Some of the data includes characteristics as well as revenu obtained. County Garden is a smaller buisness
so it is important to note that costume entrance will drive agent entrance. In other words, as the number
customers purchase policies, employees will be given the opporotunity to work on their behalf. This is
maintained by a helper method that specifically targets employees who do not currently have a customer.


III. Challenges + Assumptions:

A. Image Versus Reality:

    As I may have hinted, the entire project was not how I originally expected it. That being said. I am
content with (1) having the opporotunity to learn about database management systems in a 1-to-1 enviroment
with Prof. Korth, (2) having given many long nights poking away at different quiries in my alternative database
designs (and the joy of reaching expected output), (3) gaining knowledge and experience in a particiuar part
of Computer Science I am not comfortable with, and (4) meeting new people just as enthusiastic as I am to
learn (regardless of the virtual enviroment).

B. Notes for the Future:

    This project helped me reinforce a combination of lessons I knew as well as a new combination I did not know.
While I was initially happy with my menu design, I am afraid it took me too much time. This time would have been
better spent developing more complex database interactions. I do believe that my ER diagram was constructed well,
but the execution and realization was not equal. This leads to me to believe that the interactions must meet in
the middle for the endusers (customer, agent, adjusters, etc) and the database. Since I am a data science minor
I know I will look further into understanding database design.

C. Resource:

dbForge - https://www.devart.com/dbforge/?gclid=Cj0KCQjwytOEBhD5ARIsANnRjViFZkPsWoV6qPXFBISaXwl0NnCOw8KtlujU5bic7Mv7ExZ3j3IpyU8aAo-0EALw_wcB
