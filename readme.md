# Elaborate
Visualize your Java classes

Elaborate requires Java 11 or higher.

![](resources/img.png "An example of an HTML table output using hashCode as the title method")

## Installation
For now, only local installations are supported. In the future, I'm hoping to publish it to Maven Central.

### Publishing to Maven Local
By publishing the jar file to Maven Local, you can use it as a dependency in your projects.

```shell
git clone git@github.com:MituuZ/elaborate.git
cd elaborate
./gradlew publishToMavenLocal
```

#### Adding a local/remote dependency
This requires adding the local Maven repository to your project's repositories.
```kotlin
repositories {
    mavenLocal()
}
```

```kotlin
dependencies {
    implementation("com.mituuz:elaborate:<version>")
}
```

### Using a jar file
You can alternatively use the jar file directly in your project.

```shell
git clone git@github.com:MituuZ/elaborate.git
cd elaborate
./gradlew jar
```

#### Adding a jar file dependency
For gradle, just add the jar file to your project's dependencies, and you're good to go.

```kotlin
dependencies {
    implementation(files("path/to/elaborate.jar"))
}
```

## Usage
### Create a new Elaborate instance with the class that you want to analyze
```java
Elaborate<CustomClass> elaborate = new Elaborate<>();
```

### Set Elaborate parameters
Configure your Elaborate instance to output the desired information

Filtering can be done using conditional methods.

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
- [x] Allow disabling stdout printing
- [ ] Allow renaming title method in HTML table
- [ ] Create a builder class
- [x] Change boolean parameters to sets with default values and no parameters
- [ ] Generate unique names for files
- [ ] Inline default css
  - Update the picture on readme
- [ ] Add support for custom css
  - Try to load it after inlining the default css
- [ ] Check that the css is copied correctly when using the jar file
  - Is not
- [x] Add some styling to the HTML output
- [x] Add filtering
- [ ] Add sorting
- [ ] Publish to Maven Central
- [x] Use classes instead of strings to collect the data
- [ ] Allow specifying the output file path and name
- [ ] Add support for fields instead of only methods 
  - i.e. check for the default methods based on the field name
  - Record or getter methods
- [ ] Add support for known methods
  - e.g. `size`, `isBlank`
- [x] Add title configuration (which field/method to use as the element title)
- [ ] Add field configurations (stylize results based on values)

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
