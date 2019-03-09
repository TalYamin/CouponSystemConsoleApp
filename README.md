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

Download Derby DB:
Download the database from https://db.apache.org/derby/derby_downloads.html
Make sure you are downloading: For Java 8 and Higher
Open cmd
Run: Cd “C:\Users\user\Desktop\db-derby-10.14.2.0-bin\bin”
Run startNetworkServer -p 3301
When succeed you should get: “Started & Ready”

2 - Add JAR to our Project
Add new lib folder to our Project.
Improte into this lib new folder:
General->File System->Browse -> Under the Derby folder ->lib->add derbyclient.jar
Project -> Build Path ->Configure build path - add derby.jar and derbyclient.jar

