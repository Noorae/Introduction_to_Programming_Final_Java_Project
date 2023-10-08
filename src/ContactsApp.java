import java.util.Scanner;
import javax.management.RuntimeErrorException;
import java.util.Optional;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.io.FileWriter;
import java.io.BufferedWriter;

/**
 * This is a ContactsApp class which is the main class to navigate inside 
 * our App.
 * 
 * This App has 3 classes. ContactsApp is the main class 
 * which is used to access the App. Inside ContactsApp you
 * can navigate and give commands to the app like show all
 * contacts, add new contacts, change contact info and delete
 * contact. This class is also used to close the app.
 * When the program starts.
 * 
 * @author Noora Vainionpaa
*/
public class ContactsApp {
    
/**
 * Main method which is used to navigate throught the app.
 * 
 * Insinde the main method we create our App object Contacts.
 * When the App starts, it will first check if data is stored
 * in an file and store that data inside the Contacts class.
 * Main method contains delays to make the App feel more userfriendly
 * because it's used from the terminal.
 * When the app is closed, it will use the method inside Contacts
 * Class to store all the data inside the app to a file
 * 
 * @param args 
 */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int action = 0;
        Contacts contacts = new Contacts();
        contacts.readContacts();

        try {
            Thread.sleep(500);
            
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.print("* * * * * * * ");
        System.out.print("W E L C O M E   T O   C O N T A C T S A P P ");
        System.out.println("* * * * * * *\n\n");

        while (action != 4) {

            try {
                Thread.sleep(500);
                
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }    

            System.out.println("\nList of commands\n* * * * * * * * *\n");
            System.out.println("Press 1 to Show all contacts");
            System.out.println("Press 2 to Search for a contact");
            System.out.println("\t\t~Search to update a contact");
            System.out.println("\t\t~Search to delete a contact");
            System.out.println("Press 3 to Add a new contact");
            System.out.println("Press 4 to Quit and save changes");

            
            try {

                action = Integer.parseInt(scanner.nextLine());

                switch (action) {
                    case 1: contacts.showAllContacts();
                    break;
                    case 2: contacts.findContact();       
                    break;
                    case 3: contacts.addContacts();
                    break;
                    case 4: try {
                                Thread.sleep(500);
                            
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.print("* * * * * * * ");
                                System.out.print("A P P   C L O S E D ");
                                System.out.println("* * * * * * *\n");
                                contacts.saveContactstoFile();
                                break;
                    default:
                            System.out.println("Please enter a valid command");
                    }
                    
                    
                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid command");
                        continue;
                    }
                
        }

    }
}

/**
 * Contacts class stores the functionality of our App.
 * This class hass all the mothods needed to show data, change data, delete data
 * and add new data. It also contains methods needed to read our data from file 
 * and write our data on to a file. This class also stores a 'child class' Person
 * which is used to store information about Person objects. 
 */
class Contacts {

    /** Arraylist with type Person that is used to store every person 
     * object and their unique info.
     */ 
    private ArrayList<Person> ContactsInfo;

    /** String array that is used to get Person info
     *  from the file where our data is stored.
     * Length is set to 6 which is the number of data fields of each person. 
     * Person has max 6 fields of info stored. 
     */
    private String[] info = new String[6];

    /**Constructor of Contacts is used only once at the start 
     * of our app and it creates a single arraylist. 
     * This arraylist stores our info when the app is running.
     */
    
    Contacts() {

        /**
         * ContactsInfo arraylist that stores Person objects
         * 
         * Arraylist is created only once when constructor is used.
         */
        this.ContactsInfo = new ArrayList<>();
    }

    /**
     * This method takes user input and checks that the input is correct input
     * that is needed to move forward inside the app.
     * 
     * Inside the app user needs to use commands Y = yes and n = no to advance
     * further inside the app. To prevent the app from crashing, this methods 
     * checks the input user gives and returns false statement if the input is
     * not Y, y, N,n which are the only characters that will return true. 
     * This method also makes the code shorter so the same line of code isn't 
     * repeated.
     * @param action user input to determine action Yes or No
     * @return returns true statement if user inputs one of these characters:
     * Y,y,N,n
     */
    public boolean checkCommand(char action) {
        if (action !='N' && action !='n' && action !='Y' && action !='y') {
            System.out.println("Please enter a valid command." +
                                " Y = Yes , N = No");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method that checks that Personal ID data cannot be added to a Person
     * if it already excists with another Person object.
     * 
     * Personal Id will differentiate Persons from each other in the App and 
     * therefore there cannot be two Persons with the same Personal ID. This 
     * methods iterates through the ContactsInfo arraylist to check wether the
     * given ID matches one that is already in the list. If a match is found, 
     * method returns false.
     * 
     * @param idNumber Personal ID number 
     * @return returns true if user inputs a person ID that is not in use 
     * already
     */
    public boolean checkDublicateID(String idNumber) {
        for (Person people : ContactsInfo) {
            if ((people.getID()).equalsIgnoreCase(idNumber)) {
                System.out.println("This ID number is already in use," + 
                                    " please give another ID number");
                return false;
            }
        }
        return true;

    }

    /**
     * readContacts() method reads data from a file and brings this data to our 
     * App for use at the start of our App.
     * 
     * This method is only used once at the start of our App in the main mathod.
     * It scans the file and brings that data one line at a time and stores this
     * into String line. String is then split using colon and info is added to 
     * info array. Then Person constuctor is called as stored array info as it's
     * parameters.
     * 
     * At the end of the method String line is emptied and a new String created
     * to make sure no data is left inside.
     * 
     */
    public void readContacts() {

            
        try (Scanner fileReader = new Scanner(Paths.get("Contacts.csv"))) {


            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                info = line.split("\\,");
                Person person = new Person(info[0], info[1], info[2], info[3], 
                                            info[4], info[5]);
                ContactsInfo.add(person);
                
                info = null;
                info = new String[6];
                
                }

        } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
    }

    /**
     * This method saves all data to file while app is closed.
     * 
     * This method uses BufferedWriter class to write data to file. First 
     * the Writer is created and ContactsInfo arraylist is iterated. In that
     * arraylist the Person class parameters are taken and stored to variable
     * information. Variable Information is then stored to file and made empty
     * for the next line. When loop ends, the writer is closed
     * 
     */
    public void saveContactstoFile() {
        
        try { 

            FileWriter fileWriter = new FileWriter("Contacts.csv");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("");
            
            for (Person people : ContactsInfo) {
                String information = people.getID()+","+people.getFirstName()+","+
                                people.getLastName()+","+people.getPhoneNumber()+
                                ","+people.getAddress()+","+people.getEmail();


                bufferedWriter.write(information);
                bufferedWriter.newLine();
                information = "";

            } 

            bufferedWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * addContacts() method is used to add a new contact to App.
     * 
     * When the method is called, a new Person object is created and attributes
     * like name, phone number, address and email will be added to this object.
     * User will be asked all of the info to be added. Optional fields like 
     * address and email can be skipped by pressing Enter. Method is rather 
     * long and goes through while loops to give user a change to give another 
     * input if the validation doesn't go through. All the variables go through
     * validation methods to check if they can be set as Person object attributes.
     * ID number also goes through the checkDublicateID() method to verify that
     * theID number isn't already in use.
     * 
     */
    public void addContacts() {
        Scanner scanner = new Scanner (System.in, "Cp850");
        System.out.println("Adding new contact");

        Person newPerson = new Person();
        
        System.out.println("Enter ID number:");
        
        while (true) {
        
            String idNumber = scanner.nextLine();
            if (newPerson.validateID(idNumber)==true &&
                checkDublicateID(idNumber)== true) {
                newPerson.setID(idNumber);
                break;

            }
        }

        System.out.println("Enter first name:");

        while (true) {
        
            String firstName = scanner.nextLine();
            if (newPerson.validateFirstName(firstName)==true) {
                newPerson.setFirstName(firstName);
                break;

            }
        }

        System.out.println("Enter last name:");

        while (true) {
        
            String lastName = scanner.nextLine();
            if (newPerson.validateLastName(lastName)==true) {
                newPerson.setLastName(lastName);
                break;

            }
        }

        System.out.println("Enter phone number:");

        while (true) {
        
            String phoneNumber = scanner.nextLine();
            if (newPerson.validatePhoneNumber(phoneNumber)==true) {
                newPerson.setPhoneNumber(phoneNumber);
                break;

            }
        }

        System.out.println("Enter address, press ENTER to leave empty:");

        while (true) {
        
            String address = scanner.nextLine();
            if (newPerson.validateAddress(address)==true) {
                newPerson.setAddress(address);
                break;

            }
        }

        System.out.println("Enter email address, press ENTER to leave empty:");

        while (true) {
        
            String email = scanner.nextLine();
            if (newPerson.validateEmail(email)==true) {
                newPerson.setEmail(email);
                break;

            }
        }

        ContactsInfo.add(newPerson);

        System.out.println("Contact successfully created");

    }
    /**
     * This metod deletes Person objects from the program.
     * 
     * Index number of searched person is used to access the right Person object.
     * Method deletes object from arraylist.
     * 
     * @param index index number of searched Person in the arraylist to be 
     * deleted
     */
    public void deleteContact(int index) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you really want to delete this contact? Y / N");

        System.out.println(ContactsInfo.get(index));

        char action = '0';
        action = scanner.next().charAt(0);

        while (action != 'N' && action != 'n') {
            
            if (checkCommand(action)==true) {
                ContactsInfo.remove(index);
                System.out.println("Contact was successfully deleted\n");
                break;
            }
            action = scanner.next().charAt(0);

        }

        action = '0';

    }
    /**
     * Method changes Person object attributes based on which attributes the 
     * user wants to change.
     * 
     * changeContactInfo method is called when a user wants to change data from 
     * an already exsisting Person. User will tell the program which data they 
     * want to change and skip the ones they wish to keep the same. This is the 
     * longest method as it has the most number of while loops to make sure user
     * can re-enter values that didn't pass the validation methods. ID number
     * will be checked for dublicate values in this method too. When user input
     * passes the checks, setters will be called to add those inputs ass new 
     * attributes. The index number of Person in the ContactsInfo arraylist 
     * will be used to make sure the correct Persons data is changed.
     * 
     * Method has two scanners, one for action commands to be used for 
     * navigation
     * and the other one using encoding to make sure non english characters
     * are working correctly.
     * 
     * @param index index number of Personto be changed in ContactsInfo arraylist
     */
    public void changeContactInfo(int index) {
        Scanner scanner = new Scanner(System.in);
        Scanner dataScanner = new Scanner (System.in, "Cp850");
        
        System.out.println(ContactsInfo.get(index));
        System.out.println("Do you really want to change this contact? Y / N");

        char action = '0';
        action = scanner.next().charAt(0);

        while (action != 'N' && action != 'n') {

            if (checkCommand(action)==true) {

                System.out.println("Change the ID number? Y / N ");
                action = scanner.next().charAt(0);
                scanner.nextLine();
            
                while (action != 'N' && action != 'n') {
                    
                    if (checkCommand(action)==true) {
                        System.out.println("New ID number:");

                        
                        while (true) {
                            String idNumber = scanner.nextLine();
                            if (ContactsInfo.get(index).validateID(idNumber)== 
                            true && checkDublicateID(idNumber)== true) {
                                ContactsInfo.get(index).setID(idNumber); 
                                System.out.println("ID number changed to: " +
                                                    idNumber);
                                break;
                            }
                        }
                    break;
                    
                    } else {
                        action = scanner.next().charAt(0);
                    }
                }
            
                action = '0';

                System.out.println("Change first name? Y / N ");
                action = scanner.next().charAt(0);

                while (action != 'N' && action != 'n') {
                    if (checkCommand(action)==true) {
                        System.out.println("New first name:");

                        while (true) {
                            String firstName = dataScanner.nextLine();

                            if (ContactsInfo.get(index).validateFirstName(firstName)== true) {
                                ContactsInfo.get(index).setFirstName(firstName); 
                                System.out.println("first name changed to: " + firstName);
                            
                                break;
                        
                            }
                        }

                    break;
                
                    }  else {
                        action = scanner.next().charAt(0);
                    }
                }

                action = '0';

                System.out.println("Change last name? Y / N ");
                action = scanner.next().charAt(0);

                while(action != 'N' && action != 'n') {
                    if (checkCommand(action)==true) {
                        System.out.println("New last name:");


                        while (true) {
                            String lastName = dataScanner.nextLine();
                            if (ContactsInfo.get(index).validateLastName(lastName)== true) {
                                ContactsInfo.get(index).setLastName(lastName); 
                                System.out.println("Last name changed to: " + 
                                                    lastName);
                                break;

                            }
                        }
                        break;

                    }  else {
                        action = scanner.next().charAt(0);
                    }
                }

                action = '0';

                System.out.println("Change phone number? Y / N ");
                action = scanner.next().charAt(0);

                while (action != 'N' && action != 'n') {
                    if (checkCommand(action)==true) {
                        System.out.println("New phone number:");


                        while (true) {
                            String phoneNumber = dataScanner.nextLine();
                            if (ContactsInfo.get(index).validatePhoneNumber(phoneNumber)== true) {
                                ContactsInfo.get(index).setPhoneNumber(phoneNumber); 
                                System.out.println("Phone number changed to: " +
                                                    phoneNumber);
                            break;
                            }  
                        }
                        break;

                    }  else {
                        action = scanner.next().charAt(0);
                    }
                }

                action = '0';

                System.out.println("Change address? Y / N ");
                action = scanner.next().charAt(0);

                while (action != 'N' && action != 'n') {

                    if (checkCommand(action)==true) {
                        System.out.println("New address, press ENTER "+ 
                                            "to leave empty:");


                        while (true) {
                            String address = dataScanner.nextLine();
                            if (ContactsInfo.get(index).validateAddress(address)== true) {
                                ContactsInfo.get(index).setAddress(address); 
                                System.out.println("Address changed to: " + 
                                                    address);
                            break;
                            }
                        }
                        break;

                    }  else {
                        action = scanner.next().charAt(0);
                    }
                }

                action = '0';

                System.out.println("Change email? Y / N ");
                action = scanner.next().charAt(0);

                while (action != 'N' && action != 'n') {
                    
                    if (checkCommand(action)==true) {
                        System.out.println("New email address, press ENTER "+
                                            "to leave empty:");


                        while (true) {
                            String email = dataScanner.nextLine();
                            if (ContactsInfo.get(index).
                                validateEmail(email)== true) {
                                ContactsInfo.get(index).setEmail(email); 
                                System.out.println("Email changed to: " +
                                                     email);
                                break;
                            }
                        }
                    break;
                    
                    }  else {
                        action = scanner.next().charAt(0);
                    }
                }
                System.out.println("Here is your changed data:\n"+
                                    ContactsInfo.get(index));        
                break;

            }
            action = scanner.next().charAt(0);

        }
        
        action = '0';
            
    }

    /**
     * Method will find based on ID number if a person can be found in the app.
     * 
     * User inputs ID number which is then looped over arraylist ContactInfo 
     * which stores all the data. If a match is found, it will be printed for
     * user and user is asked what thei want to do with the data. User can go 
     * back, change or delete the data. User can also try to search again for
     * another data.

     * when listLength matches the size of the arraylist, if no matches has been
     * found, user will get a message of "No matches found".
     */
    public void findContact() {
        
        if (ContactsInfo.isEmpty()) {
            System.out.println("No contacts saved at the moment."+
                                " Search will havo no results.");
        } else {
            char action = 'Y';
            boolean notCatchedError = false;
            String searchedPerson = "";
            while (action != 'N' && action != 'n') {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Please enter the personal ID "+
                                    "of the person you want to find:");
                searchedPerson = scanner.nextLine();
                int listLength = 0;
                for (Person people : ContactsInfo) {
                    
                    if (searchedPerson.equalsIgnoreCase(people.getID())) {
                        System.out.println(people);
                        int ContactIndex = ContactsInfo.indexOf(people);

                        System.out.println("What do you want to do with this"+
                                            " contact?");
                        System.out.println("Press 1 to do nothing and go back");
                        System.out.println("Press 2 to delete contact");
                        System.out.println("Press 3 to change contact info");

                        int command = 0;
                        Scanner readCommand = new Scanner(System.in);

                        while (!notCatchedError) {

                            try {
                                command = Integer.parseInt(readCommand.nextLine());
                                
                                if (command == 2) {
                                    deleteContact(ContactIndex);
                                    notCatchedError = true;
                                    break;
                                }else if (command == 3) {
                                    changeContactInfo(ContactIndex);
                                    notCatchedError = true;
                                    break;
                                }else  if (command == 1) {
                                    command = 0;
                                    notCatchedError = true;
                                } else {
                                System.out.println("Invalid command, please "+
                                                    "choose a proper command");
                                }
                                

                            } catch (NumberFormatException e) {
                                System.out.println("Invalid command, please "+
                                                    "choose a proper command");
                                
                            }
                        } break;

                    } else {
                        listLength++;  
                    }
                    
                    if (listLength == (ContactsInfo.size())) {
                        System.out.println("No matches found\n"); 
                    }

                }     
        
            
                searchedPerson = "";

                try {
                    Thread.sleep(500);
            
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Do you want to make another search?"+
                                    " Press: Y / N");
                action = scanner.next().charAt(0);
                while (true) {
                    if (checkCommand(action)==true) {
                
                    break;
                    }
                    action = scanner.next().charAt(0);
            
                }

            }
        }

    }
    /** 
     * Simple method that lists all the Contacts stored in the App.
     * 
     * Method will loop over ContactsInfo arraylist and print every 
     * Person object to the screen.
     */
    public void showAllContacts() {
        for (Person people : ContactsInfo) {
            System.out.println(people);
        }
        if (ContactsInfo.isEmpty()) {
            System.out.println("No contacts saved at the moment\n");
        }
    }


    /** Inner class Person inside the Contacts class that stores Person 
     * attributes and has validation methods which check if user input is
     * valid.
     * 
 */
    public class Person {
        /**
        * idNumber stores unique id number of person
        */
        private String idNumber;

        /**
         *firstName stores first name of person
         */
        private String firstName;

        /**
         * lastName stores last name of person 
         */
        private String lastName;

        /**
         * phoneNumber stores phone number of person
         */
        private String phoneNumber;

        /**
         * address stores address of person (optional)
         */
        private String address;

        /**
         * email stores email address of person (optional)
         */
        private String email;

        /**
         * Constructor without parameters. Creates a new person with empty
         * attributes.
         */

        Person() {
        }

        /**
         * Constructor with parameters for the filereader so attributes can be
         * added straight from method that reads the data in file. If optional 
         * fields address and email are left empty, it is replaced with "-".
         * 
         * @param idNumber Id number of person stored on file
         * @param firstName first name of person stored on file
         * @param lastName last name of person sotred on file
         * @param phoneNumber phone number of person stored on file
         * @param address address of person stored on file
         * @param email email of person stored on file
         */
        Person(String idNumber, String firstName, String lastName, 
            String phoneNumber, String address, String email) {
            this.idNumber = idNumber.toUpperCase();
            this.firstName = firstName;
            this.lastName = lastName;
            this.phoneNumber = phoneNumber;
            if ( address.equals("")) {
                this.address = address+"-"; 
            } else {
                this.address = address;
            }
            
            if ( email.equals("")) {
                this.email = email+"-";
            } else {
                this.email = email;
            }

        }

        /** 
         * setID() method sets user input as Idnumber. Will also change it to
         * uppercase if user gave input was given in lowercase so the validation
         * method works correclty.
         * 
         * @param idNumber user input to be added as Person objects ID number
         */
        public void setID(String idNumber) {
             this.idNumber = idNumber.toUpperCase();

        }

        /**
         * setFirstName() method sets user input as first name.
         * 
         * @param firstName user input that is set as first name of Person
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * setLastName() method sets user input as last name.
         * 
         * @param lastName user input that is set as last name of Person
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         * setPhoneNumber() method sets user input as phone number.
         * 
         * @param phoneNumber user input that is set as phone number of Person
         */
        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        /**
         * setAddress() method sets user input as address.
         * If empty, sets address as "-".
         * 
         * @param address user input that is set as phone number of Person
         * if left empty, replaced with "-"
         */
        public void setAddress(String address) {
            if (address.equals("") || address == null) {
                this.address = address+"-";
            } else {
                this.address = address;
            }
        }

        /**
         * setEmail() method sets user input as email address.
         * If empty, sets email as "-".
         * 
         * @param email user input that is set as email of Person
         * if left empty, replaced with "-"
         */
        public void setEmail(String email) {
            if (email.equals("") || email == null) {
                this.email = email+"-";
            } else {
                this.email = email;
            }
        }

        /**
         * Returns ID number of Person.
         * 
         * @return returns ID number of Person
         */
        public String getID () {
            return this.idNumber;
        }
        /**
         * Returns first name of Person.
         * 
         * @return returns first name of Person
         */
        public String getFirstName() {
            return this.firstName;
        }
        /**
         * Returns last name of Person.
         * 
         * @return returns last name of Person
         */
        public String getLastName() {
            return this.lastName;
        }
        /**
         * Returns phone number of Person.
         * 
         * @return returns phone number of Person
         */
        public String getPhoneNumber() {
            return this.phoneNumber;
        }
        /**
         * Returns address of Person. Returns "-" if empty.
         * 
         * @return returns address of Person
         */
        public String getAddress() {
            if (this.address == null) {
                return this.address+"-";
            }
            
            return this.address;
        }
        /**
         * Returns email of Person. Returns "-" if empty.
         * 
         * @return returns email of Person
         */
        public String getEmail()  {
            if (this.email == null) {
                return this.email+"-";
            }
            return this.email;
        }



        /**
         * Method that checks the validation of Id number based on social 
         * security number rules in Finland. Method checks for length, century 
         * sign and control character. Method also checks some numbers that can't
         * be added as a birthdate.
         * 
         * idNumber user input that will be validated.
         * idValue takes the numeric values from ID Number to a String
         * idNumValue makes idValue String into integer
         * idRemainder stores remainder that is left over from deviding 
         * idNumValue with 31. This is used as the control character of social
         * security number.
         * controlChar arralist containing the control numbers. The 
         * idRemainder matches the index number of each control character.
         * 
         * @param idNumber user input to be validated as proper ID number

         * @return returns true if the validation is passed and id Number is 
         * valid
         */
        public boolean validateID(String idNumber) {

            idNumber = idNumber.toUpperCase();
            
            boolean realID = true;
                
                if (idNumber.length() == 11) {
                    realID = true;
                    if (String.valueOf(idNumber.charAt(6)).
                                        matches("[Aa+-]")) {
                        realID = true;
                        if (String.valueOf(idNumber.charAt(0)).
                                            matches("[0-3]")) {
                            realID = true;
                            if (String.valueOf(idNumber.charAt(1)).
                                                matches("[0-9]")) {
                                realID = true;
                                if (String.valueOf(idNumber.charAt(2)).
                                                    matches("[0-1]")) {
                                    realID = true;
                                    if (String.valueOf(idNumber.charAt(3)).
                                                        matches("[0-9]")) {
                                        realID = true;

                                        int idRemainder = 0;
                                        String idValue = "";
                                        int idNumValue = 0;
                                        char[] controlChar = 
                                            {'0', '1', '2', '3', '4', '5', '6',
                                            '7', '8', '9', 'A', 'B', 'C', 'D',
                                            'E','F', 'H', 'J', 'K', 'L', 'M',
                                            'N', 'P', 'R', 'S', 'T', 'U', 'V',
                                            'W', 'X', 'Y'};
   
                                        for (int i = 0; i<6; i++) {
                                            idValue = idValue + String.
                                            valueOf(idNumber.charAt(i));
                                        }

                                        for (int i = 7; i<10; i++) {
                                            idValue = idValue + String.
                                            valueOf(idNumber.charAt(i));
                                        }

                                        idNumValue = Integer.parseInt(idValue);
                                        idRemainder = idNumValue % 31;

                                        if (idNumber.charAt(10) == 
                                            controlChar[idRemainder]) {
                                            realID = true;
                                        } else {
                                            System.out.println("Invalid ID, "+
                                                    "please give a proper ID");
                                            realID = false;
                                        }


                                    } else {
                                        System.out.println("Invalid ID, "+
                                                    "please give a proper ID");
                                        realID = false;
                                        
                                    }
                                } else {
                                    System.out.println("Invalid ID, "+
                                                    "please give a proper ID");
                                    realID = false;
                                    
                                }
                            } else {
                                System.out.println("Invalid ID, "+
                                                    "please give a proper ID");
                                realID = false;
                                
                            }
                        } else {
                            System.out.println("Invalid ID, "+
                                            "please give a proper ID");
                            realID = false;
                            
                        }
                    } else {
                        System.out.println("Invalid ID, "+
                                        "please give a proper ID");
                        realID = false;
                        
                    }
                } else {
                    System.out.println("Invalid ID length, please give a proper ID");
                    realID = false;
                    
                    
                }

                
            return realID;

        }
        /**
         * Method that checks the validation of first name. 
         * Regular expression is used to have some restrictions as to what can
         * be considered a first name. Name cannot contain numbers or special 
         * characters other than unicode letters. "-"" and "'"" are accepted.
         * 
         * @param firstName user input that is validated
         * 
         * @return returns true if the name passes validation check
         */
        public boolean validateFirstName(String firstName) {
            boolean realFirstName = false;
            
            if (firstName.matches( "([a-zA-Z \\p{L}\\-\\']+{2,50})")) {
                realFirstName = true;
            } else {
                System.out.println("Not a valid first name, "+
                                    "please give another name:");
            }
            return realFirstName;

        }
        /**
         * Method that checks the validation of last name. 
         * Regular expression is used to have some restrictions as to what can
         * be considered a last name. Name cannot contain numbers or special 
         * characters other than unicode letters. "-"" and "'"" are accepted.
         * 
         * @param lastName user input that is validated
         * 
         * @return returns true if the name passes validation check
         */
        public boolean validateLastName(String lastName) {
            boolean realLastName = false;
            
            if (lastName.matches("([A-Za-z \\p{L}\\-\\']+{2,50})")) {
                realLastName = true;
            } else {
                System.out.println("Not a valid last name, "+
                                "please give another last name:");
            }
            return realLastName;

        }
        /**
         * Method that checks the validation of phone number. 
         * Regular expression is used to have some restrictions as to what can 
         * be considered a phone number. Phone number can only contain numbers,
         * spaces, "+" or "-". Phone number has to be 6 - 20 characters long.
         * 
         * @param phoneNumber user input that is validated
         * @return returns true if phone number passes the validation
         */
        public boolean validatePhoneNumber(String phoneNumber) {
            boolean realPhoneNum = false;

            if (phoneNumber.matches("[\\d \\- \\+]{6,20}")) {
                realPhoneNum = true;
            } else {
                System.out.println("Not a valid phone number, "+
                                    "please give another phone number:");
            }

            return realPhoneNum;
        }
        /**
         * Method that checks the validation of the address. 
         * Regular expression is used to have some restrictions as to what can
         * be considered an address.Field can either be empty or Length must be 
         * between 3-50 characters, Can contain numbers, letters, unicode 
         * letters and some special characters like "-" , "." and "'".
         * 
         * @param address user input that is validated
         * @return returns true if address passes the validation
         */
        public boolean validateAddress(String address) {
            boolean realAddress = false;

            if (address.matches("(^$|([A-Za-z0-9\\p{L} \\-\\.\\']{3,60}))")) {
                realAddress = true;
            } else {
                System.out.println("Not a valid address, "+
                                    "please give a nother address:");
            }
            return realAddress;
        }
        /**
         * Method that checks the validation of the email address. 
         * Regular expression is used to have some restrictions as to what can 
         * be considered an email address. Field can be empty or it must contain 
         * a "@" and a dot and text between and before them. Dot cannot be right
         * before or after "@", text need to be in between.
         *  
         * @param email user input that is validated
         * @return returns true if email address passes the validation
         */
        public boolean validateEmail(String email) {
            boolean realEmail = false;

            if (email.matches("(^$|^.*@.*\\..*$)")) {
                realEmail = true;
            } else {
                System.out.println("Not a valid email, "+
                                "please give another email address:");
            }
            return realEmail;
        }
        /**
         * toString override to print personal data in a clean and readable
         * way for the user
         */
        @Override
        public String toString() {
            return ("\n\tContact data\n" +
                    "----------------------------\n" +
                    "Personal ID: " + "\t"+this.getID()+"\n"+
                    "First name: " + "\t"+this.getFirstName() + "\n" + 
                    "Last name: " + "\t"+this.getLastName() + "\n" +
                    "Phone number: " + "\t"+this.getPhoneNumber()+ "\n" +
                    "Address: " + "\t"+this.getAddress() + "\n" +
                    "Email: " + "\t\t"+this.getEmail() + "\n" +
                    "----------------------------\n");

        }    
    }
}