# B_ToDoListApp

TODO list app for **Mobile Systeme**, exercise B, part 1.

## What the app can do
- show TODOs in a RecyclerView
- create new TODOs
- edit existing TODOs
- delete TODOs with a button or by swipe
- set title, description, priority, categories, done state and due date
- sort TODOs by priority or date
- change the list font size in the settings
- play a short reward sound when a TODO is marked as done

## Main files
- `MainActivity` shows the TODO list
- `DetailActivity` is used for creating and editing TODOs
- `SettingsActivity` contains the font size setting
- `TodoRepository` stores TODOs during runtime
- `TodoAdapter` connects the data with the RecyclerView
