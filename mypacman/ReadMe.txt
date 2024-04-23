Author: Matthew Lingenfelter

Design Elements:
    Each ghost has its own personallity similar to the origin Pacman Game
    Each ghost chooses which direction to go only at intersections or corners
        This was done to improve runtime by limiting the number of times each ghost has to calculate where to go
    Pacman has a small error margin when turning corners to help the player turn corners easier
    The lines from each ghost to pacman is a representation for where each ghost is attempting to get to

To be worked on for futher development:
    add indicator for which way pacman is facing
    show high score while in game
    add a "you died" or similar message when hit by ghost
    add bonus fruit that spawns when certain amounts of food has been eaten

Known bugs:
    Game will crash if a ghost is in the scared state and attempts to go from one side of the screen to the other via the teleporter
    Ghosts can occationally get stuck if pacman is not moving
        However, ghost resumes tracking once pacman moves

Git Hub Stuff ------------------------------------------------------

After adding, editing, or commenting files
Do this in command line to upload changes to github

git add .
git commit -m <"Comment of the changes">
git push -u origin master

To get changes from github:
git remote add origin https://github.com/Mattoid15/PacmanGame.git
git fetch origin
git merge origin/master
    Or
git pull origin master
