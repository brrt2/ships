#!/bin/bash

#
# Author: Piotr
# Since: 2017-12-28
#
#
# [Description]
#
# It runs mvn test, and basing on results it counts number of unit tests in project.
#

mvn test --log-file logFile
grep -A 2 Results logFile | grep Tests | sed 's/[^0-9 ]//g' > tempFile
echo -n "Tests run: "; awk '{ SUM += $1} END { print SUM }' tempFile
echo -n "Failures: "; awk '{ SUM += $2} END { print SUM }' tempFile
echo -n "Errors: "; awk '{ SUM += $3} END { print SUM }' tempFile
echo -n "Skipped: "; awk '{ SUM += $4} END { print SUM }' tempFile
rm logFile tempFile

