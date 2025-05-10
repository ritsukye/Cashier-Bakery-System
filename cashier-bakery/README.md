This is a [Next.js](https://nextjs.org) project bootstrapped with [`create-next-app`](https://nextjs.org/docs/app/api-reference/cli/create-next-app).

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
1. Pull from GitHub and open in Visual Studio Code
2. In Visual Studio Code's terminal, cd to cashier-bakery/src/app/mysql
3. Enter the commands:
    javac MainPage.java
    java -cp ".:mysql-connector-j-9.3.0.jar" MainPage
4. Run the main page of the bakery system. You won't need to extract or insert a jar connector since it's already included in the project files.

Dependencies and Required Software:


You can start editing the page by modifying `app/page.js`. The page auto-updates as you edit the file.

This project uses [`next/font`](https://nextjs.org/docs/app/building-your-application/optimizing/fonts) to automatically optimize and load [Geist](https://vercel.com/font), a new font family for Vercel.

## Learn More

To learn more about Next.js, take a look at the following resources:

- [Next.js Documentation](https://nextjs.org/docs) - learn about Next.js features and API.
- [Learn Next.js](https://nextjs.org/learn) - an interactive Next.js tutorial.

You can check out [the Next.js GitHub repository](https://github.com/vercel/next.js) - your feedback and contributions are welcome!

## Deploy on Vercel

The easiest way to deploy your Next.js app is to use the [Vercel Platform](https://vercel.com/new?utm_medium=default-template&filter=next.js&utm_source=create-next-app&utm_campaign=create-next-app-readme) from the creators of Next.js.

Check out our [Next.js deployment documentation](https://nextjs.org/docs/app/building-your-application/deploying) for more details.
