#! /usr/bin/env groovy
//shebang above for correct IDE syntax

// To lint this file:
// ssh -p 1234 crahn@172.17.0.2 declarative-linter < Jenkinsfile

pipeline {
  agent any
  options {
    timestamps()
  }
  // TODO - try below - see https://www.jenkins.io/doc/book/pipeline/docker/#caching-data-for-containers
//  agent {
//    docker {
//      image 'my-docker-build-slave-image'
//      args '-v $HOME/some/dir:/root/some/dir'
//    }
//  }
  // to build the agent dynamically from a dockerfile defined in the repo
//  agent {
//    dockerfile true
//  }
  triggers {
    pollSCM('* * * * *')
  }
  environment {
    IMAGE_NAME = "rahncw/calculator:${env.BUILD_ID}"
    CONTAINER_NAME = "calculator-${env.BUILD_ID}"
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
    // TODO - consider replacing double quotes with single quotes so that environment variable
    //  replacement occurs at the shell level rather than groovy level e.g.:
    // "docker build -t ${env.IMAGE_NAME} ..." => 'docker build -t ${IMAGE_NAME}' - supposedly this
    // may be best practice - NOTE: confirmed that this works using replay build
    // TODO - should we use environment variables or parameters?
    stage('DockerBuild') {
      steps {
        sh "docker build -t ${env.IMAGE_NAME} -f docker_images/ubuntu_calc/Dockerfile ."
//             sh 'docker build -t localhost:5000/calculator -f docker_images/ubuntu_calc/Dockerfile .'
      }
    }
    stage('DockerPush') {
      steps {
        withDockerRegistry([credentialsId: 'dockerhub', url: '']) {
          sh "docker push ${env.IMAGE_NAME}"
        }
//             sh 'docker push localhost:5000/calculator'
      }
    }
    stage('DeployToStaging') {
      steps {
        sh "docker run -d --rm -p 8083:8083 --name ${env.CONTAINER_NAME} ${env.IMAGE_NAME}"
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
      sh "docker stop ${env.CONTAINER_NAME}"
    }
  }
}
