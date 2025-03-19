import java.util.Scanner;
   public class Question {
        String questionText;
        String[] options;
        int correctAnswer;


        Question(String questionText, String[] options, int correctAnswer) {
            this.questionText = questionText;
            this.options = options;
            this.correctAnswer = correctAnswer;
        }


        void displayQuestion() {
            System.out.println(questionText);
            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
            }
        }


    }


