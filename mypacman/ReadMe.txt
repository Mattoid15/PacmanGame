After adding, editing, or commenting files
Do this in command line to upload changes to github

git add .
git commit -m <"Comment of the changes">
git push -u origin master


To get changes from github:

*probably, for first time* git remote add origin https://github.com/Mattoid15/PacmanGame.git
git fetch origin
git merge origin/master

    Or

git pull origin master
