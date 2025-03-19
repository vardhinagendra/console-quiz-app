import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main{
    private static final Scanner scanner = new Scanner(System.in);
    private static int score = 0;
    private static int questionNo = 1;
    private static int prizeMoney = 150;
    private static boolean fiftyFiftyUsed = false;
    private static boolean audiencePollUsed = false;

    public static void main(String[] args) {

        System.out.print("Do you want to play the quiz? (yes/no): ");
        String playQuiz = scanner.nextLine().toLowerCase();
        
            if (playQuiz.equals("no")) {
                System.out.println("Thank you! Have a nice day.");
                return;
            }

            dispRules();
            System.out.println("==========================");
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();

            System.out.print("Enter your age: ");
            int age = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter your place: ");
            String place = scanner.nextLine();
            System.out.println("==========================");

            Participant player = new Participant(name, age, place);
            player.displayDetails();

            Question[] questions = {
                    new Question("Who is the captain for CSK?", new String[]{"Dhoni", "Jaddu", "Gaikwad", "Noor"}, 0),
                    new Question("What is 5 + 3?", new String[]{"6", "7", "8", "9"}, 2),
                    new Question("What is India's national animal?", new String[]{"Bengal Tiger", "Lion", "Zebra", "Monkey"}, 0),
                    new Question("Which is our national fruit?", new String[]{"Banana", "Mango", "Orange", "Cherry"}, 1),
                    new Question("Who is the father of Java?", new String[]{"James Gosling", "Dennis Ritchie", "Guido van Rossum", "Charles Babbage"}, 0),
                    new Question("How many cups are won csk?", new String[]{"1", "5", "2", "4"}, 1),
                    new Question("which team are won the ipl tittle?", new String[]{"CSK", "RCB", "KKR", "GT"}, 2),
                    new Question("which team are won the Champion trophy?", new String[]{"India", "Australia", "pakistan", "newzland"}, 0),
                    new Question(" which movie get 1st day Highest collections ?", new String[]{"Salaar", "Bhahubali", "Puspa2", "Kgf"}, 2),
                    new Question(" what is the home ground of csk?", new String[]{"chapak", "chinnaswamy", "rajivghandhi", "wankadde"}, 0),
            };

            for (Question question : questions) {
                if (!askQuestion(question)) {
                    System.out.println("Incorrect! The correct answer was: " + question.options[question.correctAnswer]);
                    System.out.println("Game Over! You won ₹" + prizeMoney);
                    return;
                }
                score++;
                prizeMoney *= 2;
                questionNo++;
                System.out.println();
            }

            System.out.println("Game Over!");
            System.out.println("Your final score: " + score + " out of " + questions.length);
            System.out.println("Congratulations you won ₹" + prizeMoney);
            System.out.println("Thank you for playing.");
        }
    private static void dispRules() {
        System.out.println("\nRules of the Quiz Game:");
        System.out.println("1. Answer correctly to win prize money.");
        System.out.println("2. You have 2 lifelines: 50-50 and Audience Poll.");
        System.out.println("3. Lifelines can be used only once.");
        System.out.println("4. If you enter 'L' instead of an answer, you can choose a lifeline.");
        System.out.println("5. A wrong answer will terminate the game.");
        System.out.println("6. Good luck!\n");
    }

    private static boolean askQuestion(Question q) {
        q.displayQuestion();
        while (true) {
            System.out.print("Enter your answer (1-4) or 'L' for lifeline: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("L")) {
                if (fiftyFiftyUsed && audiencePollUsed) {
                    System.out.println("No lifelines left. Choose an answer.");
                } else {
                    useLifeline(q);
                }
            } else {
                try {
                    int answer = Integer.parseInt(input) - 1;
                    return answer == q.correctAnswer;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter 1-4 or 'L'.");
                }
            }
        }
    }

    private static void useLifeline(Question q) {
        System.out.println("Available Lifelines:");
        if (!fiftyFiftyUsed) System.out.println("1. 50-50");
        if (!audiencePollUsed) System.out.println("2. Audience Poll");

        while (true) {
            System.out.print("Choose a lifeline (1 or 2): ");
            String choice = scanner.nextLine();

            if (choice.equals("1") && !fiftyFiftyUsed) {
                useFiftyFiftyLifeline(q);
                fiftyFiftyUsed = true;
                break;
            } else if (choice.equals("2") && !audiencePollUsed) {
                useAudiencePoll(q);
                audiencePollUsed = true;
                break;
            } else {
                System.out.println("Invalid choice or lifeline already used. Try again.");
            }
        }
    }

    private static void useFiftyFiftyLifeline(Question q) {
        List<Integer> incorrectOptions = new ArrayList<>();
        for (int i = 0; i < q.options.length; i++) {
            if (i != q.correctAnswer) {
                incorrectOptions.add(i);
            }
        }

        Random rand = new Random();
        int firstToRemove = incorrectOptions.remove(rand.nextInt(incorrectOptions.size()));
        int secondToRemove = incorrectOptions.remove(rand.nextInt(incorrectOptions.size()));

        System.out.println("50-50 Lifeline used! Remaining options:");
        for (int i = 0; i < q.options.length; i++) {
            if (i != firstToRemove && i != secondToRemove) {
                System.out.println((i + 1) + ". " + q.options[i]);
            }
        }
    }

    private static void useAudiencePoll(Question q) {
        System.out.println("Audience Poll Lifeline used!");
        System.out.println("Audience votes suggest:");
        Random rand = new Random();

        for (int i = 0; i < q.options.length; i++) {
            int votes = (i == q.correctAnswer) ? rand.nextInt(40) + 50 : rand.nextInt(50);
            System.out.println((i + 1) + ". " + q.options[i] + " - " + votes + "%");
        }
    }
    private static class Participant {
        private final String name;
        private final int age;
        private final String place;

        Participant(String name, int age, String place) {
            this.name = name;
            this.age = age;
            this.place = place;
        }

        void displayDetails() {
            System.out.println("Participant Details:");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Place: " + place);
            System.out.println("==========================");
 }
}
}