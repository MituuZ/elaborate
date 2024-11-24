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
elaborate.addInstances(List.of("Hell", "Orld")); // Add the class instances to analyze
elaborate.addAnalyzeMethods("toString", "toLowerCase", "length"); // Add the methods to analyze
elaborate.printMethodNames(true); // Print the method names in the HTML file
elaborate.analyze(); // Analyze the class
```

This setup will print the following content:
```
toString: Hell
toLowerCase: hell
length: 4
toString: Orld
toLowerCase: orld
length: 4
```
