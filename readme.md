# lestrange

A playground for dark magic with Java class loaders. This is a work in progress. Currently uses reflection to invoke methods. 

This library is an attempt to understand how dynamic class loaders work and how in languages like Clojure, code is evaluated.

![Bellatrix](/utils/bella.png)


# Usage

Start the server with:
```bash
mvn clean install exec:java
```

Send a class file to be executed:
```bash
curl -XPOST "localhost:8080/loadclass/?classname=SystemInfo&methodname=info" -F "file=@SystemInfo.class"
curl -XPOST "localhost:8080/loadclass/?classname=RandomNumber&methodname=sayHello" -F "file=@RandomNumber.class"
```

# Generating class files
Ensure that clases are public and the method being accessed is public.
```bash
cd utils
javac RandomNumber.java
javac HelloClass.java
javac SystemInfo.java
```



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
