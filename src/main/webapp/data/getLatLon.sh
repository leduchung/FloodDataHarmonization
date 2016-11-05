#!/bin/bash

dataDir=./spartacus

for file in `ls $dataDir`
do
  position=`gdalinfo $dataDir/$file | grep Center | cut -d "(" -f2 | cut -d ")" -f1`
  echo $file -- $position
done
