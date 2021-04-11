# Java-Internship-Technical-Assignment

## For exercise 2 to work
### After generating the jar file, in the same directory should be created a .txt file named "storage.txt"

It works with the commands:

- java -jar myaplication.jar -createUser -fn='FirstName' - ln='LastName' -un='UserName'
- java -jar myaplication.jar -showAllUsers
- java -jar myaplication.jar -addTask -un='userName' -tt='Task Title' -td='Task Description'
- java -jar myaplication.jar -showTasks -un='userName'

## For exercise 3
### I have used MySQL Workbench in order to create the database and connect to it

### Database consisted of 3 tables : 
**Group**[groupName, groupId], 
**User**[firstName, lastName, userName, groupName], 
**Task**[taskTitle, Description, userName, taskId, groupName]

### The app is based on Maven. It has to be build.

Commands:

- java -jar myaplication.jar -createUser -fn='FirstName' -ln='LastName' -un='UserName'
- java -jar myaplication.jar -showAllUsers
- java -jar myaplication.jar -addTask -un='userName' -tt='Task Title' -td='Task Description'
- java -jar myaplication.jar -showTasks -un='userName'
- java -jar myaplication.jar -createGroup -gn='groupName'
- java -jar myaplication.jar -addUserToGroup -gn='groupName' -un='userName'
- java -jar myaplication.jar -addTaskToGroup -gn='groupName' -tt='Task Title' -td='Task Description'
