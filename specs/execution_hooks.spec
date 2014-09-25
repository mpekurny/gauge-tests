Execution Hooks
===============

Execution hooks can be used to run arbitrary test code as different levels during the test suite execution.

* In an empty directory initialize a project with the current language

* Create "Testing Hooks scenario 1" in "01 Hooks Spec" with the following steps
     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |First scenario step     |"inside first step"                                     |


* Create "Testing Hooks scenario 2" in "01 Hooks Spec" with the following steps
     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |Second scenario step    |"inside second step"                                    |

* Create "Testing Hooks scenario new" in "02 Hooks Spec2" with the following steps
     |step text               |implementation                                          |
     |------------------------|--------------------------------------------------------|
     |First scenario new step |"inside new first step"                                 |


Suite and Spec Level Hooks
-------------------------
* Create "suite" level "before" hook with implementation "inside before suite hook"
* Create "suite" level "after" hook with implementation "inside after suite hook"
* Create "spec" level "before" hook with implementation "inside before spec hook"
* Create "spec" level "after" hook with implementation "inside after spec hook"
* Execute the current project and ensure success
* Console should contain following lines in order
       |console output           |
       |-------------------------|
       |inside before suite hook |
       |inside before spec hook  |
       |inside first step        |
       |inside second step       |
       |inside after spec hook   |
       |inside before spec hook  |
       |inside new first step    |
       |inside after spec hook   |
       |inside after suite hook  |

Scenario and step level hooks
-----------------------------

* Create "scenario" level "before" hook with implementation "inside before scenario hook"
* Create "scenario" level "after" hook with implementation "inside after scenario hook"
* Create "step" level "before" hook with implementation "inside before step hook"
* Create "step" level "after" hook with implementation "inside after step hook"
* Execute the spec "01 Hooks Spec" and ensure success
* Console should contain following lines in order
       |console output               |
       |-----------------------------|
       |inside before scenario hook  |
       |inside before step hook      |
       |inside first step            |
       |inside after step hook       |
       |inside after scenario hook   |
       |inside before scenario hook  |
       |inside before step hook      |
       |inside second step           |
       |inside after step hook       |
       |inside after scenario hook   |




