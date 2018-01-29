


![Logo](https://triplogic.org/wp-content/uploads/2018/01/cropped-bitmap-copy.png)

Triplogic is an open-source taxi app. This repository contains the code for REST API which is built using Java. 

This software is developed with an intent to help small entrepreneurs and muncipal corporations create their own taxi app.
So that they are not dependent on large organizations for taxi service. And so that they can customize the service according to their local needs. 

Triplogic is conceptually similar to Sidecar App which has now shutdown its services. The conceptual similarity is coincidental as the designers were not aware of sidecar app at the time of development. 

## Screenshots

![List of Drivers](https://triplogic.org/wp-content/uploads/2018/01/1-e1517148482850.png)
![Driver Detail View](https://triplogic.org/wp-content/uploads/2018/01/2-e1517148565623.png)
![Trip Detail View](https://triplogic.org/wp-content/uploads/2018/01/3-e1517148576746.png)
![Rate and Review](https://triplogic.org/wp-content/uploads/2018/01/4-e1517148594101.png)
![Trip History](https://triplogic.org/wp-content/uploads/2018/01/5-e1517148604332.png)
![Driver Profile](https://triplogic.org/wp-content/uploads/2018/01/6-e1517148618473.png)
![Sign-In](https://triplogic.org/wp-content/uploads/2018/01/7-e1517148628451.png)


## Technology Stack

On the backend triplogic API is built with Postgres ( Our Primary Database), Hikari-CP ( Connection Pooling Library), Jetty Server, Jersey REST framework, Thumbnailator (For Generating thumbnails)

Triplogic connects with backend though an android app. Which is built using native android app using libraries such as Ok-HTTP and Picasso. 


## Features

- User Sign-up using Phone and E-mail
- OTP Verification for both Phone and E-mail 
- Vehicle tracking using a vector map on android
- Vector Maps using Open Street Maps - Tileserver GL Project. You get to save your money which you may otherwise spend on a commertial maps license
- MQTT Server for Notifications : We have implemented Push Notifications using PAHO and Mosquito MQTT Broker ... You get to save your money which you may spend on commertial push notification services
- Interated with Pelias Open-Source Geocoder ... You get to save your money on commertial geocoding service
- Rating and Review of Drivers - Users can rate their trips which generates the Driver Rating after taking an average of trip rating
- Sort Drivers by Distance and Fare - the app allows you to sort the list of drivers by their distance or trip fare
- Estimated Trip Fare is provided by the app when the user gives both the pickup and destination address.
- Automation of Distance and Fare calculation. 
- Administrators can monitor and control the registration of drivers. The drivers cannot start their service without approval from admin and staff. This ensures that drivers are verified before they are can start offering service. 
- Driver app allows taxi driver to go online and offline. The drivers can choose to work anytime and anywhere. 

## How it works ? 

Triplogic uses GPS to keep the driver location updated. When a user wants to hire a taxi the user is presented a list of nearby drivers … and their phone numbers.
The user can simply send the pickup request and call up the driver and the driver will come to pick the user.

## Bussiness Model

The Software will be managed by a nonprofit. The bussiness model will be similar to how wordpress and Ghost blog software is managed currently.

## Installation Guide

You can find installation guide on the https://triplogic.org/index/

## License

Designed and Developed at Bluetree Software LLP, India and Released under the MIT license. 
