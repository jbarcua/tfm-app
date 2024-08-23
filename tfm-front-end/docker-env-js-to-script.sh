#!/bin/bash
set -euo pipefail

#echo $FRONT_ENDPOINT

# Capture all environment variables starting with APP_ and make JSON string from them
ENV_JSON="$(jq --compact-output --null-input 'env | with_entries(select(.key | startswith("APP_")))')"

# Escape sed replacement's special characters: \, &, /.
# No need to escape newlines, because --compact-output already removed them.
# Inside of JSON strings newlines are already escaped.
ENV_JSON_ESCAPED="$(printf "%s" "${ENV_JSON}" | sed -e 's/[\&/]/\\&/g')"

find ./src -name "index.html" -exec sed -i "s/<noscript id=\"env-insertion-point\"><\/noscript>/<script>var env=${ENV_JSON_ESCAPED}; if (global === undefined) { var global = window;}<\/script>/g" '{}' ';'

exec "$@"
