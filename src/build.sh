#!/bin/sh
clear

ARR=(orgJson.jar swingx.jar)
LIB=.

for str in ${ARR[@]}; do 
  LIB=$LIB":../external/"$str
done

set -xe
find . -name "*.class" | xargs rm 
javac -cp $LIB ./main/Main.java
java -cp $LIB main/Main

