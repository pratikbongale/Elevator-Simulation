# Elevator-Simulation
  Simulation of an elevator using Threads in Java.

# Specifications:
	The elevator has to serve n floors.
  The elevator can be called to every floor.
  The elevator has to serve every floor in a fair way. The use the ’Sabbath Service’ algorithm would be fine.
  The elevator car can only hold 300kg.
  People queueing up in front of the elevator weigh between 40kg and 100kg. The weight is randomly assigned.
  There is no correlation between people going to a floor and people leaving a floor.
  The elevator, and people have to be simulated as individual threads.
  This requirement list is not complete, add requirements you find missing to this list.
  
# Design: 
  We have chosen to simulate the elevator using the Model – View – Controller (MVC) paradigm of program design.
  •	Controller:
    o	Update View
    o	Handles movement of elevator
  •	View:
    o	The display outside every elevator showing the floor being served by the elevator.
  •	Model:
    o	Stores the main logic of elevator.
    o	Processes all the input received from controller.
    o	Decides whether to open the door at a particular floor
    o	Informs when the lift is overweight.

# Implementation:
  •	An elevator is an independent thread (Elevator.java).
  •	Every person is a distinguished thread (Person.java).
  •	Controller creates elevator and person threads (Controller.java) 
  •	The program maintains 3 Linked Lists with nodes of type Person:
    o	upList: stores all the people who wish to go in upward direction.
    o	downList: stores all the people who wish to go in downward direction.
    o	inList: stores all the people currently inside the elevator.
 
# Pseudocode:
  1.	Controller:
    Create elevator thread and start it.
    Create n people ( n taken from user ).
  2.	Person:
    Synchronize on the controller(common to all people)
    Call elevator using this controller
    Get added in one of the lists(upList/downList)
    Wait and allow other threads to add themselves to elevators lists.
  3.	Elevator:
    Direction of lift = up
    Check upList, if not Null serve the people going up.
    For every floor :
      Check if anyone wants to get down
      Check if anyone wants to enter the elevator
    Check downList, if not null serve the people going down.
      For every floor :
      Check if anyone wants to get down
      Check if anyone wants to enter the elevator


# Note: 
upList stores all the person threads in ascending order of their start floors.
downList stores all the person threads in descending order of their start floors.
