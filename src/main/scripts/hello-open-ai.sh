#!/bin/bash
echo "Calling Open AI"
MY_OPENAI_KEY=""
PROMPT="Tell me a Dad Joke about computers?"

curl https://api.openai.com/v1/chat/completions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $MY_OPENAI_KEY" \
  -d '{
    "model": "gpt-3.5-turbo",
    "messages": [
      {"role": "user", "content": "'"${PROMPT}"'"}
    ]
  }'