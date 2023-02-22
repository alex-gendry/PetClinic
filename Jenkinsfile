pipeline {
    agent any

    environment {
        JAVA_HOME='/opt/java/openjdk'
    }

    stages {
//        stage('dependencies') {
//            steps {
//                container('fortify-ci-tools') {
//                    sh 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
//                }
//            }
//        }
        stage('fod') {
            steps {
                container('fortify-ci-tools') {
                    sh 'mvn --version'
                    sh 'ls -al /opt/Fortify/ScanCentral/bin'
                    sh 'env'
                    sh 'echo $JOB_BASE_NAME'
                    sh 'env'
                    fortifyRemoteAnalysis remoteAnalysisProjectType: fortifyMaven(buildFile: 'pom.xml'),
                            uploadSSC: [appName: "$JOB_BASE_NAME", appVersion: "$GIT_BRANCH"]
                }
            }
        }
    }
}

