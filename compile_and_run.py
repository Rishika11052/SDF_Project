import sys, os

class CodeRunner:

    def __init__(self):
        # Get the current script directory
        scriptDirectory = os.path.dirname(os.path.abspath(__file__))
        
        # Define paths for the JAR and source file locations
        self.jarPath = os.path.abspath(os.path.join(scriptDirectory, "target", "arbitrary-arithmetic-1.0-SNAPSHOT.jar"))
        self.srcPath = os.path.abspath(os.path.join(scriptDirectory, "src","main","java"))
        self.javaFilePath = os.path.join(self.srcPath, "MyInfArith")
    
    def compile(self):
        """Compile all Java files in src/main/java recursively."""
        print(f"Compiling all Java files in {self.jarPath}...")

        compile_command = (
            f'javac -cp ".:{self.jarPath}" '
            f'"{self.javaFilePath}/MyInfArith.java"'
        )
        os.system(compile_command)

    def run(self, mode, operator, operand1, operand2):
        """Run the compiled Java program with the specified arguments."""
        
        print(f"Running: java -cp '.:{self.jarPath}' '${self.javaFilePath}.MyInfArith' {mode} {operator} {operand1} {operand2}")
        os.system(f'java -cp ".:{self.jarPath}" "MyInfArith.MyInfArith" {mode} {operator} {operand1} {operand2}')


def main():
    """Main entry point for the script to compile or run the Java program."""
    args = sys.argv
    codeRunner = CodeRunner()

    if len(args) < 2:
        print("Usage: python compile_and_run.py <compile|run> <int|float> <add|sub|mul|div> <operand1> <operand2>")
        sys.exit(1)

    if args[1] == "compile":
        # Compile the Java code
        codeRunner.compile()

    elif args[1] == "run":
        # Validate the correct number of arguments for running
        if len(args) != 6:
            print("Usage: python compile_and_run.py run <int|float> <add|sub|mul|div> <operand1> <operand2>")
            sys.exit(1)
        
        # Run the program with provided arguments
        codeRunner.run(args[2], args[3], args[4], args[5])

    else:
        # Handle invalid command
        print("Invalid command. Usage: python compile_and_run.py <compile|run> <int|float> <operator> <operand1> <operand2>")
        sys.exit(1)


if __name__ == "__main__":
    main()
