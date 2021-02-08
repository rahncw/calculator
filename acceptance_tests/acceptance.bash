#!/bin/bash

# TODO - make this in python

test $(curl localhost:8675/sum?a=1\&b=2) = 3