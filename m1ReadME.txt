# SYSC3110_GROUP18_PROJECT
# Milestone 1
# Author: Jeneerthan Pageerathan

# Team Members
- Christina Dang
- Jeneerthan Pageerathan
- Osaretinmwen Iyamu
- Vishesh Dasani


# The Deliverables
- The source code which consists of four classes:
  - Board.java: Vishesh Dasani
  - Dice.java: Christina Dang
  - Player.java: Jeneerthan Pageerathan
  - Property.java: Osaretinmwen Iyamu

- A game manual that explains the rules and game structure of Monopoly. It includes the system requirements and how to play the game: Written by Jeneerthan Pageerathan
- A UML class diagram that shows the classes and their relationships: Created by Osaretinmwen Iyamu
- A UML sequence diagram for important events that shows how different classes interact during such events: Created by Christina Dang


# Design Decisions
The Board class contains two dice and uses an ArrayList to keep track of players since the list will need to be changed during the 
game setup and when players go bankrupt or quit.
It uses Hashmaps to track player positions and property positions. The player maps to its integer position. The integer position in the other hashmap then maps to the property.
This makes it easy for the board to connect player positions with property positions.
It avoids switch cases since the stacked cases make breaking from certain loops difficult.
The board has minimmal methods and is mainly responsible for keeping track of players, properties and the events that occur which then call upon methods from the other classes 
such as Player.

The Player class implements an ArrayList to keep track of properties that the player object owns. The ArrayList data structure is useful for this because the list will 
need to be changed as players buy properties and when the property ownership changes after a player lose. It also provides the functionality to check if a property is within 
a certain player's ownership using the contains method.
The class imitates what a player can do in a monopoly game so there are methods for rolling dice, buying property, paying rent which are called by the Board class.
The class keeps track of its own name, balance, and properties.

The Property class keeps track of its name, price, rent, and ownership. It mainly helps provide information for the Board and Player class using getters.

The Dice class is used to add a customizability to the game. The number of faces of the die can be chosen during set up and the dice objects keep track of their own value.
They provide this value using getters to the Board class.

# Known Issues
- If a group decides to end the game and the let the winner be decided by net worth. When multiple players have the highest net worth, the first in registration will be 
declared as the winner.
  - This is an unresolved issue because it would add unnesscary complexity to the current class.

# What's Next?
- The game will have a GUI, and so it will be played using a mouse and buttons instead of a keyboard and text commands.
  - It will provide visual representation of the board, players, and properties.
- Unit tests will be created to ensure correct functionality of important methods.
