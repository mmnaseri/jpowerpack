Reflection Tools
================

The tools collected here provide a means of manipulating the very beans themselves and even (at times)
change their nature without ever having to hardcode the explicit signature of the beans themselves.

The tools available via this module come into a number of different categories:

* **beans**: These are tools that relate to manipulation and reading of the Java&trade; beans themselves, including
ways to access their properties beyond that of using the actual contract to which these beans are bound.

* **compare**: These are tools that will help you compare two beans of the same type, gathering information
about the exact differences they have. This acts as a sort of `diff` tool for Java&trade; beans.

* **conversion**: Here are tools that will help you convert one bean from any arbitrary type into another,
target bean which is of a certain, specified descent. For instance, you can convert any Java&trade; bean
into a serializable bean, on the go, without ever having to first have them implement the `java.io.Serializable`
interface. This will come in handy for beans that are outside the scope of your coding access, e.g., those
provided to you as library classes and entities bundled in external JAR files.

* A number of general, everyday utilities are also available. These typically reside under "**tools**".

The packaging for this module starts at `com.agileapes.powerpack.reflection`.
