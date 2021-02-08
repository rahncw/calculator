import requests

url = 'http://localhost:8083/sum?a=1&b=4'
response = requests.get(url)
value = int(response.text)
assert value == 5
