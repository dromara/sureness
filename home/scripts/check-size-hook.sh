#!/bin/sh
#
# An example hook script to verify what is about to be committed.
# Called by "git commit" with no arguments.  The hook should
# exit with non-zero status after issuing an appropriate message if
# it wants to stop the commit.
#
# To enable this hook, rename this file to "pre-commit".

# Redirect output to stderr.
exec 1>&2

CURRENT_DIR="$(pwd)"
HAS_ERROR=""

for file in $(git diff --cached --name-only | sort | uniq); do
	file_size=$(du -m $CURRENT_DIR/$file | awk '{print $1}')
	if [ "$file_size" -ge 10 ]; then
		echo "$file is over 10MB."
		HAS_ERROR="1"
	fi
done

if [ "$HAS_ERROR" != "" ]; then
    echo "Can't commit, fix errors first." >&2
    exit 1
fi

exit 0
