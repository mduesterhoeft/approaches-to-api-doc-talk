#!/usr/bin/env bash
git reset --hard origin/master
git checkout openapi-documented
./gradlew clean
