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
Lorem ipsum dolor sit amet...

#### Day 9 (thursday)
Lorem ipsum dolor sit amet...

#### Day 10 (friday)
Lorem ipsum dolor sit amet...

### Week 3
