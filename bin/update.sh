#!/usr/bin/env bash
set -euo pipefail

PROJECT_ROOT=`git rev-parse --show-toplevel`

cd $PROJECT_ROOT
rm -rf resources/leiningen/new/sweet_tooth_app
cp -r ../minimal resources/leiningen/new/sweet_tooth_app
cd resources/leiningen/new/sweet_tooth_app

rm -rf .git package-lock.json
rm -rf `cat .gitignore`
mv src/{minimal,app}

sed -i "" 's/sweet-tooth\/minimal/{{raw-name}}/' project.clj
find ./ -type f -exec sed -i "" 's/minimal/{{ns}}/g' "{}" \;

cd $PROJECT_ROOT
lein run
