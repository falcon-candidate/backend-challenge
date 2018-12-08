#!/usr/bin/env bash

payload=${1}
echo posting ${payload}
curl --header "Content-Type: application/json" \
  --request POST \
  --data \{\"content\":\"${payload}\",\"timestamp\":\"2018-10-09\ 00:12:12+0100\"\} \
  http://localhost:8080/palindrome
