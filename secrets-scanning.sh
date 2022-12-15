#!/bin/bash

sourceanalyzer -b pre-commit -clean
sourceanalyzer -b pre-commit *
OUTPUT=$(sourceanalyzer -b pre-commit -scan -analyzers configuration)


if [[ "$OUTPUT" == *"Password Management : Password"* ]] ; then
  exit 1
fi

exit 0
