pipeline {
    agent {
//        label 'kubernetes-agent'
        label 'builtin-agent'
    }

    tools {
        CustomTool 'scancentral-22.2.0'
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
//            environment {
//                JAVA_HOME = "${tool 'jdk1.8'}"
//                SCANCENTRAL_JAVA_HOME = "${tool 'jdk1.8'}"
//                PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
//            }

            steps {

//                container('sast-client') {
//                    sh 'env'
//                sh 'mvn --version'
                bat 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
//                sh 'whereis scancentral'
//                sh 'whereis java'

                fodStaticAssessment applicationName: 'PetClinic [AG]',
                                    applicationType: '1',
                                    assessmentType: '274',
                                    attributes: '',
                                    auditPreference: '2',
                                    bsiToken: '',
                                    businessCriticality: '1',
                                    entitlementId: '11948',
                                    entitlementPreference: '',
                                    frequencyId: '2',
                                    inProgressBuildResultType: 'FailBuild',
                                    inProgressScanActionType: 'Queue',
                                    isMicroservice: false,
                                    languageLevel: '12',
                                    microserviceName: '',
                                    openSourceScan: 'true',
                                    overrideGlobalConfig: false,
                                    owner: 59565,
                                    personalAccessToken: '',
                                    releaseId: '',
                                    releaseName: "$GIT_BRANCH" ,
                                    remediationScanPreferenceType: 'RemediationScanIfAvailable',
                                    scanCentral: 'Maven',
                                    scanCentralBuildCommand: 'package',
                                    scanCentralBuildFile: 'pom.xml',
                                    scanCentralBuildToolVersion: '',
                                    scanCentralIncludeTests: '',
                                    scanCentralRequirementFile: '',
                                    scanCentralSkipBuild: '',
                                    scanCentralVirtualEnv: '',
                                    sdlcStatus: '1',
                                    srcLocation: '.',
                                    technologyStack: '7',
                                    tenantId: '',
                                    username: ''
//                }
            }
        }
    }
}

