#!/usr/bin/env bash
set -euo pipefail

cd `git rev-parse --show-toplevel`
rm -rf resources/leiningen/new/sweet_tooth_app
cp -r ../minimal resources/leiningen/new/sweet_tooth_app
cd resources/leiningen/new/sweet_tooth_app

rm -rf node_modules .git frontend-target package-lock.json target
mv src/{minimal,app}

sed -i "" 's/sweet-tooth\/minimal/{{name}}/' project.clj
find ./ -type f -exec sed -i "" 's/minimal/{{ns}}/g' "{}" \;
