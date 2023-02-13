pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                container('fortify-ci-tools') {
                    sh "ls -al /usr/bin"
                }
            }
        }
    }
}
