#!/usr/bin/env python

import argparse
import urllib.request
import json

if __name__ == "__main__":
  parser = argparse.ArgumentParser()

  parser.add_argument("-c", "--content", type=str,
                      help="content to search palindrome in",
                      default="abracadabra")

  parser.add_argument("-t", "--timestamp", type=str,
                      help="timestamp to include in query",
                      default="2018-10-09 00:12:12+0100")

  args = parser.parse_args()
  body = {"content": args.content, "timestamp": args.timestamp}
  target_url = "http://localhost:8080/palindrome"
  req = urllib.request.Request(target_url)
  req.add_header("Content-Type", "application/json; charset=utf-8")
  json_data = json.dumps(body)
  json_data_encoded = json_data.encode("utf-8")
  req.add_header("Content-Length", len(json_data_encoded))
  try:
    response = urllib.request.urlopen(req, json_data_encoded)
  except:
    print("Error posting")
  else:
    print("Success")
