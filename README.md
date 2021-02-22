# bytecode-lib
A library to parse Java class files

This library started out as a test project, in order to read and understand the
contents of Java class files — without having to rely on other people's work. As
time passed, it became obvious that the functionality merited its own library,
and this is it.

What differentiates this library from others —apart from the fact that writing
class files is currently not under consideration— is that it is intended to present
the bytecode that you need; for example, for a method invocation, or a lambda
expression.

Also, it tries to take a functional approach as much as possible: not because FP would
be somehow inherently superior, but because certain things just work so much better
when using a functional style.
