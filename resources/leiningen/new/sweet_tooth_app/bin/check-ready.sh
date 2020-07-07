#!/usr/bin/env bash
set -euo pipefail

RED=`tput setaf 1`
GREEN=`tput setaf 2`
YELLOW=`tput setaf 3`
RESET=`tput sgr0`
BOLD=`tput bold`

function assert-installed {
  if [ `which $1 -s` ]
  then
    echo "${GREEN}✔ $1 installed${RESET}"
  else
    echo "${RED}${BOLD}✗ $1 not installed${RESET} install with: $2"
  fi
}

assert-installed "npm" "brew install node"
assert-installed "shadow-cljs" "npm install -g shadow-cljs"

# function report-opt-install {
#   if [ `which $1 -s` ]
#   then
#     echo "${GREEN}✔ $1 installed${RESET}"
#   else
#     echo "${YELLOW}${BOLD}✗ (optional) $1 not installed${RESET} install with: $2"
#   fi
# }
#
# report-opt-install "tmux" "brew install tmux"
# report-opt-install "tmuxinator" "gem install tmuxinator (requires ruby)"
