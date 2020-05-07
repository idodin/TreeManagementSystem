# Project-09

## General Information

This is a repository for the TreePLE Application created by Project Group 9 for the ECSE 321 Winter 2018 Course.

## Website URL
http://ecse321-9.ece.mcgill.ca:8087/#/

## Screenshots
### Login Page
![Imgur](https://i.imgur.com/XTSfuXC.png)

### Create Tree Page
![Imgur](https://i.imgur.com/Qq3YRGN.png)

### Visualizer Page
![Imgur](https://i.imgur.com/C6zFfRh.png)

## Workflow Description

Each member has their own development branch. We did this with the following command (for future reference):

**Please substitute imad with your development branch name**

`git checkout -b imad`

We then pushed it, linking it by setting the 'upstream' (remote link) to the origin remote repository. 

`git push --set-upstream origin imad`

**Our work will proceed in the following way** 

Everyone will work on their own branches, commiting and pushing as they make changes. To be safe proceed in the following way everytime you start working:

`git checkout imad`

`git pull`

`Do work`

`git add *` (Or relevent files)

`git commit -m "Commit Message"`

`git push`

When you're done with the task you're trying to accomplish. Go to github and generate a Pull Request: **(new Pull Request on repository homepage)**

The base should be master and the compare should be your dev branch. 
Write a description of your pull request and link it the issue you're trying to solve by writing, for example, `#11` in the comment body. 

**Let someone on the team review your code and then merge** 

You can then make a comment on the issue and once decided, we can close the issue if it has been solved. 

**If you need to update your branch with work that has been implemented on the master branch**

First commit and push the work you've already done

`git checkout imad`

`git add *`

`git commit -m "Commit Message"`

`git push`

Create a pull request to grab changes from the master, but this time put the base as your dev branch and the compare as your master. Create a pull request with a message like "Updating dev branch with master changes" and merge it. There may be conflicts that you need to resolve.

You can now grab these changes locally by typing

`git checkout imad`

`git pull`






