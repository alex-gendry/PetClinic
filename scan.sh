#!/bin/sh

set -x

rm -f *.log *.taint *.err *.out

sourceanalyzer -b test -clean
sourceanalyzer -b test -verbose -debug -logfile trans.log -cp "petclinic-3.0/lib/*.jar" petclinic-3.0/src 
if [ "$1" == "debug" ]; then
    sourceanalyzer -b test -verbose -debug -logfile scan.log -scan -f petclinic.fpr -analyzers dataflow -fexec -Djava.awt.headless=false -DRuleScriptDebugger=true -DScriptsToDebug="wi_get_info" -Dcom.fortify.sca.rules.enable_wi_correlation # 2> scan.err 1> scan.out
else
    sourceanalyzer -b test -verbose -debug -logfile scan.log -scan -f petclinic.fpr -analyzers dataflow -Dcom.fortify.sca.rules.enable_wi_correlation # 2> scan.err 1> scan.out
fi
