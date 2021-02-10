#! /usr/bin/env groovy
//shebang above for correct IDE syntax

pipeline {
  agent any
  triggers {
    pollSCM('* * * * *')
  }
  environment {
    imageName = "rahncw/calculator:${env.BUILD_ID}"
    containerName = "calculator-${env.BUILD_ID}"
  }
  //set version to currentBuild.number or currentBuild.id
  stages {
//    Don't need checkout stage when Jenkinsfile is provided by repo itself
//    stage('Checkout') {
//      steps {
//        git url: 'https://github.com/rahncw/calculator.git',
//                branch: 'main'
//      }
//    }
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
        publishHTML(target: [
                reportDir  : 'build/reports/jacoco/test/html',
                reportFiles: 'index.html',
                reportName : 'JaCoCo Test Coverage Report'
        ])
        sh './gradlew test jacocoTestCoverageVerification'
      }
    }
    stage('StaticCodeAnalysis') {
      steps {
        sh './gradlew checkstyleMain'
        publishHTML(target: [
                reportDir  : 'build/reports/checkstyle',
                reportFiles: 'main.html',
                reportName : 'checkstyle Static Analysis Report'
        ])
      }
    }
    // build the jar file
    stage('Package') {
      steps {
        sh './gradlew build'
      }
    }
    // build the docker image
    // TODO - consider use the docker pipeline syntax for the docker actions -
    // see https://stackoverflow.com/questions/54446001/docker-push-in-jenkins-denied-requested-access-to-the-resource-is-denied
    stage('DockerBuild') {
      steps {
        sh "docker build -t ${env.imageName} -f docker_images/ubuntu_calc/Dockerfile ."
//             sh 'docker build -t localhost:5000/calculator -f docker_images/ubuntu_calc/Dockerfile .'
      }
    }
    stage('DockerPush') {
      steps {
        withCredentials([usernamePassword(credentialsId: 'dockerhub')]) {
          sh "docker push ${env.imageName}"
        }
//             sh 'docker push localhost:5000/calculator'
      }
    }
    stage('DeployToStaging') {
      steps {
        sh "docker run -d --rm -p 8083:8083 --name ${env.containerName} ${env.imageName}"
//             sh 'docker run -d --rm -p 8083:8083 --name calculator localhost:5000/calculator'
      }
    }
    stage('AcceptanceTest') {
      steps {
        sleep 10
        sh 'python3 acceptance_tests/acceptance.py'
      }
    }
  }
  post {
    always {
      sh "docker stop ${env.containerName}"
    }
  }
}
