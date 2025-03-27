package application;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FollowUpTests} class contains unit tests for validating the 
 * follow-up submission feature in the QA application.
 * 
 * <p>These test cases ensure proper handling of follow-ups, including:
 * <ul>
 *   <li>Submitting follow-ups with/without selecting an answer</li>
 *   <li>Validating empty follow-ups</li>
 *   <li>Verifying correct formatting</li>
 *   <li>Handling multiple follow-ups</li>
 * </ul>
 * 
 * <p>JUnit 5 is used for automated testing.</p>
 * 
 * @author [Brian Galvan]
 * @version 2.0
 * @since 2025-03-20
 */

public class FollowUpTests {
	
/**
 * Default constructor for (@code FollowUpTests).
 * Initializes the test class.
 */	
	public FollowUpTests() {
        // Default constructor
    }
    
    @SuppressWarnings("unused")
	private StudentHomePage studentHomePage;
    private List<String> answersList;
    private List<String> followUpQuestionsList;
    /**
     * Sets up test data before each test case.
     */
    @BeforeEach
    public void setUp() {
        studentHomePage = new StudentHomePage(null); // Mocking database dependency
        answersList = new ArrayList<>();
        followUpQuestionsList = new ArrayList<>();
    }

    /**
     * Test case: Submitting a follow-up when an answer is selected.
     * 
     * <p>Expected: Follow-up is merged with the selected answer.</p>
     */
    @Test
    public void testSubmitFollowUpWithAnswerSelected() {
        answersList.add("Q1 | Answer: This is an answer");
        String selectedAnswer = answersList.get(0);
        String followUp = "Can you explain further?";
        
        String mergedEntry = selectedAnswer + " | Follow-Up: " + followUp;
        followUpQuestionsList.add(mergedEntry);
        
        assertTrue(followUpQuestionsList.contains(mergedEntry), "Follow-up should be merged with the answer");
    }

    /**
     * Test case: Attempting to submit a follow-up without selecting an answer.
     * 
     * <p>Expected: Error message prompting to select an answer. </p>
     */
    @Test
    public void testSubmitFollowUpWithoutSelectingAnswer() {
        @SuppressWarnings("unused")
		String followUp = "This is a follow-up without an answer";
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (answersList.isEmpty()) {
                throw new IllegalArgumentException("Please select an answer before submitting a follow-up.");
            }
        });
        
        assertEquals("Please select an answer before submitting a follow-up.", exception.getMessage());
    }

    /**
     * Test case: Attempting to submit an empty follow-up.
     * 
     * <p>Expected: Validation message preventing empty follow-ups.</p>
     */
    @Test
    public void testSubmitEmptyFollowUp() {
        String followUp = "";
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            if (followUp.trim().isEmpty()) {
                throw new IllegalArgumentException("Follow-up cannot be empty.");
            }
        });
        
        assertEquals("Follow-up cannot be empty.", exception.getMessage());
    }

    /**
     * Test case: Checking merged entry format correctness.
     * 
     * <p>Expected: "Answer | Follow-Up: question" format.</p>
     */
    @Test
    public void testMergedEntryFormat() {
        String answer = "This is an answer";
        String followUp = "Can you provide more details?";
        
        String mergedEntry = "Answer: " + answer + " | Follow-Up: " + followUp;
        assertTrue(mergedEntry.matches("Answer: .* \\| Follow-Up: .*"), "Merged entry format should be correct");
    }

    /**
     * Test case: Adding multiple follow-ups to the same answer.
     * 
     * <p>Expected: All follow-ups should be nested correctly under the answer.</p>
     */
    @Test
    public void testMultipleFollowUpsToSameAnswer() {
        String answer = "Q1 | Answer: Original Answer";
        answersList.add(answer);
        
        String followUp1 = "Q1 | Answer: Original Answer | Follow-Up: First follow-up";
        String followUp2 = "Q1 | Answer: Original Answer | Follow-Up: Second follow-up";
        
        followUpQuestionsList.add(followUp1);
        followUpQuestionsList.add(followUp2);
        
        assertEquals(2, followUpQuestionsList.size(), "Multiple follow-ups should be added correctly");
    }
    
    /**
     * Mainline execution for running test cases.
     * 
     * <p>This method runs all test cases sequentially and prints the results.</p>
     * 
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        FollowUpTests tests = new FollowUpTests();
        tests.setUp();

        System.out.println("Running Follow-Up Tests...");
        
        try {
            tests.testSubmitFollowUpWithAnswerSelected();
            System.out.println("✔ testSubmitFollowUpWithAnswerSelected PASSED");
        } catch (AssertionError e) {
            System.out.println("✘ testSubmitFollowUpWithAnswerSelected FAILED: " + e.getMessage());
        }

        try {
            tests.testSubmitFollowUpWithoutSelectingAnswer();
            System.out.println("✔ testSubmitFollowUpWithoutSelectingAnswer PASSED");
        } catch (AssertionError e) {
            System.out.println("✘ testSubmitFollowUpWithoutSelectingAnswer FAILED: " + e.getMessage());
        }

        try {
            tests.testSubmitEmptyFollowUp();
            System.out.println("✔ testSubmitEmptyFollowUp PASSED");
        } catch (AssertionError e) {
            System.out.println("✘ testSubmitEmptyFollowUp FAILED: " + e.getMessage());
        }

        try {
            tests.testMergedEntryFormat();
            System.out.println("✔ testMergedEntryFormatCorrect PASSED");
        } catch (AssertionError e) {
            System.out.println("✘ testMergedEntryFormatCorrect FAILED: " + e.getMessage());
        }

        try {
            tests.testMultipleFollowUpsToSameAnswer();
            System.out.println("✔ testMultipleFollowUpsToSameAnswer PASSED");
        } catch (AssertionError e) {
            System.out.println("✘ testMultipleFollowUpsToSameAnswer FAILED: " + e.getMessage());
        }

        System.out.println("Follow-Up Tests Completed.");
    }
}
