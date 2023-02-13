pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                container('fortify-ci-tools') {
                    ls -al /usr/bin
                }
            }
        }
    }
}
