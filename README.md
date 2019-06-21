# 8085-Emulator-Debugger

8085-Emulator-Debugger is a Java based GUI software . It is a software which can be used to run and debug 8085 programs. GUI is developed by using JAVA-FX.

# Some Features!

  - Tracking Rgisters Values.
  - Tracking Program counter
  - Load address of your choice. 


You can also:
  - Load any .asm file and run and debug it.
  - Or write your own code in Code Area.
  - Track execution of Instruction

# Limitations!!!

For now this emulator only supports operands like::
  - MOV (all)
  - MVI (all)
  - SUB (all)
  - LDA
  - JM
  - CALL
  - STA
  - RET
  - HLT

### Requriments

* [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) 
* [Gson-2.8.5](https://search.maven.org/remotecontent?filepath=com/google/code/gson/gson/2.8.5/gson-2.8.5.jar)

Java 1 to Java 7 and Java 11 is not supported. Java 9 and Java 10 not tested.

# How To run

This project is made using [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows). 

#### IntelliJ IDEA
    - Simply download the sorce code and Import in IntelliJ IDEA.
    - Make sure your project contains correct path of Gson library.
    
#### Others
    -Simply copy [src] ans [res] folder to your project.
    



### Development

Want to contribute? Great!
Simply fork this repository and start committing for other operands of 8085.






### Todos

 - Code for more Operands.
 - Implementing stack.
 - Coding for stack pointer.

## ScreenShots

![Alt text](/ScreenShots/1.png "1.png" | 24*48)
![Alt text](/ScreenShots/2.png "2.png")
![Alt text](/ScreenShots/3.png "3.png")

