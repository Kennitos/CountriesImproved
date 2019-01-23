# Process

### Week 1
#### Day 2 (tuesday)
I decided to add a multiplechoice option for the quiz, this way it will be less intensive/..... for the user to submit an answer. The user can decide at the start wether he is to get open questions or multiplechoice questions.

#### Day 3 (wednesday)
I decided to put the RegionActivity and CharActivity together into one activity called RegCharActivity. This way the user has to visit less activities before starting the quiz.

#### Day 4 (thursday)
I got the API working properly with the application and finished navigation through the activities, I didn't make any changes in my plan.

#### Day 5 (friday)
I decided to change to autocomplete function with strings from an arraylist with all the countries names in SearchActivity to an autocomplete function with objects (the instances of the class Country). This can be achieved to add an override function in the class Country that will display the name.

### Week 2
#### Day 6 (monday)
I created the class Quiz that will generate the quiz that will be used by QuizActivity. I found a new source (wikipedia) for the images of the countries (maps). The new images also display the neighbouring countries instead of only the map imprint. However the links to more difficult to create. 
<br>Whereas with the old link used the region and iso code as input:
> https://raw.githubusercontent.com/djaiss/mapsicon/master/{region}/{iso}/{size}.png

The new datasource use MD5 encrytion for the string "{county_name}\_in\_{region}.svg" and uses the first two letters of that encryption:

> https://upload.wikimedia.org/wikipedia/commons/thumb/{first}/{first_two}/"{country_name}_in_{region}.svg/1051px-{country_name}_in_{region}.svg.png

or
> https://upload.wikimedia.org/wikipedia/commons/thumb/{first}/{first_two}/"{country_name}_in_its_region.svg/1051px-{country_name}_in_its_region.svg.png

example: https://upload.wikimedia.org/wikipedia/commons/thumb/4/48/United_Kingdom_in_Europe.svg/1051px-United_Kingdom_in_Europe.svg.png

#### Day 7 (tuesday)
I decided to add some improvement to the process of answering the quiz questions. There are three situations in which certain button will highlight:
- The user taps the correct button, the button will highlight green.
- The user taps the incorrect button, the button will highlight red and the correct button will highlight green.
- The user doens't tap a button within the timer, the correct button will highlight green.

When buttons are highlighted (green/red), the button itself will become disable for one second after which the new question is loaded. Because of this the user can't 'spam' the buttons preventing crashes and it gives the user opportunity to view the correct/incorrect answer. This also means that when the user doesn't answer in time, it will automatically load the new question.

#### Day 8 (wednesday)
Remove some error crahses, .....

#### Day 9 (thursday)
Improved the MD5 encryption, now there the MD5 encryption will display zeros. This will result in more succesfully loaded images of the countries. However there are still some images it will not load, because wikipedia uses another name for the country then the API (for example wikipedia uses United States and the API uses United States of Amercia). I also improved the SearchActivity and CompareActivity, a softkeyboard will automatically open when the activity is opened and will closes when the user navigates to another activity. In the CompareActivity a requestfocus is added, therefore you will not have to click on a the next editText, this will go automatically.

#### Day 10 (friday)
The quiz gives the correct images of countries for the continents: africa, europe, oceania, north america and south amercia. The all use a standard svg map provided by wikipedia. However for asia there is not good universal svg map provided by wikpedia (or have not found). With the other continents the format of the link would work for all the countries in that continent. However 
this is not the case with Asia, there are many different formats not all countries work with the same
univerisal (like with the other continents). These are some examples:
- https://commons.wikimedia.org/wiki/File:Vietnam_in_Asia.svg
- https://commons.wikimedia.org/wiki/File:Afghanistan_in_Asia_(-mini_map_-rivers).svg
- https://commons.wikimedia.org/wiki/File:China_in_Asia_(de-facto).svg
- https://commons.wikimedia.org/wiki/File:Japan_in_Asia_(de-facto)_(-mini_map_-rivers).svg
- https://commons.wikimedia.org/wiki/File:India_in_Asia_(only_undisputed)_(-mini_map_-rivers).svg

The methods to get and post the scores are added (using a ScoreRequest and PostScoreRequest)

### Week 3

#### Day 11 (monday)
I cannot post a JSONArray on the rester server, unfortunatelly I can only post a String-String combination (key-value). I wanted to post in this format (using the String as key and ArrayList as value) :
> [{"id": 1, "score": "870", "regions": ["Europe"], "correct": ["Ireland", "Liechtenstein", "Estonia", ...], "incorrect": "[]", "name": "Kennet"}]

Since this is not possible, I have to post it the ArrayList as a literal String

>[{"id": 1, "score": "870", "regions": "[Europe"]", "correct": "[Ireland, Liechtenstein, Estonia, ...]", "incorrect": "[]", "name": "Kennet"}]

#### Day 12 (tuesday)
Lorem ipsum dolor sit amet...

#### Day 13 (wednesday)
I added a ScoreAdapter to display the scores in a ListView
