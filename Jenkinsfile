pipeline {
    agent any

    stages {
        stage('dependencies') {
            steps {
                container('fortify-ci-tools') {
                    sh 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'
                }
            }
        }
        stage('fod') {
            steps {
                container('fortify-ci-tools') {
                    sh 'mvn --version'
                    sh 'ls -al /opt/Fortify/ScanCentral/bin'
                    sh 'ls -al /opt/bitnami/java'
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
                }
            }
        }
    }
}

