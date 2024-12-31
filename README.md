# Textilator

A Java-based command-line text processing utility that provides powerful text transformation capabilities. Textilator allows users to manipulate text files through various operations including case conversion, character shifting, line filtering, and ASCII encoding.

## Requirements

- Java 17 or higher
- Maven 3.6+ for building


## Usage

The general command syntax is:

```bash
java -jar textilator.jar [options] FILE
```

### Options

- `-c case`: Convert text case
  - `upper`: Convert to uppercase
  - `lower`: Convert to lowercase
- `-e num`: Shift characters by specified amount (-25 to 25)
- `-s number`: Skip lines based on parity
  - `0`: Process even-numbered lines
  - `1`: Process odd-numbered lines
- `-x substring`: Exclude lines containing the substring
- `-a`: Convert characters to ASCII values
- `-p prefix`: Add prefix to each line


## Testing

Run the test suite using Maven:

```bash
mvn test
```

The project includes comprehensive unit tests covering:
- Input validation
- Text transformations
- Error handling
- Edge cases
- Command-line argument processing


