package utility;

/**
 *
 * @author Ooi Choon Chong
 */
public class MessageUI {

    public static void displayInvalidChoiceMessage() {
        System.out.println("\nInvalid choice!\n");
    }

    public static void displayExitMessage() {
        System.out.println("\nExiting system");
    }

    // CC START
    public static void displayUnableCreateObjectMessage() {
        System.out.println("\nUnable to create object");
    }

    public static void displayObjectNotFoundMessage() {
        System.out.println("\nObject not found");
    }
    // CC END

    public static void displayInvalidAddMessage(String object){
        System.out.println("\nUnable to add "+ object);
    }
    
    public static void displayExitToMainMenuMessage(){
        System.out.println("\nExiting to main menu...\n");
    }
    
    public static void displaySearchNotFoundMessage(String object){
        System.out.println("\n"+ object+" not found...\n");
    }
        
}
