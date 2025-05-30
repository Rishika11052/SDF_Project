Overview

This project implements an Arbitrary-Precision Arithmetic Library in Java that supports both integers and floating-point numbers. The library provides functionality for performing mathematical operations such as addition, subtraction, multiplication, and division on very large numbers, which are beyond the limits of primitive data types.
Features

    Support for Arbitrary-Precision Integers: Allows performing operations on integers with arbitrary size.

    Support for Arbitrary-Precision Floating-Point Numbers: Performs operations on floating-point numbers with a variable level of precision.

    Operations Supported:

        Addition

        Subtraction

        Multiplication

        Division

    Modular Design: The library is divided into separate components (e.g., AInteger, AFloat) for better modularity and ease of maintenance.

    This project implements the arithmetic from scratch, without relying on BigInteger or BigDecimal.

Prerequisites



    Java 11+

    Maven (for building and testing the project)

    Git (for cloning the repository)

Clone the Repository

To get a local copy of the project, run the following command in your terminal:

```bash
git clone https://github.com/Rishika11052/SDF_Project.git

```

To build using Maven
```bash
mvn clean install

```
To run using Maven
```bashbash
mvn exec:java -Dexec.mainClass="MyInfArith.MyInfArith" -Dexec.args="'type' 'operation' num1 num2 "
```
type is int or float
operation is add/sub/mul/div

To run using the python script

First compile
```bash
python3 compile_and_run.py compile
```

Then run:
```bash
python3 compile_and_run.py run type operation num1 num2
```

