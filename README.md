# Static analyzer

### 1. (127, 3) This block of commented-out lines of code should be removed.
   This was my first sonarlent job malfunction,

#### - Issue Type: Code Smell, Issue Severity: MAJOR.
   This was the description of the problem, after I read it and examined the code, it turned out that there were lines with unnecessary comments that should be deleted, so I got rid of them.

The commented lines of code are likely leftovers from previous code changes, and are no longer necessary. Removing them simplifies the code and makes it easier to read and understand.
Description of the error "Programmers should not comment out code as it bloats programs and reduces readability.
Unused code should be deleted and can be retrieved from source control history if required".


### 2. (293, 14) Remove this unused private "saveAsText" method.
####   - Issue Type: Code Smell, Issue Severity: MAJOR.
   The saveAsText method was defined but not called in the program, which means it had no effect on the behavior of the program.I decided to remove it to make the code more clear and concise.
   I deleted the commented lines block from the code and the saveAsText method because it is not used in the program, and removing them does not affect the functionality of the program.

Description of the error on Sonarlent: "private methods that are never executed are dead code: unnecessary, inoperative code that should be removed. Cleaning out dead code decreases the size of the maintained codebase, making it easier to understand the program and preventing bugs from being introduced .
Note that this rule does not take reflection into account, which means that issues will be raised on private methods that are only accessed using the reflection API."

### 3. (234, 5) Replace this use of System.out or System.err by a logger.
### 3.1 (226, 6) Replace this use of System.out or System.err by a logger.
####  - Issue Type: Code Smell, Issue Severity: MAJOR.
   The SonarLint message "Replace use of System.out or System.err with logger" indicates that the code uses System.out or System.err to print log messages, which is generally considered bad practice. Instead, I used the java.util.logging framework.
   Then I replace all instances of System.out.println() or System.err.println() with logger.info("");

### 4.(209, 14) Refactor this method to reduce its Cognitive Complexity from 26 to the 15 allowed.
### 4.1(129, 13) Refactor this method to reduce its Cognitive Complexity from 45 to the 15 allowed. 
#### - Issue Type: Code Smell, Issue Severity: CRITICAL.
Cognitive complexity is a measure of how difficult it is to understand the control flow of a method. Methods with higher cognitive complexity will be difficult to maintain.
Here are some modifications I made to reduce cognitive complexity:

* Use the switch phrase
Use a switch statement instead of multiple if-else statements to handle different action commands. This can reduce the cognitive complexity of the method and make it easier to read and maintain.
* Move logging to a separate method to reduce code redundancy and make code easier to maintain.
* Use early returns or continue statements to exit the method early if certain conditions are met, rather than nesting further logic within an if-statement.

### 5. Make "variables" a static final constant or non public and provide accessors if needed
### 5.1 make copy a static final constant or non public and provide accessors if needed
### 5.2 make paste a static final constant or non public and provide accessors if needed
### 5.3 make cut a static final constant or non public and provide accessors if needed
### 5.4 make move a static final constant or non public and provide accessors if needed
### 5.5 make changed a static final constant or non public and provide accessors if needed
#### - Issue Type: Code Smell
#### - Issue Severity: Minor.
#### - Issue Description : 
   the issue is that the copy, paste, cut, move and changed  fields are declared as public, which means they can be accessed and modified from anywhere in the code, breaking the encapsulation principle. SonarLint recommend to modify the declaration of the public fields to static final constant or non public and provide accessor methods to improve encapsulation and maintainability.By following this recommendation, the code becomes easier to understand, test, and modify, while reducing the risk of errors and bugs, ultimately improving software quality.
   So i declare copy, paste, cut, move and changed as static final Based on constant convention in Java.

### 6.extract the nested try block into seperate method 
### Issue Type : 
### Issye Severity : 
### Issue Description : 
   The issue is that we have a nested try block in the editor file so i created a new method called readFile() which takes a File object as input and returns the contents of the file as a String so the code bacame more modular and easier to understand.
