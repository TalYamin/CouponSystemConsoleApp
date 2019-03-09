# CouponSystemOfficial

System description:

The coupon management system allows companies to produce coupons as part of advertising campaigns and marketing that they maintain.
The system also has registered customers. Customers can purchase coupons. Coupons are limited in quantity and validity.
A customer is limited to one coupon of each type.
The system records the coupons purchased by each customer.
The system's revenues are from the purchase of coupons by registered customers and the creation and updating of new coupons by companies.
Access to the system is divided into 3 types of customers
1. Administrator - Manager of a list of companies and customers
2. Company - Manage a list of coupons associated with the company
3. Customer - Purchase of coupons


Phase 1: 

Description:

At this phase, the database will be configured to store and retrieve information about customers, companies and coupons.
A layer of insulation will be created over the database to allow convenient work from Java to the SQL required for DB operations.
In addition, basic infrastructure services such as ConnectionPool and a daily Thread will be established that will maintain the system and clean it out of expired coupons. 3 Points Entry will be defined for each type of customer (administrator, company or customer) who has signed up for a login.

Test requirements:

1. Configuration of Derby Drivers

1- Download Derby DB:
(1) Download the database from https://db.apache.org/derby/derby_downloads.html
(2) Make sure you are downloading: For Java 8 and Higher
(3) Open cmd
(4) Run: Cd “C:\Users\user\Desktop\db-derby-10.14.2.0-bin\bin”
(5) Run startNetworkServer -p 3301
(6) When succeed you should get: “Started & Ready”

2 - Add JAR to our Project:
(1) Add new lib folder to our Project.
(2) Improte into this lib new folder:
(3) General->File System->Browse -> Under the Derby folder ->lib->add derbyclient.jar
(4) Project -> Build Path ->Configure build path - add derby.jar and derbyclient.jar

3  - Install Derby Database Plugins into eclipse:
(1) Download 2 jars from this url : 
http://db.apache.org/derby/releases/release-10.1.3.1.html
Derby_core_plugin_10.3.1  and Derby_ui_plugin_1.1.1 
(2) Extract 
(3) Follow the attached tutorial to install the plugin: 
https://www.youtube.com/watch?v=rhT_XXAaRYE  
(4) The connection string and the properties of the database should be correlated
Local host, Port : 3301, Database: CouponManagment


2. Users and Passwords:

ADMIN:

userName: admin

password: 1234


3. DataBase folder:

DB derby > bin > CouponManagment

4. Essential Perspectives in Eclpise:

Data Source Explorer 

SQL Results 

