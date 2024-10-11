import java.util.ArrayList;
import java.util.Scanner;
public class MessageApp {
    private ArrayList<String> contactList;
    private ArrayList<ArrayList<Message>> messages;
    private Scanner scanner;
    public MessageApp() {
        contactList = new ArrayList<>();
        contactList.add("Ahmad");
        contactList.add("Ayan");
        contactList.add("Ali");

        messages = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++) {
            messages.add(new ArrayList<>());
        }

        scanner = new Scanner(System.in);
    }
    private int getContactIndex(String contact) {
        for (int i = 0; i < contactList.size(); i++) {
            if (contactList.get(i).equalsIgnoreCase(contact)) {
                return i;
            }
        }
        return -1;
    }
    private void sendMessage(String contact, String content, boolean status) {
        int index = getContactIndex(contact);
        if (index != -1) {
            Message newMessage = new Message(contact, content, status);
            messages.get(index).add(newMessage);
            System.out.println("Message sent to " + contact + " successfully.\n");
        } else {
            System.out.println("Incorrect contact name: " + contact + ".\n");
        }
    }
    private void displayContacts() {
        System.out.println("Contact List:");
        if (contactList.isEmpty()) {
            System.out.println("No contacts available.\n");
            return;
        }
        for (int i = 0; i < contactList.size(); i++) {
            System.out.println((i + 1) + ". " + contactList.get(i));
        }
        System.out.println();
    }

    private void addContact() {
        System.out.print("Name of the new contact: ");
        String newContact = scanner.nextLine().trim();

        if (newContact.isEmpty()) {
            System.out.println("Contact name cannot be empty.\n");
            return;
        }

        if (getContactIndex(newContact) != -1) {
            System.out.println("Contact \"" + newContact + "\" already exists.\n");
            return;
        }

        contactList.add(newContact);
        messages.add(new ArrayList<>());
        System.out.println("Contact \"" + newContact + "\" added successfully.\n");
    }

    private void deleteContact() {
        System.out.print("Name of the contact to delete: ");
        String contactToDelete = scanner.nextLine().trim();

        int index = getContactIndex(contactToDelete);
        if (index == -1) {
            System.out.println("Contact \"" + contactToDelete + "\" does not exist.\n");
            return;
        }

        System.out.print("Are you sure you want to delete \"" + contactToDelete + "\"? (yes/no): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();

        if (confirmation.equals("yes") || confirmation.equals("y")) {
            contactList.remove(index);
            messages.remove(index);
            System.out.println("Contact \"" + contactToDelete + "\" has been deleted successfully.\n");
        } else {
            System.out.println("Deletion cancelled.\n");
        }
    }

    private void displayMessages(String contact) {
        int index = getContactIndex(contact);
        if (index != -1) {
            System.out.println("=== Messages for " + contact + " ===");
            if (messages.get(index).isEmpty()) {
                System.out.println("No messages found for " + contact + ".\n");
                return;
            }
            for (Message msg : messages.get(index)) {
                System.out.println(msg.toString());
            }
            System.out.println();
        } else {
            System.out.println("Contact \"" + contact + "\" does not exist.\n");
        }
    }

    private void checkMessageStatusByContact() {
        System.out.print("Enter the contact name to check message statuses: ");
        String contact = scanner.nextLine().trim();

        int index = getContactIndex(contact);
        if (index == -1) {
            System.out.println("Contact \"" + contact + "\" does not exist.\n");
            return;
        }

        ArrayList<Message> contactMessages = messages.get(index);
        if (contactMessages.isEmpty()) {
            System.out.println("No messages found for " + contact + ".\n");
            return;
        }

        System.out.println("=== Message Statuses for " + contact + " ===");
        for (int i = 0; i < contactMessages.size(); i++) {
            Message msg = contactMessages.get(i);
            System.out.println((i + 1) + ". " + msg.getContent() + " - " + (msg.getStatus() ? "Sent" : "Not Sent"));
        }
        System.out.println();
    }
    public static void main(String[] args) {
        MessageApp app = new MessageApp();
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("Here Messaging App\n");

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Display Contacts");
            System.out.println("2. Add Contact");
            System.out.println("3. Delete Contact");
            System.out.println("4. Send Message");
            System.out.println("5. Display Messages for a Contact");
            System.out.println("6. Check Message Status");
            System.out.println("7. Exit");
            System.out.print("Enter your choice (1-7): ");

            while (true) {
                try {
                    choice = Integer.parseInt(scanner.nextLine());
                    if (choice < 1 || choice > 7) {
                        System.out.print("Please enter a valid choice (1-7): ");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a number (1-7): ");
                }
            }

            switch (choice) {
                case 1:
                    app.displayContacts();
                    break;
                case 2:
                    app.addContact();
                    break;
                case 3:
                    app.deleteContact();
                    break;
                case 4:
                    System.out.print("Enter receiver's name: ");
                    String receiver = scanner.nextLine().trim();

                    System.out.print("Enter message content: ");
                    String content = scanner.nextLine().trim();

                    System.out.print("Enter message status (true for Sent, false for Not Sent): ");
                    boolean status;
                    while (true) {
                        String statusInput = scanner.nextLine().trim().toLowerCase();
                        if (statusInput.equals("true") || statusInput.equals("t") || statusInput.equals("yes") || statusInput.equals("y")) {
                            status = true;
                            break;
                        } else if (statusInput.equals("false") || statusInput.equals("f") || statusInput.equals("no") || statusInput.equals("n")) {
                            status = false;
                            break;
                        } else {
                            System.out.print("Invalid input. Please enter true/false: ");
                        }
                    }

                    app.sendMessage(receiver, content, status);
                    break;
                case 5:
                    System.out.print("Enter contact name to view messages: ");
                    String contact = scanner.nextLine().trim();
                    app.displayMessages(contact);
                    break;
                case 6:
                    app.checkMessageStatusByContact();
                    break;
                case 7:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please select between 1 and 7.\n");
            }
        }
    }
}
