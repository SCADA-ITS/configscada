#!/bin/bash

# paths
base=$1
file=/src/main/resources/git.version

# git data
commit=$(git log --pretty=format:'%h %d' --abbrev-commit -n 1)
author=$(git log --pretty=format:'%an <%ae>' -n 1)
date=$(git log --pretty=format:'%ci' -n 1)
message=$(git log --pretty=format:'%s' -n 1)

echo "Generating $base$file ..."
	
echo "$commit" > "$base$file"
echo "$author" >> "$base$file"
echo "$date" >> "$base$file"
echo "$message" >> "$base$file"
