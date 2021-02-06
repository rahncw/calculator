pipeline {
    agent any
    triggers {
      pollSCM('* * * * * *')
    }
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
                publishHTML (target: [
                    reportDir: 'build/reports/jacoco/test/html',
                    reportFiles: 'index.html',
                    reportName: 'JaCoCo Test Coverage Report'
                ])
                sh './gradlew test jacocoTestCoverageVerification'
            }
        }
        stage('StaticCodeAnalysis') {
          steps {
            sh './gradlew checkstyleMain'
            publishHTML (target: [
                reportDir: 'build/reports/checkstyle',
                reportFiles: 'main.html',
                reportName: 'checkstyle Static Analysis Report'
            ])

          }
        }
    }
}
