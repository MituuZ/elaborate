# Elaborate
Visualize your Java classes

## Usage
### Create a new Elaborate instance with the class that you want to analyze
```java
Elaborate<CustomClass> elaborate = new Elaborate<>();
```

### Set Elaborate parameters
Configure your Elaborate instance to output the desired information

```java
Elaborate<String> elaborate = new Elaborate<>();
elaborate.generateHtml(false); // Generate HTML file
elaborate.addInstances(List.of("Hello", "Orld")); // Add the class instances to analyze
elaborate.addAnalyzeMethods("toString", "toLowerCase", "length"); // Add the methods to analyze
elaborate.printMethodNames(true); // Print the method names in the HTML file
elaborate.analyze(); // Analyze the class
```

This setup will print the following content:
```
toString: Hello
toLowerCase: hello
length: 5
toString: Orld
toLowerCase: orld
length: 4
```

## ToDo
- [ ] Allow specifying the output file path and name
- [ ] Add support for fields instead of only methods 
  - i.e. check for the default methods based on the field name
  - Record or getter methods
