#!/usr/bin/env bash
set -euo pipefail

mvn -Dsuite=${TEST_SUITE} test
