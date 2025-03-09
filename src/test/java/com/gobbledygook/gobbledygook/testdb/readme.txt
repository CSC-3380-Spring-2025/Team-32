You can setup the word database by running the command on a terminal:

sqlite3 words.db < setup.sql

This will run a script that sets up the database on your pc. This is suitable since the database is small enough.

Then any database functions in the application should be able to query the table for words.