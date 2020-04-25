# lestrange

A playground for dark magic with Java class loaders. This is a work in
progress. There are two implmentations to call methods on dynamically
loaded classes currently:
- Using reflection to call a method by name on a class file  passed in
  an multi-part HTTP POST request
- Using the interface `Callable` and invoking the `call` method on the
  object created from the class file passed

This library is an attempt to understand how dynamic class loaders
work and how in languages like Clojure, code is evaluated.

![Bellatrix](/utils/bella.png)


# Usage

Start the server with:
```bash
mvn clean install exec:java
```

Send a class file to be executed via reflection:
```bash
curl -XPOST "localhost:8080/loadclass/?classname=SystemInfo&methodname=info" -F "file=@SystemInfo.class"
curl -XPOST "localhost:8080/loadclass/?classname=RandomNumber&methodname=sayHello" -F "file=@RandomNumber.class"
```

To run the `call` method on a `Callable` class:
```bash
curl -XPOST "localhost:8080/callable/?classname=CallableAPI" -F "file=@CallableAPI.class"
  _              _
 | |            | |
 | |     ___ ___| |_ _ __ __ _ _ __   __ _  ___
 | |    / _ \ __| __| '__/ _` | '_ \ / _` |/ _ \
 | |____  __\__ \ |_| | | (_| | | | | (_| |  __/
 |______\___|___/\__|_|  \__,_|_| |_|\__, |\___|
                                      __/ |
                                     |___/
```

# Generating class files
Ensure that clases are public and the method being accessed is public.
```bash
cd utils
javac RandomNumber.java
javac HelloClass.java
javac SystemInfo.java
javac CallableAPI.java
```

# Notes

## Is it okay to create a new class loader every time?
We are creating a new classloader for any class passed to the
/loadclass route. We do this because if we don't, then it is
impossible to reload a class that is already loaded.

This will not cause a memory leak because we are not holding on to any
object of the classes, which therefore can be garbage collected, and
once that happens, no object will reference the classloader, which can
also be garbage collected. [Recall](https://www.dynatrace.com/resources/ebooks/javabook/class-loader-issues/) that:

> A classloader will be removed by the garbage collector only if nothing else refers to it. All classes hold a reference to their classloader and all objects hold references to their classes.


This can be demonstrated using:
```shell
MAVEN_OPTS="-XX:+PrintGC -Xmx20m -XX:+HeapDumpOnOutOfMemoryError-XX:+CrashOnOutOfMemoryError" mvn exec:java
```

```shell
while true;
  do
      curl -XPOST "localhost:8080/loadclass/?classname=SystemInfo&methodname=info" -F "file=@SystemInfo.class";
      curl -XPOST "localhost:8080/loadclass/?classname=HelloClass&methodname=sayHello" -F "file=@HelloClass.class";
      curl -XPOST "localhost:8080/loadclass/?classname=RandomNumber&methodname=sayHello" -F "file=@RandomNumber.class";
      curl -XPOST "localhost:8080/callable/?classname=CallableAPI" -F "file=@CallableAPI.class";
  done
```

The GC logs will show metaspace cleanup of the form:
```shell
[GC (Metadata GC Threshold)  14670K->12241K(18944K), 0.0041918 secs]
[Full GC (Metadata GC Threshold)  12241K->8766K(18944K), 0.0548479 secs]
[Full GC (Ergonomics)  13400K->7388K(16896K), 0.0567170 secs]
```

Investigating further, we can check the memory performance in Visual
VM. Also, let us stop firing requests and then force a GC via VisualVM
and take a heap dump to confirm none of the classloaders we created on
the fly are remaining.

![classloaders](/utils/screenshot_class_loader_objects.jpg)
![classes](/utils/screenshot_classes_loaded.jpg)
![heap](/utils/screenshot_heapspace.jpg)
![meta](/utils/screenshot_metaspace.jpg)



## License

Copyright © 2020 Mourjo Sen

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
