JPowerPack
==========

A Java power pack of utilities that will most likely come in handy. This is intended to be a modular design, letting developers choose exactly what components they need.

Modules
-------

Currently, there are a number of modules devised for this project:

* **reflection**; will help you handle your reflection needs. This is not &mdash; in any way &mdash; an attempt to replicate what
[Javassist][1] does.  Rather, it is designed to help you with more high-level tasks, such as wrapping beans of unknown
types, comparing two or more beans, accessing properties within fields in any given bean, etc.

* **tools**; will contain more of round-the-block sort of utilities that will come in handy throughout your development
life.

* **string-tools**; will contain tools concerning String modification, processing, and manipulation.

* **tree**; contains tools to help create, manipulate, and work with various tree data structures.

IDE and Tools
-------------

This project has been developed in [IntelliJ IDEA][2] &trade; and thus, there are references to its special files
under [.gitignore](.gitignore).

[1]: <http://www.javassist.org/> (The Javassist Project Home)
[2]: <http://www.jetbrains.com/idea> (The IntelliJ IDEA Home)