# CTL-model-checker
Full subject [here](https://moodle.myefrei.fr/pluginfile.php/221074/mod_resource/content/1/MiniProject_SCV21_22.pdf).

## Requirements

The aim of the project is to implement a CTL model checker. The target tool has two inputs:
1. A Kripke structure describing the behaviour of the system
2. A CTL formula

The output of the model checker is whether the formula is satisfied by the system or not.  
To achieve this task you have to solve the following issues:
1. Clearly determine the syntax of the inputs (the KS and the CTL formula)
2. Choose the appropriate data structures of the inputs
3. Implement the parsers that will check the correctness of the inputs and fill the corresponding data structures
4. Implement the algorithms corresponding to the different possible sub-formulae

You are free to choose the programming language to be used for the implementation of the model checker.

## Improvement

Some improvements can be considered through optional functionalities.
- Graphical interface: Allows to draw a graph (KS) and enter a CTL formula
- A random generator of KSs from a number of states and a set of atomic propositions. This allows to test the limits of your model checker when the size of the KS is big
- A random generator of CTL properties.
- A plugin allowing to generate the KS from a formal models such as Petri nets or state machines

## Project planning
1. Publication of project subject : January 3d, 2022
2. Project follow-up sessions : January 4th, 2022, January 12th, 2022
3. Project intermediary defense: January 19th, 2022 (SE2) and January 25th, 2022 (SE1)
4. Submission of code sources and report (on Moodle) : February 6th, 2022 at 11 :59 pm.

You will work in trinomial (at most) and you have to upload your work on Moodle as a compressed folder containing the following elements:
- A structured pdf document (around 10 pages) containing the description of your input formats, the chosen data structures and the design of the complementary verification algorithms. The document should also contain a discussion on the encountered difficulties, the possible improvements and a conclusion.
- The source code,
- A readme file explaining the technical points allowing the execution of your code,
- A sample folder containing some examples of systems and CTL formulae to be used to test your implementation.