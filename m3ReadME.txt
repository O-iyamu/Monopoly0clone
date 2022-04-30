# SYSC3110_GROUP18_PROJECT
# Milestone 3
# Author: Jeneerthan Pageerathan

# Team Members
- Christina Dang
- Jeneerthan Pageerathan
- Osaretinmwen Iyamu
- Vishesh Dasani


# The Deliverables
- The source code which consists of twenty one classes:
  - BoardModel.java: Vishesh Dasani
  - BoardEvent.java: Vishesh Dasani
  - Dice.java: Christina Dang
  - Player.java: Jeneerthan Pageerathan
  - Property.java: Osaretinmwen Iyamu
  - BoardView.java: Vishesh Dasani
  - GameFrame.java: Christina Dang, Jeneerthan Pageerathan & Vishesh Dasani
  - BoardController.java: Vishesh Dasani
  - Space.java: Jeneerthan Pageerathan
  - PlayerBot.java: Jeneerthan Pageerathan
  - Estate.java: Jeneerthan Pageerathan
  - Utility.java: Jeneerthan Pageerathan
  - Railroad.java: Jeneerthan Pageerathan
  - Jail.java: Jeneerthan Pageerathan
  - Go.java: Jeneerthan Pageerathan
  - IncomeTax.java: Jeneerthan Pageerathan
  - ParkingSpace.java: Jeneerthan Pageerathan
  - BoardModelTest.java: Osaretinmwen Iyamu
  - PropertyTest.java: Osaretinmwen Iyamu
  - PlayerTest.java: Osaretinmwen Iyamu
  - JailTest.java: Osaretinmwen Iyamu

- A game manual that explains the rules and game structure of Monopoly. It includes the system requirements and how to play the game: Written by Jeneerthan Pageerathan
- A UML class diagram that shows the classes and their relationships: Created by Osaretinmwen Iyamu
- A UML sequence diagram for important events that shows how different classes interact during such events: Created by Jeneerthan Pageerathan

# Changes Made
- Added new classes for new features such as houses, railroads, etc.
- New classes to parent these spaces called Space
- Added a class for player bots called PlayerBot

# Design Decisions
The BoardModel class contains two dice and uses an ArrayList to keep track of players since the list will need to be changed during the game setup and when players go bankrupt or quit. It uses Hashmaps to track player positions and property positions. The player maps to its integer position. The integer position in the other hashmap then maps to the property. This makes it easy for the boardModel to connect player positions with property positions. It avoids switch cases since the stacked cases make breaking from certain loops difficult. The boardModel has minimmal methods and is mainly responsible for keeping track of players, properties and the events that occur which then call upon methods from the other classes such as Player.

The Player class implements an ArrayList to keep track of properties that the player object owns. The ArrayList data structure is useful for this because the list will need to be changed as players buy properties and when the property ownership changes after a player lose. It also provides the functionality to check if a property is within a certain player's ownership using the contains method. The class imitates what a player can do in a monopoly game so there are methods for rolling dice, buying property, paying rent which are called by the BoardModel class. The class keeps track of its own name, balance, and properties.

The Property class keeps track of its name, price, rent, and ownership. It mainly helps provide information for the BoardModel and Player class using getters.

The Dice class is used to add customizability to the game. The number of faces of the die can be chosen during set up and the dice objects keep track of their own value. They provide this value using getters to the BoardModel class.

The new graphical user interface is implemented using MVC model which includes adding classes for a controller and view.

The BoardController class uses a switch case that will act according to the action command of each button that helps identify it.

The GameFrame contains code that composes each component individually, this was to avoid errors that ooccurred when using methods to create components. There is some functionality allocated to methods such as creating buttons and setting panel layouts. The class is composed of multiple panels that contain different aspects of the game such as the board, the player information, and player buttons. It contains multiple buttons that the player can use to play the game. It also uses the hashmap of properties from the BoardModel to instantiate the respective property panels.

Created a new class called Space for properties and new space classes such as Go, Jail, etc. to inherit from. This helps integrate new implementations to the view and model. Classes such as Estate, Railroad, and Utilities are created and inherit from Property to provide a new getRent() method. IncomeTax objects are associated with a ParkingSpace object so that it could add to the sum collected by the parking space.


# Known Issues
- Not able to add bots from set up to game model due to the BoardModel's constructor.
- Some issues with premature house prompting instead of only prompting when monopolies are complete.

# What's Next?
- Need to add the feature to save and load games
- Need to add the ability to switch the board to international versions
- Need to fix monopoly prompts
- Need to fix adding robots into the model
