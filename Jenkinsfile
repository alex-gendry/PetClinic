pipeline {
    agent {
//        label 'kubernetes-agent'
        label 'builtin-agent'
    }

    stages {
        stage('fod') {
            steps {
                bat 'mvn dependency:tree -DoutputFile=.debricked-maven-dependencies.tgf -DoutputType=tgf'

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

