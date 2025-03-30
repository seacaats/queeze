/**
 * The game modes for the game include:
 * - Easy
 * - Normal
 * - Hard
 * Each mode extends the Base class and provides their own collection of values
 */

public class GameModes {

    public static class EasyMode extends Game.Base {
        /**
         * Initializes easy mode with predefined questions and answers
         * Derived from Information Technology and Computer Science concepts
         *
         * @param username - Player's username
         */
        public EasyMode(String username) {
            super(username);
            this.lives = initialLives();
            this.questions = new String[] {
                    "Which statement can also be used to jump out of a loop?",
                    "Which keyword is used to create an object?",
                    "Which of these is a relational database management system?",
                    "Who developed the Python Programming Language?",
                    "What does \"URL\" stand for?",
                    "What is the smallest unit of digital data?",
                    "What is the output of \"apple.length()\"?",
                    "What does \"CPU\" stand for?",
                    "Which of these is an example of an operating system?",
                    "What is the correct way to write a comment in Python?",
                    "What is the output of System.out.print(2 + 3 * 2)?",
                    "Who is the father of computer?",
                    "Who made the Electronic Numerical Integrator And Computer (ENIAC)?",
                    "What is the keyword for defining a class in Java?",
                    "Which data type is used for whole numbers?"
            };

            this.options = new String[][] {
                    {"break", "next", "return", "stop"},
                    {"new", "create", "object", "instance"},
                    {"MySQL", "MongoDB", "Redis", "Cassandra"},
                    {"Bill Gates", "Linus Torvalds", "Guido van Rossum", "Steve Jobs"},
                    {"Universal Resource Locator", "Uniform Resource Locator", "Unified Resource Locator", "Universal Resource Locator"},
                    {"Byte", "Bit", "Kilobyte", "Megabit"},
                    {"5", "4", "6", "e"},
                    {"Central Processing Unit", "Computer Power Unit", "Core Processing Unit", "Central Performance Unit"},
                    {"Microsoft Word", "Google Chrome", "Windows 11", "Adobe Photoshop"},
                    {"/* text */", "// text", "# text", "<!-- text -->"},
                    {"10", "8", "12", "7"},
                    {"Charles Babbage", "Alan Turing", "Joseph Marie Jacquard", "Herman Hollerith"},
                    {"Alan Turing", "Konrad Zuse", "John Mauchly & J. Presper Eckert", "Bill Gates & Steve Jobs"},
                    {"function", "define","class", "method"},
                    {"float", "String", "int", "boolean"}


            };

            this.correctAnswers = new String[] {"break", "new", "MySQL", "Guido van Rossum", "Uniform Resource Locator", "Bit",
                                                 "5", "Central Processing Unit", "Windows 11", "# text", "8", "Charles Babbage",
                                                 "John Mauchly & J. Presper Eckert", "class", "int"};
        }

        @Override
        protected int initialLives() {
            return 3;
        }

        @Override

        protected String getDifficulty() {
            return "Easy";
        }

    }

    public static class NormalMode extends Game.Base {
        /**
         * Initializes normal mode with predefined questions and answers
         * Derived from Information Technology and Computer Science concepts
         *
         * @param username - Player's username
         */
        public NormalMode(String username) {
            super(username);
            this.lives = initialLives();
            this.questions = new String[] {
                    "Which of these is a version control system?",
                    "What does API stand for?",
                    "Which of these is not a type of cyber attack?",
                    "Which of these is a NoSQL database?",
                    "Which command is used to check network connectivity in Windows?",
                    "What does the super() keyword do in a Java constructor?",
                    "<html>What is the output of this Java code? <br>" +
                        "String s1 = new String(\"Hello\"); <br>" +
                        "String s2 = \"Hello\"; <br>" +
                        "System.out.println(s1 ==s2)</html>",
                    "In Python, what is the purpose of __init__?",
                    "What does \"CMS\" stand for in web development?",
                    "What is the primary purpose of polymorphism?",
                    "What is the purpose of a cache in computing?",
                    "What does the \"this\" keyword refer to in Java?",
                    "Which of these is a private IP address range?",
                    "What is the purpose of try-catch blocks?",
                    "Which of these is not a common cloud computing provider?"
            };

            this.options = new String[][] {
                    {"Docker", "Kubernates", "Git", "Jenkins"},
                    {"Automated Programming Interface", "Application Programming Interface", "Advanced Protocol Integration", "Application Process Integration"},
                    {"Phishing", "Spoofing", "Defragmenting", "DDoS"},
                    {"PostgresSQL", "MongoDB", "SQLite", "Oracle"},
                    {"ipconfig", "ping", "netstat", "tracert"},
                    {"Call the parent class constructor", "Refer to the current object", "Create a new superclass instance", "Stops inheritance"},
                    {"true", "false", "Error", "Hello"},
                    {"To initialize a class' attributes", "To terminate an object", "To import modules", "To handle errors"},
                    {"Content Management System", "Computer Monitoring Service", "Centralized Media Storage", "Customer Management Software"},
                    {"To restrict access", "To create multiple instances of a class", "To allow a method to operate on different data types", "To hasten code execution"},
                    {"Long-term data storage", "Temporary storage for frequently accessed data", "Internet connection sharing", "Virus protection"},
                    {"The current class object", "The parent class object", "The global object", "The method being executed"},
                    {"192.168.1.1", "8.8.8.8", "172.217.0.0", "200.100.50.25"},
                    {"To handle exceptions", "To loop through code", "To define functions", "To optimize performance"},
                    {"AWS", "Azure", "Google Cloud", "Oracle"}
            };

            this.correctAnswers = new String[] {"Git", "Application Programming Interface", "Defragmenting", "MongoDB", "ping",
                                                "Calls the parent class constructor", "Error", "To initialize a class' attributes",
                                                "Content Management System", "To allow a method to operate on different data types",
                                                "Temporary storage for frequently accessed data", "The current class object", "192.168.1.1",
                                                "To handle exceptions", "Oracle"};
        }

        @Override
        protected int initialLives() {
            return 2;
        }

        @Override
        protected String getDifficulty() {
            return "Normal";
        }


    }

    public static class HardMode extends Game.Base {
        public HardMode(String username) {
            /**
             * Initializes hard mode with predefined questions and answers
             * Derived from Information Technology and Computer Science concepts
             *
             * @param username - Player's username
             */
            super(username);
            this.lives = initialLives();
            this.questions = new String[]{
                    "What is the time complexity of a binary search?",
                    "Which data structure uses LIFO (Last In First Out)?",
                    "What is the primary advantage of NVMe over SATA for SSDs?",
                    "What is \"Shannon's\" entropy in information theory?",
                    "Which design pattern ensures only one instance of a class?",
                    "What is garbage collection in programming?",
                    "Which of these is not a design pattern?",
                    "Which of these is a homomorphic encryption technique?",
                    "What causes \"pipelining stalls\" in CPUs?",
                    "What is the primary risk of manual memory management in C/C++ compared to garbage-collected languages?",
                    "What does a \"blue screen\" error typically indicate in Windows?",
                    "Which scheduling algorithm can lead to starvation?",
                    "Which technology enables decentralized digital ledgers?",
                    "What sorting algorithm has the worst-case time complexity of O(n²)?",
                    "Why do GPUs excel at deep learning?"
            };

            this.options = new String[][]{
                    {"O(1)", "O(log n)", "O(n)", "0(n^2)"},
                    {"Queue", "Stack", "Array", "LinkedList"},
                    {"Lower power consumption", "Higher maximum throughput", "Compatibility with older systems", "Larger storage capacity"},
                    {"Measure of randomness in data", "Type of compression algorithm", "Network routing protocol", "Cryptographic key exchange method"},
                    {"Singleton", "Factory", "Observer", "Builder"},
                    {"Automatic memory management", "A cybersecurity technique", "A database optimization method", "A type of sorting algorithm"},
                    {"Singleton", "Observer", "Prototype", "Compiler"},
                    {"Computing on encrypted data", "Symmetric encryption", "A VPN tunneling protocol", "A blockchain hashing method"},
                    {"Cache misses", "Branch mispredictions", "Disk I/O latency", "GPU Overheating"},
                    {"Memory leaks", "Slower allocation", "Type errors", "Larger binaries"},
                    {"A kernel-level crash", "A virus infection", "Insufficient RAM", "Hard drive failure"},
                    {"Round Robin", "Shortest Job First", "First Come First Serve", "Multilevel Feedback Queue"},
                    {"Cloud Computing", "Blockchain", "Virtual Reality", "Quantum Computing"},
                    {"Merge Sort", "Quick Sort", "Bubble Sort", "Heap Sort"},
                    {"Massive parallelism for matrices", "Higher clock speeds", "More precise math", "Better branching"},
            };

            this.correctAnswers = new String[] {"O(log n)", "Stack", "Higher maximum throughput", "Measure of randomness in data",
                                                "Singleton", "Automatic memory management", "Compiler", "Computing on encrypted data",
                                                "Branch mispredictions", "Memory leaks", "A kernel-level crash", "Shortest Job First",
                                                "Blockchain", "Bubble Sort", "Massive parallelism for matrices"};
        }

        @Override
        protected int initialLives() {
            return 1;
        }

        @Override
        protected String getDifficulty() {
            return "Hard";
        }
    }
}