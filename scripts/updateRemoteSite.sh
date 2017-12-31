#!/bin/bash

#
# Author: Sandor
#
# Since: 2017-12-31
#
# TODO: add extra info
#

mkdir ../../tempUpdateRemoteSite
echo "Clonning first repo [from].."
./clone.sh ../../tempUpdateRemoteSite/from
echo "Clonning repo [to].."
./clone.sh ../../tempUpdateRemoteSite/to
echo "Checking out to master branch on [from].."
cd ../../tempUpdateRemoteSite/from
git checkout master -q
echo "Checking out to gh-pages branch on [to].."
cd ../to
git checkout gh-pages -q
echo "Generating JaCoCo datafiles on [from].."
cd ../from
mvn clean install -q
echo "Generating site on [from].."
mvn site -q
echo "Staging site on [from].."
mvn site:stage -q
echo "Removing all files on [to].."
cd ../to
rm -rf *
echo "Copying staged site from [from] to [to].."
cd ..
cp from/target/staging/. to/. -r
echo "Adding.. Committing.. Pushing (your credentials will be required).."
cd to
git add .
git commit -m 'Trigger site update' -q
git push origin gh-pages
echo "Done updating remote site. Cleaning up.."
cd ../..
rm -rf tempUpdateRemoteSite
echo "Done cleaning."
