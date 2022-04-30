# SYSC3110_GROUP18_PROJECT
# Milestone 2
# Author: Jeneerthan Pageerathan

# Team Members
- Christina Dang
- Jeneerthan Pageerathan
- Osaretinmwen Iyamu
- Vishesh Dasani


# The Deliverables
- The source code which consists of seven classes:
  - BoardModel.java: Vishesh Dasani
  - Dice.java: Christina Dang
  - Player.java: Jeneerthan Pageerathan
  - Property.java: Osaretinmwen Iyamu
  - GameFrame.java: Christina Dang & Jeneerthan Pageerathan
  - BoardController: Vishesh Dasani
  - BoardModelTest: Osaretinmwen Iyamu

- A game manual that explains the rules and game structure of Monopoly. It includes the system requirements and how to play the game: Written by Jeneerthan Pageerathan
- A UML class diagram that shows the classes and their relationships: Created by Osaretinmwen Iyamu
- A UML sequence diagram for important events that shows how different classes interact during such events: Created by Jeneerthan Pageerathan

# Changes Made
- Added a GUI using JSwing and MVC
  - New classes for the implementation: GameFrame, BoardController
- Added Unit Testing: BoardModelTest

# Design Decisions
The BoardModel class contains two dice and uses an ArrayList to keep track of players since the list will need to be changed during the 
game setup and when players go bankrupt or quit.
It uses Hashmaps to track player positions and property positions. The player maps to its integer position. The integer position in the other hashmap then maps to the property.
This makes it easy for the boardModel to connect player positions with property positions.
It avoids switch cases since the stacked cases make breaking from certain loops difficult.
The boardModel has minimmal methods and is mainly responsible for keeping track of players, properties and the events that occur which then call upon methods from the other classes 
such as Player.

The Player class implements an ArrayList to keep track of properties that the player object owns. The ArrayList data structure is useful for this because the list will 
need to be changed as players buy properties and when the property ownership changes after a player lose. It also provides the functionality to check if a property is within 
a certain player's ownership using the contains method.
The class imitates what a player can do in a monopoly game so there are methods for rolling dice, buying property, paying rent which are called by the BoardModel class.
The class keeps track of its own name, balance, and properties.

The Property class keeps track of its name, price, rent, and ownership. It mainly helps provide information for the BoardModel and Player class using getters.

The Dice class is used to add customizability to the game. The number of faces of the die can be chosen during set up and the dice objects keep track of their own value.
They provide this value using getters to the BoardModel class.

The new graphical user interface is implemented using MVC model which includes adding classes for a controller and view.

The BoardController class uses a switch case that will act according to the action command of each button that helps identify it.

The GameFrame contains code that composes each component individually, this was to avoid errors that ooccurred when using methods to create components. There is some functionality allocated to methods such as creating buttons and setting panel layouts. The class is composed of multiple panels that contain different aspects of the game such as the board, the player information, and player buttons. It contains multiple buttons that the player can use to play the game. It also uses the hashmap of properties from the 
BoardModel to instantiate the respective property panels.


# Known Issues
- If a group decides to end the game and they let the winner be decided by net worth. When multiple players have the highest net worth, the first in registration will be 
declared as the winner.
  - This is an unresolved issue because it would add unnecessary complexity to the current class.
- Some player buttons don't work such as roll and buy.
  - This includes the information labels not appearing after button presses.
- Some tests failed such as getProperty and getPlayers.
- Errors found through testing in quit command, buy command, and declare winner command.


# What's Next?
- Need to get the remaining player buttons working
- Implement a view interface
- Add additional features such as buying houses and hotels
- Add special properties such as jail, railways, utilities
- Add option to use AI players
