<!--
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to you under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
-->
# Contributing to Baremaps

This document describes the way you can contribute to the Baremaps project.

## Ways to Help Out

If you plan to work on any code or add a new feature, start by opening an issue 
(or comment an existing issue). This way, duplicate work is prevented and we can discuss the ideas
and designs first.

There are several ways you can help us out. First of all code contributions and
bug fixes are most welcome. However, even "minor" details such as fixing spelling
errors, improving documentation and examples, or pointing out usability issues, are of great importance too.

You can contribute in the following ways.
- Improve the code: Report [a bug or submit a feature request](https://github.com/apache/incubator-baremaps/issues), or [submit a pull request](https://github.com/apache/incubator-baremaps/pulls) in the [incubator-baremaps](https://github.com/apache/incubator-baremaps/) repository.
- Improve the documentation: Report [documentation issue](https://github.com/apache/incubator-baremaps-site/issues) or [submit a pull request](https://github.com/apache/incubator-baremaps-site/pulls) in the [incubator-baremaps-site](https://github.com/apache/incubator-baremaps-site/) repository.

If you want to find an area that currently needs improving have a look at the
open issues listed. This is also the place for discussing enhancement to Baremaps.

If you are unsure what to do, please have a look at the issues, especially
those tagged
[minor complexity](https://github.com/baremaps/baremaps/labels/minor%20complexity).

## Reporting Bugs

You've found a bug? Thanks for letting us know so we can fix it! It is a good
idea to describe in detail how to reproduce the bug (when you know how), what
environment was used and so on. Please tell us at least the following things:

 * What's the version of Baremaps, Java and Postgis you used?
 * What commands did you execute to get to where the bug occurred?
 * What did you expect?
 * What happened instead?
 * Are you aware of a way to reproduce the bug?

Remember, the easier it is for us to reproduce the bug, the earlier it will be
corrected!

## Development Environment

Baremaps is developed and tested with Java 17, Maven 3.6, Postgres 13 and Postgis 3.

The repository contains several sets of directories with code:
The source code of Baremaps is organized in modules:

- `baremaps-benchmark` contains JMH benchmarks.
- `baremaps-cli` contains the command line interface.
- `baremaps-core` contains the core features.
- `baremaps-server` contains web resources.

The naming convention used for the modules is 'baremaps-{component}'.
Here, component corresponds to the api and the main implementation of a component.
If the component has multiple implementation or requires some specific dependencies, an additional module is created.

Knowing that, you should be able to checkout and build Baremaps from source, and start hacking:

```bash
git clone git@github.com:baremaps/baremaps.git
cd baremaps/
mvn install
``` 

The following commands can be used to run all the tests:

```bash
mvn test
``` 

Given a local and properly configured install of Postgres, the following command can be used to run the integration tests.

```bash
mvn install -P integration
```

## Providing Patches

You have fixed an annoying bug or have added a new feature? 
Let's get it into the project! 
To do so, follow guidelines described in the [GitHub Flow](https://guides.github.com/introduction/flow/),
a branch-based workflow that supports teams and projects.

Try to be concise and relevant in your [commit messages](https://chris.beams.io/posts/git-commit/)
If you change multiple different things that aren't related at all, try to
make several smaller commits. This is much easier to review.

Before making a pull request, check that your source code is formatted according to the rules defined in the `codestyle.xml` file. 
This file can typically used to [configure](https://www.jetbrains.com/help/idea/settings-code-style.html) your favourite IDE.
You can run `mvn spotless:apply` to format your code automatically.

Execute a run of Apache Rat to check for compliance with [ ASF Source Header and Copyright Notice Policy](https://www.apache.org/legal/src-headers.html). A textual report is added in `target/rat.txt`.
```
mvn clean && mvn apache-rat:check
```
Notes: 
 - It is required to do the `clean` separately in two commands given that the parent module scan submodules files.
 - At the moment the check is failing as project is in progress of reaching compliance, please check for newly added content in Apache Rat report.

Finally, verify that your contribution passes all the tests (integration included).
Once everything looks good, we'll merge it.

