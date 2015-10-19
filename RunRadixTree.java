import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;

/**
 * A class to create a radix tree from a text file
 *
 * @author Austin Leal
 * @version 1.0
 */
public class RunRadixTree {
    private static RadixTree<StringRadixTreeElement> myTree;

    /**
     * main method
     *
     * @param  args string array
     */
    public static void main(String[] args) {
        Scanner textFile = getFile();
        createTree(textFile);
        prompt();
    }

    private static Scanner getFile() {
        Scanner scandy = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Enter filename or \"exit\" to stop: ");
                String filename = scandy.next();
                if (filename.equalsIgnoreCase("exit")) {
                    System.exit(0);
                }
                printOriginal(new Scanner(new File(filename)));
                return new Scanner(new File(filename));
            } catch (IOException e) {
                System.out.println(e + ("\nTry Again.\n"));
            }
        }
    }

    /**
     * creates tree from file
     *
     * @param  textFile   Scanner to create tree with
     *
     * @return completed tree
     */
    private static void createTree(Scanner textFile) {
        //textFile is assured to be not null by getFile()
        myTree = new RadixTree<>();
        while (textFile.hasNext()) {
            myTree.add(new StringRadixTreeElement(textFile.next()));
        }
    }

    /**
     * prints out original
     *
     * @param  textFile      [description]
     */
    private static void printOriginal(Scanner textFile) {
        System.out.println("\nOriginal File: ");
        while (textFile.hasNextLine()) {
            System.out.println("    " + textFile.nextLine());
        }
    }

    /**
     * prompts user for action to take on tree
     *
     * @method prompt
     */
    private static void prompt() {
        Scanner scandy = new Scanner(System.in);
        while (true) {
            int integer = -1;
            boolean stop = false;
            while (!stop) {
                System.out.print("\nChoose an action:"
                        + "\n1 - Add"
                        + "\n2 - Clear"
                        + "\n3 - Check if contains"
                        + "\n4 - Print file"
                        + "\n5 - Remove"
                        + "\n6 - Search"
                        + "\n7 - Search for all"
                        + "\n8 - Size"
                        + "\n-1 - Exit\n");
                try {
                    integer = Integer.parseInt(scandy.next());
                    stop = true;
                } catch (Exception e) {
                    System.out.println("Invalid input. Try again.");
                }
            }
            switch (integer) {
            case 1:
                System.out.println("Enter word to add");
                myTree.add(new StringRadixTreeElement(scandy.next()));
                break;
            case 2:
                System.out.println("Are you sure? (Y/N)");
                String answer = scandy.next();
                switch (answer) {
                case "Y":
                case "y":
                    myTree.clear();
                    break;
                case "N":
                case "n":
                    break;
                default:
                    System.out.println("Sorry, try again.");
                    break;
                }
                break;
            case 3:
                System.out.println("Enter word to check.");
                if (myTree.contains(new StringRadixTreeElement(scandy.next())))
                {
                    System.out.println("Yes.");
                } else {
                    System.out.println("No.");
                }
                break;
            case 4:
                System.out.println(myTree == null);
                Object[] array = myTree.toArray();
                String result = "";
                for (Object element : array) {
                    result += element + " ";
                }
                if (result.length() > 1) {
                    System.out.println(result.substring(0,
                            result.length() - 1));
                } else {
                    System.out.println("Empty.");
                }
                break;
            case 5:
                System.out.println("Enter word to remove");
                if (myTree.remove(new StringRadixTreeElement(scandy.next()))) {
                    System.out.println("Word removed.");
                } else {
                    System.out.println("Word not found.");
                }
                break;
            case 6:
                System.out.println("Enter word to find");
                int location = myTree.find(new StringRadixTreeElement(
                        scandy.next()));
                if (location != -1) {
                    System.out.println(location);
                } else {
                    System.out.println("Not found");
                }
                break;
            case 7:
                System.out.println("Enter word to find");
                List<Integer> array2 = myTree.findAll(
                        new StringRadixTreeElement(scandy.next()));
                String result2 = "";
                if (array2 == null) {
                    result2 = "Not found  ";
                } else {
                    for (int i : array2) {
                        result2 += i + ", ";
                    }
                }
                System.out.println(result2.substring(0, result2.length() - 2));
                break;
            case 8:
                System.out.println("Size: " + myTree.size());
                break;
            case -1:
                System.exit(0);
                break;
            default:
                System.out.println("Input Invalid.");
                break;
            }
        }
    }
}