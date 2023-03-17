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
   the issue is that the copy, paste, cut, move and changed  fields are declared as public, which means they can be accessed and modified from anywhere in the code, breaking the encapsulation principle. SonarLint recommend to modify the declaration of the public fields to static final constant or non public and provide accessor methods to improve encapsulation and maintainability.By following this recommendation, the code becomes easier to understand, test, and modify, while reducing the risk of errors and bugs, ultimately improving software quality.
   So i declare copy, paste, cut , tp , menu and changed as static final Based on constant convention in Java.
   and delete move cause no usage for it 

### 6.extract the nested try block into seperate method 
### Issue Type : Code smell
### Issye Severity : Major
### Issue Description : 
   The issue is that we have a nested try block in the editor file so i created a new method called readFile() which takes a File object as input and returns the contents of the file as a String so the code bacame more modular and easier to understand.
   and this improve the quality of the code by making it more modular, readable, and maintainable.

### 7. Define constant instead of duplicating 
### 7.1 Define constant instead of duplicating copy 
### 7.2 Define constant instead of duplicating paste
### 7.3 Define constant instead of duplicating cut
### 7.4 Define constant instead of duplicating tp
### 7.5 Define constant instead of duplicating menu
### Issue Type : Code Smell 
### Issue Severity : Critical 
### Issue Description : 
The issue is that we have to use the constant  " PASTE_ITEM", " COPY_ITEM " , " CUT_ITEM " , "MENU" , "TP "  wherever we need to use the  string  in the editor file .
and this improve the quality of the code by improving readability, consistency, scalability, and maintainability.
### 8. Add a default case to this switch.
### Issue Type : Code Smell 
### Issue Severity : Critical 
### 9. A "NullPointerException" could be thrown: "writer" is nullable here.
### Issue Type : Bug
### Issue Severity : Major
### 9.1
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
private void saveAs(String dialogTitle) {
        dialogTitle = dialogTitle.toUpperCase();
        JFileChooser dialog = new JFileChooser(System.getProperty("user.home"));
        dialog.setDialogTitle(dialogTitle);
        int result = dialog.showSaveDialog(this);
        if (result != 0)//0 value if approve (yes, ok) is chosen.
            return;
        file = dialog.getSelectedFile();
        PrintWriter writer = getWriter(file);
        if (writer!=null){
        writer.write(textPanel.getText());
        }
        changeStatus = false;
        setTitle("Editor - " + file.getName());
    }
```
* There is a problem with the getWriter method, which returns null if an exception is thrown during PrintWriter creation. If writer is null, a NullPointerException will be thrown when trying to call the write method on it at the writer line.write(textPanel.getText());.
To fix this, you can modify the saveAs method to handle the case where the writer is null. One way to do this is to add an if statement to check if the writer is null, and if so, return from the method without writing anything to the file.
In the modified method, if the writer is not null, it will try to write to the file as before. However, we've added a try-catch-final block to catch any IO exceptions that might occur while writing to the file, and to close the writer regardless of whether the write was successful or not.
In the else block, we handle the case where the writer is empty by displaying an error message to the user. You can customize this error message to suit your needs.
### 10.Rename this method name (BuildMenu) to match regular expression '[a-z][a-zA-Z0-9]*$'.
### Issue Type : code smell
### Issue Severity : Minor
### 11. Use static access with "javax.swing.WindowConstants" for "EXIT_ON_CLOSE".
### Issue Type : Code Smell 
### Issue Severity : Critical 

### 12. 
```agsl
super("Editor"); 
```
to 
```agsl
setTitle("Simple Editor");
```
* I changed super("Editor"); to setTitle("Simple Editor"); because super("Editor"); sets the title of the frame to "Editor", but in the context of this program, it would be more appropriate to set the title to "Simple Editor" to better reflect the purpose of the program.

* In Swing, JFrame is a subclass of Frame and Frame has a setTitle method that sets the title of the frame. So, instead of calling the constructor of JFrame with a string argument to set the title, I used the setTitle method to set the title of the frame. This is a more direct and explicit way of setting the title of the frame, and it also makes it clear what the title of the frame will be.

