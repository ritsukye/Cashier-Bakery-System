Project Overview:
Our project is a bakery management system. Bakery Managers can add/view employee, order, and product information. It also allows employees to place/view orders. The purpose of our project is to be able to do all these tasks in one place, simplifying bakery management.

To set up and run the project:
// MySQL Setup
1. Open MySQL Workbench (Download the latest version if you haven't already downloaded it)
2. Save your password and create a local MySQL instance for MySQL Database
3. Create a new MySQL table in that instance called Bakery
4. Copy over the MySQL script provided
5. Download MySQL Extension in VSC Extension
6. Add the matching details to connect your MySQL to VSC
     a. Importantly, add your MySQL username and password to the connection ("root" and "bakery2025" as username and password)
// VSC Set Up
1. Pull from the employee-page branch on GitHub and open in Visual Studio Code
     a. We abandoned main because we initially started with React and eventually left it for Java Swing
3. In Visual Studio Code's terminal, cd to cashier-bakery/src/app/mysql
4. Enter the commands:
    javac MainPage.java
    java -cp ".:mysql-connector-j-9.3.0.jar" MainPage
5. Run the main page of the bakery system. You won't need to extract or insert a jar connector since it's already included in the project files.

Dependencies and Required Software:
