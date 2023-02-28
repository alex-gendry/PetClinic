pipeline {
    agent {
        label 'kubernetes-agent'
//        label 'builtin-agent'
    }

//    tools {
//        maven 'mvn-3.8'
//        //jdk 'jdk-8'
//    }

    stages {
        stage('fod') {
            steps {
                container('sast-client') {
//                    sh 'mvn dependency:tree -DoutputFile=src/.debricked-maven-dependencies.tgf -DoutputType=tgf'
//                    sh 'mvn dependency:copy-dependencies -DoutputDirectory=src/lib'
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
                            releaseName: "$GIT_BRANCH",
                            remediationScanPreferenceType: 'NonRemediationScanOnly',
                            scanCentral: '',
                            scanCentralBuildCommand: '',
                            scanCentralBuildFile: '',
                            scanCentralBuildToolVersion: '',
                            scanCentralIncludeTests: '',
                            scanCentralRequirementFile: '',
                            scanCentralSkipBuild: '',
                            scanCentralVirtualEnv: '',
                            sdlcStatus: '1',
                            srcLocation: 'src',
                            technologyStack: '7',
                            tenantId: '',
                            username: ''
                }
            }
        }
    }
}


