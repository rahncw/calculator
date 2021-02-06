pipeline {
    agent any
    stages {
    //Don't need checkout stage when Jenkinsfile is provided by repo itself
//         stage('Checkout') {
//             steps {
//                 git url: 'https://github.com/rahncw/calculator.git',
//                 branch: 'main'
//             }
//         }
        stage('Compile') {
            steps {
                sh './gradlew compileJava'
            }
        }
        stage('UnitTest') {
            steps {
                sh './gradlew test'
            }
        }
        stage('TestCoverage') {
            steps {
                sh './gradlew test jacocoTestReport'
                sh './gradlew test jacocoTestCoverageVerification'
            }
        }
    }
}
