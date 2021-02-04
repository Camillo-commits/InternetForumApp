# InternetForumApp
Internet forum app for android with connection to database via PHP.   
It is recommended for database to be set up on XAMPP web server solution. To create working environment for the app follow instructions below.
## Instructions
1. Install XAMPP Control Panel
2. Paste files from PHP folder into /XAMPP/htdocs
3. Change host ip in every file pasted in point 2
4. Change host ip in every doInBackground method downloaded from https://github.com/Camillo-commits/InternetForumApp/tree/main/app/src/main/java/com/example/forumprojectwithphp
5. Import database structure
6. Turn on Apache Server and MySQL module
7. Run the app
## Permissions used in the app
There are two types of permits curently aplied in the app. 
### Activity permissions
Shows what each user can do on the forum.
1. Admin permission  
allows to delete permissions and users
2. Full creator permission  
allows to add new topic groups
3. Creator permission  
allows to add only new topics
4. Basic permission  
allows to read, reply and write messages on topics that are allowed for the user
### Topic entry permissions
Show how advanced and involved the user is. Some of the less advanced users might not be able to enter some more advanced topics to ensure that the informations spread across the forum are correct.  
1. For everybody
2. For active users
3. For experienced users
4. For veterans
