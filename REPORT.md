# Report


## Short description
This application lets the user search for the characteristics of a country, compare those to other countries and provides a quiz for those characteristics. The characteristics contain the name, capital, continent, region, population, area, weather and language.
 
<img src="https://github.com/Kennitos/Countries/blob/master/doc/Screenshot_6.png" width="275" height="490">

## Technical design

## Advanced sketches
![](doc/)

## Diagram of utility modules, classes and functions
These are the diagrams of the most import parts of the application. I did not include everthing some classes look very alike, for example the different request. The same goes for very simple objects like DummyIntegers or MD5Encryption
![](doc/scorediagram.png)
![](doc/countrydiagram.png)
![](doc/quizflagdiagram.png)

### APIs and frameworks or plugins
- REST Countries API [link](https://restcountries.eu/)
- Open Weather API [link](https://openweathermap.org/api)

### Data sources 
- Maps of each individual country [link](https://github.com/djaiss/mapsicon)
<br><br>The maps are png files and are stored on an unique link. The link is based on their region, official two-letter country codes (defined in ISO 3166-1) and file size.
Those three characteristics can be derived from the REST Countries API. The links will have the following structure:
> https://raw.githubusercontent.com/djaiss/mapsicon/master/{region}/{iso}/{size}.png

For example https://raw.githubusercontent.com/djaiss/mapsicon/master/europe/nl/1024.png

- Wikipedia images for the map of countries
The new datasource use MD5 encrytion for the string "{county_name}\_in\_{region}.svg" and uses the first two letters of that encryption:
> https://upload.wikimedia.org/wikipedia/commons/thumb/{first}/{first_two}/{country}_in_{region}.svg/{size}-{country}_in_{region}.svg.png

For example https://upload.wikimedia.org/wikipedia/commons/thumb/6/6f/Netherlands_in_Europe.svg/1051px-Netherlands_in_Europe.svg.png

- Flags of countries were provide by the site: https://www.countryflags.io. This datasource is based on the official two-letter country codes (defined in ISO 3166-1), the style of the flag and file size.
> https://www.countryflags.io/{iso}/{style}/{size}.png

For example https://www.countryflags.io/be/flat/64.png

### Database tables and fields (and their types)
I’m using the rester server


## Process / challenges
##### Autocompletion
The first challenge I encountered was with the creation of the autocomplete function, with this function the user would be able see suggestions to finish of their string. First I created an ArrayList<String> of the already existing ArrayList<Country> of all the countries. If the user pressed on the suggestion, that would save the string and later on I needed to check that string against all the countries. 
The simple solution for this was to create the autocomplete function with an ArrayList<Country> of the countries and implement an @Override toString() function within the Country class that will always show the string of the country instead of @object12345.

##### Images of the countries
In the second week I found out that wikipedia (wikimedia) also has standard formats of images of countries in their continent. Prior I only used the images of the imprint of the countries maps. However they use MD5 encryption for the string "{county_name}_in_{region}.svg" in their links, that I wanted to use. 
<br>At first I could not get the encryption 100% right, since it would not display any zero’s. Fortunately Tijmen also worked MD5 encryption at the time and he got working encryption from StackOverflow. 
<br>But this was not the end of the problem, since the country names from the REST API and from the link will not always overlap. For example REST sometimes uses countries with brackets like ‘Macedonia (the former Yugoslav Republic of)’ and WikiMedia would use ‘Macedonia’ in their link. For this I created code that would remove everything between the brackets. But sometimes there were countries that I could not fix with code like ‘United States of America’ versus ‘United States’. Since there was not a solution for this, I would hard code it for important countries, that I wanted in the app (prioritizing countries like the USA above Saint Lucia ).
As a final solution for the countries where the image did not load because of an incorrect link, I used the old image of the imprint of the country (note: this makes it far more difficult to guess the country for the user).

Also Wikimedia link gives the correct images of countries for the continents: africa, europe, oceania, north america and south america. The all use a standard svg map provided by wikipedia. However for asia there is not good universal svg map provided by wikpedia (or have not found). With the other continents the format of the link would work for all the countries in that continent. However this is not the case with Asia, there are many different formats not all countries work with the same universal link (like the other continents). These are some examples:

- https://commons.wikimedia.org/wiki/File:Vietnam_in_Asia.svg
- https://commons.wikimedia.org/wiki/File:Afghanistan_in_Asia_(-mini_map_-rivers).svg
- https://commons.wikimedia.org/wiki/File:China_in_Asia_(de-facto).svg
- https://commons.wikimedia.org/wiki/File:Japan_in_Asia_(de-facto)_(-mini_map_-rivers).svg
- https://commons.wikimedia.org/wiki/File:India_in_Asia_(only_undisputed)_(-mini_map_-rivers).svg

The solution is to use the the string “{country_name}_in_its_region}” instead of “{country_name}_in_{region}”, this would display the country and its direct neighboring countries centered instead of an imago with the whole continent centered. Therefore it will unfortunately lead to less slick transitions in the quiz between countries of the same continent

##### Highlighting buttons and CountDownTimer
I decided to add some improvement to the process of answering the quiz questions. I added a CountDownTimer to make the quiz more challenging, the user has now 10 seconds to answer the question. And I made the button highlight by changing their background color, there are three situations in which certain button will highlight:
- The user taps the correct button, the button will highlight green.
- The user taps the incorrect button, the button will highlight red and the correct button will highlight green.
- The user does not tap a button within the timer, the correct button will highlight green.

When buttons are highlighted (green/red), the button itself will become disable for one second after which the new question is loaded. Because of this the user can't 'spam' the buttons preventing crashes and it gives the user opportunity to view the correct/incorrect answer. This also means that when the user doesn't answer in time, it will automatically load the new question. The button will be enabled again on if the imago in the next question is loaded.
<br>The CountDownTimer caused problems by continuing after the QuizActivity was left during the quiz. This was solved with on onStop function. 

##### FlagActivity
Adding the flags of countries in the quiz was very difficult, since I used Button as answer buttons and for images (of the flags) you need to use an ImageButton. Since I did not found a solution in which the questions about flags would stay in the same QuizActivity, I created a new FlagActivity (with more or less identical code). During the quiz the app switches between QuizActivity and FlagActivity back and forth.

##### Scraping functionalities
I did not have enough time to implement functionalities like adding a difficulty with ‘easy’, ‘medium’ and ‘hard’ and the possibility to answer open questions  




