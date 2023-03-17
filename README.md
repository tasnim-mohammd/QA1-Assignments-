# Static analyzer

### 1. (127, 3) This block of commented-out lines of code should be removed.
   This was my first sonarlint job malfunction,

#### - Issue Type: Code Smell, Issue Severity: MAJOR.
   This was the description of the problem, after I read it and examined the code, it turned out that there were lines with unnecessary comments that should be deleted, so I got rid of them.

The commented lines of code are likely leftovers from previous code changes, and are no longer necessary. Removing them simplifies the code and makes it easier to read and understand.
Description of the error "Programmers should not comment out code as it bloats programs and reduces readability.
Unused code should be deleted and can be retrieved from source control history if required".


### 2. (293, 14) Remove this unused private "saveAsText" method.
####   - Issue Type: Code Smell, Issue Severity: MAJOR.
   The saveAsText method was defined but not called in the program, which means it had no effect on the behavior of the program.I decided to remove it to make the code more clear and concise.
   I deleted the commented lines block from the code and the saveAsText method because it is not used in the program, and removing them does not affect the functionality of the program.

Description of the error on Sonarlint: "private methods that are never executed are dead code: unnecessary, inoperative code that should be removed. Cleaning out dead code decreases the size of the maintained codebase, making it easier to understand the program and preventing bugs from being introduced .
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
### 5.6 make Tp a static final constant or non public and provide accessors if needed
### 5.7 make menu a static final constant or non public and provide accessors if needed
#### - Issue Type: Code Smell
#### - Issue Severity: Minor.
#### - Issue Description : 
   the issue is that the copy, paste, cut, move and changed  fields are declared as public, which means they can be accessed and modified from anywhere in the code, breaking the encapsulation principle. SonarLint recommend to modify the declaration of the public fields to static final constant or non public and provide accessor methods to improve encapsulation and maintainability.
During my attempt to solve the error and make it static final constant or non public as the sonar mintioned Other errors were occurring depending on it .. so the optimal one was : 
To avoid this error I did the following
Once added JMenuItem all the variables declaration in the top of the file  became unnecessary and no usages for them so I deleted "copy,paste,cut" 

![image](https://user-images.githubusercontent.com/114527203/225830629-7e5b6999-83ba-4130-8392-2af1b8c45525.png)

for "move" it was already no usages for it so i deleted it also
for TP rename it to textPanel and then declared as protected because it used in the subclass
for changed rename it to changeStatus then declared as private 
finally for "menu" it declared as private 
### 6.extract the nested try block into seperate method 
### Issue Type : Code smell
### Issye Severity : Major
### Issue Description : 
   The issue is that we have a nested try block in the editor file so i created a new method called readFile() "as the follwing photo"  which takes a File object as input and returns the contents of the file as a String so the code bacame more modular and easier to understand.
   and this improve the quality of the code by making it more modular, readable, and maintainable.
   
![image](https://user-images.githubusercontent.com/114527203/225834110-2c7fa1ef-f8c4-41b4-8993-6437644b7689.png)


### 7. Add a default case to this switch.
#### Issue Type : Code Smell 
#### Issue Severity : Critical 
### 8. A "NullPointerException" could be thrown: "writer" is nullable here.
#### Issue Type : Bug
#### Issue Severity : Major
### 8.1
```agsl
private void handleSaveFileAction() {
		int ans = 0;
		if (changeStatus) {
			ans = JOptionPane.showConfirmDialog(null, "The file has changed. You want to save it?", "Save file", 0, 2);
		}
		if (ans != 1) {
			if (file == null) {
				saveAs(actions[1]);
			} else {
				String text = textPanel.getText();
				try (PrintWriter writer = new PrintWriter(file)) {
					if (!file.canWrite()) {
						throw new EditorSaveException("Cannot write file!");
					}
					writer.write(text);
					changeStatus = false;
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
```
* Based on this code I noticed that in the handleSaveFileAction() method, the answer variable is not initialized if the changeStatus condition is false, which may result in a NullPointerException when checking its value in the if condition (answer! = 1). To avoid this, you can initialize the ans variable to a default value, such as -1 , before the if (changeStatus) block.
```agsl

```
* There is a problem with the getWriter method, which returns null if an exception is thrown during PrintWriter creation. If writer is null, a NullPointerException will be thrown when trying to call the write method on it at the writer line.write(textPanel.getText());.
To fix this, you can modify the saveAs method to handle the case where the writer is null. One way to do this is to add an if statement to check if the writer is null, and if so, return from the method without writing anything to the file.
In the modified method, if the writer is not null, it will try to write to the file as before. However, we've added a try-catch-final block to catch any IO exceptions that might occur while writing to the file, and to close the writer regardless of whether the write was successful or not.
In the else block, we handle the case where the writer is empty by displaying an error message to the user. You can customize this error message to suit your needs.
### 9.Rename this method name (BuildMenu) to match regular expression '[a-z][a-zA-Z0-9]*$'.
#### Issue Type : code smell
#### Issue Severity : Minor
### 11. Use static access with "javax.swing.WindowConstants" for "EXIT_ON_CLOSE".
#### Issue Type : Code Smell 
#### Issue Severity : Critical 

### 10. Remove deprecated constant :
### 10.1 Remove this use for "CTRL_MASK"; it is deprecated
#### - Issue Type : code smell
#### - Issue Severity : Minor
#### - Issue Description : 
The CTRL_MASK constants was deprecated in Java 9 and this reduced maintainability. so i found that it replaced with the InputEvent.CTRL_DOWN_MASK constants
### 10.2 Remove this use for "SHIFT_MASK"; it is deprecated
#### - Issue Type : code smell
#### - Issue Severity : Minor
#### - Issue Description : 
The SHIFT_MASK constants was deprecated in Java 9 and this reduced maintainability. so i found that it replaced with the InputEvent.SHIFT_DOWN_MASK constants

![image](https://user-images.githubusercontent.com/114527203/225839870-6938c519-bbaf-4ec8-914e-623d5ccff5f3.png)

### 11.Use try-with-resources or close this "FileWriter" in a "finally" clause. 
#### - Issue Type : Bug
#### - Issue Severity : Blocker
#### - Issue Description : 
Leaving file open for an extended period it can lead to various issues like Resource leaks, Performance degradation , Data corruption etc or unexpected behavior that can be difficult to diagnose and fix.
i solve this issue by using the try-with-resources statement  then it is automatically closed when the try block is exited so the try-with-resources statement is  simplifies the code and eliminates the need for the finally block as following : 

![image](https://user-images.githubusercontent.com/114527203/225845072-eab93c5e-1094-4431-a495-058aa2eb181a.png)


 cause of try-with-resources statement it is automatically closed  no needed for writer.close() so i deleted it to became as following : 
 
![image](https://user-images.githubusercontent.com/114527203/225845358-d6e64401-e5fb-40ad-a894-5d528417a32c.png)

