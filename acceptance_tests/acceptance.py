#!/usr/bin/python3
import requests


# NOTE - the hardcoded URL below is the URL of the localhost and 8083 is the host side port
# mapping for the calculator server.  We cannot use "localhost" since that would map to the docker
# container itself and we need to go "out" to the docker host machine
# TODO - figure out docker network to handle the URL better
def get_url(a, b, func):
    #below only works in python 3.6+
    # return f'http://192.168.2.35:8083/{func}?a={a}&b={b}'
    return 'http://192.168.2.35:8083/%s?a=%d&b=%d' % (func, a, b)


sum_response = requests.get(get_url(2, 7, 'sum'))
value = int(sum_response.text)
assert value == 9

diff_response = requests.get(get_url(10, 7, 'diff'))
value = int(diff_response.text)
assert value == 3
