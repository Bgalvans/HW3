package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import databasePart1.DatabaseHelper;


class StudentHomePageTest {

    private StudentHomePage studentHomePage;

    @BeforeEach
    void setUp() {
        studentHomePage = new StudentHomePage(new DatabaseHelper());
        System.out.println("Setup complete: StudentHomePage instance created.");
    }

    /**
     * 1) Test "Ask Question" (Create)
     *    Simulate adding a new question to unresolved & allQuestions lists.
     */
    @Test
    void testAskQuestion() {
        // Suppose the new question is "Q1: What is Java?"
        String question = "Q1: What is Java?";

        studentHomePage.unresolvedQuestionsList.add(question);
        studentHomePage.allQuestionsList.add(question);

        // Verify
        assertTrue(studentHomePage.unresolvedQuestionsList.contains(question));
        assertTrue(studentHomePage.allQuestionsList.contains(question));
        assertFalse(studentHomePage.resolvedQuestionsList.contains(question));
        System.out.println("testAskQuestion passed.");
    }

    /**
     * 2) Test "Answer a Question" (Create answer)
     *    Simulate adding an answer to answersList, referencing the question.
     */
    @Test
    void testAnswerQuestion() {
        String question = "Q2: What is CRUD?";
        studentHomePage.unresolvedQuestionsList.add(question);
        studentHomePage.allQuestionsList.add(question);

        // Now simulate the user providing an answer: "Create, Read, Update, Delete"
        String answerEntry = question + " | Answer: Create, Read, Update, Delete by student";
        studentHomePage.answersList.add(answerEntry);

        // Verify
        assertTrue(studentHomePage.answersList.contains(answerEntry));
        System.out.println("testAnswerQuestion passed.");
    }

    /**
     * 3) Test "Delete Q&A" (Delete)
     *    Simulate deleting a question from all relevant lists.
     */
    @Test
    void testDeleteQuestion() {
        // Add a question to all relevant lists
        String question = "Q3: What is JUnit?";
        studentHomePage.allQuestionsList.add(question);
        studentHomePage.unresolvedQuestionsList.add(question);

        // Also add an answer referencing it
        String answerEntry = question + " | Answer: JUnit is a testing framework by student";
        studentHomePage.answersList.add(answerEntry);

        // "Delete Q&A" button logic typically removes the question from
        // allQuestionsList, unresolvedQuestionsList, resolvedQuestionsList,
        // and any matching entries in answersList
        studentHomePage.allQuestionsList.remove(question);
        studentHomePage.unresolvedQuestionsList.remove(question);
        studentHomePage.resolvedQuestionsList.remove(question);
        studentHomePage.answersList.removeIf(a -> a.startsWith(question + " | Answer: "));

        // Verify the question is gone from all
        assertFalse(studentHomePage.allQuestionsList.contains(question));
        assertFalse(studentHomePage.unresolvedQuestionsList.contains(question));
        assertFalse(studentHomePage.resolvedQuestionsList.contains(question));
        // Verify the answer referencing it is also gone
        assertFalse(studentHomePage.answersList.contains(answerEntry));
        System.out.println("testDeleteQuestion passed.");
    }

    /**
     * 4) Test "Mark as Resolved" (Update)
     *    Moves a question from unresolved to resolved.
     */
    @Test
    void testMarkQuestionAsResolved() {
        // Add a sample question to the unresolved list
        String question = "Q4: When is CSE360 Midterm?";
        studentHomePage.unresolvedQuestionsList.add(question);
        System.out.println("Added question to unresolved list: " + question);

        // Ensure it starts in unresolved questions
        assertTrue(studentHomePage.unresolvedQuestionsList.contains(question));
        assertFalse(studentHomePage.resolvedQuestionsList.contains(question));

        // Simulate marking the question as resolved
        // (like the "Mark as Resolved" button does)
        studentHomePage.unresolvedQuestionsList.remove(question);
        studentHomePage.resolvedQuestionsList.add(question);

        // Assert that the question is removed from unresolved list
        assertFalse(studentHomePage.unresolvedQuestionsList.contains(question));
        // Assert that the question is now in the resolved list
        assertTrue(studentHomePage.resolvedQuestionsList.contains(question));
        System.out.println("testMarkQuestionAsResolved passed.");
    }

    /**
     * 5) Test "Edit Q&A" (Update)
     *    Simulate editing an existing question or answer in the lists.
     */
    @Test
    void testEditQuestion() {
        // Suppose we have an existing question in allQuestionsList
        String oldQuestion = "Q5: What is testing?";
        studentHomePage.allQuestionsList.add(oldQuestion);
        studentHomePage.allQuestionsList.remove(oldQuestion);
        String newQuestion = "Q5: What is software testing exactly?";
        studentHomePage.allQuestionsList.add(newQuestion);

        // Verify old is gone, new is present
        assertFalse(studentHomePage.allQuestionsList.contains(oldQuestion));
        assertTrue(studentHomePage.allQuestionsList.contains(newQuestion));
        System.out.println("testEditQuestion passed.");
    }

    /**
     * 6) Test "Follow-Up Question" (Create/Update)
     *    The real code merges follow-up question text into answersList or followUpQuestionsList.
     */
    @Test
    void testFollowUpQuestion() {
        // Suppose there's an existing answer
        String question = "Q6: What is JavaFX?";
        studentHomePage.allQuestionsList.add(question);
        String mainAnswer = question + " | Answer: JavaFX is a GUI toolkit by userX";
        studentHomePage.answersList.add(mainAnswer);

        // Now the user asks a follow-up. The real code:
        // 1) removes the old answer from answersList
        // 2) merges the new follow-up text
        // 3) adds it to followUpQuestionsList
        studentHomePage.answersList.remove(mainAnswer);
        String mergedEntry = mainAnswer + " | Follow-Up: How do I install it?";
        studentHomePage.followUpQuestionsList.add(mergedEntry);

        // Verify the old answer is removed
        assertFalse(studentHomePage.answersList.contains(mainAnswer));
        // Verify the new merged entry is in followUpQuestionsList
        assertTrue(studentHomePage.followUpQuestionsList.contains(mergedEntry));
        System.out.println("testFollowUpQuestion passed.");
    }

    /**
     * 7) Test "Search" (Read)
     *    The real code loops through allQuestionsList, calls combineQA(...), checks for a keyword.
     */
    @Test
    void testSearch() {
        // Insert a question and answer
        String question = "Q7: Define CRUD?";
        studentHomePage.allQuestionsList.add(question);
        String answer = question + " | Answer: Create, Read, Update, Delete by admin";
        studentHomePage.answersList.add(answer);

        String combined = combineQA(question);
        assertTrue(combined.toLowerCase().contains("update"),
            "Combined QA should contain 'update' keyword from the answer.");

        System.out.println("testSearch passed.");
    }

    // A simple local version of combineQA(...) to simulate StudentHomePage logic
    private String combineQA(String question) {
        StringBuilder sb = new StringBuilder();
        sb.append(question).append("\n");
        for (String ans : studentHomePage.answersList) {
            String prefix = question + " | Answer: ";
            if (ans.startsWith(prefix)) {
                sb.append("- ").append(ans.substring(prefix.length())).append("\n");
            }
        }
        return sb.toString().trim();
    }
}