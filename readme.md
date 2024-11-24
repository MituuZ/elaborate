# Elaborate
Visualize your Java classes

![](resources/img.png "An example of an HTML table output using hashCode as the title method")

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

### Run the analysis
```java
elaborate.analyze(); // Analyze the class
```

## Output
By default, the class instances use the `toString` method as the title of the element.

For now, the output options are as follows:
- Print the results to the console
- Generate an HTML file with the results
- Generate an HTML file with the results formatted as a table

## ToDo
- [ ] Add some styling to the HTML output
- [ ] Publish to Maven Central
- [x] Use classes instead of strings to collect the data
- [ ] Allow specifying the output file path and name
- [ ] Add support for fields instead of only methods 
  - i.e. check for the default methods based on the field name
  - Record or getter methods
- [x] Add title configuration (which field/method to use as the element title)
- [ ] Add field configurations (stylize results based on values)
