del *.log *.taint *.err *.out

:parse
IF "%~1"=="" GOTO debugScan
IF "%~1"=="trans" GOTO translate
IF "%~1"=="scan" GOTO scan
:endParse

:translate
sourceanalyzer -b test -clean
sourceanalyzer -b test -verbose -debug -cp "petclinic-3.0/lib/*.jar" petclinic-3.0/src 
:endTranslate

:scan
@REM NOTE: needs default rules!
sourceanalyzer -b test -verbose -debug -logfile scan.log -scan -rules test_rules.xml -f my_results.fpr -analyzers dataflow
GOTO end
:endScan

:debugScan
@REM With Debugging
sourceanalyzer -b test -verbose -debug -logfile scan.log -scan -rules test_rules.xml -f my_results.fpr -analyzers dataflow -Djava.awt.headless=false -DRuleScriptDebugger=true -DScriptsToDebug="get_info_for_WI"
:end
