# My Personal Project

## A subtitle

A *bulleted* list:
- item 1
- item 2
- item 3

An example of text with **bold** and *italic* fonts.  Note that the IntelliJ markdown previewer doesn't seem to render 
the bold and italic fonts correctly but they will appear correctly on GitHub.

The application is something like a game menu, where the user would be given options and interact with 
a character.
The application contains a todo list, ie. you can ask the character to remember your todos
but it can also remember other things, for example, your favourite movie names.

Anyone can use it for organizing what they have to do, or remember the names of some of their favourite things:
movies, songs, shows, etc

This project interests me because I always use a notepad application to record the things listed above
I would like to make an application like this, and preferably with more advanced graphics if time permits

I could use markdown to create titles and lists for the String collector.

## User Stories

A *bulleted* list:

- As a user, I want to be able to add a task to my lists
- As a user, I want to be able to view the list of tasks on my lists
- As a user, I want to be able to mark a task as complete on my to-do list
- As a user, I want to be able to delete an item from any of my lists
- As a user, I want to be able to see the completion status of my tasks

- As a user, I want to be able to save my to-do list to file
- As a user, I want to be able to load my to-do list from file 
- As a user, I want to be given the option to load the to-do list upon launching the app
- As a user, I want to be given the option to save the to-do list upon closing the app

- A class in my model package (TodoList) is made robust and some methods throws NotInBoundsException. 
The cases when an exception is thrown and when no exceptions are thrown are both tested in the TodoListTest class


Phase 4: Task 3
If I had more time, I would refactor to reduce the number of fields in GraphicalApp. I might make a new class
to hold all the imageIcons/JLabels/JPanels/etc.

I could make a new class in the model package to organize and hold all the actionListeners.

I could make a new method that gives the range of the amount of tasks in todoList, which is useful when changing
the imageLabel in the main panel.



