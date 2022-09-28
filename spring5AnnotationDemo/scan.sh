#!/bin/sh
set -x
rm -f *.log *.taint *.err *.out

if [ "$1" == "trans" ]; then
    sourceanalyzer -b mavenDemo -clean
    sourceanalyzer -b mavenDemo mvn clean package
fi
if [ "$1" == "debug" ]; then
    sourceanalyzer -b mavenDemo -verbose -debug -logfile scan.log -scan -f my_results.fpr -Djava.awt.headless=false -DRuleScriptDebugger=true -DScriptsToDebug="wi_get_inf" # 2> scan.err 1> scan.out
fi
sourceanalyzer -b mavenDemo -verbose -debug -logfile scan.log -scan -f my_results.fpr  # 2> scan.err 1> scan.out
