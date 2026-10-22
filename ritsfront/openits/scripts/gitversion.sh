#!/bin/bash
#git log -n 1 > ../src/main/resources/git.version
git log --pretty=format:'%h %d' --abbrev-commit -n 1 > ../src/main/resources/git.version
echo '' >> ../src/main/resources/git.version
git log --pretty=format:'%an <%ae>' -n 1 >> ../src/main/resources/git.version
echo '' >> ../src/main/resources/git.version
git log --pretty=format:'%ci' -n 1 >> ../src/main/resources/git.version
echo '' >> ../src/main/resources/git.version
git log --pretty=format:'%s' --abbrev-commit -n 1 >> ../src/main/resources/git.version
echo '' >> ../src/main/resources/git.version

