# Conway Java Large 
This application implements the paper written for NU CSC 603
It include a way to Start, Stop, Reset, set the inital random seed, and point-click to change cells. 

it takes advantage of the composite or hiarchical MVC design pattern from Conway_002. 

Added onClick event hanlder to the the individual cells, where the MyAppControler Class decides what to do with the events. 
Added and connected button events added to the MyAppView to GameBoardController so the MyAppController connects and decides how to handle each event

TODO: add a way to save each board to a file.
plan is to use CSV and/or JSON to save and load arbitriary sized boards. 
the data within the json files should save size of board and initial conditions, and possibly if a seed was used to generate it

should be able to re-use much of the FileOutput Class used for the commandLine version of the application. 

# No functionallity should be lost between Conway_002 to this version. 
