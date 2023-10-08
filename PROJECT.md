# Final Project
The Introduction to Programming project work is a programming assignment that is done independently.

## Contacts Application

### Topic

Create simple contacts application where a user can create, read, update and delete contacts. Contact information has a [personal identification number](https://fi.wikipedia.org/wiki/Henkilötunnus) (mandatory), first name (mandatory), last name (mandatory), phone number (mandatory), address (optional) and e-mail (optional). All the information are stored persistently in a text file.

### Example data

| Field        | Value                |
| ------------ | -------------------- |
| personal id  | 131052-308T          |
| first name   | Jack                 |
| last name    | Smith                |
| phone number | +3584023423          |
| address      | Hämeenkatu 1         |
| e-mail       | jack.smith@gmail.com |

### Requirements
- Application must run using following commands: `cd src/ && javac *.java && java ContactsApp` or if using gradle: `gradle run`
- Application must be returned before deadline. If deadline is missed and is returned in January, the grade will be 1. Project work cannot be returned after January.
- The app does what it is suppose to do (topic).
- Project has proper documentation and it is returned using github.
- App must be run on Java 17
- Usage of third-party class libraries is prohibited (exception: JavaFX)

### Code Style
- Write everything in English.
- Use Javadoc.
- Use **4** spaces for indentation.
- User proper naming in variables and methods (Camel case).
- Maximum line length is 80 chars.

### Documentation
- Create README.md file.
- Add a link to screencast video to the README.md file.
- Add instructions how to compile and run your app. (Test these!)
- Generate html documentation from javadoc comments.


### Return

Use GitHub Classroom to accept the assignment and use the private github repository for your project. The directory structure is following:
```
    .gitignore
    README.md
    src/ContactsApp.java // Your main java file
        X.java           // Other Java files 
        Y.java
    doc/index.html       // Generated javadoc html documentation
```

Or use gradle project structure.

### Screencast

Each student creates a screencast of the project work. In the screencast (max 20 min) student goes through the app from:
1. User perspective.
2. Developer’s perspective (key aspects of code).
3. What known bugs do you have in your app.
4. Self-evaluation of the project (what grade are you targeting at).

Create video chapters to each section if using youtube. 

There are several screen capture apps for both Windows and Mac OS. In Mac, you can just use the Quicktime Player provided in the OS. In Windows, you can for example use OBS. You can upload the screencast to YouTube or Vimeo and it does not have to be public. If the screencast is under password, please provide the password in the `README.md`-file. TEST that your screencast works (permissions)! For example: log out of youtube or vimeo, then try to test the url!


### Tips
- There are many available classes in the Java API that can be used to read and write files in Java: `FileReader, BufferedReader, Files, Scanner, FileInputStream, FileWriter, BufferedWriter, FileOutputStream`, etc. Which one to use depends on the Java version you're working with and whether you need to read bytes or characters, and the size of the file/lines etc. You may have to do some research on this.
- You will have to decide the file format of your “database” text file, a lot of options here also: csv, xml, json or you can decide your own file format.
- For graphical user interface you can use swing (old) or javafx (new)
- One possible way of doing validation is regular expressions.

### Grading
- 1 - App compiles and runs. User is able to create, read, update and delete contacts that are stored in textfile. App has major bugs and some of the functionality is missing or not working as excepted. Javadoc comments are missing and/or html documentation is not generated. 
- 2 - User input is validated but has some bugs.
- 3 - Code is divided into methods and these methods are used in a reasonable manner. User input has good validation. 
- 4 - Object oriented programming used in some manner for implementing the application.
- 5 - Excellent object oriented usage and/or application has a graphical user interface (GUI).

Also code conventions / style usage influences the grading.

## Plagiarism

A student can discuss the work on a general level with other students, but copying even one line of code from another student is considered fraudulent activity. Returned work is run through a plagiarism checking application that looks for similarities in work. Naming the variables slightly differently or changing the indentation will not prevent the plagiarism tool from finding similarities. In addition to the tool, the similarity of the works is evaluated visually by the evaluator.

Plagiarism of the work leads to the rejection of the project work.

See if [intranet](https://intra.tuni.fi/handbook?page=2255).
