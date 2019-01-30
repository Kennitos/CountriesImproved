# Country Knowledge app

## Goals

This app aims to help you remember countries and their characteristics
## Problem

> Write a statement about the problem that your finished project will solve. 
	The problem1 has to be clearly described and very specific. In total, keep the problem statement to four or five lines of text.
	
When trying to learn new countries and their characteristics there are many geography (quiz) applications that can provide a solution. However these applications only focus on their geographical location and ignore other characteristics, while these are vital to get a more general idea of the country. Therefore you need an application that can provide information like the capital, language, population, flag, climate and you can test/quiz that knowledge.



## Solution

### Description and visualization

In this app you can search all kinds of characteristics about countries, compare them and test your knowledge about them.

<p float="left">
	<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_8.png" width="275" height="490">
	<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_1.png" width="275" height="490">
	<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_2.png" width="275" height="490">
</p>

<p float="center">
	<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_3.png" width="275" height="490">
	<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_4.png" width="275" height="490">
</p>

<p float="left">
<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_5.png" width="275" height="490">
<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_6.png" width="275" height="490">
<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_7.png" width="275" height="490">
</p>

### Features

Main features:
- Search for countries
- Compare countries (side by side)
- Quiz/test countries and their characteristics 

Minimum viable product:
- Search for countries
- Compare countries
- Quiz/test countries and their characteristics
- Save quiz score locally

Optional part:
- Autocomplete country while typing
- Save quiz scores online
- Pin and zoom through world map
-


## Prerequisites
### Data Source


-The characteristics of the countries (name, capital, languages, population, flag) are acquired through the REST Countries API. Information can be found at their [homepage](https://restcountries.eu/).

-The weather information will be acquired through Open Weather Map [here](https://openweathermap.org/api) and the images of the countries can be found [here](https://restcountries.eu/).

-Maps of each individual country [link](https://github.com/djaiss/mapsicon)
<br>The maps are png files and are stored on an unique link. The link is based on their region, official two-letter country codes (defined in ISO 3166-1) and file size.
Those three characteristics can be derived from the REST Countries API. The links will have the following structure:
> https://raw.githubusercontent.com/djaiss/mapsicon/master/{region}/{iso}/{size}.png

For example https://raw.githubusercontent.com/djaiss/mapsicon/master/europe/nl/1024.png

-Wikipedia images for the map of countries
The new datasource use MD5 encrytion for the string "{county_name}\_in\_{region}.svg" and uses the first two letters of that encryption:

-Flags of countries were provide by the site: https://www.countryflags.io


### External components

I used the REST data server from: https://github.com/stgm/rester

### Review of similar apps/visualizations

Applications in Google Play Store:<br>
Most of the geography applications in the playstore have don’t have slick designs and look like they were build for 
children of the age 6 to 12 ([example](https://play.google.com/store/apps/details?id=com.age.wgg.appspot)). However there are a 
few that actually look good, for example [World Map Quiz](https://play.google.com/store/apps/details?id=com.qbis.guessthecountry). 
This app provides the user with an interactive map in which the can select countries to give answers for the quiz, this will be hard to 
resemble for my own app. Nonetheless this app is limited in its characteristics of countries.


Jetpunk quizzes (website):<br>
The website Jetpunk is a host for all kinds of different quizzes including geography ones. 
these quizzes are all separately, but individually they are very good. The how many countries can you name quiz highlights every country you guessed correctly and shows the remaining countries with a black dot (therefore you won’t miss small countries like Lichtenstein). Unfortunately this will be hard to do with my own application.
[Name capitals](https://www.jetpunk.com/quizzes/name-world-capitals)
[Name countries](https://www.jetpunk.com/quizzes/how-many-countries-can-you-name)


### Hardest parts

I think the hardest part will be creating an interactive map in which you can click different countries (if possible at all).



